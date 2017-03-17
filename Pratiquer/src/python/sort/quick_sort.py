
import pdb


def swap(data, i, j) :
        '''
        swaps data in an array
        '''
        temp = data[i]
        data[i] = data[j]
        data[j] = temp


def partition(data, left, right) :
	'''
	finds out partition index, and suffles the data array
	'''

	# the pivot, whose position will be determined
	pivot = data[right]
	
	# index of the pivot element after re-positioning, init = left (must)
	part_idx = left
	
	for i in range(left, right) :
		if (data[i] < pivot) :
			swap(data, i, part_idx)
			part_idx = part_idx + 1
	
	swap(data, part_idx, right)
	
	#print("partition %s" %  str(data))
	return part_idx

#def c = 0


def quick_sort(data, left, right) :
	'''
	quick sort procedure
	'''

	# find partition 
	if left < right :
		p = partition(data, left, right)
		#print ("left = %d : p = %d : right = %d" %  (left, p, right))	
		
		quick_sort(data, left, p - 1)
		quick_sort(data, p + 1, right) 
	
	return data

def sort(data) :
	return quick_sort(data, 0, len(data) - 1)


d = [6, 7, 2, 0, 1, 5, 8, 3, 9, 4]

print(d)
sd = sort(d)

print(sd)

