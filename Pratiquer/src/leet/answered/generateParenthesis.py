class Solution(object):
    def generateParenthesis(self, n):
        """
        :type n: int
        :rtype: List[str]
        """
        def fill(result, s, l, r):
            if l == 0 and r == 0:
                result.append(s)
                return
            if l:
                fill(result, s+'(', l-1, r+1)
            if r:
                fill(result, s+')', l, r-1)
        result = []
        fill(result, '', n, 0)
        return result

def main():
    gen = Solution()

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