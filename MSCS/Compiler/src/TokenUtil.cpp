#include "TokenUtil.h"

TokenUtil::TokenUtil()
{
    //ctor
}

TokenUtil::~TokenUtil()
{
    //dtor
}

static Token numberToken("toknum", "\\b\\d*\\.?\\d+([eE][-+]?\\d+)?\\b");
static Token wordToken("tokword", "\\b[a-zA-Z]\\w*\\b");
static Token operandToken("tokop", "\\b\\s*(<>|<=|=>|=|>|<)\\s*\\b");


Token* TokenUtil::getWordToken(){
    return &wordToken;
}

Token* TokenUtil::getNumToken(){
    return &numberToken;
}

Token* TokenUtil::getOperandToken(){
    return &operandToken;
}

