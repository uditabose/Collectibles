'''
http://www.geeksforgeeks.org/largest-sum-contiguous-subarray/

Book : 6.1
'''

def max_contiguous_subsequence(seq) :
	sum_till_now = seq[0]
	max_so_far = seq[0]

	for e in seq[1::] :
		sum_till_now = max(e, sum_till_now + e)
		max_so_far = max(max_so_far, sum_till_now)

	return max_so_far



def main() :
	seq = [-2, -3, 4, -1, -2, 1, 5, -3] #[5, 15, -30, 10, -5, 40, 10]
	print(max_contiguous_subsequence(seq))

	print("----------------")

	seq = [5, 15, -30, 10, -5, 40, 10]
	print(max_contiguous_subsequence(seq))

if __name__ == "__main__" :
	main()
	
