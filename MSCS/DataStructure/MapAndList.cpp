#include <iostream>
#include <fstream>
#include <cstring>
#include <list>
#include <map>

using namespace std;

/**
* Trims a string
*/
string trim(string strToTrim)
{

    string trimmedString(strToTrim);

    size_t nonWhiteCharPos = trimmedString.find_last_not_of(' ');


    if(nonWhiteCharPos != string::npos) {
        // Right trim
        trimmedString.erase(nonWhiteCharPos + 1);
        nonWhiteCharPos = trimmedString.find_first_not_of(' ');

        // Left trim
        if(nonWhiteCharPos != string::npos) {
            trimmedString.erase(0, nonWhiteCharPos);
        }
    }  else {
        // A string with just whitespace
        trimmedString.erase(trimmedString.begin(), trimmedString.end());
    }

  return trimmedString;
}

/**
* Splits input line by comma and adds the parts in a list
*/
void parseInput(string inputLine, list<string> &parsedInputLine) {
    string delimiter = ",";
    string ip = trim(inputLine.substr(0, inputLine.find(delimiter)));
    string visitedSite = trim(inputLine.substr(inputLine.find(delimiter) + 1, inputLine.size()));
    parsedInputLine.push_back(ip);
    parsedInputLine.push_back(visitedSite);
}

/**
* Prepares the map for IP and visited sites
*/
void persistData(map<string, list<string> > &ipTable, list<string> &parsedInputLine){

    string ip = parsedInputLine.front();
    string visitedSite = parsedInputLine.back();
    map<string, list<string> >::iterator elemIt = ipTable.find(ip);

    if(elemIt == ipTable.end()) {
        // no element yet inserted for the IP
        list<string> siteList;
        siteList.push_back(visitedSite);
        ipTable[ip] = siteList;
    } else {
        // going to add the new site in the list associated to this IP
        elemIt->second.push_back(visitedSite);
    }
}

/**
* Prints the IP table
*/
void printIPTable(map<string, list<string> > &ipTable){

    for (map<string, list<string> >::iterator elemIt = ipTable.begin(); elemIt != ipTable.end(); ++elemIt) {
        cout << elemIt->first << ": ";
        list<string> siteList = elemIt->second;
        for(list<string>::iterator siteIt = siteList.begin(); siteIt != siteList.end(); ++siteIt) {
            cout << *siteIt << " ";
        }
        cout << endl;
    }

}

int main(int argc, char** argv) {

    if(argc < 2) {
        cerr << "No input file name!" << endl;
        return -1;
    }

    char *inputFileName = argv[1];

    ifstream inputFileStream(inputFileName);

    string inputLine;
    list<string> parsedInputLine;
    map<string, list<string> > ipTable;

    while(inputFileStream) {
        getline(inputFileStream, inputLine);

        parseInput(inputLine, parsedInputLine);

        persistData(ipTable, parsedInputLine);

        inputLine.clear();

        parsedInputLine.clear();
    }

    printIPTable(ipTable);

    return 0;

}



