"""
  Author: Udita Bose
  Module: File system calls for CS 6233 File System.   
  This file system is adapted from Prof. Cappos's filesystem
  keeping with the constraints and specifications of HW3
  for CS6233 (Spring, 2014) course.
  
  General file system structure
    ------------------------------------------------
    superBlock = linddata.0
        contains a dictionary, mentioned in HW3 document
        
        superBlock = {'creationTime': 1376483073,   mounted: 50, 'devId':20,
        freeStart:1, freeEnd:25, root:26, maxBlocks:10000}
  
    free block list = linddata.1 to linddata.25
        each contains an list of size 400
        values are eiather 1 = used block
                       or  0 = free block
        for blocks 0-26, blocks are always marked as 1
    file type = regular or directory
    directory inode = linddata.x
        contains a dictionary, mentioned in HW3 document
        must contain linkcount and filename_to_inode_dict entries
        
        Directory metadata - 
        {'size':1033, 'uid':1000, 'gid':1000, 'mode':16877, 'atime':1323630836, 
        'ctime':1323630836, 'mtime':1323630836, 'linkcount':4, 
        'filename_to_inode_dict':  {'foo':1234, '.':102, '..':10, 'bar':2245}}
        
        Root directory metadata -
        {'size':0, 'uid':DEFAULT_UID, 'gid':DEFAULT_GID, 'mode':S_IFDIR | S_IRWXA,
        'atime':1323630836, 'ctime':1323630836, 'mtime':1323630836, 'linkcount':2, 
        'filename_to_inode_dict': {.:rootDirectoryInode,..:rootDirectoryInode}}
        
    file inode = linddata.x
        contains a dictionary, mentioned in HW3 document
        must contain location and indirect entries
        indirect = 0, 
            linddata.<location> = data block of size 4096
        indirect = 1
            linddata.<location> = index list, max index number = 400 
    -------------------------------------------------------------------  
  
  Start Date: May 2nd, 2014
"""

import time

# Root inode
ROOTDIRECTORYINODE = 26

# Super block metadata
METADATAFILENAME = 'linddata.0'

# Block metadata
FILEDATAPREFIX = 'linddata.'

BLOCKSIZE = 4096

filesystemmetadata = {}

blockmetadata = {}

# A lock that prevents inconsistencies in metadata
filesystemmetadatalock = createlock()

# fast lookup table...   
fastinodelookuptable = {}

# contains open file descriptor information... (keyed by fd)
filedescriptortable = {}

# I use this so that I can assign to a global string (currentworkingdirectory)
# without using global, which is blocked by RepyV2
fs_calls_context = {}

# Where we currently are at...

fs_calls_context['currentworkingdirectory'] = '/'

SILENT=False

def warning(*msg):
  if not SILENT:
    for part in msg:
      print part,
    print


# This is raised to return an error...
class SyscallError(Exception):
  """A system call had an error"""


# This is raised if part of a call is not implemented
class UnimplementedError(Exception):
  """A call was called with arguments that are not fully implemented"""


def _load_lower_handle_stubs():
  """The lower file hadles need stubs in the descriptor talbe."""

  filedescriptortable[0] = {'position':0, 'inode':0, 'lock':createlock(), 'flags':O_RDWRFLAGS, 'note':'this is a stub1'}
  filedescriptortable[1] = {'position':0, 'inode':1, 'lock':createlock(), 'flags':O_RDWRFLAGS, 'note':'this is a stub2'}
  filedescriptortable[2] = {'position':0, 'inode':2, 'lock':createlock(), 'flags':O_RDWRFLAGS, 'note':'this is a stub3'}


def load_fs(name=METADATAFILENAME):
  """ Help to correcly load a filesystem, if one exists, otherwise
  make a new empty one.  To do this, check if metadata exists.
  If it doesnt, call _blank_fs_init, if it DOES exist call restore_metadata

  This is the best entry point for programs loading the file subsystem.
  """
  try:
    # lets see if the metadata file is already here?
    f = openfile(name, False)
  except FileNotFoundError, e:
    warning("Note: No filesystem found, building a fresh one.")
    _blank_fs_init()
  else :  
    f.close()
    try:
      restore_metadata(name)
      mounted = filesystemmetadata['mounted']
      mounted = mounted + 1
      filesystemmetadata['mounted'] = mounted

    except (IndexError, KeyError), e:
      print "Error: Cannot reload filesystem.  Run lind_fsck for details."
      print e
      return
  _load_lower_handle_stubs()


# This function is never called as not creating the /dev and
# character device files
def load_fs_special_files():
  """ If called adds special files in standard locations.
  Specifically /dev/null, /dev/urandom and /dev/random
  """
  try: 
    mkdir_syscall("/dev", S_IRWXA)
  except SyscallError as e:
    warning( "making /dev failed. Skipping",str(e))

  # load /dev/null
  try:
    mknod_syscall("/dev/null", S_IFCHR, (1,3))
  except SyscallError as e:
    warning("making /dev/null failed. Skipping", str(e))

  # load /dev/urandom
  try:
    mknod_syscall("/dev/urandom", S_IFCHR, (1,9))
  except SyscallError as e:
    warning("making /dev/urandcom failed. Skipping",str(e))

  # load /dev/random
  try:
    mknod_syscall("/dev/random", S_IFCHR, (1,8))
  except SyscallError as e:
    warning("making /dev/random failed. Skipping", str(e))


# To have a simple, blank file system, simply run this block of code.
# 
def _blank_fs_init():

  # kill all left over data files...
  # metadata will be killed on persist.
  for filename in listfiles():
    if filename.startswith(FILEDATAPREFIX):
      removefile(filename)

  # Setting up super block
  currentTime = int(time.time())
  filesystemmetadata['creationTime'] = str(currentTime)
  filesystemmetadata['mounted'] = 1
  filesystemmetadata['devId'] = 20
  filesystemmetadata['freeStart'] = 1
  filesystemmetadata['freeEnd'] = 25
  filesystemmetadata['root'] = ROOTDIRECTORYINODE
  filesystemmetadata['maxBlocks'] = 10000
    
  fastinodelookuptable['/'] = ROOTDIRECTORYINODE
 
  # it makes no sense this wasn't done before...
  persist_metadata(METADATAFILENAME, filesystemmetadata)

  log(" creating free blocks list ")
  _free_block_list()
  log(" creating root directory ")
  _root_directory()
  log(" creating 10,000 blocks ")
  _blank_block()
  

# Creates and persists free block list
def _free_block_list():
  counter = filesystemmetadata['freeStart']
  
  # the first of the free block metadata will contain from 27 to 400
  freeBlockMetadata = FILEDATAPREFIX + str(counter)
  freeList = [1] * 27 + [0] * 373;
  persist_metadata(freeBlockMetadata, freeList)
  blockmetadata[counter] = freeList
  counter = counter + 1
  
  # Rest of the free blocks list initially are marked as free
  while(counter <= filesystemmetadata['freeEnd']) :
    freeBlockMetadata = FILEDATAPREFIX + str(counter)
    freeList = [0] * 400;
    persist_metadata(freeBlockMetadata, freeList)
    blockmetadata[counter] = freeList
    counter = counter + 1
    
