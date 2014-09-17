#include <iostream>
#include <cstdlib>
#include <string>
#include <cmath>

using namespace std;

// ---------------- Utility functions --------------------

/**
* Strips only the digits (0-9) from a given string
* and returns a string only with digit maintaining
* the order of input string
*/
string onlyDigitsAsString(string stringToConvert) {

    string onlyDigits("");

    for(string::iterator charIter = stringToConvert.begin(); charIter != stringToConvert.end(); ++charIter) {
        if(*charIter > '9' || *charIter < '0') {
            // not a digit
            continue;
        } else {
            // Wow a digit! What a pleasure!
            onlyDigits.append(1, *charIter);
        }
    }
    return onlyDigits;

}


/**
* First collects all digits using onlyDigitsAsString, then
* converts that string to a long value
*/
long stringToLong(const string stringToConvert) {

    return atol(onlyDigitsAsString(stringToConvert).c_str());
}

// ---------------- Utility functions --------------------

// ---------------- Classes and related methods ----------


/**
* Class to represent a Prepaid Account
*/
class PrepaidAccount {

    public :
        PrepaidAccount(string phoneNumber="") : availableBalance(0.0d){
            this->phoneNumber = phoneNumber;
        }

        void AddBalance(double howMuch) {
            this->availableBalance += howMuch;
        }

        double PayForCall(int callDuration, double tariff) {
            int callDurationInMinutes = (callDuration / 60);
            callDurationInMinutes = ((callDuration % 60) == 0 ? callDurationInMinutes : callDurationInMinutes + 1 );
            double callCost = (double)(tariff * ((double)callDurationInMinutes));

            if(callCost > this->availableBalance) {
                return -1;
            } else {
                this->availableBalance -= callCost;
                return callCost;
            }
        }

        double getAvailableBalance() {
            return availableBalance;
        }

        const string& getPhoneNumber() const {
            return phoneNumber;
        }

    private :
        string phoneNumber;
        double availableBalance;
};


/**
* Compares two prepaid account by their phone number
*/
int compareAccounts(const PrepaidAccount a1, const PrepaidAccount a2) {
    long phone1AsNum = stringToLong(a1.getPhoneNumber());
    long phone2AsNum = stringToLong(a2.getPhoneNumber());

    int comparedVal;
    if(phone1AsNum < phone2AsNum) {
        comparedVal = -1;
    } else if(phone1AsNum == phone2AsNum) {
        comparedVal = 0;
    } else {
        comparedVal = 1;
    }

    return comparedVal;
}


/**
* Container class to maintain a collection of Prepaid Account
*/
class AccountStore {
    public :
        /**
        * Default constructor, initializes no of accounts,
        * and the all account array with max capacity
        */
        AccountStore() :  MAX_NUMBER_OF_ACCOUNTS(100), noOfAccounts(0) {
            allAcounts = new PrepaidAccount[MAX_NUMBER_OF_ACCOUNTS];
        }

        /**
        * Returns the number of accounts in the store
        */
        int getSize() {
            return noOfAccounts;
        }

        /**
        * Returns the account at the index, if invalid index
        * then return a void account
        */
        PrepaidAccount& getAccount(int index) {
            PrepaidAccount voidAcc;
            if(noOfAccounts == 0 || index < 0 || index > noOfAccounts - 1) {
                PrepaidAccount& voidAccRef = voidAcc;
                return voidAccRef;
            }
            return allAcounts[index];
        }

        /**
        * Removes the account instance with the phone number,
        * if only in the store
        * Returns the index of accounts removed
        */
        int removeAccount(string phoneNumber) {
            if(noOfAccounts == 0) {
                // nothing to remove
                return -1;
            }
            // search for the account with this phone number
            int accIdx = searchAccountIndex(phoneNumber);
            // validate once that this is the accout
            PrepaidAccount& theAcc = getAccount(accIdx);
            if(stringToLong(phoneNumber) == stringToLong(theAcc.getPhoneNumber())) {
                // Yep, this is it
                // delete and reorder
                reorderAccountsForRemove(accIdx);
            }

            // update the size
            noOfAccounts--;
            return accIdx;
        }

