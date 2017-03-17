def longest_contiguous_subsequence(seq) :
	#print("Here I am")
	included = [True]	
	max_sum_seq = [seq[0]]
	
	#for i in range(0, len(seq)) :
		#included.append(False)
		
	for i in range(1, len(seq)) :
		if included[-1] :
			if max_sum_seq[-1] + seq[i] > max_sum_seq[-1] :
				max_sum_seq.append(max_sum_seq[-1] + seq[i])
				included.append(True)
			else :
				max_sum_seq.append(max_sum_seq[-1])
				included.append(False)
				
		else :
			sum_with_last = max_sum_seq[-2] + seq[i-1] + seq[i]
			sum_wo_last = max_sum_seq[-1] + seq[i]
			print("sum_with_last = %i :: sum_wo_last = %i" % (sum_with_last, sum_wo_last))
			if sum_with_last > sum_wo_last :
				max_sum_seq.append(sum_with_last)
				included[-1] = True
				included.append(True)
			else :
				max_sum_seq.append(seq[i])
				included.append(True)
	print(seq)		
	print(included)
	print(max_sum_seq) 

def main() :
	seq = [5, 15, -30, 10, -5, 40, 10]
	longest_contiguous_subsequence(seq)

if __name__ == "__main__" :
	main()
	