# Setting up root directory
def _root_directory() :

  currentTime = int(time.time())
  rootDirMetadata = {}
  rootDirMetadata['size'] = 0
  rootDirMetadata['uid'] = DEFAULT_UID
  rootDirMetadata['gid'] = DEFAULT_GID
  rootDirMetadata['mode'] = S_IFDIR | S_IRWXA
  rootDirMetadata['atime'] = currentTime
  rootDirMetadata['ctime'] = currentTime
  rootDirMetadata['mtime'] = currentTime
  rootDirMetadata['linkcount'] = 2
  rootDirMetadata['filename_to_inode_dict'] = {}
  rootDirMetadata['filename_to_inode_dict']['.'] = ROOTDIRECTORYINODE
  rootDirMetadata['filename_to_inode_dict']['..'] = ROOTDIRECTORYINODE

  rootDir = FILEDATAPREFIX + str(filesystemmetadata['root'])
  blockmetadata[filesystemmetadata['root']] = rootDirMetadata
  persist_metadata(rootDir, rootDirMetadata)

# Blank block file
def _blank_block() :
  counter = ROOTDIRECTORYINODE + 1
  while(counter < filesystemmetadata['maxBlocks']) :
    metadataFile = FILEDATAPREFIX + str(counter)
    persist_metadata(metadataFile, '')
    counter = counter + 1

# These are used to initialize and stop the system
def persist_metadata(metadatafilename, dataMap={}):

  metadatastring = serializedata(dataMap)

  metadatafo = openfile(metadatafilename,True)
  metadatafo.writeat(metadatastring,0)
  metadatafo.close()



def restore_metadata(metadatafilename):
  # should only be called with a fresh system...
  assert(filesystemmetadata == {})

  # open the file and write out the information...
  metadatafo = openfile(metadatafilename,True)
  metadatastring = metadatafo.readat(None, 0)
  metadatafo.close()

  # get the dict we want
  desiredmetadata = deserializedata(metadatastring)

  # I need to put things in the dict, but it's not a global...   so instead
  # add them one at a time.   It should be empty to start with
  for item in desiredmetadata:
    filesystemmetadata[item] = desiredmetadata[item]

  # I need to rebuild the fastinodelookuptable. let's do this!
  _rebuild_fastinodelookuptable()



# I'm already added.
def _recursive_rebuild_fastinodelookuptable_helper(path, inode):
  
  # for each entry in my table...
  for entryname,entryinode in blockmetadata[inode]['filename_to_inode_dict'].iteritems():
    
    # if it's . or .. skip it.
    if entryname == '.' or entryname == '..':
      continue

    # always add it...
    entrypurepathname = _get_absolute_path(path+'/'+entryname)
    fastinodelookuptable[entrypurepathname] = entryinode

    # and recurse if a directory...
    if 'filename_to_inode_dict' in blockmetadata[entryinode]:
      _recursive_rebuild_fastinodelookuptable_helper(entrypurepathname,entryinode)
    

def _rebuild_fastinodelookuptable():
  # first, empty it...
  for item in fastinodelookuptable:
    del fastinodelookuptable[item]

  # now let's go through and add items...
  
  # I need to add the root.   
  fastinodelookuptable['/'] = ROOTDIRECTORYINODE
  # let's recursively do the rest...
  
  # TODO : figureout this table later
  # _recursive_rebuild_fastinodelookuptable_helper('/', ROOTDIRECTORYINODE)


######################   Generic Helper functions   #########################



# private helper function that converts a relative path or a path with things
# like foo/../bar to a normal path.
def _get_absolute_path(path):
  
  # should raise an ENOENT error...
  if path == '':
    return path

  # If it's a relative path, prepend the CWD...
  if path[0] != '/':
    path = fs_calls_context['currentworkingdirectory'] + '/' + path


  # now I'll split on '/'.   This gives a list like: ['','foo','bar'] for 
  # '/foo/bar'
  pathlist = path.split('/')

  # let's remove the leading ''
  assert(pathlist[0] == '')
  pathlist = pathlist[1:]

  # Now, let's remove any '.' entries...
  while True:
    try:
      pathlist.remove('.')
    except ValueError:
      break

  # Also remove any '' entries...
  while True:
    try:
      pathlist.remove('')
    except ValueError:
      break

  # NOTE: This makes '/foo/bar/' -> '/foo/bar'.   I think this is okay.
  
  # for a '..' entry, remove the previous entry (if one exists).   This will
  # only work if we go left to right.
  position = 0
  while position < len(pathlist):
    if pathlist[position] == '..':
      # if there is a parent, remove it and this entry.  
      if position > 0:
        del pathlist[position]
        del pathlist[position-1]

        # go back one position and continue...
        position = position -1
        continue

      else:
        # I'm at the beginning.   Remove this, but no need to adjust position
        del pathlist[position]
        continue

    else:
      # it's a normal entry...   move along...
      position = position + 1


  # now let's join the pathlist!
  return '/'+'/'.join(pathlist)


# private helper function
def _get_absolute_parent_path(path):
  return _get_absolute_path(path+'/..')
  

# private helper function
def _get_next_inode():
    """
    Searches the free block list to find first free block
    """
    counter = 1
    while(counter <= filesystemmetadata['freeEnd']) :
        if(0 in blockmetadata[counter]) :
            idx = blockmetadata[counter].index(0)
            return (counter - 1) * 400 + idx
        counter += 1
    raise SyscallError("*_syscall","EBADF","Ran out of memory.")
        

# private helper function
def _set_consumed_inode(inode):
    """
    Set the inode value in free block list as 1
    """
    block_list_idx = int(inode/400)
    in_idx = inode - (block_list_idx * 400)
    log(str(inode) + "-" + str(block_list_idx) + "-" + str(in_idx))
    blockmetadata[block_list_idx + 1][in_idx] = 1
    persist_metadata(FILEDATAPREFIX + str(block_list_idx + 1), blockmetadata[block_list_idx + 1])

# private helper function
def _set_freed_inode(inode):
    """
    Set the inode value in free block list as 0
    """
    block_list_idx = int(inode/400)
    in_idx = inode - (block_list_idx * 400)
    log(str(inode) + "-" + str(block_list_idx) + "-" + str(in_idx))
    blockmetadata[block_list_idx + 1][in_idx] = 0
    persist_metadata(FILEDATAPREFIX + str(block_list_idx + 1), blockmetadata[block_list_idx + 1])
    

#################### The actual system calls...   #############################


##### FSTATFS  #####


# return statfs data for fstatfs and statfs
def _istatfs_helper(inode):
  """
  """

  # I need to compute the amount of disk available / used
  limits, usage, stoptimes = getresources()

  # I'm going to fake large parts of this.   
  myfsdata = {}

  myfsdata['f_type'] = 0xBEEFC0DE   # unassigned.   New to us...

  myfsdata['f_bsize'] = BLOCKSIZE        # Match the repy V2 block size

  myfsdata['f_blocks'] = int(limits['diskused']) / BLOCKSIZE   

  myfsdata['f_bfree'] = (int(limits['diskused']-usage['diskused'])) / BLOCKSIZE  
  # same as above...
  myfsdata['f_bavail'] = (int(limits['diskused']-usage['diskused'])) / BLOCKSIZE  

  # file nodes...   I think this is infinite...
  myfsdata['f_files'] = 1024*1024*1024

  # free file nodes...   I think this is also infinite...
  myfsdata['f_files'] = 1024*1024*512

  myfsdata['f_fsid'] = filesystemmetadata['devId']

  # we don't really have a limit, but let's say 254
  myfsdata['f_namelen'] = 254

  # same as blocksize...
  myfsdata['f_frsize'] = BLOCKSIZE 
  
  # it's supposed to be 5 bytes...   Let's try null characters...
  #CM: should be 8 bytes by my calc
  myfsdata['f_spare'] = '\x00'*8


  return myfsdata


