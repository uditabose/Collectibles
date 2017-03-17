class Node :

	def __init__(self, data=None, next=None, prev=None):
		self.data = data
		self.next = next
		self.prev = prev

	def __repr__(self):
		return self.__str__()

	def __str__(self):
		if self.data == None:
			return "None"
		return str(self.data)

class Stack (object):
	"""
    Stack is a linear data structure which follows a particular 
    order in which the operations are performed. The order may be 
    LIFO(Last In First Out) or FILO(First In Last Out).

    Mainly the following three basic operations are performed in the stack:
    
    Push: Adds an item in the stack. If the stack is full, 
          then it is said to be an Overflow condition.

    Pop: Removes an item from the stack. The items are popped 
         in the reversed order in which they are pushed. If the 
         stack is empty, then it is said to be an Underflow condition.
    
    Peek: Get the topmost item.
	"""
	def __init__(self):
		self.head = None


	def push(self, data):
		if data == None:
			return

		if self.head == None:
			self.head = Node(data)
		else :
			node = Node(data)
			node.next = self.head
			self.head.prev = node
			self.head = node

	def pop(self):
		if self.head == None:
			return None
		else :
			ret = self.head
			new_head = self.head.next

			if new_head != None:
				new_head.prev = None
			
			ret.next = None
			self.head = new_head
			return ret

	def peek(self):
		if self.head == None:
			return None
		else :
			return self.head.data

	def empty(self):
		return (self.head == None)

	def __repr__(self):
		return self.__str__()

	def __str__(self):
		rpr = None
		if self.head == None:
			rpr = "None"
		else :
			rpr = str(self.head)
		return "<%s>" % (rpr)

	def dump(self):
		if self.head == None:
			return "None"

		curr = self.head
		d_str = ""	
		while curr != None:
			d_str = "%s:%s" % (str(curr), d_str)
			curr = curr.next
		return d_str


def infix_to_postfix(exprn):

	'''
	 http://geeksquiz.com/stack-set-2-infix-to-postfix/
	'''
	if exprn == None:
		return

	ops = "+-*/"
	digits = "0123456789"

	v_stack = Stack()
	op_stack = Stack()

	for e in exprn:
		if e == " ":
			continue

		if e == "(" :
			op_stack.push(e)
		elif e == ")" :
			to_loop = not op_stack.empty()
			while not op_stack.empty():
				op = op_stack.pop()

				if str(op) ==  "(" :
					break
				else  :
					v_stack.push(op)
	
		elif e in digits :
			v_stack.push(e)
		elif e in ops :
			if op_stack.empty():
				op_stack.push(e)
			else :
				pk = op_stack.peek()
				if str(pk) == "(":
					op_stack.push(e)
				elif ops.index(op_stack.peek()) > ops.index(e):
					v_stack.push(op_stack.pop())
					op_stack.push(e)
				else :
					op_stack.push(e)

	while not op_stack.empty():
		v_stack.push(op_stack.pop())

	return v_stack

def reverse_string(a_str):
	'''
	http://quiz.geeksforgeeks.org/stack-set-3-reverse-string-using-stack/
	'''
	if a_str == None or a_str == "":
		return a_str

	stack  = Stack()
	for e in a_str :
		stack.push(e)

	b_str = ""
	while not stack.empty() :
		b_str = b_str + str(stack.pop())
	return b_str

def balanced_paren(exprn):
	'''
	http://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/
	'''
	if exprn == None or exprn == "" or len(exprn) == 1:
		return False

	open_paren = "({["
	close_paren = ")}]"

	p_stack = Stack()
	for e in exprn:
		if e in open_paren :
			p_stack.push(e)
		elif e in close_paren :
			last = p_stack.pop()
			if last == None:
				return False
			last = str(last)
			if open_paren.index(last) != close_paren.index(str(e)) :
				return False

	return p_stack.empty()

def next_greatest_elem(arr):
	'''
	http://www.geeksforgeeks.org/next-greater-element/
	'''

	if arr == None or len(arr) == 0:
		return

	min_stack = Stack()
	count = len(arr) - 2
	min_stack.push(-1)
	max_right = arr[-1]
	
	while count >= 0:

		if arr[count + 1] > arr[count] :
			min_stack.push(arr[count + 1])
		elif  min_stack.peek() > arr[count] :
			min_stack.push(min_stack.peek())
		elif max_right > arr[count] :
			min_stack.push(max_right)
		else :
			min_stack.push(-1)

		max_right = max(max_right, arr[count + 1])
		count = count - 1

	for x in arr:
		print("%i ---> %s" % (x, str(min_stack.pop()) ))

	print("============")

def stock_span(stocks):
	'''
	http://www.geeksforgeeks.org/the-stock-span-problem/
	'''
	if stocks == None or len(stocks) == 0:
		return None

	span = Stack()
	span.push(1)
	count = 1

	max_span_till_now = 
	while count < len(stocks):
		pass


def main():
	'''
	stack = Stack()
	stack.push(1)
	stack.push(2)
	stack.push(3)
	stack.push(4)
	stack.push(5)
	stack.push(6)
	stack.push(7)
	stack.push(8)
	stack.push(9)

	print(stack.dump())
	'''

	'''
	deleted = stack.pop()

	print("-----------")
	print(stack.dump())
	print("Deleted %s" % deleted)
	'''

	'''
	stack = stack.reverse()
	print("-----------")
	#print(stack1.dump())
	print(stack.dump())
	'''

	# 3 + 4 / 2 - 1 =>> 3:4:2:/:1:+:-

	
	exprn = "3 + 4"
	p_fix = infix_to_postfix(exprn)
	print("-----------")
	print(p_fix.dump())


	exprn = "3 + 4 / 2"
	p_fix = infix_to_postfix(exprn)
	print("-----------")
	print(p_fix.dump())

	exprn = "3 + 4 / 2 - 1"
	p_fix = infix_to_postfix(exprn)
	print("-----------")
	print(p_fix.dump())
	

	exprn = "(3 + 4)"
	p_fix = infix_to_postfix(exprn)
	print("-----------")
	print(p_fix.dump())

	exprn = "(3 + 4) / 2 - 1"
	p_fix = infix_to_postfix(exprn)
	print("-----------")
	print(p_fix.dump())

	a_str = "My name is Papa" 
	# "apaP si eman yM"
	#  apaP si eman yM
	b_str = reverse_string(a_str)

	print(b_str) 

	print("-----------")
	exprn = "()"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[}"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "]"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "]]]"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[()]"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[(\{\})]"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[({"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[{()"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[(])"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	exprn = "[()]\{\}{[()()]()}"
	balanced = "Balanced" if balanced_paren(exprn) else "Not Balanced"
	print("%s # %s" % (exprn, balanced))

	print("-----------")
	arr = [10, 20, 30, 40]
	next_greatest_elem(arr)
	#print(next_greatest_elem(arr).dump())

	arr = [40, 30, 20, 10]
	next_greatest_elem(arr)
	#print(next_greatest_elem(arr).dump())

	arr = [4, 5, 20, 10]
	next_greatest_elem(arr)
	#print(next_greatest_elem(arr).dump())

	arr = [4, 5, 2, 25]
	next_greatest_elem(arr)
	#print(next_greatest_elem(arr).dump())

	arr = [45, 5, 2, 25]
	next_greatest_elem(arr)

	arr = [11, 13, 21, 3]
	next_greatest_elem(arr)

	arr = [23, 32, 24, 28, 36]
	next_greatest_elem(arr)
	#print(next_greatest_elem(arr).dump())


if __name__ == "__main__": main()


