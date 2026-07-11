public class Maintenance {
    
    private RegularVendingMachine machine;

    public Maintenance(RegularVendingMachine machine) {
        this.machine = machine;
    }
    
    public void restockItems(int slotIndex, int amount) {
            if (slotIndex >= 0 && slotIndex < machine.getSlots().length) {
                machine.getSlots()[slotIndex].restock(amount);
            }
    }

    public void setItemPrice(int slotIndex, double newPrice) {
        if (slotIndex >= 0 && slotIndex < machine.getSlots().length) {
            Item item = machine.getSlots()[slotIndex].getItem();
            if (item != null) {
                item.setPrice(newPrice);
            }
        }
    }

    public void collectPayments() {
        double collected = machine.getCashInventory().collectMoney();
        System.out.println("Emptied registers. Vault collected: $" + collected);
    }

    public void replenishChange() {
        machine.getCashInventory().replenishMoney();
    }

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