public class RegularVendingMachine {

    public static final int NUM_SLOTS = 12;

    private ItemSlot[] slots;
    private CashInventory cashInventory;
    private TransactionSummary transactionSummary;

    public RegularVendingMachine() {
        slots = new ItemSlot[NUM_SLOTS];
        cashInventory = new CashInventory();
        transactionSummary = new TransactionSummary(NUM_SLOTS);    
    }

    // Displays all items in the vending machine
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

    // Adds an ItemSlot into the vending machine
    public void setSlot(int slotIndex, ItemSlot slot) {

        if (slotIndex >= 0 && slotIndex < NUM_SLOTS) {
            slots[slotIndex] = slot;
        }
    }

    // Purchase an item
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

    // Cancels transaction
    public void cancelTransaction(double payment) {

        if (payment > 0) {
            System.out.println("Returning PHP " + payment);
        }
    }

    //==========================
    // Getters
    //==========================

    public ItemSlot[] getSlots() {
        return slots;
    }

    public CashInventory getCashInventory() {
        return cashInventory;
    }

    public TransactionSummary getTransactionSummary() {
        return transactionSummary;
    }
}