        /**
        * Inserts a new account in the store
        */
        void insertAccount(PrepaidAccount& acct) {
            if(noOfAccounts == 100) {
                // can not add any more
                return;
            }
            // search for the account with this phone number
            int accIdx = searchAccountIndex(acct);
            // add and reorder the list
            reorderAccountsForInsert(accIdx, acct);
            // update the size
            noOfAccounts++;
        }

    private :
        const int MAX_NUMBER_OF_ACCOUNTS;
        int noOfAccounts;
        PrepaidAccount* allAcounts;

        int searchAccountIndex(string phoneNumber);
        int searchAccountIndex(PrepaidAccount& prepaidAccount);
        void reorderAccountsForRemove(int indexToRemove);
        void reorderAccountsForInsert(int indextToInsert, PrepaidAccount& newAccount);
};

//void printAccounts(PrepaidAccount* accounts, int numOfAccts);

/**
* Searches the phone number in the store
*/
int AccountStore::searchAccountIndex(string phoneNumber) {
    PrepaidAccount searchingAccount(phoneNumber);
    return searchAccountIndex(searchingAccount);
}

/**
* Searches the phone account in the store
*/
int AccountStore::searchAccountIndex(PrepaidAccount& prepaidAccount) {

    if(noOfAccounts == 0) {
        return 0;
    }

    int acctIndex = 0;

    // search only till new account has a higher value than a preexisting account
    while(1 == compareAccounts(prepaidAccount, getAccount(acctIndex)) && acctIndex < noOfAccounts) {
        acctIndex++;
    }

    return acctIndex;
}

/**
* Reorders the store after a removal
*/
void AccountStore::reorderAccountsForRemove(int indexToRemove) {
    // Loop through the unchanged indexes
    for(int counter = 0; counter < noOfAccounts; counter++) {
        if(counter >= indexToRemove) {
            // pull up the accounts
            allAcounts[counter] = allAcounts[counter + 1];
        }
    }
}

/**
* Reorders the store after an insertion
*/
void AccountStore::reorderAccountsForInsert(int indexToInsert, PrepaidAccount& newAccount) {

    for(int counter = noOfAccounts; counter >= 0; counter--) {
        if(counter > indexToInsert) {
            // move elements one step down
            allAcounts[counter + 1] = allAcounts[counter];
        } else if(counter == indexToInsert) {
            // move the current element down
            allAcounts[counter + 1] = allAcounts[counter];
            // insert the new one
            allAcounts[counter] = newAccount;
        }
    }
 }


// ---------------- Classes and related methods ----------

// ---------------- Test methods -------------------------

void printAccountStore(AccountStore& theStore) {
    cout << "---------------Account Store------------------" << endl;
    cout << " No of elements in the store : " << theStore.getSize() << endl;
    int counter = 0;
    while (counter < theStore.getSize()) {
        PrepaidAccount& theAcct = theStore.getAccount(counter++);
        cout << " Phone number : " << theAcct.getPhoneNumber()
             << " The balance : " << theAcct.getAvailableBalance() << endl;
        cout << "---------------------------------" << endl;
    }
}

/*void printAccounts(PrepaidAccount* accounts, int numOfAcct) {
    cout << "--------------- Prepaid Accounts ------------------" << endl;
    int counter = 0;
    while (counter < numOfAcct) {
        PrepaidAccount& theAcct = accounts[counter++];
        cout << " Phone number : " << theAcct.getPhoneNumber()
             << " The balance : " << theAcct.getAvailableBalance() << endl;
        cout << "---------------------------------" << endl;
    }
}*/


