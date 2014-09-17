#ifndef LEXEME_H
#define LEXEME_H

#include <string>
#include "Token.h"

class Lexeme
{
    public:
        Lexeme();
        Lexeme(std::string val, Token* tok);
        void setValue(std::string val);
        void setToken(Token *tok);
        std::string getValue();
        Token* getToken();
        virtual ~Lexeme();
    protected:
    private:
        std::string value;
        Token *token;
};

#endif // LEXEME_H