def fstatfs_syscall(fd):
  """ 
    http://linux.die.net/man/2/fstatfs
  """
  print "fstatfs_syscall"
  # is the file descriptor valid?
  if fd not in filedescriptortable:
    raise SyscallError("fstatfs_syscall","EBADF","The file descriptor is invalid.")

  
  # if so, return the information...
  return _istatfs_helper(filedescriptortable[fd]['inode'])



##### STATFS  #####


def statfs_syscall(path):
  """ 
    http://linux.die.net/man/2/statfs
  """
  print "statfs_syscall"
  # in an abundance of caution, I'll grab a lock...
  filesystemmetadatalock.acquire(True)

  # ... but always release it...
  try:
    truepath = _get_absolute_path(path)

    # is the path there?
    if truepath not in fastinodelookuptable:
      raise SyscallError("statfs_syscall","ENOENT","The path does not exist.")

    thisinode = fastinodelookuptable[truepath]
      
    return _istatfs_helper(thisinode)

  finally:
    filesystemmetadatalock.release()





##### ACCESS  #####

def access_syscall(path, amode):
  """
    See: http://linux.die.net/man/2/access
  """

  print "access_syscall"
  # lock to prevent things from changing while we look this up...
  filesystemmetadatalock.acquire(True)

  # ... but always release the lock
  try:

    # get the actual name.   Remove things like '../foo'
    truepath = _get_absolute_path(path)

    if truepath not in fastinodelookuptable:
      raise SyscallError("access_syscall","ENOENT","A directory in the path does not exist or file not found.")

    # BUG: This code should really walk the directories instead of using this 
    # table...   This will have to be fixed for symlinks to work.
    thisinode = fastinodelookuptable[truepath]

    # if all of the bits for this file are set as requested, then indicate
    # success (return 0)
    if blockmetadata[thisinode]['mode'] & amode == amode:
      return 0

    raise SyscallError("access_syscall","EACESS","The requested access is denied.")

  finally:
    persist_metadata(FILEDATAPREFIX + str(thisinode), blockmetadata[thisinode])
    # release the lock
    filesystemmetadatalock.release()



##### CHDIR  #####


def chdir_syscall(path):
  """ 
    http://linux.die.net/man/2/chdir
  """
  print "chdir_syscall"
  print "chdir : ", path
  # Note: I don't think I need locking here.   I don't modify any state and 
  # only check the fs state once...

  # get the actual name.   Remove things like '../foo'
  truepath = _get_absolute_path(path)

  # If it doesn't exist...
  if truepath not in fastinodelookuptable:
    raise SyscallError("chdir_syscall","ENOENT","A directory in the path does not exist.")

  # let's update and return success (0)
  fs_calls_context['currentworkingdirectory'] = truepath
  
  print fs_calls_context['currentworkingdirectory']

  return 0




##### MKDIR  #####

def mkdir_syscall(path, mode):
  """ 
    http://linux.die.net/man/2/mkdir
  """
  print "smkdir_syscall"
  # lock to prevent things from changing while we look this up...
  filesystemmetadatalock.acquire(True)

  # ... but always release it...
  try:
    log(" Creating directory : " + path)
    if path == '':
      raise SyscallError("mkdir_syscall","ENOENT","Path does not exist.")

    truepath = _get_absolute_path(path)
    # log(truepath)
    # is the path there?
    if truepath in fastinodelookuptable:
      raise SyscallError("mkdir_syscall","EEXIST","The path exists.")

      
    # okay, it doesn't exist (great!).   Does it's parent exist and is it a 
    # dir?
    trueparentpath = _get_absolute_parent_path(path)
    #log(trueparentpath)

    if trueparentpath not in fastinodelookuptable:
      raise SyscallError("mkdir_syscall","ENOENT","Path does not exist.")

    parentinode = fastinodelookuptable[trueparentpath]
    log(parentinode)
    if not IS_DIR(blockmetadata[parentinode]['mode']):
      raise SyscallError("mkdir_syscall","ENOTDIR","Path's parent is not a directory.")

    assert(mode & S_IRWXA == mode)

    # okay, great!!!   We're ready to go!   Let's make the new directory...
    dirname = truepath.split('/')[-1]

    # first, get the new inode
    newinode = _get_next_inode()

    currentTime = int(time.time())
    newinodeentry = {'size':0, 'uid':DEFAULT_UID, 'gid':DEFAULT_GID, 
            'mode':mode | S_IFDIR,  # DIR+rwxr-xr-x
            'atime':currentTime, 'ctime':currentTime, 'mtime':currentTime,
            'linkcount':2,    # the number of dir entries...
            'filename_to_inode_dict': {'.':newinode, '..':parentinode}}
    
    # ... and put it in the table..
    blockmetadata[newinode] = newinodeentry   
    blockmetadata[parentinode]['linkcount'] += 1
    blockmetadata[parentinode]['filename_to_inode_dict'][dirname] = newinode

    # set this inode as used in free blocks list
    _set_consumed_inode(newinode)

    # finally, update the fastinodelookuptable and return success!!!
    fastinodelookuptable[truepath] = newinode
    
    return 0

  finally:
    persist_metadata(FILEDATAPREFIX + str(newinode), blockmetadata[newinode])
    persist_metadata(FILEDATAPREFIX + str(parentinode), blockmetadata[parentinode])
    filesystemmetadatalock.release()




##### RMDIR  #####

def rmdir_syscall(path):
  """ 
    http://linux.die.net/man/2/rmdir
  """
  print "rmdir_syscall"
  # lock to prevent things from changing while we look this up...
  filesystemmetadatalock.acquire(True)

  # ... but always release it...
  try:
    truepath = _get_absolute_path(path)

    # Is it the root?
    if truepath == '/':
      raise SyscallError("rmdir_syscall","EINVAL","Cannot remove the root directory.")
      
    # is the path there?
    if truepath not in fastinodelookuptable:
      raise SyscallError("rmdir_syscall","EEXIST","The path does not exist.")

    thisinode = fastinodelookuptable[truepath]
      
    # okay, is it a directory?
    if not IS_DIR(blockmetadata[thisinode]['mode']):
      raise SyscallError("rmdir_syscall","ENOTDIR","Path is not a directory.")

    # Is it empty?
    if blockmetadata[thisinode]['linkcount'] > 2:
      raise SyscallError("rmdir_syscall","ENOTEMPTY","Path is not empty.")

    trueparentpath = _get_absolute_parent_path(path)
    parentinode = fastinodelookuptable[trueparentpath]

    # remove the entry from the inode table...
    del blockmetadata[thisinode]

    # We're ready to go!   Let's clean up the file entry
    dirname = truepath.split('/')[-1]
    # remove the entry from the parent...

    del blockmetadata[parentinode]['filename_to_inode_dict'][dirname]
    # decrement the link count on the dir...
    blockmetadata[parentinode]['linkcount'] -= 1

    # finally, clean up the fastinodelookuptable and return success!!!
    del fastinodelookuptable[truepath]
    
    return 0

  finally:
    persist_metadata(FILEDATAPREFIX + str(parentinode), blockmetadata[parentinode])
    persist_metadata(FILEDATAPREFIX + str(thisinode), '')
    _set_freed_inode(thisinode)
    filesystemmetadatalock.release()


