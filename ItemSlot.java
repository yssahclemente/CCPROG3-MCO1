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

    public void dispenseItem() {
        if (isAvailable()) {
            quantity--;
        }
    }

    // public void restock() {

    // }

    public int getQuantity() {
        return this.quantity;
    }
}