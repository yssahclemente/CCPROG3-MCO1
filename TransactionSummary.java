/**
 * TransactionSummary.java
 *
 * Keeps a running record of a vending machine's sales activity since the
 * last time it was reset (typically at each restocking): total revenue
 * collected and how many units were sold from each slot.
 */

public class TransactionSummary {
    private double totalSales;
    private int[] itemsSold;

    /*
        TransactionSummary() is a constructor that sets initializes the given private variables for this class

        @param numSlots - this dictates the number of slots
    */
    public TransactionSummary(int numSlots) {
        this.totalSales = 0.0;
        this.itemsSold = new int[numSlots];
    }

    /*
        recordSale() is a function that records the items that have been sold by the vending machine

        @param price - this is the total price of the items sold
        @param slotIndex - this will have the respective slot index stored
    */
    public void recordSale(double price, int slotIndex) {
        if (slotIndex >= 0 && slotIndex < itemsSold.length) {
            this.totalSales += price;
            this.itemsSold[slotIndex]++;
        }
    }

    /*
        printSummary() is a function that displays a summary of the transaction done, like a receipt
    */
    public void printSummary() {
        System.out.println("=== TRANSACTION SUMMARY ===");
        System.out.println("Total Sales Revenue: $" + totalSales);
        for (int i = 0; i < itemsSold.length; i++) {
            System.out.println("Slot " + (i+1) + " items sold: " + itemsSold[i]);
        }
    }

    /*
        resetSummary() is a function that will reset what the user wishes to buy from the vending machine
    */
    public void resetSummary() {
        this.totalSales = 0.0;
        this.itemsSold = new int[itemsSold.length];
    }

    // getters

    public double getTotalSales() {
        return totalSales;
    }

    public int[] getItemsSold() {
        return itemsSold;
    }
}