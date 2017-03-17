def swap(data, i, j) :
	'''
	swaps data in an array
	'''
	temp = data[i]
	data[i] = data[j]
	data[j] = temp

def sort(data) :
	for i in range(0, len(data) - 1) :
		# the index of min value
		idx = i
		for j in range(i + 1, len(data)):
			if data[j] < data[idx] :
				# reset min value index
				idx = j

		# swap for the i'th min value by placing the value in it's correct position
		swap(data, i, idx)
	
	return data

d = [6, 7, 2, 0, 1, 5, 8, 3, 9, 4]

print(d)
sd = sort(d)

print(sd)
 