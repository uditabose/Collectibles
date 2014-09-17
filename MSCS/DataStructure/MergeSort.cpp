#include <iostream>
#include <cstring>
#include <cassert>
#include <cstdlib>
#include <algorithm>
#include <ctime>

using namespace std;

/**
* prints an array interspaced by a separator
*/
template <typename T>
void printArray(T* arrayOfT, int noOfElem, char* sparator=", ") {
    for(int elCnt = 0; elCnt < noOfElem; elCnt++) {
        cout << *arrayOfT++ << sparator;
    }
    cout << endl;
}

/**
* compares two values for ascending sort
*/
template <class Item>
bool compareAscend(Item leftData, Item rightData) {
    return (leftData > rightData);
}

/**
* compares two values for descending sort
*/
template <class Item>
bool compareDescend(Item leftData, Item rightData) {
    return (leftData < rightData);
}

template <class Item, class SizeType>
void merge(Item data[], SizeType leftSize, SizeType rightSize, bool (*compare)(Item, Item), int *noOfTimesCopied)
{
    Item* temp = NULL;
    int copiedLeft = 0;
    int copiedRight = 0;
    int copied = 0;


    temp = new Item[leftSize + rightSize];
    while((copiedLeft < leftSize) && (copiedRight < rightSize))
    {
        if(compare(data[copiedLeft], (data+leftSize)[copiedRight])) {
            temp[copied++] = data[copiedLeft++];
            *noOfTimesCopied = *noOfTimesCopied + 1;
        } else {
            temp[copied++] = (data+leftSize)[copiedRight++];
            *noOfTimesCopied = *noOfTimesCopied + 1;
        }
    }

    //finish copying the left side if there is any
    while(copiedLeft < leftSize) {
        temp[copied++] = data[copiedLeft++];
        *noOfTimesCopied = *noOfTimesCopied + 1;
    }

    //finish copying the right
    while(copiedRight < rightSize) {
        temp[copied++] = (data+copiedLeft)[copiedRight++];
        *noOfTimesCopied = *noOfTimesCopied + 1;
    }

    //copy from temp array back into original array
    for(int i = 0; i < leftSize+rightSize; i++)
        data[i] = temp[i];

    delete [ ] temp;
}

template <class Item, class SizeType>
void mergeSort(Item data[], SizeType arraySize, bool (*compare)(Item, Item), int *dataCopyTimes)
{
    int leftSize;
    int rightSize;

    if(arraySize <= 1)
        return;

    leftSize = arraySize/2;
    rightSize = arraySize - leftSize;
    mergeSort(data, leftSize, compare, dataCopyTimes);
    mergeSort( (data+leftSize), rightSize, compare, dataCopyTimes);
    merge(data, leftSize, rightSize, compare, dataCopyTimes);

    //printArray(data, arraySize);
}


int main(int argc, char** argv)
{
    if(argc < 3) {
        cerr << "Wrong input format. Please enter input as 'HW7 [mode] [list of integers]" << endl;
        cerr << "Valid modes are - A and D" << endl;
        return -1;
    }

    int *arrayToSort = new int[argc - 2] ;

    for(int argCnt = 0; argCnt < argc - 2; argCnt++) {
        arrayToSort[argCnt] = atoi(argv[argCnt + 2]);
    }

    cout << " Input Array -------------------------" << endl;
    printArray(arrayToSort, argc - 2);

    int dataCopyTimes = 0;

    if(strcmp(argv[1], "A")) {
        mergeSort(arrayToSort, argc - 2, compareAscend, &dataCopyTimes);
    } else if(strcmp(argv[1], "D")){
        mergeSort(arrayToSort, argc - 2, compareDescend, &dataCopyTimes);
    } else {
        cerr << " Wrong sorting mode, valid sorting modes are A and D " << endl;
        return -1;
    }

    cout << " Sorted Array -------------------------" << endl;
    printArray(arrayToSort, argc - 2);

    cout << endl;

    cout << " Data copied total : " << dataCopyTimes << endl;

    return 0;
}
