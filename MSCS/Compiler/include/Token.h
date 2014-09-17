/**
* Class to represent a token class
*/

#ifndef TOKEN_H
#define TOKEN_H

#include <string>

class Token
{
    public:
        Token(std::string tokType, std::string tokRegEx);
        virtual ~Token();
        /**
        * returns the token type
        */
        std::string getTokenType();
        /**
        * returns the regular expression
        * associated with the token type
        */
        std::string getTokenRegEx();
    protected:
    private:
        // the type of token - tokword, toknum
        std::string tokenType;
        // the regular expression associated with the token class
        std::string tokenRegEx;

};

#endif // TOKEN_H
