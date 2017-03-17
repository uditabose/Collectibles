
def swap(data, i, j) :
	'''
	swaps data in an array
	'''
	temp = data[i]
	data[i] = data[j]
	data[j] = temp



def sort(data) : 
	'''
	bubble sort procedure
	'''

	for i in range(0, len(data)) :
		for j in range(0, len(data)) : 

			# in the first round it pushes the large values on left of array
			# in the subsequent trips the smaller values on right are pushed on left
			if data[i] < data[j] :
				swap(data, j, i)

	return data

d = [6, 7, 2, 0, 1, 5, 8, 3, 9, 4]

print(d)
sd = sort(d)

print(sd)
 