##### LINK  #####


def link_syscall(oldpath, newpath):
  """ 
    http://linux.die.net/man/2/link
  """
  print "link_syscall"
  # lock to prevent things from changing while we look this up...
  filesystemmetadatalock.acquire(True)

  # ... but always release it...
  try:
    trueoldpath = _get_absolute_path(oldpath)

    # is the old path there?
    if trueoldpath not in fastinodelookuptable:
      raise SyscallError("link_syscall","ENOENT","Old path does not exist.")

    oldinode = fastinodelookuptable[trueoldpath]
    # is oldpath a directory?
    if IS_DIR(blockmetadata[oldinode]['mode']):
      raise SyscallError("link_syscall","EPERM","Old path is a directory.")


    # okay, the old path info seems fine.   
    if newpath == '':
      raise SyscallError("link_syscall","ENOENT","New path does not exist.")

    truenewpath = _get_absolute_path(newpath)

    # does the newpath exist?   It shouldn't
    if truenewpath in fastinodelookuptable:
      raise SyscallError("link_syscall","EEXIST","newpath already exists.")
      
    # okay, it doesn't exist (great!).   Does it's parent exist and is it a 
    # dir?
    truenewparentpath = _get_absolute_parent_path(newpath)

    if truenewparentpath not in fastinodelookuptable:
      raise SyscallError("link_syscall","ENOENT","New path does not exist.")

    newparentinode = fastinodelookuptable[truenewparentpath]
    if not IS_DIR(blockmetadata[newparentinode]['mode']):
      raise SyscallError("link_syscall","ENOTDIR","New path's parent is not a directory.")


    # okay, great!!!   We're ready to go!   Let's make the file...
    newfilename = truenewpath.split('/')[-1]
    # first, make the directory entry...
    blockmetadata[newparentinode]['filename_to_inode_dict'][newfilename] = oldinode
    # increment the link count on the dir...
    blockmetadata[newparentinode]['linkcount'] += 1

    # ... and the file itself
    blockmetadata[oldinode]['linkcount'] += 1

    # finally, update the fastinodelookuptable and return success!!!
    fastinodelookuptable[truenewpath] = oldinode
    
    return 0

  finally:
    persist_metadata(FILEDATAPREFIX + str(oldinode), blockmetadata[oldinode])
    persist_metadata(FILEDATAPREFIX + str(newparentinode), blockmetadata[newparentinode])
    filesystemmetadatalock.release()


##### UNLINK  #####

def unlink_syscall(path):
  """ 
    http://linux.die.net/man/2/unlink
  """
  print "unlink_syscall"
  # lock to prevent things from changing while we do this...
  filesystemmetadatalock.acquire(True)

  # ... but always release it...
  try:
    truepath = _get_absolute_path(path)

    # is the path there?
    if truepath not in fastinodelookuptable:
      raise SyscallError("unlink_syscall","ENOENT","The path does not exist.")

    thisinode = fastinodelookuptable[truepath]
      
    # okay, is it a directory?
    if IS_DIR(blockmetadata[thisinode]['mode']):
      raise SyscallError("unlink_syscall","EISDIR","Path is a directory.")

    trueparentpath = _get_absolute_parent_path(path)
    parentinode = fastinodelookuptable[trueparentpath]

    # We're ready to go!   Let's clean up the file entry
    dirname = truepath.split('/')[-1]
    # remove the entry from the parent...

    del blockmetadata[parentinode]['filename_to_inode_dict'][dirname]
    # decrement the link count on the dir...
    blockmetadata[parentinode]['linkcount'] -= 1

    # clean up the fastinodelookuptable
    del fastinodelookuptable[truepath]

    # decrement the link count...
    blockmetadata[thisinode]['linkcount'] -= 1

    # If zero, remove the entry from the inode table
    if blockmetadata[thisinode]['linkcount'] == 0:
      del blockmetadata[thisinode]
    
    return 0

  finally:
    try:
        persist_metadata(FILEDATAPREFIX + str(thisinode), blockmetadata[thisinode])
    except Exception, e:
        print "Exception: ", e
    
    try:
        persist_metadata(FILEDATAPREFIX + str(parentinode), blockmetadata[parentinode])
    except Exception, e:
        print "Exception: ", e
    
    
    filesystemmetadatalock.release()


##### STAT  #####


def stat_syscall(path):
  """ 
    http://linux.die.net/man/2/stat
  """
  print "stat_syscall"
  # in an abundance of caution, I'll grab a lock...
  filesystemmetadatalock.acquire(True)
  thisinode = None
  # ... but always release it...
  try:
    truepath = _get_absolute_path(path)

    # is the path there?
    if truepath not in fastinodelookuptable:
      raise SyscallError("stat_syscall","ENOENT","The path does not exist.")

    thisinode = fastinodelookuptable[truepath]
    
    # If its a character file, call the helper function.
    if IS_CHR(blockmetadata[thisinode]['mode']):
      return _istat_helper_chr_file(thisinode)
   
    return _istat_helper(thisinode)

  finally:
    if(thisinode != None) :
        persist_metadata(FILEDATAPREFIX + str(thisinode), blockmetadata[thisinode])
    
    filesystemmetadatalock.release()

   

##### FSTAT  #####

def fstat_syscall(fd):
  """ 
    http://linux.die.net/man/2/fstat
  """
  print "stat_syscall"
  # is the file descriptor valid?
  if fd not in filedescriptortable:
    raise SyscallError("fstat_syscall","EBADF","The file descriptor is invalid.")

  # if so, return the information...
  inode = filedescriptortable[fd]['inode']
  if fd in [0,1,2] or \
    (filedescriptortable[fd] is filedescriptortable[0] or \
     filedescriptortable[fd] is filedescriptortable[1] or \
     filedescriptortable[fd] is filedescriptortable[2] \
    ):
    return (filesystemmetadata['dev_id'],          # st_dev
          inode,                                 # inode
            49590, #mode
          1,  # links
          DEFAULT_UID, # uid
          DEFAULT_GID, #gid
          0,                                     # st_rdev     ignored(?)
          0, # size
          0,                                     # st_blksize  ignored(?)
          0,                                     # st_blocks   ignored(?)
          0,
          0,                                     # atime ns
          0,
          0,                                     # mtime ns
          0,
          0,                                     # ctime ns
        )
  if IS_CHR(blockmetadata[inode]['mode']):
    return _istat_helper_chr_file(inode)
  return _istat_helper(inode)



# private helper routine that returns stat data given an inode
def _istat_helper(inode):
  ret =  (filesystemmetadata['devId'],          # st_dev
          inode,                                 # inode
          blockmetadata[inode]['mode'],
          blockmetadata[inode]['linkcount'],
          blockmetadata[inode]['uid'],
          blockmetadata[inode]['gid'],
          0,                                     # st_rdev     ignored(?)
          blockmetadata[inode]['size'],
          0,                                     # st_blksize  ignored(?)
          0,                                     # st_blocks   ignored(?)
          blockmetadata[inode]['atime'],
          0,                                     # atime ns
          blockmetadata[inode]['mtime'],
          0,                                     # mtime ns
          blockmetadata[inode]['ctime'],
          0,                                     # ctime ns
        )
  return ret


##### OPEN  #####


