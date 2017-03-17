def sum_of_ap(until) :
	if until == 0:
		return 0
	else :
		return until + sum_of_ap(until - 1)


def fibonacci(nth):
	#fib = 0

	if nth <= 1:
		return nth
	else :
		return fibonacci(nth - 1) + fibonacci(nth - 2)


def main():
	print("AP of 3 = %i" % sum_of_ap(3))
	print("AP of 10 = %i" % sum_of_ap(10))
	print("AP of 11 = %i" % sum_of_ap(11))

	print("Fibonacci 0 = %i" % fibonacci(0))
	print("Fibonacci 1 = %i" % fibonacci(1))
	print("Fibonacci 2 = %i" % fibonacci(2))
	print("Fibonacci 3 = %i" % fibonacci(3))
	print("Fibonacci 4 = %i" % fibonacci(4))
	print("Fibonacci 5 = %i" % fibonacci(5))
	print("Fibonacci 6 = %i" % fibonacci(6))
	print("Fibonacci 7 = %i" % fibonacci(7))
	print("Fibonacci 8 = %i" % fibonacci(8))
	print("Fibonacci 9 = %i" % fibonacci(9))
	print("Fibonacci 10 = %i" % fibonacci(10))
	print("Fibonacci 11 = %i" % fibonacci(11))


if __name__ == "__main__": main()


