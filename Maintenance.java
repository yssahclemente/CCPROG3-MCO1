/**
 * Maintenance.java
 *
 * Represents the operator-facing maintenance tools for a
 * {@link RegularVendingMachine}: restocking items, adjusting prices,
 * collecting cash, and replenishing change. This class wraps a single
 * vending machine instance and performs upkeep operations on its behalf.
 */

public class Maintenance {
    
    private RegularVendingMachine machine;

    /**
     * Constructs a Maintenance helper bound to the given vending machine.
     *
     * @param machine the vending machine this maintenance session operates on
     */
    public Maintenance(RegularVendingMachine machine) {
        this.machine = machine;
    }
    
    /**
     * Adds stock to a specific slot in the vending machine.
     *
     * @param slotIndex the 0-based index of the slot to restock
     * @param amount    the number of units to add
     */
    public void restockItems(int slotIndex, int amount) {
            if (slotIndex >= 0 && slotIndex < machine.getSlots().length) {
                machine.getSlots()[slotIndex].restock(amount);
            }
    }

    /**
     * Updates the price of the item assigned to a specific slot.
     *
     * @param slotIndex the 0-based index of the slot whose item price is being changed
     * @param newPrice  the new price, in PHP
     */
    public void setItemPrice(int slotIndex, double newPrice) {
        if (slotIndex >= 0 && slotIndex < machine.getSlots().length) {
            Item item = machine.getSlots()[slotIndex].getItem();
            if (item != null) {
                item.setPrice(newPrice);
            }
        }
    }

    /**
     * Empties the vending machine's cash drawer and reports the total
     * amount collected to the console.
     */
    public void collectPayments() {
        double collected = machine.getCashInventory().collectMoney();
        System.out.println("Emptied registers. Vault collected: $" + collected);
    }

    /**
     * Restocks the vending machine's cash drawer with a baseline supply of
     * change across all denominations.
     */
    public void replenishChange() {
        machine.getCashInventory().replenishMoney();
    }

    /**
     * Prints the current inventory of every occupied slot in the vending
     * machine, showing each item's name and remaining quantity.
     */
    public void displayInventory() {
        for (ItemSlot slot : machine.getSlots()) {
            if (slot != null && slot.getItem() != null) {
                System.out.println(
                    slot.getItem().getName() +
                    " | Quantity: " + slot.getQuantity()
                );
            }
        }
    }
}