# get the next free file descriptor
def get_next_fd():
  # let's get the next available fd number.   The standard says we need to 
  # return the lowest open fd number.
  for fd in range(STARTINGFD, MAX_FD):
    if not fd in filedescriptortable:
      return fd

  raise SyscallError("open_syscall","EMFILE","The maximum number of files are open.")
  

def open_syscall(path, flags, mode):
  """ 
    http://linux.die.net/man/2/open
  """
  print "open_syscall"
  # in an abundance of caution, lock...   I think this should only be needed
  # with O_CREAT flags...
  filesystemmetadatalock.acquire(True)
  log(" Opening file : " + path)
  # ... but always release it...
  newinode = None
  parentinode = None
  try:
    if path == '':
      raise SyscallError("open_syscall","ENOENT","The file does not exist.")

    truepath = _get_absolute_path(path)

    # is the file missing?
    if truepath not in fastinodelookuptable:

      # did they use O_CREAT?
      if not O_CREAT & flags:
        raise SyscallError("open_syscall","ENOENT","The file does not exist.")
      
      # okay, it doesn't exist (great!).   Does it's parent exist and is it a 
      # dir?
      trueparentpath = _get_absolute_parent_path(path)

      if trueparentpath not in fastinodelookuptable:
        raise SyscallError("open_syscall","ENOENT","Path does not exist.")

      #log(fastinodelookuptable)
      parentinode = fastinodelookuptable[trueparentpath]
      if not IS_DIR(blockmetadata[parentinode]['mode']):
        raise SyscallError("open_syscall","ENOTDIR","Path's parent is not a directory.")


      # okay, great!!!   We're ready to go!   Let's make the new file...
      filename = truepath.split('/')[-1]
      #print "open_syscall ->", filename
      
      # first, make the new file's entry...
      newinode = _get_next_inode()
      #print "open_syscall ->", newinode

      # be sure there aren't extra mode bits...   No errno seems to exist for 
      # this.
      assert(mode & (S_IRWXA|S_FILETYPEFLAGS) == mode)

      effective_mode = (S_IFCHR | mode) if (S_IFCHR & flags) != 0 else (S_IFREG | mode)
      
      # mark the inode as used, else at the time of write this inode will be used
      # to persist the data of the file
      _set_consumed_inode(newinode)
      
      # get a new inode for location
      locationinode = _get_next_inode()
      currentTime = int(time.time())
      newinodeentry = {'size':0, 'uid':DEFAULT_UID, 'gid':DEFAULT_GID, 
            'mode':effective_mode,
            'atime':currentTime, 'ctime':currentTime, 'mtime':currentTime,
            'linkcount':1,
            'indirect' : 0,
            'location' : locationinode # blank file initialized with a data block node
      }

      # ... and put it in the table..
      blockmetadata[newinode] = newinodeentry

      # let's make the parent point to it...
      blockmetadata[parentinode]['filename_to_inode_dict'][filename] = newinode
      # ... and increment the link count on the dir...
      blockmetadata[parentinode]['linkcount'] += 1

      # finally, update the fastinodelookuptable
      fastinodelookuptable[truepath] = newinode
      
      # marking this inode as used in free block list
      
      # marking the location inode as used in free block list
      _set_consumed_inode(locationinode)

    # if the file did exist, were we told to create with exclusion?
    else:
      # did they use O_CREAT and O_EXCL?
      if O_CREAT & flags and O_EXCL & flags:
        raise SyscallError("open_syscall","EEXIST","The file exists.")

      # This file should be removed.   If O_RDONLY is set, the behavior
      # is undefined, so this is okay, I guess...
      if O_TRUNC & flags:
        inode = fastinodelookuptable[truepath]


    # At this point, the file will exist... 
    # Let's find the inode
    inode = fastinodelookuptable[truepath]
    
    # get the next fd so we can use it...
    thisfd = get_next_fd()

    # I'm going to assume that if you use O_APPEND I only need to 
    # start the pointer in the right place.
    if O_APPEND & flags:
      position = blockmetadata[inode]['size']
    else:
      # else, let's start at the beginning
      position = 0

    # Add the entry to the table!

    filedescriptortable[thisfd] = {'position':position, 'inode':inode, 'lock':createlock(), 
    'flags':flags&O_RDWRFLAGS}

    # Done!   Let's return the file descriptor.
    return thisfd

  finally:
    if(newinode != None) :
        persist_metadata(FILEDATAPREFIX + str(newinode), blockmetadata[newinode])
    if(parentinode != None) :
        persist_metadata(FILEDATAPREFIX + str(parentinode), blockmetadata[parentinode])

    filesystemmetadatalock.release()


##### CREAT  #####

def creat_syscall(pathname, mode):
  """ 
    http://linux.die.net/man/2/creat
  """
  print "creat_syscall"
  try:

    fd = open_syscall(pathname, O_CREAT | O_TRUNC | O_WRONLY, mode)

    thisinode = filedescriptortable[fd]['inode']
    
    _set_consumed_inode(thisinode)
    return fd
  except SyscallError, e:
    # If it's a system call error, return our call name instead.
    assert(e[0]=='open_syscall')
    
    raise SyscallError('creat_syscall',e[1],e[2])
  


##### LSEEK  #####

def lseek_syscall(fd, offset, whence):
  """ 
    http://linux.die.net/man/2/lseek
  """
  print "lseek_syscall"
  # check the fd
  if fd not in filedescriptortable:
    raise SyscallError("lseek_syscall","EBADF","Invalid file descriptor.")

  # if we are any of the odd handles(stderr, sockets), we cant seek, so just report we are at 0
  if filedescriptortable[fd]['inode'] in [0,1,2]:
    return 0
  # Acquire the fd lock...
  filedescriptortable[fd]['lock'].acquire(True)


  # ... but always release it...
  try:

    # we will need the file size in a moment, but also need to check the type
    try:
      inode = filedescriptortable[fd]['inode']
    except KeyError:
      raise SyscallError("lseek_syscall","ESPIPE","This is a socket, not a file.")
    
    # Let's figure out if this has a length / pointer...
    if IS_REG(blockmetadata[inode]['mode']):
      # straightforward if it is a file...
      filesize = blockmetadata[inode]['size']

    elif IS_DIR(blockmetadata[inode]['mode']):
      # if a directory, let's use the number of entries
      filesize = len(blockmetadata[inode]['filename_to_inode_dict'])

    else:
      # otherwise we don't know
      raise SyscallError("lseek_syscall","EINVAL","File descriptor does not refer to a regular file or directory.")
      

    # Figure out where we will seek to and check it...
    if whence == SEEK_SET:
      eventualpos = offset
    elif whence == SEEK_CUR:
      eventualpos = filedescriptortable[fd]['position']+offset
    elif whence == SEEK_END:
      eventualpos = filesize+offset
    else:
      raise SyscallError("lseek_syscall","EINVAL","Invalid whence.")

    # did we fall off the front?
    if eventualpos < 0:
      raise SyscallError("lseek_syscall","EINVAL","Seek before position 0 in file.")

    # did we fall off the back?
    # if so, we'll handle this when we do a write.   The correct behavior is
    # to write '\0' bytes between here and that pos.

    # do the seek and return success (the position)!
    filedescriptortable[fd]['position'] = eventualpos

    return eventualpos

  finally:
    # ... release the lock
    filedescriptortable[fd]['lock'].release()



##### READ  #####

