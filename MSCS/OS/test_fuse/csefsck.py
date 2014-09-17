#! /usr/bin/python

'''
    Author : Udita Bose
    Poly ID : 0527963
'''

import sys
import os
import time

mount = {}
blockfd = {}
blocks = {}
consumedBlocks = []
STATUS = {}

# initialize file system check #################################################
def _init_fs_check_(mp) :
    '''
    initiates the file system checker
    '''

    # initializes the error no
    _init_status_no()
    
    # the mount directory
    mount['mountpoint'] = mp

    # validate the mountpoint 
    status = _check_mountpoint()

    if(status != STATUS['OK']) :
        print "Invalid filesystem mountpoint > ", mountpoint
        return
    else :
        # check file system
        status = _fs_checker()
    
    
    print status


# initialize error status ######################################################
def _init_status_no() :
    '''
    initiates the error numbers
    '''
    STATUS['OK'] = 100 # all ok!
    STATUS['I_MP'] = 200 # invalid mountpoint
    STATUS['I_SB'] = 300 # invalid superblock metadata
    STATUS['I_DID'] = 400 # invalid dev id
    STATUS['I_TS'] = 500 # invalid timestamp
    STATUS['M_BD'] = 600 # missing block data
    STATUS['F_MFB'] = 600 # missing freeblock
    STATUS['F_NFB'] = 700 # data in freeblock
    STATUS['D_IP'] = 800 # invalid parent dir
    STATUS['D_ILC'] = 900 # wrong numbers of link count
    STATUS['F_ID'] = 1000 # wrong indirect
    STATUS['F_BS'] = 1100 # wrong block size
    
    

# just validate if the superblock metadata file exists #########################
def _check_mountpoint() :
    try:
        parentdirpath = os.path.abspath(os.path.join(mount['mountpoint'], os.pardir))
        print mount['mountpoint'], " - ", parentdirpath
        mount['parent'] = parentdirpath
        sb = open(parentdirpath + '/linddata.0', 'r')
    except IOError, e:
        return STATUS['I_MP']
    else :
        blockfd[0] = sb
        return STATUS['OK']


# start file system check ######################################################
def _fs_checker() :
    status = STATUS['OK']

    status = _check_super_block()
    if(status != STATUS['OK']) :
        return status
    
    status = _check_free_block()
    if(status != STATUS['OK']) :
        return status
    
    status = _validate_block_type()
    if(status != STATUS['OK']) :
        return status
    
    return status


# super block check ############################################################
def _check_super_block():
    """ 
    validate super block
    """
    sbdatastring = blockfd[0].readline()
    blockfd[0].close()

    # get the dict we want
    sbdata = deserializedata(sbdatastring)
    
    if(sbdata['devId'] != 20) :
        return STATUS['I_DID']
    
    currentTime = int(time.time())
    if(int(sbdata['creationTime']) > currentTime) :
        return STATUS['I_SB']
    
    if(sbdata['freeStart'] < 1) :
        return STATUS['I_SB']
    
    if(sbdata['freeEnd'] > 25) :
        return STATUS['I_SB']
    
    if(sbdata['maxBlocks'] > 10000) :
        return STATUS['I_SB']
    
    if(sbdata['root'] != 26) :
        return STATUS['I_SB']

    blocks[0] = sbdata

    return STATUS['OK']


# check free block consistency #################################################
def _check_free_block():
    """
    consistency check for free block list
        1. Making sure the free block list contains ALL of the free blocks
        2. Make sure than there are no files/directories stored on items listed in the free block list
    """
    
    # populate the block data fields
    status = _prepare_blocks()
    if(status != STATUS['OK']) :
        # some metadata file reading error
        return status
    
    # find which blocks are already consumed, 
    # per the block level metadata
    _prepare_consumed_block()
    
    
    # TODO : remove debug statements
    # print consumedBlocks
    
    freeStart = blocks[0]['freeStart']
    freeEnd = blocks[0]['freeEnd']
    blknum = 0
    for fblkcnt in xrange(freeStart, freeEnd + 1, 1) :
        for fblk in blocks[fblkcnt] :
            # if free block is zero, then, it should not be in consumed blocks
            # else it definitely should be
            print fblkcnt, " - ", fblk, " - ", blknum
            if (fblk == 0 and (blknum in consumedBlocks)):
                # free blocks says it's free, but consumed block says it's not
                return STATUS['F_NFB']
            elif(fblk == 1 and not(blknum in consumedBlocks)) :                
                # free block says it's not free, but consumed block says it is
                return STATUS['F_MFB']
                
            blknum += 1
    # consistency all ok
    return STATUS['OK']
    
     
    
