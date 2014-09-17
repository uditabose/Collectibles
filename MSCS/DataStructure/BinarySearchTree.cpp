#include <iostream>
#include <cstring>
#include <cassert>
#include <cstdlib>
#include <algorithm>
#include <ctime>

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

        bool searchValue(T value, int *traversedNode) {
            if(headNode == 0) {
                cout << "Empty linked list!" << endl;
                return false;
            }

            return searchValue(value, headNode, traversedNode);
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

        bool searchValue(T value, Node<T> *currentNode, int *traversedNode) {
            *traversedNode = *traversedNode + 1;
            if (currentNode == 0) {
                return false;
            } else if(currentNode->getValue() == value) {
                return true;
            } else {
                return searchValue(value, currentNode->getNext(), traversedNode);
            }

        }


};

//----------------- Linked list implementation -----------------

//----------------- Binary search tree implementation ----------

/**
* Class represents a node in Binary Search Tree
*/
class TreeNode {

    public :
    TreeNode(): nodeValue(0), leftChild(NULL), rightChild(NULL) {
        // do nothing
    }

    TreeNode(int value): leftChild(NULL), rightChild(NULL) {
        nodeValue = value;
    }

    /**
    * Returns the value contained by this node
    */
    int getNodeValue() {
        return nodeValue;
    }

    /**
    * Returns the left child
    */
    TreeNode* getLeftChild() {
        return leftChild;
    }

    /**
    * Returns the right child
    */
    TreeNode* getRightChild() {
        return rightChild;
    }

    /**
    * Creates the left child
    */
    void setLeftChild(int value) {
        leftChild = new TreeNode(value);
    }

    /**
    * Creates the right child
    */
    void setRightChild(int value) {
        rightChild = new TreeNode(value);
    }


    private :
    int nodeValue;
    TreeNode *leftChild;
    TreeNode *rightChild;

};

/**
* Class represents the Binary Search Tree
*/
class BinarySearchTree {

    public :
    BinarySearchTree() : rootNode(NULL) {
        // do nothing
    }

    BinarySearchTree(int *intArr, int noOfInt) : rootNode(NULL) {
        for(int counter = 0; counter < noOfInt; counter++) {
            insertNode(intArr[counter]);
        }
    }

    /**
    * Returns the root node
    */
    TreeNode* getRootNode() {
        return rootNode;
    }

    /**
    * Inserts a node, recursively finds suitable position
    */
    void insertNode(int value) {
        if(rootNode == NULL) {
            rootNode = new TreeNode(value);
        } else {
            insertNode(value, rootNode);
        }

    }

    /**
    * Searches for a value in the tree
    */
    bool searchValue(int value, int *traversedNode) {
        TreeNode *currentNode = rootNode;

        return searchValue(value, currentNode, traversedNode);
    }

    private :
    TreeNode *rootNode;
    void insertNode(int value, TreeNode *currentNode ) {
        if(value > currentNode->getNodeValue()) {
            // if value is more than the current node value
            if(currentNode->getRightChild() == NULL) {
                // and right child is empty, then set the right child
               currentNode->setRightChild(value);
            } else {
                // else search in right subtree
                insertNode(value, currentNode->getRightChild());
            }

        } else {
            if(currentNode->getLeftChild() == NULL) {
                // and left child is empty, then set the left child
               currentNode->setLeftChild(value);
            } else {
                // else search in left subtree
                insertNode(value, currentNode->getLeftChild());
            }
        }
    }

    bool searchValue(int value, TreeNode *currentNode, int *traversedNode) {
        *traversedNode = *traversedNode + 1;

        if(currentNode == NULL) {
            return false;
        } else if(currentNode->getNodeValue() == value) {
            return true;
        } else if(value > currentNode->getNodeValue()){
            return searchValue(value, currentNode->getRightChild(), traversedNode);
        }else {
            return searchValue(value, currentNode->getLeftChild(), traversedNode);
        }
    }

};

//----------------- Binary search tree implementation ----------

//----------------- Utility and experiments --------------------

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
* prints an tree node and children, in-order
*/
void printTreeNode(TreeNode *currentNode, char position='I') {
    if(currentNode == NULL) {
        return;
    }
    printTreeNode(currentNode->getLeftChild(), 'L');
    cout << currentNode->getNodeValue() << ":" << position << endl;
    printTreeNode(currentNode->getRightChild(), 'R');

}

