#include "Scanner.h"
#include "TokenUtil.h"
#include <iostream>
#include <algorithm>


using namespace std;

// ------------ Private methods --------------------------------
/**
* trims the blank spaces fromstart and end of string
*/
void trim(string& strToTrim);
/**
* checks for a blank space
*/
int checkForBlank(string scanLine, int startPosition, int currPosition);
// ------------ Private methods --------------------------------

Scanner::Scanner(char* filePath):fileStream(filePath)
{
    //ctor
}

Scanner::~Scanner()
{
    //dtor
}

Lexeme* Scanner::getToken() {

    // if the list already has elements, then return
    if(!lexemeList.empty()) {
        return getFirstToken();
    }

    // the list is empty, so read next line
    scanNext();

    // if there are new tokens, then return the frst one
    if(!lexemeList.empty()) {
        return getFirstToken();
    }

    // close the stream
    fileStream.close();

    return NULL;
}

// ------------ Private Helper Methods --------------
void Scanner::scanNext(){


    string nextLine("");
    int currentPosition = 0;

    // skipping the blanks, if any at the start of the file
    while(getline(fileStream, nextLine) && nextLine.empty()) {
        trim(nextLine);
    }

    // comment starts with '{'
    bool isComment = (!nextLine.empty() && nextLine.at(0) == '{');

    // start of comment, so skip it
    while(isComment) {
        // skip till closing bracket '}' is there
        isComment = (!nextLine.empty() && (nextLine[nextLine.length() - 1] != '}'));

        if(!getline(fileStream, nextLine)) {
            return;
        };

        trim(nextLine);
    }

    // skipping the blanks, if any after the comments
     while(nextLine.empty()) {

        if(!getline(fileStream, nextLine)) {
            return;
        };

        trim(nextLine);
    }

    trim(nextLine);
    char fc; // the first character for the token
    int progress = 0; // characters read and processed from the scanned line
    while(currentPosition < nextLine.length()) {
        fc = nextLine[currentPosition];

        if((fc >= 'a' && fc <= 'z') || (fc >= 'A' && fc <= 'Z')) {
            // starts with alpha character, so a word token
            progress = scanWord(nextLine, currentPosition);
        } else if(fc >= '0' && fc <= '9') {
            // starts with numeric character, so a number token
            progress = scanNumber(nextLine, currentPosition);
        }  else {
            // starts with non alpha-numeric character, so a operand token
            progress = scanOperand(nextLine, currentPosition);
        }

        // increament the characters read
        currentPosition = currentPosition + progress;
    }
}

int Scanner::scanOperand(string scanLine, int startPosition){
    char currChar;
    string resultString("");

    int progress = 0;
    do {
        currChar = scanLine[startPosition + progress];

        progress++;

        // read and recognize if it is a valid operand keyword
        if(currChar == '=' || currChar == '<' || currChar == '<'
                || currChar == ',' || currChar == ';' || currChar == '('  || currChar == ')') {
            resultString += currChar;

        } else {
            // re-adjusts the progress, in case there is no blank space between two tokens
            progress = checkForBlank(scanLine, startPosition, progress);
            break;
        }

    } while((startPosition + progress) < scanLine.length());

    // create a new operand token with value, and push it on the list
    if(!resultString.empty()) {
        TokenUtil tokUtil;
        Lexeme* opLexeme = new Lexeme(resultString, tokUtil.getOperandToken());
        lexemeList.push_back(opLexeme);
    }

    return progress;

}

int Scanner::scanWord(string scanLine, int startPosition){

    char currChar;
    string resultString("");

    int progress = 0;

    do {
        currChar = scanLine[startPosition + progress];
        progress++;
        if((currChar >= 'a' && currChar <= 'z') || (currChar >= 'A' && currChar <= 'Z')
                || (currChar >= '0' && currChar <= '9')) {
            // if the character is alpha-numeric, then it is valid
            resultString += currChar;

        } else {
            // re-adjustment for blanks
            progress = checkForBlank(scanLine, startPosition, progress);
            break;
        }

    } while((startPosition + progress) < scanLine.length());

    TokenUtil tokUtil;
    if(!resultString.empty()) {
        // a tokword
        Lexeme* wordLexeme = new Lexeme(resultString, tokUtil.getWordToken());
        lexemeList.push_back(wordLexeme);
    }

    return progress;

}

int Scanner::scanNumber(string scanLine, int startPosition){
    char currChar;
    string resultString("");

    int progress = 0;
    do {
        currChar = scanLine[startPosition + progress];
        progress++;
        if(currChar >= '0' && currChar <= '9') {
            // accept only numbers
            resultString += currChar;

        } else {
            progress = checkForBlank(scanLine, startPosition, progress);
            break;
        }

    } while((startPosition + progress) < scanLine.length());

    TokenUtil tokUtil;
    if(!resultString.empty()) {
        // a toknum
        Lexeme* numLexeme = new Lexeme(resultString, tokUtil.getNumToken());
        lexemeList.push_back(numLexeme);
    }

    return progress;
}

Lexeme* Scanner::getFirstToken(){
    Lexeme *retLex = lexemeList.front();
    lexemeList.pop_front();
    return retLex;
}

/**
* Trims a string
*/
void trim(string& strToTrim)
{
  std::replace( strToTrim.begin(), strToTrim.end(), '\t', ' ');
  size_t nonWhiteCharPos = strToTrim.find_last_not_of(' ');

  if(nonWhiteCharPos != string::npos) {
    // Right trim
    strToTrim.erase(nonWhiteCharPos + 1);
    nonWhiteCharPos = strToTrim.find_first_not_of(' ');

    // Left trim
    if(nonWhiteCharPos != string::npos) {
        strToTrim.erase(0, nonWhiteCharPos);
    }
  }  else {
    // A string with just whitespace
    strToTrim.erase(strToTrim.begin(), strToTrim.end());
  }
}

/**
Checks if the previous position is a blank
*/

int checkForBlank(string scanLine, int startPosition, int currPosition) {
    if(scanLine[startPosition + currPosition - 1] == ' ') {
        return currPosition;
    } else {
        return (currPosition - 1);
    }
}



