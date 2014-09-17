#ifndef TOKENUTIL_H
#define TOKENUTIL_H

#include "Token.h"
#include <string>

class TokenUtil
{
    public:
        TokenUtil();
        virtual ~TokenUtil();
        Token* getWordToken();
        Token* getNumToken();
        Token* getOperandToken();
    protected:
    private:


};

#endif // TOKENUTIL_H
