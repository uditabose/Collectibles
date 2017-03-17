class Node :
	'''
	node for linked list
	'''
	def __init__(self, data=None, next_node=None) :
		self.data = data
		self.next_node = next_node

	def get_data(self):
		return self.data

	def get_next(self):
		return self.next_node

	def set_next(self, next_node):
		self.next_node = next_node

	def __str__(self):
		if self.data == None:
			return "None"
		return "%s->%s" % (str(self.data), str(self.next_node))

	def __repr__(self):
		return self.__str__()

class LinkedList:
	"""
	implementation of singly linked list
	"""
	def __init__(self, node=None):
		self.node = node
		self.size = 0

	def get_head(self):
		return self.node

	def set_head(self, node):
		if self.node == None:
			self.node = node
		elif node == None:
			self.node = None
		else :
			node.set_next(self.node)
			self.node = node

	def insert_head(self, node):
		if self.node == None:
			self.node = node

		

	def get_last(self):
		if (self.node == None) :
			return None
		else :
			curr_node = self.node
			while (curr_node.get_next() != None) :
				curr_node = curr_node.get_next()
		return curr_node

	'''
	http://quiz.geeksforgeeks.org/find-length-of-a-linked-list-iterative-and-recursive/
	'''
	def size_iter(self) :
		if self.node == None :
			return 0

		curr_node = self.node
		size = 0
		while curr_node != None:
			size = size + 1
			curr_node = curr_node.get_next()
		return size

	def _size_(self, node) :
		if node != None :
			return 1 + self._size_(node.get_next())
		else :
			return 0
		
	def size_recur(self):
		if self.node == None :
			return 0
		return self._size_(self.node)

	def get_prev(self, node) :
		prev_node = None
		curr_node = self.node

		while curr_node != node:
			prev_node = curr_node
			curr_node = curr_node.get_next()
		return prev_node


	def insert(self, data):
		new_node = Node(data)

		if (self.node == None) :
			self.node = new_node
		else :
			curr_node = self.node
			while (curr_node.get_next() != None) :
				curr_node = curr_node.get_next()
			curr_node.set_next(new_node)

		self.size = self.size + 1

	def delete(self, node):

		if (self.node == None) :
			return None

		prev_node = None
		curr_node = self.node

		while (curr_node != node) :
			prev_node = curr_node
			curr_node = curr_node.get_next()

		if (curr_node != None):
			prev_node.set_next(curr_node.get_next())
			self.size = self.size - 1
			return curr_node
		else :
			return None

	def search(self, data):

		prev_node = None
		curr_node = self.node
		while (curr_node != None):
			if (curr_node.get_data() != data) :
				prev_node = curr_node
				curr_node = curr_node.get_next()
			else :
				break;

		return curr_node, prev_node


	def size(self) :
		return self.size

	def dump(self):
		print("\n**************")
		print("List of size %i" % self.size)
		print("----------------")
		if (self.node == None) :
			print("empty list")
			return

		curr_node = self.node
		count = 1
		while (curr_node != None):
			print("list[%i]=%s" % (count, curr_node.get_data()))
			curr_node = curr_node.get_next()
			count = count + 1

def swap_node(ll, data1, data2):
	"""
	http://www.geeksforgeeks.org/swap-nodes-in-a-linked-list-without-swapping-data/
	"""
	print("\nSwapping : %s with %s" % (data1, data2))
	node1, node1_prev = ll.search(data1)
	node2, node2_prev = ll.search(data2)

	if node1 == None or node2 == None:
		return

	if node1_prev != None:
		node1_prev.set_next(node2)
	else :
		ll.set_head(node2)

	if node2_prev != None:
		node2_prev.set_next(node1)
	else :
		ll.set_head(node1)

	temp = node1.get_next()
	node1.set_next(node2.get_next())
	node2.set_next(temp)

