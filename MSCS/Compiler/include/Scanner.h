
#ifndef SCANNER_H
#define SCANNER_H

#include <string>
#include <fstream>
#include <iostream>
#include <list>
#include "Lexeme.h"


class Scanner
{
    public:
        Scanner(char* filePath);
        virtual ~Scanner();
        /**
        * returns the token
        */
        Lexeme* getToken();


    protected:
    private:

        /**
        * input stream for the file
        */
        std::ifstream fileStream;
        /**
        * the list of lexemes tokens
        */
        std::list<Lexeme*> lexemeList;
        /**
        * scans the next line and populates the lexeme list
        */
        void scanNext();
        /**
        * scans for a word in the line
        */
        int scanWord(std::string scanLine, int startPosition);
        /**
        * scans for a number in the line
        */
        int scanNumber(std::string scanLine, int startPosition);
        /**
        * scans for an operand in the line
        */
        int scanOperand(std::string scanLine, int startPosition);
        /**
        * returns the top element of the list, and removes it from the list
        */
        Lexeme* getFirstToken();
};

#endif // SCANNER_H
