#include <iostream>

using namespace std;

/**
* Trims a string
*/
string& trim(string& strToTrim)
{
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

  return strToTrim;
}

int main() {
    string st1("  Papa  ");
    string st2("  Papa");
    string st3("Papa  ");
    string st4("    ");
    cout << trim(st1) << endl;
    cout << trim(st2) << endl;
    cout << trim(st3) << endl;
    cout << trim(st4) << endl;


}

