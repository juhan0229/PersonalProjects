# Juhan Hong
# Guess Game

import random

def guess_the_number():
    # generating a random int
    secretNumber = random.randint(1, 20)
    attempts = 0

    print("I am thinking of a number between 1 and 20. You have 10 tries.")

    while attempts < 10:
        try:
            guess = int(input("Take a guess: "))
            attempts += 1

            #if else if statements to determine if the gues is correct
            if guess < secretNumber:
                print("Your guess is too low.")
            elif guess > secretNumber:
                print("Your guess is too high.")
            else:
                print(f"Good job! You guessed my number in {attempts} guesses!")
                return

        except ValueError:
            print("Please enter a valid number.")

    print(f"Sorry, the number I was thinking of was ." + secretNumber)

guess_the_number()