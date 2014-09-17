#include <iostream>
using namespace std;

int compare(int a, int b) {
    if(a == b)
        return 0;
    else if(a > b)
        return 1;
    else
        return -1;
}

bool binarySearch(int* arr, int from, int to, int toSearch) {
    int hi = to;
    int lo = from;
    int mid;

    if(hi == lo) {
        return (compare(arr[hi], toSearch) == 0);
    } else if(hi == lo + 1){
        return (compare(arr[hi], toSearch) == 0 || compare(arr[lo], toSearch) == 0);
    } else {
        mid = (hi + lo) / 2;
        int cmp = compare(arr[mid], toSearch);
        if(cmp == 0)
            return true;
        else if(cmp == 1)
            binarySearch(arr, lo, mid, toSearch);
        else
            binarySearch(arr, mid, to, toSearch);
    }
}


int main() {

    int arr[] = {12, 17, 20, 23, 34, 39, 45, 79, 90, 99, 345};

    cout << " The value 79 " << (binarySearch(arr, 0, 11, 79) ? "is THERE" : " NOT there") << endl;
    cout << " The value 20 " << (binarySearch(arr, 0, 11, 20) ? "is THERE" : " NOT there") << endl;

    cout << " The value 12 " << (binarySearch(arr, 0, 11, 12) ? "is THERE" : " NOT there") << endl;
    cout << " The value 99 " << (binarySearch(arr, 0, 11, 99) ? "is THERE" : " NOT there") << endl;
    cout << " The value 34 " << (binarySearch(arr, 0, 11, 34) ? "is THERE" : " NOT there") << endl;

    cout << " The value 11 " << (binarySearch(arr, 0, 11, 11) ? "is THERE" : " NOT there") << endl;
    cout << " The value 200 " << (binarySearch(arr, 0, 11, 200) ? "is THERE" : " NOT there") << endl;

    cout << " The value 35 " << (binarySearch(arr, 0, 11, 35) ? "is THERE" : " NOT there") << endl;

    return 0;
}