/**
* prints an BST structure
*/
void printBST(BinarySearchTree *searchTree) {
    printTreeNode(searchTree->getRootNode());
}


/**
* generates a array of random numbers, and another
* number that is not in this array, returns this
* number
*/
int generateRandomNumber(int *randomIntArr, int noOfInts) {

    srand (time(NULL));

    int exclusion = (RAND_MAX - rand()) % noOfInts;

    int generated = 0;
    for(int counter = 0; counter < noOfInts; ) {
        generated = (RAND_MAX - rand()) % noOfInts;

        if(generated != exclusion) {
            randomIntArr[counter++] = generated;
        }

    }
    return exclusion;
}

/**
* searches an integer in BST and in linked list and prints the
* results.
*/
void searchAndCompare(int *intArr, int noOfInts, int intToFind) {
    LinkedList<int> linkedList(intArr, noOfInts);
    BinarySearchTree binarySearchTree(intArr, noOfInts);

    int listTraversedNode = 0, treeTraversedNode = 0;

    bool foundInList = linkedList.searchValue(intToFind, &listTraversedNode);
    bool foundInTree = binarySearchTree.searchValue(intToFind, &treeTraversedNode);


    printBST(&binarySearchTree);

    if(foundInList && foundInTree) {
        cout << intToFind << " is found. BST visited "
            << treeTraversedNode
            << " nodes. Linked-List visited "
            << listTraversedNode
            << " nodes"
            << endl;
    } else {
        cout << intToFind << " is NOT found. BST visited "
            << treeTraversedNode
            << " nodes. Linked-List visited "
            << listTraversedNode
            << " nodes"
            << endl;
    }
}

/**
* User interaction mode, user supplies the list of integers
*/
void userInteractionMode() {
    string userInput;
    int hundred = 100;
    int *intArr = new int[hundred];

    cout << "Please enter list of integers (max 100):" << endl;

    getline(cin, userInput);

    int whitePos = 0, startPos = 0, intCounter = 0;

    // splitting the data line with whitespace
    while((whitePos = userInput.find((' '), startPos)) != string::npos && intCounter < hundred) {

        intArr[intCounter++] =
                    atoi(trim(userInput.substr(startPos, whitePos - startPos)).c_str());
        startPos = whitePos + 1;
    }

    // content after last whitespace
    intArr[intCounter++] = atoi(trim(userInput.substr(startPos)).c_str());

    int *userIntArr = new int[intCounter];

    for(int cnt = 0; cnt < intCounter; cnt++) {
        userIntArr[cnt] = intArr[cnt];
    }

    userInput.clear();

    cout << "Please enter a number to search:" << endl;

    getline(cin, userInput);

    int intToFind = atoi(trim(userInput).c_str());

    searchAndCompare(userIntArr, intCounter, intToFind);




}

/**
* Experiment 1 : Random integer array is not sorted
*/
void experiment1(int *randomIntArr, int noOfInts, int intToFind) {

    searchAndCompare(randomIntArr, noOfInts, intToFind);
}

/**
* Experiment 2 : Random integer array is sorted
*/
void experiment2(int *randomIntArr, int noOfInts, int intToFind) {

    std::sort(randomIntArr, randomIntArr + noOfInts);

    searchAndCompare(randomIntArr, noOfInts, intToFind);
}

//----------------- Utility and experiments --------------------

int main() {

    string userInput;

    cout << "Please enter a mode : ";

    getline(cin, userInput);

    if(userInput == "u") {
        // user interaction mode, work on user provided list of numbers
        userInteractionMode();
    } else if(userInput == "r") {
        // run the experiment1 and experiment2, with a list of random numbers
        int hundred = 100;
        int *hundredRandom = new int[hundred];

        int intToSearch = generateRandomNumber(hundredRandom, hundred);

        printArray(hundredRandom, hundred);

        cout << " Running experiment 1 with random int array -------------" << endl;
        experiment1(hundredRandom, hundred, intToSearch);

        cout << endl;

        cout << " Running experiment 2 with random but sorted int array -------------" << endl;
        experiment2(hundredRandom, hundred, intToSearch);
    } else {
        cout << "Mode not recognized : " << userInput << endl;
        cout << "Re-run the program with mode u or r" << endl;
    }

    return 0;
}
