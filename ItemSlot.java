public class ItemSlot {

    private final int slotNumber;
    private Item item;
    private int quantity;
    private final int capacity = 10;

    public ItemSlot(int slotNumber, Item item, int quantity) {
        this.slotNumber = slotNumber;
        this.item = item;
        
        if (quantity < 0 || quantity > capacity) {
            throw new IllegalArgumentException("Invalid quantity.");
        }

        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public boolean dispenseItem() {
        if (isAvailable()) {
            quantity--;
            return true;
        }
        return false;
    }

    public void restock(int amount) {
        if (amount > 0) {
            quantity = Math.min(quantity + amount, capacity);
        }
    }

    // ------------- Getters -------------
    public int getSlotNumber() {
        return slotNumber;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCapacity() {
        return capacity;
    }

    // ---------- Setters ----------

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0 && quantity <= capacity) {
            this.quantity = quantity;
        }
    }
}