void testPrepaidAccount() {
    PrepaidAccount testAccount1;

    bool allGood = true;
    if(testAccount1.getPhoneNumber() != "") {
        allGood = false;
        cerr << " PrepaidAccount constructor fails to initialize phone number with blank string" << endl;
    }

    if(testAccount1.getAvailableBalance() != 0.0d) {
        allGood = false;
        cerr << " PrepaidAccount constructor fails initialize available balance with Zero value" << endl;
    }

    PrepaidAccount testAccount2("111-222-3333");

    if(testAccount2.getPhoneNumber() != "111-222-3333") {
        allGood = false;
        cerr << " PrepaidAccount constructor fails to initialize phone number with proper value" << endl;
    }

    if(testAccount2.getAvailableBalance() != 0.0d) {
        allGood = false;
        cerr << " PrepaidAccount constructor fails initialize available balance with Zero value" << endl;
    }


    if(!allGood) {
        cout << " Fix PrepaidAccount constructor  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with PrepaidAccount constructor " << endl;
        cout << "------------------------------" << endl;
    }
}

void testPrepaidAccountAddBalance() {

    PrepaidAccount testAccount("111-222-3333");
    testAccount.AddBalance(45.98);

    bool allGood = true;
    if(testAccount.getPhoneNumber() != "111-222-3333") {
        allGood = false;
        cerr << " PrepaidAccount constructor fails to initialize phone number with proper value" << endl;
    }

    if(testAccount.getAvailableBalance() != 45.98d) {
        allGood = false;
        cerr << " PrepaidAccount constructor fails initialize available balance with proper value" << endl;
    }

    if(!allGood) {
        cout << " Fix PrepaidAccount AddBalance  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with PrepaidAccount AddBalance " << endl;
        cout << "------------------------------" << endl;
    }

}

void testPrepaidAccountPayForCall() {
    PrepaidAccount testAccount("111-222-3333");
    testAccount.AddBalance(45.98);

    bool allGood = true;

    int zero = 0;

    if(testAccount.PayForCall(zero, zero) != zero) {
        allGood = false;
        cerr << " PrepaidAccount PayForCall fails to calculate tariff" << endl;
    }

    if(testAccount.PayForCall(zero, 19.00) != zero) {
        allGood = false;
        cerr << " PrepaidAccount PayForCall fails to calculate tariff" << endl;
    }

    if(testAccount.PayForCall(10, zero) != zero) {
        allGood = false;
        cerr << " PrepaidAccount PayForCall fails to calculate tariff" << endl;
    }

    double tariff = rand() * 10 + 0.8;
    int callDuration = 10;

    if(callDuration * tariff < 45.98) {
        if(testAccount.PayForCall(callDuration, zero) == -1) {
            allGood = false;
            cerr << " PrepaidAccount PayForCall fails to calculate tariff" << endl;
        }
    }

    if(!allGood) {
        cout << " Fix PrepaidAccount PayForCall  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with PrepaidAccount PayForCall " << endl;
        cout << "------------------------------" << endl;
    }

}

void testAccountStore() {
    AccountStore testAccountStrore;

    bool allGood = true;
    if(testAccountStrore.getSize() != 0) {
        allGood = false;
        cerr << " AccountStore constructor fails to initialize the no of accounts to Zero" << endl;
    }

    if(!allGood) {
        cout << " Fix AccountStore constructor  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with AccountStore constructor " << endl;
        cout << "------------------------------" << endl;
    }
}

void testAccountStoreGetSize() {
    AccountStore testAccountStrore;

    bool allGood = true;
    if(testAccountStrore.getSize() != 0) {
        allGood = false;
        cerr << " AccountStore getSize fails to get the correct size of store" << endl;
    }

    if(!allGood) {
        cout << " Fix AccountStore getSize  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with AccountStore getSize " << endl;
        cout << "------------------------------" << endl;
    }

}

