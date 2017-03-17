def sort(data) :
	'''
	insertion sort procedure
	'''
	# start from the second element 
	for i in range(1, len(data)) :
		# pick the key for comparison
		key = data[i]
		# find the subarray length for comparison
		j = i - 1
		# compare, if key is less than current indexed value then move 
		# the current value one space right, else this is the end of 
		# comparison. 
		while (j >= 0 and data[j] > key) :
			data[j+1] = data[j]
			j = j - 1
		# case 1 : key was greater than the last value in subarray, 
		# j+1=i
		# case 2 : key was the less than the first value in subarray,
		# j+1=0
		# case 3 : key falls somewhere in middle, 0 < j < i - 1 
		data[j+1] = key
	return data


d = [6, 7, 2, 0, 1, 5, 8, 3, 9, 4]

print(d)
sd = sort(d)

print(sd)


