#include "Token.h"

using namespace std;

Token::Token(string tokType, string regEx)
{
    //ctor
    tokenType = tokType;
    tokenRegEx = regEx;
}

Token::~Token()
{
    //dtor
}


string Token::getTokenType() {
    return tokenType;
}


string Token::getTokenRegEx() {
    return tokenRegEx;
}