def _prepare_blocks():
    """
    populate the blocks list
    """

    counter = blocks[0]['freeStart']
    maxBlocks = blocks[0]['maxBlocks']
    while(counter < maxBlocks) :
        try:
            # print (mount['parent'] + '/linddata.' + str(counter))
            f = open(mount['parent'] + '/linddata.' + str(counter), 'r')          
        except IOError, e:
            return STATUS['M_BD']
        else :
            fdatastring = f.next()
            fdata = deserializedata(fdatastring)
            blocks[counter] = fdata
        counter += 1
            
    return STATUS['OK']

def _prepare_consumed_block():
    """
    prepares a list for consumed blocks
    this list is prepared as a convenience for further
    free-block consumed-block consistency checks
    """
    
    blkcounter = 0
    maxBlocks = blocks[0]['maxBlocks']
    
    freeStart = blocks[0]['freeStart']
    freeEnd = blocks[0]['freeEnd']
    
    while(blkcounter in xrange(0, maxBlocks, 1)) :
        # data type can be varied
        # dictionary - for superBlock, directory and file inode
        # list - for free block list, index list
        # string - data blocks
        # empty - free block
        bdata = blocks[blkcounter]
        # an afterthought - we really do not need to bother about
        # data blocks here, as all the data blocks should be in one
        # of index list or in location attribute of inode
        
        if (type(bdata) == list) :
            if(blkcounter >= freeStart and blkcounter <= freeEnd) :
                # free block list, so no reading the content of the list, 
                # but mark the block as a consumed block 
                consumedBlocks.append(blkcounter)
            else :
                # this is an index list, marking this block as consumed
                # mark all the blocks listed in this list as consumed as well
                consumedBlocks.append(blkcounter)
                for n in bdata :
                    consumedBlocks.append(n)
        elif (type(bdata) == dict) :
            # mark this block as consumed block
            consumedBlocks.append(blkcounter)
            if(bdata.has_key('filename_to_inode_dict')) :
                # if directory node has connected files
                for n in bdata['filename_to_inode_dict'].values() :
                    consumedBlocks.append(n)
            elif(bdata.has_key('location')):  
                # if this is a file node, then check indirect attribute
                # and if inderect if 0, mark the value in location attribute
                # as consumed block
                # if indirect is 1, mark the value in location attribute
                # as consumed block, then leave it, cause the 'if' block already
                # does a check on index list 
                consumedBlocks.append(bdata['location'])
        

  
def _check_block_type():
    """
    block type specific checks
    directory : 
      validate link count 
    file :
      validate indirect, data block
    index (am not really sure :():
      validate this index is keyed to some file's location attribute
      validate this inode and all inodes are consumed

    Not checking the super block - done at the begining of checks
    Not cheking the free blocks list - done at the 2nd step
    """
      
    
    maxBlocks = blocks[0]['maxBlocks']
    freeEnd = blocks[0]['freeEnd']
    
    while(blkcounter in  xrange(freeEnd + 1, maxBlocks, 1)) :
        if (type(bdata) == dict) :
            # directory, file inodes
            # won't touch the character device file
            if IS_DIR(blocks[blkcounter]['mode']):
                status = _check_dir_type(blocks[blkcounter], blkcounter)
            elif IS_REG(blocks[blkcounter]['mode']) :
                status = _check_reg_type(blocks[blkcounter], blkcounter)
            
            if(status != STATUS['OK']) :
                return status
            
    return STATUS['OK']
                
      
      
        
