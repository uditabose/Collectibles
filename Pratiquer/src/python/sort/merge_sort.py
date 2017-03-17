def swap(data, i, j) :
	'''
	swaps data in an array
	'''
	temp = data[i]
	data[i] = data[j]
	data[j] = temp

def do_sort1(data, lo, mid, hi) :
	'''
	left  = data[lo:mid+1]
	right = data[mid:hi]

	print("%i > %i > %i" % (hi, mid, lo)) 
	print("left ::: %s" % str(left))
	print("right ::: %s" % str(right))
	'''

	i = lo
	j = mid
	temp = []
	while i < mid and j < hi:
		if data[i] < data[j]:
			temp.append(data[i])
			i = i + 1
		else :
			temp.append(data[j])
			j = j + 1

	while i < mid :
		temp.append(data[i])
		i = i + 1

	while j < hi :
		temp.append(data[j])
		j = j + 1

	print("%i > %i > %i" % (hi, mid, lo))
	'''
	print("left ::: %s" % str(data[lo:mid]))
	print("right ::: %s" % str(data[mid:hi]))
	print("temp ::: %s" % str(temp))
	'''
	for k in range(0, len(temp)):
		data[k + lo] = temp[k]

	print("data ::: %s" % str(data))

def do_sort(left, right) :
	print("left ::: %s" % str(left))
	print("right ::: %s" % str(right))

	temp = []
	i = j = 0
	while i < len(left) and j < len(right):
		if left[i] < right[j]:
			temp.append(left[i])
			i = i + 1
		else :
			temp.append(right[j])
			j = j + 1

	while i < len(left) :
		temp.append(left[i])
		i = i + 1

	while j < len(right) :
		temp.append(right[j])
		j = j + 1

	print("temp ::: %s" % str(temp))
	return temp

def merge(data, lo=0, hi=0) :
	if len(data) > 1:
		mid = len(data)//2
		left = data[:mid]
		right = data[mid:]
		merge(left)
		merge(right)
		data =  do_sort(left, right)
	
	return data
	

def sort(data) :
	merge(data, 0, len(data))
	return data

d = [6, 7, 2]
#d = [6, 7, 2, 0, 1, 5, 8, 3, 9, 4, 10]

print(d)
sd = sort(d)

print(sd)
