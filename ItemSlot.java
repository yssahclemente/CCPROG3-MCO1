/**
 * ItemSlot.java
 *
 * Represents a single dispensing slot inside a vending machine. Each slot
 * holds one {@link Item} type and tracks how many units of that item are
 * currently in stock, up to a fixed per-slot capacity.
 */

public class ItemSlot {

    private final int slotNumber;
    private Item item;
    private int quantity;
    private final int capacity = 10;

    /**
     * Constructs a new ItemSlot bound to a specific slot position, stocked
     * with the given item and starting quantity.
     *
     * @param slotNumber the position of this slot within the machine (1-based)
     * @param item       the item type assigned to this slot
     * @param quantity   the starting quantity of the item, must be between
     *                   0 and this slot's capacity (inclusive)
     * @throws IllegalArgumentException if quantity is negative or exceeds capacity
     */
    public ItemSlot(int slotNumber, Item item, int quantity) {
        this.slotNumber = slotNumber;
        this.item = item;
        
        if (quantity < 0 || quantity > capacity) {
            throw new IllegalArgumentException("Invalid quantity.");
        }

        this.quantity = quantity;
    }

     /**
     * Checks whether this slot currently has stock to dispense.
     *
     * @return {@code true} if quantity is greater than zero, {@code false} otherwise
     */
    public boolean isAvailable() {
        return quantity > 0;
    }

    /**
     * Dispenses one unit of the item from this slot, decrementing the
     * quantity, if stock is available.
     *
     * @return {@code true} if an item was successfully dispensed,
     *         {@code false} if the slot was out of stock
     */
    public boolean dispenseItem() {
        if (isAvailable()) {
            quantity--;
            return true;
        }
        return false;
    }

    /**
     * Adds stock back into this slot, typically performed during maintenance.
     * The resulting quantity is capped at this slot's capacity.
     *
     * @param amount the number of units to add; ignored if not positive
     */
    public void restock(int amount) {
        if (amount > 0) {
            quantity = Math.min(quantity + amount, capacity);
        }
    }

    // ------------- Getters -------------
    /**
     * Returns the position of this slot within the machine.
     *
     * @return the 1-based slot number
     */
    public int getSlotNumber() {
        return slotNumber;
    }

    /**
     * Returns the item type assigned to this slot.
     *
     * @return the item, or {@code null} if the slot has none assigned
     */
    public Item getItem() {
        return item;
    }

    /**
     * Returns the current quantity of stock in this slot.
     *
     * @return the current quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the maximum number of items this slot can hold.
     *
     * @return the slot's capacity
     */
    public int getCapacity() {
        return capacity;
    }

    // ---------- Setters ----------

     /**
     * Assigns (or replaces) the item type stocked in this slot.
     *
     * @param item the item to assign to this slot
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Directly sets the quantity of stock in this slot, if the value is
     * within the valid range.
     *
     * @param quantity the new quantity, must be between 0 and capacity (inclusive)
     */
    public void setQuantity(int quantity) {
        if (quantity >= 0 && quantity <= capacity) {
            this.quantity = quantity;
        }
    }
}