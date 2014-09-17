#include "Lexeme.h"

using namespace std;

Lexeme::Lexeme():value(""), token(NULL)
{
    //ctor
}

Lexeme::~Lexeme()
{
    //dtor
}

Lexeme::Lexeme(string val, Token* tok):
    value(val), token(tok){

}

void Lexeme::setValue(string val){
    value = val;
}

void Lexeme::setToken(Token *tok){
    token = tok;
}

string Lexeme::getValue(){
    return value;
}

Token* Lexeme::getToken(){
    return token;
}