def read_syscall(fd, count):
  """ 
    http://linux.die.net/man/2/read
  """
  print "read_syscall"
  # BUG: I probably need a filedescriptortable lock to prevent an untimely
  # close call or similar from messing everything up...

  # check the fd
  if fd not in filedescriptortable:
    raise SyscallError("read_syscall","EBADF","Invalid file descriptor.")

  # Is it open for reading?
  if IS_WRONLY(filedescriptortable[fd]['flags']): 
    raise SyscallError("read_syscall","EBADF","File descriptor is not open for reading.")

  # Acquire the fd lock...
  filedescriptortable[fd]['lock'].acquire(True)

  # ... but always release it...
  try:

    # get the inode so I can and check the mode (type)
    inode = filedescriptortable[fd]['inode']

    # If its a character file, call the helper function.
    if IS_CHR(blockmetadata[inode]['mode']):
      return _read_chr_file(inode, count)

    # Is it anything other than a regular file?
    if not IS_REG(blockmetadata[inode]['mode']):
      raise SyscallError("read_syscall","EINVAL","File descriptor does not refer to a regular file.")
      

    # let's do a readat!
    # data is the total data stored in location pointer or at index blocks
    indirect = blockmetadata[inode]['indirect']
    location = blockmetadata[inode]['location']
    data = ""
    if indirect == 0:
        # if indirect is 0, then all data in location block
        df = openfile(FILEDATAPREFIX + str(location), True)
        data += df.readat(None, 0)
    else :
        # location block is an index list
        locdata = blockmetadata[location]
        
        # if index list length is more than 400, 
        # something went wrong while writing, so just not read the file
        if len(locdata) > 400:
            raise SyscallError("read_syscall","EINVAL","Corrupt file, larger than permitted size.")
  
        for bn in locdata :
           df = openfile(FILEDATAPREFIX + str(bn), True)
           data += df.readat(None, 0)
           df.close()
        

    # and update the position
    filedescriptortable[fd]['position'] += len(data)

    return data

  finally:
    # ... release the lock
    filedescriptortable[fd]['lock'].release()


##### WRITE  #####

def write_syscall(fd, data):
  """ 
    http://linux.die.net/man/2/write
  """
  print "write_syscall"
  print "write_syscall started------------------------------\n"
  # check the fd
  if fd not in filedescriptortable:
    raise SyscallError("write_syscall","EBADF","Invalid file descriptor.")

  if filedescriptortable[fd]['inode'] in [0,1,2]:
    return len(data)

  # Is it open for writing?
  if IS_RDONLY(filedescriptortable[fd]['flags']): 
    raise SyscallError("write_syscall","EBADF","File descriptor is not open for writing.")


  # Acquire the fd lock...
  filedescriptortable[fd]['lock'].acquire(True)

  # ... but always release it...
  try:

    # get the inode so I can update the size (if needed) and check the type
    inode = filedescriptortable[fd]['inode']

    # If its a character file, call the helper function.
    if IS_CHR(blockmetadata[inode]['mode']):
      return _write_chr_file(inode, data)

    # Is it anything other than a regular file?
    if not IS_REG(blockmetadata[inode]['mode']):
      raise SyscallError("write_syscall","EINVAL","File descriptor does not refer to a regular file.")
      
    # let's get the position...
    #position = filedescriptortable[fd]['position']
    
    # and the file size...
    filesize = blockmetadata[inode]['size']
    newdatalen = len(data) + filesize
    
    # data size inappropriate as it requires more than one indirect
    if newdatalen > (400 * BLOCKSIZE):
        raise SyscallError("write_syscall","ENOMEM","File size exceeds the maximum permissible limit.")
        
    indirect = blockmetadata[inode]['indirect']
    location = blockmetadata[inode]['location']
    
    #print "prv : ", filesize, " new : ", newdatalen
    reqblocks = (newdatalen + BLOCKSIZE/2)/BLOCKSIZE
    
    #print "reqblocks : ", reqblocks, " indirect : ", indirect
    
    if reqblocks > 400:
        raise SyscallError("write_syscall","ENOMEM","File size exceeds the maximum permissible limit.")
    
    if(indirect == 0) :
        noofblocks = 1  
        writeinode = blockmetadata[inode]['location']
    else :
        olddata = blockmetadata[blockmetadata[inode]['location']]
        noofblocks = len(olddata)
        writeinode = blockmetadata[blockmetadata[inode]['location']][-1] 
   
    #print "file : ", inode, " location : ", blockmetadata[inode]['location'], " towrite : ", writeinode
    if reqblocks == noofblocks:
        # just append the data at the end
        avsp = noofblocks * BLOCKSIZE - filesize
        avsp = BLOCKSIZE - avsp
        wfd = openfile(FILEDATAPREFIX + str(writeinode), True)
        wfd.writeat(data, avsp)
        wfd.close()
    elif reqblocks > noofblocks :
        # here we append some data (if possible) at the end of the
        # last block for this file, and then use the next block to write next part
        if indirect == 0:
            newloc = _get_next_inode()
            indirect = 1
            blockmetadata[inode]['indirect'] = indirect # updating the indirect
            blockmetadata[inode]['location'] = newloc # updating the location pointer
            blockmetadata[newloc] = [location] # add the location to new index block             
            writeinode = location # this is the new write location
            _set_consumed_inode(newloc) # mark the new index block as used
            
        # append and write new block with rest
        avsp = noofblocks * BLOCKSIZE - filesize # free space in the previous block
        #print " avail : ", avsp, " writeinode ", writeinode
        if avsp > 0:
            wfd = openfile(FILEDATAPREFIX + str(writeinode), True)
            wfd.writeat(data[0:avsp], (BLOCKSIZE - avsp))
            wfd.close()
            
        restdatalen = newdatalen - (noofblocks * BLOCKSIZE)
        #print "restdatalen ", restdatalen 
        
        if restdatalen > 0:
            # rest of the data goes in new data block
            
            #print " restdatalen : ", restdatalen, " writeinode ", writeinode
            writeinode = _get_next_inode() # adding a new data block
            wfd = openfile(FILEDATAPREFIX + str(writeinode), True)
            wfd.writeat(data[avsp:], 0)
            wfd.close()
            blockmetadata[blockmetadata[inode]['location']].append(writeinode) # update the index block
            _set_consumed_inode(writeinode) # update the free block list
            

    blockmetadata[inode]['size'] = newdatalen
    # file inode is updated with recent write info
    persist_metadata(FILEDATAPREFIX + str(inode), blockmetadata[inode]) # persist the file metadata
    if(indirect == 1):
        # index list is updatedFS
        persist_metadata(FILEDATAPREFIX + str(blockmetadata[inode]['location']), 
                                blockmetadata[blockmetadata[inode]['location']]) # persist the index block
    
      
    # we always write it all, so just return the length of what we were passed.
    # We do not mention whether we write blank data (if position is after the 
    # end)
    return len(data)

  finally:
    # ... release the lock
    filedescriptortable[fd]['lock'].release()



##### CLOSE  #####

# private helper.   Get the fds for an inode (or [] if none)
def _lookup_fds_by_inode(inode):
  returnedfdlist = []
  for fd in filedescriptortable:
    if not IS_SOCK_DESC(fd) and filedescriptortable[fd]['inode'] == inode:
      returnedfdlist.append(fd)
  return returnedfdlist


