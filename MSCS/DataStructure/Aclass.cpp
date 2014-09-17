#include <iostream>

using namespace std;

class PrepaidAccount {



    public :
        PrepaidAccount(string phoneNumber) : availableBalance(0.0d){
            this->phoneNumber = phoneNumber;
        }

        /*void AddBalance(double howMuch) {
            this.availableBalance += howMuch;
        }

        double PayForCall(int callDuration, double tariff) {
            int callDurationInMinutes = (callDuration / 60);
            callDurationInMinutes = ((callDuration % 60) == 0 ? callDurationInMinutes : callDurationInMinutes + 1 );
            double callCost = (double)(tariff * ((double)callDurationInMinutes));

            if(callCost > this.availableBalance) {
                return -1;
            } else {
                this.availableBalance -= callCost;
                return callCost;
            }
        }*/

    private :
        string phoneNumber;
        double availableBalance;

};

int main() {
    return 0;
}
