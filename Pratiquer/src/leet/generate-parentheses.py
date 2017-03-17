'''
https://leetcode.com/problems/generate-parentheses/
'''

from sets import Set

class GenerateParentheses(object) :
	def __init__(self):
		pass

	def generateParenthesis(self, n):

		if n == 0:
			return Set([])
		elif n == 1 :
			return Set(["()"])
		else :
			parens = self.generateParenthesis(n-1)
			n_parens = Set([])
			for p in parens:
				n_parens.add("()" + p)
				n_parens.add(p + "()")
				n_parens.add("(" + p + ")")

			print(len(n_parens))
			return list(n_parens)


def main():
	gen = GenerateParentheses()

	parens = gen.generateParenthesis(0)
	print("0 = %s" % str(parens))

	parens = gen.generateParenthesis(1)
	print("1 = %s" % str(parens))

	parens = gen.generateParenthesis(2)
	print("2 = %s" % str(parens))

	parens = gen.generateParenthesis(3)
	print("3 = %s" % str(parens))

	parens = gen.generateParenthesis(4)
	print("4 = %s" % str(parens))


if __name__ == "__main__" : main()

# 