class DNode :

	def __init__(self, data=None, next=None, prev=None):
		self.data = data
		self.next = next
		self.prev = prev

	def _last_(self):
		# O(n)
		if self.next == None:
			return self
		else :
			curr = self.next
			while curr.next != None:
				curr = curr.next
		return curr

	def insert(self, data):
		if data == None:
			return

		if self.data == None:
			self.data = data
		else :
			dnode = DNode(data)
			last = self._last_()
			last.next = dnode
			dnode.prev = last

		#print("added=%s" % str(data))

	def __repr__(self):
		return self.__str__()

	def __str__(self):
		rpr = None
		if self.data == None:
			rpr = "None"
		else :
			rpr = str(self.data)
		return "<%s>" % (rpr)

	def dump(self):
		if self.data == None:
			return "None"
		elif self.next != None:
			return "%s->%s" % (str(self.data), self.next.dump())
		else : 
			return "%s" % str(self.data)

	def delete(self, data):
		'''
		http://www.geeksforgeeks.org/delete-a-node-in-a-doubly-linked-list/
		'''
		if data == None:
			return None
		
		curr = self
		found = False
		while (not found and curr != None):
			if curr.data == data:
				found = True
			else :
				curr = curr.next

		if not found :
			return None

		prev = curr.prev
		next = curr.next

		prev.next = next
		next.prev = prev

		return curr

	def __recursive_reverse(self, orig, rever=None):
		if orig == None:
			return rever

		next_orig = orig.next
		if rever != None:
			orig.next = rever
			rever.prev = orig
			rever = orig
		else :
			rever = orig
			rever.next = None		

		return self.__recursive_reverse(next_orig, rever)

	def reverse(self):
		'''
		http://www.geeksforgeeks.org/reverse-a-doubly-linked-list/
		'''

		'''
		S : a=>b<=>c<=>d=>None

		1 : b=>c<=>d=>None a=>None
		2 : c<=>d=>None b=>a=>None
		3 : d=>None c=>b<=>a=>None
		3 : None d>c<=>b<=>a=>None

		'''

		return self.__recursive_reverse(self)


def main():
	dll = DNode()
	dll.insert(1)
	dll.insert(2)
	dll.insert(3)
	dll.insert(4)
	dll.insert(5)
	dll.insert(6)
	dll.insert(7)
	dll.insert(8)
	dll.insert(9)

	print(dll.dump())

	'''
	deleted = dll.delete(6)

	print("-----------")
	print(dll.dump())
	print("Deleted %s" % deleted)
	'''

	dll = dll.reverse()
	print("-----------")
	#print(dll1.dump())
	print(dll.dump())




if __name__ == "__main__": main()







