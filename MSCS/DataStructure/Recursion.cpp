#include <iostream>
#include <cstring>
#include <cassert>
#include <cstdlib>
#include <algorithm>
#include <ctime>

using namespace std;

int recurseWithoutReturn(int seed){

    if(seed <= 0) {
        return seed;
    }

    int fib = seed + recurseWithoutReturn(seed - 1);

    cout << "Now the seed is : " << seed << " And fib is : " << fib << endl;

    return fib;
}

int main() {

    int fibb = recurseWithoutReturn(17);

    cout << "Final : " << fibb << endl;
    return 0;
}