def reverse(ll):
	'''
	http://www.geeksforgeeks.org/write-a-function-to-reverse-the-nodes-of-a-linked-list/
	'''
	head = ll.get_head()
	tail = ll.get_last()

	while (head != None and tail != None):

		if head == tail:
			break
		
		head_next = head.get_next()
		tail_prev = ll.get_prev(tail)

		swap_node(ll, head.get_data(), tail.get_data())

		tail_next = tail.get_next()

		if tail_next == tail_prev:
			break
		
		head = head_next
		tail = tail_prev	

def merge_sorted_new(sorted1, sorted2):
	'''
	http://www.geeksforgeeks.org/merge-two-sorted-linked-lists/
	'''
	if sorted1 == None:
		return sorted2

	if sorted2 == None:
		return sorted1

	left = sorted1.get_head()
	right = sorted2.get_head()

	merged = LinkedList()

	while left != None and right != None:
		if left.get_data() <= right.get_data():
			merged.insert(left.get_data())
			left = left.get_next()
		else :
			merged.insert(right.get_data())
			right = right.get_next()

	while left != None:
		merged.insert(left.get_data())
		left = left.get_next()
	while right != None:
		merged.insert(right.get_data())
		right = right.get_next()

	return merged

def reverse_by_nodes(ll):

	# assume a list [a->b->c->d]
	# curr_node = a
	# prev_node = None
	# loop 1:
	#  next  = curr_node.next(b->c->d)
	#  curr_node.next = prev_node (a.next = None)
	#  prev_node = curr_node (a)
	#  curr_node = next (b->c->d)

	# curr_node = b
	# prev_node = a
	# loop 2:
	#  next  = curr_node.next(c->d)
	#  curr_node.next = prev_node (b.next = a => b->a->None)
	#  prev_node = curr_node (b)
	#  curr_node = next (c->d)

	# curr_node = c
	# prev_node = b
	# loop 3:
	#  next  = curr_node.next(d)
	#  curr_node.next = prev_node (c.next = b => c->b->a->None)
	#  prev_node = curr_node (c)
	#  curr_node = next (d)

	# curr_node = d
	# prev_node = c
	# loop 3:
	#  next  = curr_node.next(None)
	#  curr_node.next = prev_node (d.next = c => d->c->b->a->None)
	#  prev_node = curr_node (d)
	#  curr_node = next (None)


	curr_node = ll.get_head()
	prev_node = None
	while (curr_node != None):
		next = curr_node.get_next()

		print("curr_node %s, next %s, prev_node %s" % (curr_node, next, prev_node))

		curr_node.set_next(prev_node)
		
		prev_node = curr_node
		curr_node = next

def reverse_in_group(ll, group):
	'''
	http://www.geeksforgeeks.org/reverse-a-list-in-groups-of-given-size/
	'''
	if ll == None :
		return

	head = ll.get_head()

	if head == None:
		return

	tail = head

	counter = 0
	while True:
		if tail == None:
			reverse_nodes(head, tail)
			break

		if counter % group == 0 :
			reverse_nodes(head, tail)
			head = tail.get_next()
			tail = head 
		else :
			tail = tail.get_next()

		counter = counter + 1


def sum_of_two(list1, list2):
	'''
	http://www.geeksforgeeks.org/add-two-numbers-represented-by-linked-lists/
	'''
	curr1 = list1.get_head()
	curr2 = list2.get_head()
	carried = 0
	summed = LinkedList()
	while curr1 != None or curr2 != None:
		if curr1 == None:
			sumt = curr2.get_data() + carried
			curr2 = curr2.get_next()
		elif curr2 == None :
			sumt = curr1.get_data() + carried
			curr1 = curr1.get_next()
		else :
			sumt = curr1.get_data() + curr2.get_data() + carried
			curr1 = curr1.get_next()
			curr2 = curr2.get_next()

		summed.insert(sumt % 10)
		carried = sumt/10

	return summed 

