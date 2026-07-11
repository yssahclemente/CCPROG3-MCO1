/**
 * RegularVendingMachine.java
 *
 * Represents a standard vending machine with a fixed number of item slots,
 * a cash drawer, and a running record of sales. Provides the core
 * customer-facing operations: browsing items, purchasing an item with
 * payment and change handling, and cancelling a transaction.
 */

public class RegularVendingMachine {

    /** The fixed number of item slots this machine has. */
    public static final int NUM_SLOTS = 12;

    private ItemSlot[] slots;
    private CashInventory cashInventory;
    private TransactionSummary transactionSummary;

    /**
     * Constructs a new RegularVendingMachine with all slots empty, an
     * empty cash drawer, and a fresh transaction summary.
     */
    public RegularVendingMachine() {
        slots = new ItemSlot[NUM_SLOTS];
        cashInventory = new CashInventory();
        transactionSummary = new TransactionSummary(NUM_SLOTS);    
    }

    /**
     * Prints a listing of every slot in the machine to the console,
     * showing the item's name, price, calories, and quantity for occupied
     * slots, or marking the slot as empty otherwise.
     */
    public void displayItems() {
        System.out.println("===== ITEMS AVAILABLE =====");

        for (int i = 0; i < slots.length; i++) {

            if (slots[i] != null && slots[i].getItem() != null) {

                Item item = slots[i].getItem();

                System.out.println(
                        "Slot " + (i + 1) +
                        " | " + item.getName() +
                        " | Price: PHP " + item.getPrice() +
                        " | Calories: " + item.getCalories() +
                        " | Quantity: " + slots[i].getQuantity());
            } else {
                System.out.println("Slot " + (i + 1) + " : Empty");
            }
        }
    }

    /**
     * Assigns an {@link ItemSlot} to a position within the machine.
     *
     * @param slotIndex the 0-based index of the slot to set
     * @param slot      the item slot to place at that index
     */
    public void setSlot(int slotIndex, ItemSlot slot) {

        if (slotIndex >= 0 && slotIndex < NUM_SLOTS) {
            slots[slotIndex] = slot;
        }
    }

    /**
     * Attempts to purchase the item at the given slot using the supplied
     * payment. Validates the slot, stock availability, payment sufficiency,
     * and available change before dispensing the item, updating the cash
     * drawer, and recording the sale.
     *
     * @param slotIndex the 0-based index of the slot to purchase from
     * @param payment   the amount of money inserted by the customer, in PHP
     * @return {@code true} if the purchase completed successfully,
     *         {@code false} if it was rejected for any reason (invalid
     *         slot, empty slot, out of stock, insufficient payment, or
     *         insufficient change)
     */
    public boolean purchaseItem(int slotIndex, double payment) {

        if (slotIndex < 0 || slotIndex >= NUM_SLOTS) {
            System.out.println("Invalid slot.");
            return false;
        }

        ItemSlot slot = slots[slotIndex];

        if (slot == null || slot.getItem() == null) {
            System.out.println("Slot is empty.");
            return false;
        }

        if (!slot.isAvailable()) {
            System.out.println("Item is out of stock.");
            return false;
        }

        Item item = slot.getItem();

        if (payment < item.getPrice()) {
            System.out.println("Insufficient payment.");
            return false;
        }

        double change = payment - item.getPrice();

        if (!cashInventory.hasEnoughChange(change)) {
            System.out.println("Machine has insufficient change.");
            return false;
        }

        cashInventory.addMoney((int) payment, 1);

        slot.dispenseItem();

        cashInventory.calculateAndDispense(change);

        transactionSummary.recordSale(item.getPrice(), slotIndex);

        System.out.println("Dispensing " + item.getName());

        if (change > 0) {
            System.out.println("Change: PHP " + change);
        }

        return true;
    }

    /**
     * Cancels an in-progress transaction, returning any inserted payment
     * to the customer without dispensing an item.
     *
     * @param payment the amount of money to return to the customer, in PHP
     */
    public void cancelTransaction(double payment) {

        if (payment > 0) {
            System.out.println("Returning PHP " + payment);
        }
    }

    //==========================
    // Getters
    //==========================

    /**
     * Returns the full array of item slots in this machine.
     *
     * @return the machine's slots, indexed 0 to {@link #NUM_SLOTS} - 1
     */
    public ItemSlot[] getSlots() {
        return slots;
    }

    /**
     * Returns the machine's cash drawer.
     *
     * @return the cash inventory used for payments and change
     */
    public CashInventory getCashInventory() {
        return cashInventory;
    }

    /**
     * Returns the machine's running sales record.
     *
     * @return the transaction summary for this machine
     */
    public TransactionSummary getTransactionSummary() {
        return transactionSummary;
    }
}