# is this file descriptor a socket? 
def IS_SOCK_DESC(fd):
  if 'domain' in filedescriptortable[fd]:
    return True
  else:
    return False



# BAD this is copied from net_calls, but there is no way to get it
def _cleanup_socket(fd):
  if 'socketobjectid' in filedescriptortable[fd]:
    thesocket = socketobjecttable[filedescriptortable[fd]['socketobjectid']]
    thesocket.close()
    localport = filedescriptortable[fd]['localport']
    try:
      _release_localport(localport, filedescriptortable[fd]['protocol'])
    except KeyError:
      pass
    del socketobjecttable[filedescriptortable[fd]['socketobjectid']]
    del filedescriptortable[fd]['socketobjectid']
    
    filedescriptortable[fd]['state'] = NOTCONNECTED
    return 0



# private helper that allows this to be called in other places (like dup2)
# without changing to re-entrant locks
def _close_helper(fd):
  # if we are a socket, we dont change disk metadata
  if IS_SOCK_DESC(fd):
    _cleanup_socket(fd)
    return 0

  # get the inode for the filedescriptor
  inode = filedescriptortable[fd]['inode']

  # If it's not a regular file, we have nothing to close...
  if not IS_REG(blockmetadata[inode]['mode']):
 
    # and return success
    return 0

  # so it's a regular file.

  # get the list of file descriptors for the inode
  fdsforinode = _lookup_fds_by_inode(inode)

  # I should be in there!
  assert(fd in fdsforinode)

  # I should only close here if it's the last use of the file.   This can
  # happen due to dup, multiple opens, etc.
  if len(fdsforinode) > 1:
    # Is there more than one descriptor open?   If so, return success
    return 0

  return 0



def close_syscall(fd):
  """ 
    http://linux.die.net/man/2/close
  """
  print "close_syscall"
  # BUG: I probably need a filedescriptortable lock to prevent race conditions
  # check the fd
  if fd not in filedescriptortable:
    raise SyscallError("close_syscall","EBADF","Invalid file descriptor.")
  try:
    if filedescriptortable[fd]['inode'] in [0,1,2]:
      return 0
  except KeyError:
    pass
  # Acquire the fd lock, if there is one.
  if 'lock' in filedescriptortable[fd]:
    filedescriptortable[fd]['lock'].acquire(True)

  # ... but always release it...
  try:
    return _close_helper(fd)

  finally:
    # ... release the lock, if there is one
    inode = filedescriptortable[fd]['inode']
    persist_metadata(FILEDATAPREFIX + str(inode), blockmetadata[inode])
    if 'lock' in filedescriptortable[fd]:
      filedescriptortable[fd]['lock'].release()
    del filedescriptortable[fd]



##### DUP2  #####

def dup2_syscall(oldfd,newfd):
  """ 
    http://linux.die.net/man/2/dup2
  """

  log("dup2_syscall NOT implemented")




##### DUP  #####

def dup_syscall(fd):
  """ 
    http://linux.die.net/man/2/dup
  """
  log("dup_syscall NOT implemented")

##### FCNTL  #####



def fcntl_syscall(fd, cmd, *args):
  """ 
    http://linux.die.net/man/2/fcntl
  """
  # this call is totally crazy!   I'll just implement the basics and add more
  # as is needed.

  # BUG: I probably need a filedescriptortable lock to prevent race conditions
  print "fcntl_syscall"
  # check the fd
  if fd not in filedescriptortable:
    raise SyscallError("fcntl_syscall","EBADF","Invalid file descriptor.")

  # Acquire the fd lock...
  filedescriptortable[fd]['lock'].acquire(True)

  # ... but always release it...
  try:
    # if we're getting the flags, return them... (but this is just CLO_EXEC, 
    # so ignore)
    if cmd == F_GETFD:
      if len(args) > 0:
        raise SyscallError("fcntl_syscall", "EINVAL", "Argument is more than\
          maximun allowable value.")
      return int((filedescriptortable[fd]['flags'] & FD_CLOEXEC) != 0)

    # set the flags...
    elif cmd == F_SETFD:
      assert(len(args) == 1)
      filedescriptortable[fd]['flags'] |= args[0]
      return 0

    # if we're getting the flags, return them...
    elif cmd == F_GETFL:
      assert(len(args) == 0)
      return filedescriptortable[fd]['flags']

    # set the flags...
    elif cmd == F_SETFL:
      assert(len(args) == 1)
      assert(type(args[0]) == int or type(args[0]) == long)
      filedescriptortable[fd]['flags'] = args[0]
      return 0

    # This is saying we'll get signals for this.   Let's punt this...
    elif cmd == F_GETOWN:
      assert(len(args) == 0)
      # Saying traditional SIGIO behavior...
      return 0

    # indicate that we want to receive signals for this FD...
    elif cmd == F_SETOWN:
      assert(len(args) == 1)
      assert(type(args[0]) == int or type(args[0]) == long)
      # this would almost certainly say our PID (if positive) or our process
      # group (if negative).   Either way, we do nothing and return success.
      return 0


    else:
      # This is either unimplemented or malformed.   Let's raise
      # an exception.
      raise UnimplementedError('FCNTL with command '+str(cmd)+' is not yet implemented.')

  finally:
    # ... release the lock
    filedescriptortable[fd]['lock'].release()




##### GETDENTS  #####



def getdents_syscall(fd, quantity):
  """ 
    http://linux.die.net/man/2/getdents
  """
  print "getdents_syscall"
  # check the fd
  if fd not in filedescriptortable:
    raise SyscallError("getdents_syscall","EBADF","Invalid file descriptor.")

  # Sanitizing the Input, there are people who would send other types too.
  if not isinstance(quantity, (int, long)):
    raise SyscallError("getdents_syscall","EINVAL","Invalid type for buffer size.")

  # This is the minimum number of bytes, that should be provided.
  if quantity < 24:
    raise SyscallError("getdents_syscall","EINVAL","Buffer size is too small.")

  # Acquire the fd lock...
  filedescriptortable[fd]['lock'].acquire(True)

  # ... but always release it...
  try:

    # get the inode so I can read the directory entries
    inode = filedescriptortable[fd]['inode']

    # Is it a directory?
    if not IS_DIR(blockmetadata[inode]['mode']):
      raise SyscallError("getdents_syscall","EINVAL","File descriptor does not refer to a directory.")
      
    returninodefntuplelist = []
    bufferedquantity = 0

    # let's move the position forward...
    startposition = filedescriptortable[fd]['position']
    # return tuple with inode, name, type tuples...
    for entryname,entryinode in list(blockmetadata[inode]['filename_to_inode_dict'].iteritems())[startposition:]:
      # getdents returns the mode also (at least on Linux)...
      entrytype = get_direnttype_from_mode(blockmetadata[entryinode]['mode'])

      # Get the size of each entry, the size should be a multiple of 8.
      # The size of each entry is determined by sizeof(struct linux_dirent) which is 20 bytes plus the length of name of the file.
      # So, size of each entry becomes : 21 => 24, 26 => 32, 32 => 32.
      currentquantity = (((20 + len(entryname)) + 7) / 8) * 8

      # This is the overall size of entries parsed till now, if size exceeds given size, then stop parsing and return
      bufferedquantity += currentquantity
      if bufferedquantity > quantity:
        break

      returninodefntuplelist.append((entryinode, entryname, entrytype, currentquantity))

    # and move the position along.   Go no further than the end...
    filedescriptortable[fd]['position'] = min(startposition + len(returninodefntuplelist),\
      len(blockmetadata[inode]['filename_to_inode_dict']))
    
    return returninodefntuplelist

  finally:
    # ... release the lock
    filedescriptortable[fd]['lock'].release()



