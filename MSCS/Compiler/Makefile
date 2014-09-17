all: Compiler

Compiler: main.o Token.o Lexeme.o TokenUtil.o Scanner.o
	g++ main.o Token.o Lexeme.o TokenUtil.o Scanner.o -o Compiler
	mkdir build
	mv -f *o build
	mv -f Compiler build

main.o:
	g++ -Iinclude -c main.cpp

Token.o: 
	g++ -Iinclude -c "src/Token.cpp"

Lexeme.o: 
	g++ -Iinclude -c "src/Lexeme.cpp"

TokenUtil.o: 
	g++ -Iinclude -c "src/TokenUtil.cpp"

Scanner.o: 
	g++ -Iinclude -c "src/Scanner.cpp"

clean:
	rm -rf build

