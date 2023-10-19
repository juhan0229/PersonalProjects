#include <stdio.h>
#include <stdbool.h>

// constants to be declared in main for rental property information 
int const SENTINAL_NEG1 = -1;
int unsigned const MIN_RENTAL_NIGHTS = 1;
unsigned int const MAX_RENTAL_NIGHTS = 14;
unsigned int const INTERVAL_1_NIGHTS = 3;
unsigned int const INTERVAL_2_NIGHTS = 6;
double const RENTAL_RATE = 400;
double const DISCOUNT = 50;

// function prototypes
void printRentalPropertyInfo(unsigned int minNights, unsigned int maxNights, unsigned int interval1Nights, unsigned int interval2Nights, double rate, double discount);
int validateInput(int min, int max, int sentinel);
double calculateCharges(unsigned int nights, unsigned int interval1Nights, unsigned int interval2Nights, double rate, double discount);
void printNightsCharges(unsigned int nights, double charges);
void printTotalCharges(unsigned int totalNights, double totalCharges);


int main(void) {
    int userNight = 0;
    double visitCharge = 0;;
    int totalNight = 0;
    double totalCharge = 0;
    bool airUCCS = true;

    // this while loops will continuesly run until the user enters the sentinal value. 
    // use of bool with while loop
    while (airUCCS) {
        printRentalPropertyInfo(MIN_RENTAL_NIGHTS, MAX_RENTAL_NIGHTS, INTERVAL_1_NIGHTS, INTERVAL_2_NIGHTS, RENTAL_RATE, DISCOUNT);
        userNight = validateInput(MIN_RENTAL_NIGHTS, MAX_RENTAL_NIGHTS, SENTINAL_NEG1);

        if (userNight == SENTINAL_NEG1) {
            printTotalCharges(totalNight, totalCharge);
            airUCCS = false;
        }
        // when the input is a valid integer, it will print the number of night and the cost. 
        // the else statement also continuesly add the total number of nights and total cost for all the guests to be showned in
        // property owner mode
        else {
            visitCharge = calculateCharges(userNight, INTERVAL_1_NIGHTS, INTERVAL_2_NIGHTS, RENTAL_RATE, DISCOUNT);
            printNightsCharges(userNight, visitCharge);

            totalNight = totalNight + userNight;
            totalCharge = totalCharge + visitCharge;
        }
    }



    return 0;
}

// printing the property information.
void printRentalPropertyInfo(unsigned int minNights, unsigned int maxNights, unsigned int interval1Nights, unsigned int interval2Nights, double rate, double discount) {

    printf("%s", "Welcome to AirUCCS!\n\n");
    printf("%s%d%s%d%s", "Rental property can be rented for ", minNights, " to ", maxNights, " nights\n");
    printf("%s%.2f%s%d%s", "$", rate, " a night for the first ", interval1Nights, " nights\n");
    printf("%s%.2f%s%d%s%d%s", "$", discount, " discount rate a night for nights ", interval1Nights + 1, " to ", interval2Nights, "\n");
    printf("%s%.2f%s%d%s", "$", discount * 2, " discount rate a night for each remaining night over ", interval2Nights, "\n\n");
}

// validating the input
// using a do while loop to continuesly run until the user enters the valid integer. 
// this function also has error handlign for when the user enters a value that is not valid.

int validateInput(int min, int max, int sentinal) {
    int input;
    int validInput = 0;

    do {
        printf("Enter the number of nights: ");

        validInput = scanf("%d", &input);
        // clears buffer
        while ((getchar()) != '\n');

        if (validInput == 1) {
            if (input == sentinal) {
                printf("Sentinal value entered.\n");
            }
            else if (input < min || input > max) {
                printf("Input must be in the range %d, %d.\n", min, max);
                validInput = 0;
            }
            else {
                printf("%s", "\n");
            }
        }
        else {
            printf("Not a numeric value. Please try again.\n");
        }
    } while (validInput != 1);
    return input;
}

// calculating the charges based on the guideline
// 1-3 nights -> 400 per night
// 4-6 nights  -> 50 discount per night
// 6+ -> 100 discount per night
double calculateCharges(unsigned int nights, unsigned int interval1Nights, unsigned int interval2Nights, double rate, double discount) {
    double totalCost = 0;
    double totalDiscount = 0;

    if (nights <= interval1Nights) {
        totalCost = nights * rate;
    }
    else if (nights > interval1Nights && nights < interval2Nights) {
        totalDiscount = (nights - interval1Nights) * discount;
        totalCost = (nights * rate) - totalDiscount;
    }
    else {
        totalDiscount = (nights - interval2Nights) * discount;
        totalCost = (nights * rate) - totalDiscount;
    }

    return totalCost;
}

// print the number of night and the total charge for that 1 customer
void printNightsCharges(unsigned int nights, double charges) {
    printf("\n%s", "Rental Charges\n");
    printf("%s", "Nights        Charges\n");
    printf("%-15u $%.2lf\n\n", nights, charges);
}


//print the total charges for all the guests. 
void printTotalCharges(unsigned int totalNights, double totalCharges) {
    printf("\n\n%s", "Rental Property Owner Total Summary\n\n");
    printf("%s", "Nights        Charges\n");
    printf("%-15u $%.2lf\n\n", totalNights, totalCharges);

}