public class CashInventory {
    private int ones, fives, tens, twenties, fifties, hundreds, fiveHundreds, oneThousands;
    private final int[] DENOMINATIONS = {1000, 500, 100, 50, 20, 10, 5, 1};

    /*
        This will serve as the overloaded constructor when starting with a specific bank
    */
    public CashInventory(int ones, int fives, int tens,
                        int twenties, int fifties, 
                        int hundreds, int fiveHundreds,
                        int oneThousands) 
    {
        this.ones = Math.max(0, ones);
        this.fives = Math.max(0, fives);
        this.tens = Math.max(0, tens);
        this.twenties = Math.max(0, twenties);
        this.fifties = Math.max(0, fifties);
        this.hundreds = Math.max(0, hundreds);
        this.fiveHundreds = Math.max(0, fiveHundreds);
        this.oneThousands = Math.max(0, oneThousands);
    }

    // Default Constructor starting w. 0 bills across every denomination
    public CashInventory() {
        this(0, 0, 0, 0, 0, 0, 0, 0);
    }
    /* 
        addMoney() is a function where it increments the count of a specific denomination

        @param denomination - this will be the variable where the face value of a bill is specified
        @param count - this will be what tracks how many of those specific bills are to be used
    */
    public void addMoney(int denomination, int count) {
        if (count <= 0)
            return;
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

    /*
        removeMoney() decrements the count of a specific denomination w/o dropping below 0

        @param denomination - is the value where the face value of a bill is specified
        @param count - this will be what tracks how many of those specific bills are to be used
    */
    public void removeMoney(int denomination, int count) {
        if (count <= 0)
            return;
        switch (denomination) {
            case 1 -> ones = Math.max(0, ones - count);
            case 5 -> fives = Math.max(0, fives - count);
            case 10 -> tens = Math.max(0, tens - count);
            case 20 -> twenties = Math.max(0, twenties - count);
            case 50 -> fifties = Math.max(0, fifties - count);
            case 100 -> hundreds = Math.max(0, hundreds - count);
            case 500 -> fiveHundreds = Math.max(0, fiveHundreds - count);
            case 1000 -> oneThousands = Math.max(0, oneThousands - count);
            default -> System.out.println("[Error] Invalid denomination: PHP " + denomination);
        }
    }

    // getTotalValue() tallies the amount of change that is in the inventory drawer
    public double getTotalValue() {
        return (ones) + (fives * 5) + (tens * 10) + (twenties * 20) + (fifties * 50) + (hundreds * 100)
               + (fiveHundreds * 500) + (oneThousands * 1000);
    }

    // hasEnoughChange() is a function that validates if there's enough change in the vending machine to dispense to the user
    public boolean hasEnoughChange(double changeRequired) {
        int remaining = (int) changeRequired;

        for (int denom : DENOMINATIONS) {
            int availableCount = getDenominationCount(denom);
            int neededCount = remaining / denom;
            int usedCount = Math.min(neededCount, availableCount);

            remaining -= usedCount * denom;
        }
        return remaining == 0;
    }

    /*
        calculateAndDispense()
    */
    public int[] calculateAndDispense(double changeRequired) {
        int remaining = (int) changeRequired;
        int[] dispensedCounts = new int[DENOMINATIONS.length];

        for (int i = 0; i < DENOMINATIONS.length; i++) {
            int denom = DENOMINATIONS[i];
            int availableCount = getDenominationCount(denom);
            int neededCount = remaining / denom;

            dispensedCounts[i] = Math.min(neededCount, availableCount);
            remaining -= dispensedCounts[i] * denom;
        }

        if (remaining == 0) {
            for (int i = 0; i < DENOMINATIONS.length; i++) {
                if (dispensedCounts[i] > 0) {
                    removeMoney(DENOMINATIONS[i], dispensedCounts[i]);
                }
            }
            return dispensedCounts;
        } else {
            System.out.println("[Process Error] Cannot produce change with current coin/bill combination.");
            return new int[DENOMINATIONS.length];
        }
    }

    // collectMoney() returns the amount of money needed, and resets the values for money back to 0
    public double collectMoney() {
        double total = getTotalValue();
        ones = fives = tens = twenties = fifties = hundreds = fiveHundreds = oneThousands = 0;
        return total;
    }

    // replenishMoney() function will execute the baseline for maintenance with change
    public void replenishMoney() {
        this.ones = 20;
        this.fives = 20;
        this.tens = 20;
        this.twenties = 20;
        this.fifties = 10;
        this.hundreds = 10;
        this.fiveHundreds = 5;
        this.oneThousands = 5;
        System.out.println("[Maintenance] Cash inventories replenished successfully.");
    }

    // Private helper for some of the functions above

    private int getDenominationCount(int denomination) {
        return switch (denomination) {
            case 1000 -> oneThousands;
            case 500 -> fiveHundreds;
            case 100 -> hundreds;
            case 50 -> fifties;
            case 20 -> twenties;
            case 10 -> tens;
            case 5 -> fives;
            case 1 -> ones;
            default -> 0;
        };
    }

    public int[] makeChange(double amount) {
        return calculateAndDispense(amount);
    }

    public void returnInsertedMoney() {
        System.out.println("Returning inserted money...");
    }

    // getters & setters below

    //for ones
    public void setOnes(int ones) {
        this.ones = Math.max(0, ones);
    }
    public int getOnes() {
        return this.ones;
    }

    //for fives
    public void setFives(int fives) {
        this.fives = Math.max(0, fives);
    }
    public int getFives() {
        return this.fives;
    }

    // for tens
    public void setTens(int tens) {
        this.tens = Math.max(0, tens);
    }
    public int getTens() {
        return this.tens;
    }

    // for twenties
    public void setTwenties(int twenties) {
        this.twenties = Math.max(0, twenties);
    }

    public int getTwenties() {
        return twenties;
    }

    // for fifties
    public void setFifties(int fifties) {
        this.fifties = Math.max(0, fifties);
    }
    public int getFifties() {
        return this.fifties;
    }

    // for hundreds
    public void setHundreds(int hundreds) {
        this.hundreds = Math.max(0, hundreds);
    }
    public int getHundreds() {
        return this.hundreds;
    }

    // for five hundreds
    public void setFiveHundreds(int fiveHundreds) {
        this.fiveHundreds = Math.max(0, fiveHundreds);
    }
    public int getFiveHundreds() {
        return this.fiveHundreds;
    }

    // for thousands
    public void setOneThousands(int oneThousands) {
        this.oneThousands = Math.max(0, oneThousands);
    }
    public int getOneThousands() {
        return this.oneThousands;
    }

    public double getTotalCash() {
        return getTotalValue();
    }
}
