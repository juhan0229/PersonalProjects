
# Juhan Hong
# 10/5/2023
# Description: This program has a base inventory. The program also utilizes
# different functions to add,delete, and print the whole inventory.
# When you cannot delete any more of inventory, the program will throw an error.
#


# Initialize the inventory dictionary
inventory = {
    "Hand sanitizer": 10,
    "Soap": 6,
    "Kleenex": 22,
    "Lotion": 16,
    "Razors": 12
}


# Function to print the inventory
def printInventory(inventory):
    print("Inventory:")
    for item, count in inventory.items():
        print(f"{item}: {count}")


# Function to add an item to the inventory
def addItem(inventory, item):
    inventory[item] = inventory.get(item, 0) + 1


# Function to delete an item from the inventory
def deleteItem(inventory, item):
    # item count must be more than 0 to delete
    if item in inventory and inventory[item] > 0:
        inventory[item] -= 1
    else:
        print(f"Error: {item} cannot be deleted any further.")


# Print the initial inventory
printInventory(inventory)
print(" ")

# Test adding items
addItem(inventory, "Comb")
addItem(inventory, "Lotion")

# Print the updated inventory
printInventory(inventory)
print(" ")

# Test deleting items
deleteItem(inventory, "Soap")
deleteItem(inventory, "Comb")
deleteItem(inventory, "Comb")  # Deleting comb again to test the error message

# Print the final inventory
printInventory(inventory)
print(" ")