def _check_dir_type(dirblock, thisinode):
    """
    checks the directory block for consistency
    """
    
    currentTime = int(time.time())
    if not(dirblock.has_key('ctime') and dirblock.has_key('atime') and dirblock.has_key('mtime') ) :
        # creation, access, modified time can not be done away with
        return STATUS['I_TS']
    
    if (dirblock['ctime'] > currentTime or dirblock['atime'] > currentTime or dirblock['mtime'] > currentTime):
        # none of the time can be in future
        return STATUS['I_TS']
    
    
    if not(dirblock.has_key('linkcount')) :
        # minimum link count is 2, one for self, 
        # one for parent 
        return STATUS['D_ILC']
    linkcount = dirblock['linkcount']
    
    if not(dirblock.has_key('filename_to_inode_dict')) :
        # has to have a filename_to_inode_dict, 
        # with minimum 2 linked inode
        return STATUS['D_ILC']
    
    linkedinodes = dirblock['filename_to_inode_dict']
    
    if(linkcount != len(linkedinodes)) :
        # if these 2 count do not match, then just crash!!
        return STATUS['D_ILC']
    
    if((not linkedinodes.has_key('.')) or (thisinode != linkedinodes['.'])) :
        # major whoopla! this dir does not have a pointer to self!
        return STATUS['D_ILC']
    
    if not (linkedinodes.has_key('..')) :
        # major whoopla level 2! this dir does not have a pointer to parent!
        # bad kid!
        return STATUS['D_ILC']
    
    parentinode = linkedinodes['..']
    if not(blocks.has_key(parentinode)):
        # really weird, there is no parent - bad 
        return STATUS['D_IP']
        
    parentblock = blocks[linkedinodes['..']]
    
    if not IS_DIR(parentblock['mode']):
        # super strange, parent is not a dir
        return STATUS['D_IP']
    
    if not (parentblock.has_key('filename_to_inode_dict')):
        # well this part is repeatitious, as at the the parent dir
        # is tested, it will validate this again
        # but in our fs, we have fixed inodes (0-10000)
        # and at some point of time, due to deletion and creation
        # without any particular order, it may happen that a block
        # will have a parent with higher inode, so can not assume
        # that a parent block is always tested before child
        return STATUS['D_IP']
    
    if not thisinode in parentblock['filename_to_inode_dict'].values() :
        # well, boom, parent does not have key for child
        # creation(?)/deletion(?) gone bad or something exponentially
        # more freak-worthy
        return STATUS['D_IP']
    
    return STATUS['OK']
        
      
def _check_reg_type(regblock, thisinode):
    """
    checks the file (regular file) block for consistency
    """
    currentTime = int(time.time())
    if not(regblock.has_key('ctime') and regblock.has_key('atime') and regblock.has_key('mtime') ) :
        # creation, access, modified time can not be done away with
        return STATUS['I_TS']
    
    if (regblock['ctime'] > currentTime or regblock['atime'] > currentTime or regblock['mtime'] > currentTime):
        # none of the time can be in future
        return STATUS['I_TS']
    
    if not(regblock.has_key('indirect')) :
        # no indirect - that should not happen
        return STATUS['F_ID']
    
    if not(regblock.has_key('location')) :
        # no location - no data, that should not happen
        return STATUS['F_ID']
    
    indirect = regblock['indirect']
    bdata = blocks[regblock['location']]
    
    
    if indirect == 0:
        if (type(bdata) != str):
            # location contains should contain data
            return STATUS['F_ID']
        elif (len(bdata) > 4096):
            # data length should not go beyond size of a block
            return STATUS['F_BS']
        elif regblock['size'] > 4096 :
            # if data length is more than 4096, then indirect should be 1
            return STATUS['F_BS']
    
    if indirect == 1 :
        if(type(bdata) != list):
            # location contains should contain index list
            return STATUS['F_ID']
        elif regblock['size'] <= 4096 :
            # if data length is less than or equal 4096, then indirect should be 0
            return STATUS['F_BS']
        else :
            bdatalen = len(bdata)
            if not((bdatalen * 4096) >= regblock['size'] and (bdatalen-1 * 4096) < regblock['size']):
                return STATUS['F_BS']
            
    return STATUS['OK']

    
    

# initialization ###############################################################
if __name__ == "__main__":
    # add repy install path to script
    repy_path = os.getenv("REPY_PATH")

    if repy_path == None:
        # if not set, use the location of this file.
        (lind_fuse_path, name) = os.path.split(os.path.abspath(__file__))
        os.environ['REPY_PATH'] = lind_fuse_path
        code_path = lind_fuse_path
    else:
        # If it is set, use the standard lind path
        code_path = os.path.join(repy_path, "repy/")

    sys.path.append(code_path)
    
    
    from repy_workarounds import *
    from lind_fs_constants import *
    from lind_net_constants import *

    # start the file checker
    _init_fs_check_(sys.argv[1])   
        
        
# TODO : Convert repy way