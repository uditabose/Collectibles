#include <iostream>
#include <string>
#include <cstdlib>
#include "Scanner.h"
#include "Token.h"
#include "TokenUtil.h"


using namespace std;

int main(int argc, char** argv)
{
    // validate the input parameter
    if(argc < 2) {
        cerr << "No filename provided" << endl;
        return -1;
    }

    char *filePath = argv[1];
    Scanner theScanner(filePath);

    Lexeme* lex;

    // request tokens till the end of file
    while(true) {
        lex = theScanner.getToken();

        if(lex == NULL || lex->getValue() == "")
            break;

        cout << lex->getValue() << " :: " << lex->getToken()->getTokenType() << endl;
    }

    return 0;
}
