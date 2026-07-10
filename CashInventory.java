public class CashInventory {
    private double ones, fives, tens, twenties, fifties, hundreds, fiveHundreds, oneThousands;

    /* 
        addMoney() is a function where such cases will be added to a count variable to calculate for the total

        @param denomination - this will be the variable where the face value of a bill is specified
        @param count - this will be what tracks how many of those specific bills are to be used
    */
    public void addMoney(int denomination, int count) {
        switch (denomination) {
            case 1 -> ones += count;
            case 5 -> fives += count;
            case 10 -> tens += count;
            case 20 -> twenties += count;
            case 50 -> fifties += count;
            case 100 -> hundreds += count;
            case 500 -> fiveHundreds += count;
            case 1000 -> oneThousands += count;
        }
    }

    // calculateChange() tallies what is needed from above
    public int calculateChange() {
        return (ones) + (fives * 5) + (tens * 10) + (twenties * 20) + (fifties * 50) + (hundreds * 100)
               + (fiveHundreds * 500) + (oneThousands * 1000);
    }

    // hasEnoughChange() is a function that validates if there's enough change in the vending machine to dispense to the user
    public boolean hasEnoughChange(double changeRequired) {
        return calculateChange() >= changeRequired;
    }

    // collectMoney() returns the amount of money needed, and resets the values for money back to 0
    public int collectMoney() {
        int total = calculateChange();
        ones = fives = tens = twenties = fifties = hundreds = fiveHundreds = oneThousands = 0;
        return total;
    }

    // replenishMoney() function will execute the baseline for maintenance with change
    public void replenishMoney() {
        this.ones = 20;
        this.fives = 20;
        this.tens = 20;
        this.twenties = 20;
    }
}