#### CHMOD ####


def chmod_syscall(path, mode):
  """
    http://linux.die.net/man/2/chmod
  """
  log("chmod_syscall NOT Implemented")

#### TRUNCATE  ####
def truncate_syscall(path, length):
    log("truncate_syscall NOT Implemented")

#### FTRUNCATE ####
def ftruncate_syscall(fd, new_len):
    log("truncate_syscall NOT Implemented")

#### MKNOD ####

# for now, I am considering few assumptions:
# 1. It is only used for creating character special files.
# 2. I am not bothering about S_IRWXA in mode. (I need to fix this).
# 3. /dev/null    : (1, 3)
#    /dev/random  : (1, 8)
#    /dev/urandom : (1, 9)
#    The major and minor device number's should be passed in as a 2-tuple.

def mknod_syscall(path, mode, dev):
  """
    http://linux.die.net/man/2/mknod
  """
  if path == '':
    raise SyscallError("mknod_syscall","ENOENT","The file does not exist.")

  truepath = _get_absolute_path(path)

  # check if file already exists, if so raise an error.
  if truepath in fastinodelookuptable:
    raise SyscallError("mknod_syscall", "EEXIST", "file already exists.")

  # FIXME: mode should also accept user permissions(S_IRWXA)
  if not mode & S_FILETYPEFLAGS == mode: 
    raise SyscallError("mknod_syscall", "EINVAL", "mode requested creation\
      of something other than regular file, device special file, FIFO or socket")

  # FIXME: for now, lets just only create character special file 
  if not IS_CHR(mode):
    raise UnimplementedError("Only Character special files are supported.")

  # this has nothing to do with syscall, so I will raise UnimplementedError.
  if type(dev) is not tuple or len(dev) != 2:
    raise UnimplementedError("Third argument should be 2-tuple.")

  # Create a file, but don't open it. openning a chr_file should be done only using
  # open_syscall. S_IFCHR flag will ensure that the file is not opened.
  fd = open_syscall(path, mode | O_CREAT, S_IRWXA)

  # add the major and minor device no.'s, I did it here so that the code can be managed
  # properly, instead of putting everything in open_syscall.
  inode = filedescriptortable[fd]['inode']
  blockmetadata[inode]['rdev'] = dev
 
  # close the file descriptor... 
  close_syscall(fd)
  return 0


#### Helper Functions for Character Files.####
# currently supported devices are:
# 1. /dev/null
# 2. /dev/random
# 3. /dev/urandom

def _read_chr_file(inode, count):
  """
   helper function for reading data from chr_file's.
  """

  # check if it's a /dev/null. 
  if blockmetadata[inode]['rdev'] == (1, 3):
    return ''
  # /dev/random
  elif blockmetadata[inode]['rdev'] == (1, 8):
    return randombytes()[0:count]
  # /dev/urandom
  # FIXME: urandom is supposed to be non-blocking.
  elif blockmetadata[inode]['rdev'] == (1, 9):
    return randombytes()[0:count]
  else:
    raise UnimplementedError("Given device is not supported.")


def _write_chr_file(inode, data):
  """
   helper function for writing data to chr_file's.
  """

  # check if it's a /dev/null.
  if blockmetadata[inode]['rdev'] == (1, 3):
    return len(data)
  # /dev/random
  # There's no real /dev/random file, just vanish it into thin air.
  elif blockmetadata[inode]['rdev'] == (1, 8):
    return len(data)
  # /dev/urandom
  # There's no real /dev/random file, just vanish it into thin air.
  elif blockmetadata[inode]['rdev'] == (1, 9):
    return len(data)
  else:
    raise UnimplementedError("Given device is not supported.")


def _istat_helper_chr_file(inode):
  ret =  (5,          # st_dev, its always 5 for chr_file's.
          inode,                                 # inode
          blockmetadata[inode]['mode'],
          blockmetadata[inode]['linkcount'],
          blockmetadata[inode]['uid'],
          blockmetadata[inode]['gid'],
          blockmetadata[inode]['rdev'],
          blockmetadata[inode]['size'],
          0,                                     # st_blksize  ignored(?)
          0,                                     # st_blocks   ignored(?)
          blockmetadata[inode]['atime'],
          0,                                     # atime ns
          blockmetadata[inode]['mtime'],
          0,                                     # mtime ns
          blockmetadata[inode]['ctime'],
          0,                                     # ctime ns
        )
  return ret

#### USER/GROUP IDENTITIES ####


def getuid_syscall():
  """
    http://linux.die.net/man/2/getuid
  """
  # I will return 1000, since this is also used in stat
  return DEFAULT_UID

def geteuid_syscall():
  """
    http://linux.die.net/man/2/geteuid
  """
  # I will return 1000, since this is also used in stat
  return DEFAULT_UID

def getgid_syscall():
  """
    http://linux.die.net/man/2/getgid
  """
  # I will return 1000, since this is also used in stat
  return DEFAULT_GID

def getegid_syscall():
  """
    http://linux.die.net/man/2/getegid
  """
  # I will return 1000, since this is also used in stat
  return DEFAULT_GID


#### RESOURCE LIMITS  ####

# FIXME: These constants should be specified in a different file, 
# it at all additional support needs to be added.
NOFILE_CUR = 1024
NOFILE_MAX = 4*1024

STACK_CUR = 8192*1024
STACK_MAX = 2**32

def getrlimit_syscall(res_type):
  """
    http://linux.die.net/man/2/getrlimit

    NOTE: Second argument is deprecated. 
  """
  log("getrlimit_syscall NOT Implemented")

def setrlimit_syscall(res_type, limits):
  """
    http://linux.die.net/man/2/setrlimit
  """

  log("setrlimit_syscall NOT Implemented")

#### FLOCK SYSCALL  ####

def flock_syscall(fd, operation):
  """
    http://linux.die.net/man/2/flock
  """
  log("flock_syscall NOT Implemented")
  

def rename_syscall(old, new):
  """
  http://linux.die.net/man/2/rename
  TODO: this needs to be fixed up.
  """
  print "rename_syscall"
  filesystemmetadatalock.acquire(True)
  try:
    true_old_path = _get_absolute_path(old)
    true_new_path = _get_absolute_path(new)

    if true_old_path not in fastinodelookuptable:
      raise SyscallError("rename_syscall", "ENOENT", "Old file does not exist")

    if true_new_path == '':
      raise SyscallError("rename_syscall", "ENOENT", "New file does not exist")

    trueparentpath_old = _get_absolute_parent_path(true_old_path)
    parentinode = fastinodelookuptable[trueparentpath_old]

    inode = fastinodelookuptable[true_old_path]

    newname = true_new_path.split('/')[-1]
    blockmetadata[parentinode]['filename_to_inode_dict'][newname] = inode

    fastinodelookuptable[true_new_path] = inode

    oldname = true_old_path.split('/')[-1]
    del blockmetadata[parentinode]['filename_to_inode_dict'][oldname]
    del fastinodelookuptable[true_old_path]

  finally:
    persist_metadata(FILEDATAPREFIX + str(parentinode), blockmetadata[parentinode])
    filesystemmetadatalock.release()
  return 0
