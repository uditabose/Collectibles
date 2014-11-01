#include <iostream>
#include <cstring>

using namespace std;

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
        LinkedList() : headNode(0), footNode(0) {
            // default constructor, do nothing
        }

        LinkedList(T* elements, int noOfElements) : headNode(0), footNode(0) {
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

        void setHead(Node<T>* head) {
            headNode = head;
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
        void removeNode(int nodeIndex) {
            if(nodeIndex < 0 || nodeIndex >= size) {
                return;
            }

            Node<T>* nodeToRemove = getNode(nodeIndex);
            Node<T>* previousNode = getNode(nodeIndex - 1);

            if(previousNode == 0) {
                // Head node is being removed, headNode reassigned
                headNode = nodeToRemove->getNext();
            } else {
                previousNode->setNext(nodeToRemove->getNext());
            }

            // node deleted
            delete nodeToRemove;

            // decrease the size
            decrementSize();
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

/**
* Prints an entire node hierarchy starting at the headNode,
* interpersed with a separator
*/
template<typename T>
void printNode(Node<T>* headNode, char* separator="->") {
    if(headNode == 0) {
        return;
    }

    Node<T>* currentNode = headNode;

    while(currentNode != 0){
        cout << currentNode->getValue();
        currentNode = currentNode->getNext();
        if(currentNode != 0) {
            cout << separator;
        }
    }

}

/**
* Prints an entire LinkedList, interpersed with a separator
*/
template<typename T>
void printList(LinkedList<T>* linkedList, int noOfPieces=0) {
    for(int pieceCnt = 0; pieceCnt < noOfPieces; pieceCnt++) {
        printNode(linkedList[pieceCnt].getHead());
        cout << endl;
    }
    cout << endl;
}

/**
* Prints an entire array of wordList in reverse order,
* interpersed with a separator
*/
template<typename T>
void printReverseSentence(LinkedList<T>* wordList, int noOfPieces=0) {
    for(int pieceCnt = noOfPieces - 1; pieceCnt >= 0; pieceCnt--) {
        printNode(wordList[pieceCnt].getHead(), "");
        cout << " ";
    }
    cout << endl;
}

template<typename T>
void reversedList(LinkedList<T>* wordList) {
	Node<T> *head = wordList->getHead();
    Node<T> *tail = head;
    Node<T> *target = head->getNext();
    
    while(target != 0) {
        Node<T> *tempTarget = target->getNext();
        target->setNext(head);
        tail->setNext(tempTarget);
        head = target;
        target = tempTarget;
        printNode(head);
        cout << endl;
    }
    
    cout << "Final reversed " << endl;
    wordList->setHead(head);
    printNode(wordList->getHead());
    cout << endl;
	
}

/**
* Prints merged arrays of wordLists,
* interpersed with a separator
*/
template<typename T>
void printMergedSentence(LinkedList<T>* wordList1, int noOfPieces1, LinkedList<T>* wordList2, int noOfPieces2) {

    int pieceCnt = (noOfPieces1 <= noOfPieces2) ? noOfPieces1 : noOfPieces2;

    for(int pcnt = 0; pcnt < pieceCnt; pcnt++) {
        printNode(wordList1[pcnt].getHead(), "");
        cout << " ";
        printNode(wordList2[pcnt].getHead(), "");
        cout << " ";
    }

    if(noOfPieces1 == noOfPieces2) {
        return;
    }

    LinkedList<T>* appendList = (noOfPieces1 < noOfPieces2) ? wordList2 : wordList1;

    int pieceCnt1 = (noOfPieces1 < noOfPieces2) ? noOfPieces2 : noOfPieces1;

    for(int pcnt = pieceCnt; pcnt < pieceCnt1; pcnt++){
        printNode(appendList[pcnt].getHead(), "");
        cout << " ";
    }
    cout << endl;

}

/**
* Builds a linked list, returns the list
*/
template<typename T>
LinkedList<T> buildLinkedList(T* theSentence, int noOfElems) {

    LinkedList<T> linkedList(theSentence, noOfElems);

    return linkedList;

}

/**
* Builds a linked list, returns the headNode
*/
template<typename T>
Node<T>* getHeadNode(T* theSentence) {
    LinkedList<T> linkedList = buildLinkedList(theSentence);

    return linkedList.getHead();
}

/**
* Finds the number of words within the sentence
* NB - This method will give wrong result if the sentence
* ends with whitespace!
*/
template<typename T>
const int noOfWords(LinkedList<T>* linkedList, T wordSeparator) {
    int noOfElem = 0;
    Node<T>* currentNode = linkedList->getHead();

    while(currentNode != 0){
        if(currentNode->getValue() == wordSeparator) {
            noOfElem++;
        }
        currentNode = currentNode->getNext();
    }

    // for the last word
    noOfElem++;

    return noOfElem;

}

/**
* Builds a linked list, returns the headNode
*/
template<typename T>
void splitList(LinkedList<T>* linkedList, LinkedList<T>* wordList, T wordSeparator) {
    Node<T>* currentNode = linkedList->getHead();

    int noOfElem = 0;
    while(currentNode != 0){
        if(currentNode->getValue() != wordSeparator) {
            wordList[noOfElem].insertValue(currentNode->getValue());
        } else {
            noOfElem++;
        }
        currentNode = currentNode->getNext();
    }
}


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

int main(int argc, char* argv[]) {

    char* wordSep = " ";

    // Reverse sentence
    string revCommand = "R";
    // Merge sentence
    string mergeCommand = "M";
    // Quit command
    string quitCommand = "q";

    // Loop until quit command is entered
    while(true) {
        string userInput;

        cout << "Please enter operation:" << endl;

        getline(cin, userInput);

        userInput = trim(userInput);
        string command = userInput.substr(0, 1);

        userInput = trim(userInput.substr(1));

        if(command == revCommand) {

            char* sentence = new char[userInput.size() + 1];
            strcpy(sentence, userInput.c_str());
            int noOfChars = userInput.size();

            LinkedList<char> linkedList = buildLinkedList(sentence, noOfChars);

            const int noOfWordsInSentence = noOfWords(&linkedList, *wordSep);

            LinkedList<char>* wordList = new LinkedList<char>[noOfWordsInSentence];

            splitList(&linkedList, wordList, *wordSep);
            
            reversedList(&linkedList);

            printReverseSentence(wordList, noOfWordsInSentence);

            cout << endl;

        } else if(command == mergeCommand) {

            int posOfComma = userInput.find(',');

            if(posOfComma < 0) {
                cerr << "Invalid input sentence, must include a comma!" << endl;
                continue;
            }

            string frsSentence = userInput.substr(0, posOfComma);
            string scndSentence = trim(userInput.substr(posOfComma + 1));

            char* sentence1 = new char[frsSentence.size() + 1];
            strcpy(sentence1, frsSentence.c_str());
            char* sentence2 = new char[scndSentence.size() + 1];
            strcpy(sentence2, scndSentence.c_str());

            //------ First sentence ------------
            LinkedList<char> linkedList1 = buildLinkedList(sentence1, frsSentence.size());

            const int noOfWordsInSentence1 = noOfWords(&linkedList1, *wordSep);

            LinkedList<char>* wordList1 = new LinkedList<char>[noOfWordsInSentence1];

            splitList(&linkedList1, wordList1, *wordSep);
            //----------------------------------

            //------ Second sentence -----------
            LinkedList<char> linkedList2 = buildLinkedList(sentence2, scndSentence.size());

            const int noOfWordsInSentence2 = noOfWords(&linkedList2, *wordSep);

            LinkedList<char>* wordList2 = new LinkedList<char>[noOfWordsInSentence2];

            splitList(&linkedList2, wordList2, *wordSep);
            //----------------------------------

            // print merged version
            printMergedSentence(wordList1, noOfWordsInSentence1, wordList2, noOfWordsInSentence2);

            cout << endl;

        } else if(command == quitCommand) {
            break;
        } else {
            cout << "Invalid command, valid commands are M and R" << endl;
        }
    }


    return 0;
}
