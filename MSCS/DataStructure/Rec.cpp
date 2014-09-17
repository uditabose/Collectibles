//************************************************************
// Author: D.S. Malik
//
// This program uses a recursive function to find the largest
// element in a list.
//************************************************************
#include <iostream>
using namespace std;

int largest(const int list[], int lowerIndex, int upperIndex);
int main()
{
    int intArray[10] = {23, 43, 35, 38, 67, 12, 76, 10, 34, 8};
    cout << "The largest element in intArray: "
         << largest(intArray, 0, 9);
    cout << endl;
    return 0;
}
int largest(const int list[], int lowerIndex, int upperIndex)
{
    int max, counter;
    counter = 1;
    if (lowerIndex == upperIndex) {
        cout << "lowerIndex  :   " << lowerIndex << endl;
        //size of the sublist is one
        return list[lowerIndex];
        counter++;
    } else {
        cout << " lowerIndex : " << lowerIndex << " upperIndex : " << upperIndex << endl;
        max = largest(list, lowerIndex + 1, upperIndex);
        cout << " Finished : counter " << counter << " max : " << max << " lowerIndex : " << lowerIndex << " upperIndex : " << upperIndex << endl;
        if (list[lowerIndex] >= max) {
            return list[lowerIndex];
        } else {
            return max;
        }

    }
}
