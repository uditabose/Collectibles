/**
* Throttle implementation
*/

#include "Throttle.h"
#include <cassert>

using namespace std;

//Throttle constructor

Throttle::Throttle(int maxThrottle) {
    this->minThrottle = 0;
    this->currentThrottle = this->minThrottle;

    assert(maxThrottle > this->minThrottle);

    this->maxThrottle = maxThrottle;

    this->isActivated = true;
}

bool Throttle::isOn() {
    return this->isActivated;
}

int Throttle::shift(int amount) {
    if(this->currentThrottle + amount <= this->maxThrottle
        && this->currentThrottle + amount >= this->minThrottle) {
        this->currentThrottle = this->currentThrottle + amount;
    } else {
         throw " Throttle too high/low";
    }

    return this->currentThrottle;
}

int Throttle::flow() {
    return (this->currentThrottle * 5);
}

int main() {
    Throttle throttle(6);
    throttle.shift(3);
    cout << throttle.flow() << endl;
}