void testAccountStoreInsertAccount() {
    PrepaidAccount testAccount1("111-222-3333");
    PrepaidAccount testAccount2("111-333-3333");
    PrepaidAccount testAccount3("111-111-3333");
    PrepaidAccount testAccount4("111-111-3333");
    PrepaidAccount testAccount5("111-555-3333");
    PrepaidAccount testAccount6("111-000-3333");

    AccountStore testAccountStrore;

    testAccountStrore.insertAccount(testAccount1);
    testAccountStrore.insertAccount(testAccount2);
    testAccountStrore.insertAccount(testAccount3);
    testAccountStrore.insertAccount(testAccount4);
    testAccountStrore.insertAccount(testAccount5);
    testAccountStrore.insertAccount(testAccount6);


    bool allGood = true;
    if(testAccountStrore.getSize() != 6) {
        allGood = false;
        cerr << " AccountStore insertAccount fails to add new accounts to the store" << endl;
    }

    if(testAccountStrore.getAccount(0).getPhoneNumber() != testAccount6.getPhoneNumber()) {
        allGood = false;

        cerr << " AccountStore insertAccount fails to add new accounts in proper order to the store "
             << testAccountStrore.getAccount(0).getPhoneNumber()
             << endl;
    }

    if(testAccountStrore.getAccount(1).getPhoneNumber() != testAccount3.getPhoneNumber()) {
        allGood = false;

        cerr << " AccountStore insertAccount fails to add new accounts in proper order to the store "
             << testAccountStrore.getAccount(1).getPhoneNumber()
             << endl;
    }

    if(testAccountStrore.getAccount(5).getPhoneNumber() != testAccount5.getPhoneNumber()) {
        allGood = false;

        cerr << " AccountStore insertAccount fails to add new accounts in proper order to the store "
             << testAccountStrore.getAccount(5).getPhoneNumber()
             << endl;
    }



    if(!allGood) {
        cout << " Fix AccountStore insertAccount  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with AccountStore insertAccount " << endl;
        cout << "------------------------------" << endl;
    }

    printAccountStore(testAccountStrore);
}

void testAccountStoreRemoveAccount() {

    PrepaidAccount testAccount1("111-222-3333");
    PrepaidAccount testAccount2("111-333-3333");
    PrepaidAccount testAccount3("111-111-3333");

    AccountStore testAccountStrore;

    testAccountStrore.insertAccount(testAccount1);
    testAccountStrore.insertAccount(testAccount2);
    testAccountStrore.insertAccount(testAccount3);

    testAccountStrore.removeAccount("111-222-3333");

    bool allGood = true;
    if(testAccountStrore.getSize() != 2) {
        allGood = false;
        cerr << " AccountStore removeAccount fails to delete new accounts from the store" << endl;
    }

    if(testAccountStrore.getAccount(0).getPhoneNumber() != testAccount3.getPhoneNumber()) {
        allGood = false;

        cerr << " AccountStore insertAccount fails to add new accounts in proper order to the store "
             << testAccountStrore.getAccount(0).getPhoneNumber()
             << endl;
    }

    if(testAccountStrore.getAccount(1).getPhoneNumber() != testAccount2.getPhoneNumber()) {
        allGood = false;

        cerr << " AccountStore insertAccount fails to add new accounts in proper order to the store "
             << testAccountStrore.getAccount(1).getPhoneNumber()
             << endl;
    }

    if(!allGood) {
        cout << " Fix AccountStore removeAccount  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with AccountStore removeAccount " << endl;
        cout << "------------------------------" << endl;
    }

    printAccountStore(testAccountStrore);

}

