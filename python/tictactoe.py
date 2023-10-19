
# Juhan Hong
# 10/5/2023
# Description: Make shift tik tac toe. This program has an initialized board
# with each section of the board with the position names. When 2 users,
# X and O, and enters the correct name of the position, the program will
# simulate playing tik tac toe.

# Create the data structure for the tik tac toe board using a dictionary
board = {
    'top-left': ' ',
    'top-middle': ' ',
    'top-right': ' ',
    'middle-left': ' ',
    'middle-middle': ' ',
    'middle-right': ' ',
    'bottom-left': ' ',
    'bottom-middle': ' ',
    'bottom-right': ' '
}


# Function to print the tik tac toe board
def printBoard(board):
    print(board['top-left'] + '|' + board['top-middle'] + '|' + board['top-right'])
    print('-+-+-')
    print(board['middle-left'] + '|' + board['middle-middle'] + '|' + board['middle-right'])
    print('-+-+-')
    print(board['bottom-left'] + '|' + board['bottom-middle'] + '|' + board['bottom-right'])


# Function to check if a move is valid
def validateMove(board, position):
    return board[position] == ' '


# Function to make a move on the board
def makeMove(board, position, player):
    # validate the input to be sure of positions
    if validateMove(board, position):
        board[position] = player
        return True
    else:
        return False


# Main game loop
player = 'X'
while True:
    printBoard(board)
    print(f"Player {player}'s turn. Enter your move (e.g., top-left):")
    move = input()

    if move in board:
        if makeMove(board, move, player):
            # Switch players
            if player == 'X':
                player = 'O'
            else:
                player = 'X'
        else:
            print("Invalid move. Try again.")
    else:
        print("Invalid input. Please enter a valid position (e.g., top-left, middle-middle).")


