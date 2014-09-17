#include <iostream>
#include <cstring>
#include <cassert>

using namespace std;


//----------------- Linked list implementation -----------------


/**
* Class to represent a Node of Linked List
*/
template <typename T>
class Node {
    public :
        Node(T theValue) : next(0) {
            value = theValue;
        }

        T getValue() {
            return value;
        }

        Node<T>* getNext() {
            return next;
        }

        void setNext(Node<T>* theNext) {
            next = theNext;
        }

    private :
        T value;
        Node<T>* next;
};


/**
* Class to represent Linked list
*/
template <typename T>
class LinkedList {

    public :
        LinkedList() : headNode(0), footNode(0), size(0) {
            // default constructor, do nothing
        }

        LinkedList(T* elements, int noOfElements) : headNode(0), footNode(0), size(0) {
            for(int elemCnt = 0; elemCnt < noOfElements; elemCnt++) {
                insertValue(elements[elemCnt]);
            }
        }

        /**
        * Returns the head node pointer
        */
        Node<T>* getHead() {
            return headNode;
        }

        /**
        * Returns a node at nodeIndex, iff nodeIndex is valid
        */
        Node<T>* getNode(int nodeIndex) {
            if(nodeIndex < 0 || nodeIndex >= size) {
                // invalid index
                return (Node<T>*)0;
            }

            Node<T>* currentNode = headNode;
            int nodeCounter = 0;
            while(currentNode != 0) {
                if(nodeCounter == nodeIndex) {
                    break;
                }

                currentNode = currentNode->getNext();
                nodeCounter++;
            }

            return currentNode;
        }

        /**
        * Inserts a new node at the end of footNode
        */
        void insertNode(Node<T>* aNode) {

            if(headNode == 0) {
                // initializing the list, at first head is eqaul to foot!
                headNode = aNode;
                footNode = headNode;
            } else {
                // and onwards
                footNode->setNext(aNode);
                // foot node reassigned
                footNode = footNode->getNext();
            }
            incrementSize();

        }

        /**
        * Inserts a new node at the end of footNode,onvenience method
        */
        void insertValue(T aValue) {
            Node<T>* aNode = new Node<T>(aValue);
            // cout << " Node entering " << aNode << endl;
            insertNode(aNode);

        }

        /**
        * Removes the node at nodeIndex
        */
        T removeNode(int nodeIndex) {

            assert(nodeIndex >= 0 && nodeIndex < size);

            Node<T>* nodeToRemove = getNode(nodeIndex);
            Node<T>* previousNode = getNode(nodeIndex - 1);

            if(previousNode == 0) {
                // Head node is being removed, headNode reassigned
                headNode = nodeToRemove->getNext();
            } else {
                previousNode->setNext(nodeToRemove->getNext());
            }

            T theValue = nodeToRemove->getValue();

            // decrease the size
            decrementSize();

            // delete node
            delete nodeToRemove;

            return theValue;
        }

    protected :
        int getSize() {
            return size;
        }

    private :
        Node<T>* headNode;
        Node<T>* footNode;

        int size;
        void incrementSize() {
            size++;
        }

        void decrementSize() {
            size--;
        }


};

//----------------- Linked list implementation -----------------

/**
* Class to represent Stack
*/

template <typename T>
class Stack : protected LinkedList<T> {
    public :
        // push an elemnet at stack end
        void push(T aValue) {
            insertValue(aValue);
        }

        // remove the element at stack bottom
        T pop() {
            return this->removeNode(this->getSize() - 1);
        }

};

/**
* Class to represent Queue
*/
template <typename T>
class Queue : protected LinkedList<T> {
    public :
        // insert an element at queue end
        void insert(T aValue) {
            insertValue(aValue);
        }

        // remove the element at queue head
        T remove() {
            return this->removeNode(0);
        }
};

// ---------- Utility functions -----------------------------

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
* buids a stack with given elements
*/
template <typename T>
Stack<T> buildStack(T* elemsToAdd, int noOfElems) {
    Stack<T> theStack;

    for(int elCnt = 0; elCnt < noOfElems; elCnt++) {
        theStack.push(*elemsToAdd++);
    }

    return theStack;
}

/**
* pops all elements of the stack and adds the element in an array
*/
template <typename T>
void cleanStack(Stack<T>* theStack, T* popedArr, int noOfElems) {
    for(int elCnt = 0; elCnt < noOfElems; elCnt++) {
        popedArr[elCnt] = theStack->pop();
    }
}

/**
* buids a queue with given elements
*/
template <typename T>
Queue<T> buildQueue(T* elemsToAdd, int noOfElems) {
    Queue<T> theQueue;

    for(int elCnt = 0; elCnt < noOfElems; elCnt++) {
        theQueue.insert(*elemsToAdd++);
    }

    return theQueue;

}

/**
* removes all elements of the queue and adds the element in an array
*/
template <typename T>
void cleanQueue(Queue<T>* theQueue, T* remArr, int noOfElems) {
    for(int elCnt = 0; elCnt < noOfElems; elCnt++) {
        remArr[elCnt] = theQueue->remove();
    }
}


// ---------- Utility functions -----------------------------


int main() {

    string userInput;

    cout << "Please enter a sentence : " << endl;

    getline(cin, userInput);

    int sizeOfInput = userInput.size();
    char* userInputAsChar = new char[sizeOfInput + 1];
    strcpy(userInputAsChar, userInput.c_str());

    // Initial array of input
    cout << "Initial input" << endl;
    printArray(userInputAsChar, sizeOfInput);
    cout << "-----------------------------------------" << endl;

    // Array in stack
    Stack<char> userInputAsStack = buildStack(userInputAsChar, sizeOfInput);
    char* cleanedStackArr = new char[sizeOfInput + 1];
    cleanStack(&userInputAsStack, cleanedStackArr, sizeOfInput);
    cout << "After pushing in stack" << endl;
    printArray(cleanedStackArr, sizeOfInput);
    cout << "-----------------------------------------" << endl;

    // Stack to queue
    Queue<char> userInputQueue = buildQueue(cleanedStackArr, sizeOfInput);
    char* cleanedQueueArr = new char[sizeOfInput + 1];
    cleanQueue(&userInputQueue, cleanedQueueArr, sizeOfInput);
    cout << "After inserting in queue " << endl;
    printArray(cleanedQueueArr, sizeOfInput);
    cout << "-----------------------------------------" << endl;


    return 0;
}
