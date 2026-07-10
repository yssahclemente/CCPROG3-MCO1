public class Maintenance {
    // most of these functions will need a constructor from RegularVendingMachine since it'll need to be operated ww
    
    public void restockItems() {
            if (slotIndex >= 0 && slotIndex < machine.getSlots().length) {
                machine.getSlots()[slotIndex].restock(amount);
            }
    }

    public void setItemPrice() {
        if (slotIndex >= 0 && slotIndex < machine.getSlots().length) {
            Item item = machine.getSlots()[slotIndex].getItem();
            if (item != null) {
                item.setPrice(newPrice);
            }
        }
    }

    public void collectPayments() {
        int collected = getCashInventory().collectMoney();
        System.out.println("Emptied registers. Vault collected: $" + collected);
    }

    public void replenishChange() {
        machine.getCashInventory().replenishMoney();
    }

    public void displayInventory() {
        for (ItemSlot slot : machine.getSlots()) {
            if (slot != null && slot.getItem != null) {
                System.out.println("Slot Quantity: " + slot.getQuantity());
            }
        }
    }
}