def loop_remove(ll):
	'''
	http://www.geeksforgeeks.org/detect-and-remove-loop-in-a-linked-list/
	'''

	slow = ll.get_head()
	fast = ll.get_head().get_next()

	while slow != fast:
		slow = slow.get_next()
		fast = fast.get_next().get_next()

	loop_size = 0
	fast = slow
	while fast.get_next() != slow:
		fast = fast.get_next()
		loop_size = loop_size + 1

	count = 0
	head = ll.get_head()
	loop_end = head
	while count < loop_size:
		loop_end = loop_end.get_next()
		count = count + 1

	while head != loop_end.get_next() :
		head = head.get_next()
		loop_end = loop_end.get_next()

	loop_end.set_next(None)

def rotate_by_k(ll, k):
	if ll == None:
		return

	if k == 0:
		return

	j = ll.size
	rot_start = ll.get_head()
	rot_prev = None
	for x in xrange(0,j-k):
		rot_prev = rot_start
		rot_start = rot_start.get_next()

	head = ll.get_head()
	last = ll.get_last()

	print(rot_start)
	print(rot_prev)
	
	rot_prev.set_next(None)
	print(head)
	ll.set_head(None)
	ll.set_head(rot_start)
	ll.insert(head)


def main():
	"""
	ll = LinkedList()
	ll.dump()

	ll.insert("Papa")
	ll.insert("Puchi")
	ll.insert("Madhu")
	ll.insert("Payel")
	ll.insert("Paro")

	ll.dump()
	"""

	"""
	swap_node(ll, "Puchi", "Madhu")
	ll.dump()

	swap_node(ll, "Papa", "Madhu")
	ll.dump()

	swap_node(ll, "Puchi", "Payel")
	ll.dump()

	swap_node(ll, "Puchi", "Madhu")
	ll.dump()
	"""

	"""
	reverse(ll)
	ll.dump()

	reverse(ll)
	ll.dump()

	print("ll size by iteration %i" % ll.size_iter())
	print("ll size by recursion %i" % ll.size_recur())
	"""

	"""
	left = LinkedList()
	left.insert(5)
	left.insert(10)
	left.insert(15)

	right = LinkedList()
	right.insert(2)
	right.insert(3)
	right.insert(20)

	merged = merge_sorted_new(left, right)
	merged.dump()
	"""
	'''
	ll = LinkedList()
	ll.insert(1)
	ll.insert(2)
	ll.insert(3)
	
	ll.insert(4)
	ll.insert(5)
	ll.insert(6)
	ll.insert(7)
	ll.insert(8)
	ll.insert(9)
	ll.insert(10)
	'''

	#reverse_in_group(ll, 3)
	#ll.dump()

	#reverse_by_nodes(ll)
	#ll.dump()

	num1 = LinkedList()
	num1.insert(5)
	num1.insert(6)
	num1.insert(3)

	num2 = LinkedList()
	num2.insert(8)
	num2.insert(4)
	num2.insert(2)

	sum_of_two(num1, num2).dump()
	print("-----------")
	sum_of_two(num2, num1).dump()

	num1 = LinkedList()
	num1.insert(7)
	num1.insert(5)
	num1.insert(9)
	num1.insert(4)
	num1.insert(6)

	num2 = LinkedList()
	num2.insert(8)
	num2.insert(4)

	print("-----------")
	sum_of_two(num1, num2).dump()
	print("-----------")

	
	one = Node(1)
	two = Node(2)
	three = Node(3)
	four = Node(4)
	five = Node(5)

	one.set_next(two)
	two.set_next(three)
	three.set_next(four)
	four.set_next(five)
	five.set_next(two)

	loop = LinkedList()
	loop.set_head(one)

	loop_remove(loop)

	loop.dump()

	rotate = LinkedList()
	rotate.insert(10)
	rotate.insert(20)
	rotate.insert(30)
	rotate.insert(40)
	rotate.insert(50)
	rotate.insert(60)

	rotate_by_k(rotate, 2)

	rotate.dump()


if __name__ == "__main__": main()

		