void testAccountStoreGetAccount() {
    PrepaidAccount testAccount1("111-222-3333");
    PrepaidAccount testAccount2("111-333-3333");
    PrepaidAccount testAccount3("111-111-3333");

    AccountStore testAccountStrore;

    PrepaidAccount& voidAcc = testAccountStrore.getAccount(2);
    cout << "voidAcc : " << voidAcc.getPhoneNumber() << endl;
    bool allGood = true;
    if(voidAcc.getPhoneNumber() != "") {
        allGood = false;

        cerr << " AccountStore getAccount returned a non-void account from an empty store " << endl;
    }

    testAccountStrore.insertAccount(testAccount1);
    testAccountStrore.insertAccount(testAccount2);
    testAccountStrore.insertAccount(testAccount3);

    if(testAccountStrore.getAccount(0).getPhoneNumber() != testAccount3.getPhoneNumber()) {
        allGood = false;

        cerr << " AccountStore getAccount fails to retrieve proper account from the store "
             << testAccountStrore.getAccount(0).getPhoneNumber()
             << endl;
    }

    if(!allGood) {
        cout << " Fix AccountStore getAccount  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with AccountStore getAccount " << endl;
        cout << "------------------------------" << endl;
    }

}

void testStringToLong() {

    string t1("224-730-8581");
    string t2("(224)-730-8581");
    string t3("224.730.8581");
    string t4("2247308581");
    string t5("ABC224rgh730kjjj8581");
    string t6("2247308581skadjkl");
    string t7("            ----- 224730   8581");

    bool allGood = true;

    if(stringToLong(t1) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t1 << endl;
    }

    if(stringToLong(t2) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t2 << endl;
    }

    if(stringToLong(t3) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t3 << endl;
    }

    if(stringToLong(t4) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t4 << endl;
    }

    if(stringToLong(t5) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t5 << endl;
    }

    if(stringToLong(t6) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t6 << endl;
    }

    if(stringToLong(t7) != 2247308581) {
        allGood = false;
        cerr << " stringToLong fails for " << t7 << endl;
    }

    if(!allGood) {
        cout << " Fix stringToLong ASAP " << endl;
        cout << "------------------------------" << endl;
    } else {
        cout << " ALL GOOD with stringToLong " << endl;
        cout << "------------------------------" << endl;
    }

}

void testCompareAccounts() {
    PrepaidAccount testAccount1("111-222-3333");
    PrepaidAccount testAccount2("111-333-3333");
    PrepaidAccount testAccount3("111-111-3333");

    bool allGood = true;

    int compareVal = compareAccounts(testAccount1, testAccount1);
    if(compareVal != 0) {
        allGood = false;
        cerr << " compareAccounts fails to compare 2 accounts with same phone number" << endl;
    }

    compareVal = compareAccounts(testAccount1, testAccount2);
    if(compareVal != -1) {
        allGood = false;
        cerr << " compareAccounts fails to compare 2 accounts, where second has higher value" << endl;
    }

    compareVal = compareAccounts(testAccount1, testAccount3);
    if(compareVal != 1) {
        allGood = false;
        cerr << " compareAccounts fails to compare 2 accounts, where second has lower value" << endl;
    }

    if(!allGood) {
        cout << " Fix compareAccounts  ASAP! " << endl;
        cout << "------------------------------------" << endl;
    } else {
        cout << " ALL GOOD with compareAccounts " << endl;
        cout << "------------------------------" << endl;
    }
}

// ---------------- Test methods -------------------------

int main(int argc, char* argv[]) {

    // Test methods
    cout << " Starting to test all methods" << endl;
    cout << "------------------------------" << endl;

    // String to long conversion
    testStringToLong();

    // Comparing 2 prepaid account
    testCompareAccounts();

    // Testing prepaid account constructor
    testPrepaidAccount();

    // Testing prepaid account balance addition
    testPrepaidAccountAddBalance();

    // Testing cost deduction
    testPrepaidAccountPayForCall();

    // Testing account store constructor
    testAccountStore();

    // Testing store size
    testAccountStoreGetSize();

    // Testing new account addition to store
    testAccountStoreInsertAccount();

    // Testing account deletion from the store
    testAccountStoreRemoveAccount();

    // Testing get account method
    testAccountStoreGetAccount();
}
