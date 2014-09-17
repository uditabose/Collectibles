#include <iostream>

using namespace std;

template<typename T>
void printArray(T arr[], int n) {
    for(int i = 0; i < n; i++) {
        cout << arr[i] << ", ";
    }
    cout << endl;
}

template<typename T>
void insertionSortAsc(T anArray[], int length) {

    int fs = 0;
    for(int i = 1; i < length; i++) {
        fs = anArray[i];
        //cout << fs << endl;
        for(int j = i - 1; (j >= 0 && (anArray[j] > fs)); j--) {
            anArray[j + 1] = anArray[j];
            anArray[j] = fs;
        }
        //cout << i << ": ";

    }

}

template<typename T>
void insertionSortDsc(T anArray[], int length) {

    int fs = 0;
    for(int i = 1; i < length; i++) {
        fs = anArray[i];
        //cout << fs << endl;
        for(int j = i - 1; (j >= 0 && (anArray[j] < fs)); j--) {
            anArray[j + 1] = anArray[j];
            anArray[j] = fs;
        }
        //cout << i << ": ";

    }

}


int main() {
    int arr[] = {10, 5, 1, 2, 7, 9, 10, 8};
    insertionSortDsc(arr, 8);
    cout << "Final Sorted Array" << endl;
    printArray(arr, 8);
}
