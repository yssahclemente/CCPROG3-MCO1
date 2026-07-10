public class TransactionSummary {
    private int totalSales;
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
    public void recordSale(int price, int slotIndex) {
        if (slotIndex >= 0 && slotIndex < itemsSold.length) {
            this.totalSales += price;
            this.itemSold[slotIndex]++;
        }
    }

    /*
        printSummary() is a function that displays a summary of the transaction done, like a receipt
    */
    public void printSummary() {
        System.out.println("=== TRANSACTION SUMMARY ===");
        System.out.println("Total Sales Revenue: $" + totalSales);
        for (int i = 0; i < itemSold.length; i++) {
            System.out.println("Slot " + i + " items sold: " + itemSold[i]);
        }
    }

    /*
        resetSummary() is a function that will reset what the user wishes to buy from the vending machine
    */
    public void resetSummary() {
        this.totalSales = 0.0;
        this.itemsSold = new int[numSlots];
    }
}