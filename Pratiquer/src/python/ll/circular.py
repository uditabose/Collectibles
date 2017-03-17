class Node :
	'''
	node for linked list
	'''
	def __init__(self, data=None, next_node=None) :
		self._data = data
		self.next_node = next_node

	def data(self):
		return self._data

	def next(self):
		return self.next_node

	def set_next(self, next_node):
		self.next_node = next_node

	def __str__(self):
		if self._data == None:
			return "None"
		return "%s" % (str(self._data))

	def __repr__(self):
		return self.__str__()

	def dump(self):
		dump_str = str(self)
		nxt = self.next_node

		while nxt != self:
			dump_str += "->" + str(nxt)
			nxt = nxt.next()

		print(dump_str)
		


def part_it(clircular):
	'''
	http://www.geeksforgeeks.org/split-a-circular-linked-list-into-two-halves/
	'''
	if clircular == None:
		return

	if clircular.next() == None:
		return clircular

	tail = clircular
	size = 0

	while tail.next() != clircular :
		tail = tail.next()
		size = size + 1

	mid = clircular
	for x in xrange(0, size/2):
		mid = mid.next()

	mid_next = mid.next()

	mid.set_next(clircular)
	tail.set_next(mid_next)

	clircular.dump()
	mid_next.dump()

def insert_sorted(sorted, new_data):
	'''
	http://www.geeksforgeeks.org/sorted-insert-for-circular-linked-list/
	'''
	if new_data == None:
		return sorted

	new_node = Node(new_data)

	if sorted == None:
		return new_node

	tail = sorted
	while tail.next() != sorted :
		tail = tail.next()

	if new_data < sorted.data():
		new_node.set_next(sorted)
		tail.set_next(new_node)
		return new_node

	if new_data > tail.data() :
		tail.set_next(new_node)
		new_node.set_next(sorted)
		return sorted

	curr = sorted
	prev = None
	while curr.data() < new_data :
		prev = curr
		curr = curr.next()
	
	prev.set_next(new_node)
	new_node.set_next(curr)

	return sorted
	#sorted.dump()

def main():
	one = Node(1)
	two = Node(2)
	three = Node(3)
	four = Node(4)
	five = Node(5)
	six = Node(6)
	seven = Node(7)

	one.set_next(two)
	two.set_next(three)
	three.set_next(four)
	four.set_next(five)
	five.set_next(six)
	six.set_next(seven)
	seven.set_next(one)

	clircular = one

	part_it(clircular)
	print("----------------")

	one.set_next(two)
	two.set_next(four)
	#three.set_next(four)
	four.set_next(five)
	five.set_next(six)
	six.set_next(seven)
	seven.set_next(one)

	sortd = one

	#insert_sorted(sorted, 3)
	sortd.dump()
	sortd = insert_sorted(sortd, 0)
	print("----------------")
	sortd.dump()
	sortd = insert_sorted(sortd, 8)
	print("----------------")
	sortd.dump()
	print("----------------")
	sortd = insert_sorted(sortd, 3)
	sortd.dump()

if __name__ == "__main__": main()