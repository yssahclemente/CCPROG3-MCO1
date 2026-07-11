import java.util.Scanner;

public class Driver {

    private static final Scanner sc = new Scanner(System.in);

    // Only the most recently created machine is "active" for testing,
    // per the spec ("the most recent that was created").
    private static RegularVendingMachine currentRegularMachine = null;
    private static Maintenance currentMaintenance = null;

    // Snapshot of quantities taken right after creation / after each restock,
    // used to show "starting vs ending inventory since last restocking".
    private static int[] startingInventorySnapshot = null;

    // Names for the 12 required items (index 0 = Slot 1)
    private static final String[] ITEM_NAMES = {
        "Italian Bread", "Wheat Bread", "Ham", "Turkey", "Roast Beef",
        "American Cheese", "Cheddar Cheese", "Lettuce", "Tomato",
        "Cucumber", "Mayonnaise", "Ranch"
    };

    // Assumed prices (PHP) and calorie counts, since these weren't specified.
    private static final double[] ITEM_PRICES  = {45, 45, 55, 60, 65, 30, 35, 15, 15, 15, 20, 20};
    private static final int[]    ITEM_CALORIES = {120, 110, 80, 70, 90, 100, 110, 5, 10, 8, 90, 100};

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n===== VENDING MACHINE MAIN MENU =====");
            System.out.println("1. Create a Vending Machine");
            System.out.println("2. Test a Vending Machine");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> createVendingMachineMenu();
                case 2 -> testVendingMachineMenu();
                case 3 -> {
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }

    // ==========================================================
    // 1. CREATE A VENDING MACHINE
    // ==========================================================

    private static void createVendingMachineMenu() {
        System.out.println("\n--- Create a Vending Machine ---");
        System.out.println("1. Regular Vending Machine");
        System.out.println("2. Special Vending Machine");
        System.out.print("Choose type: ");

        int choice = readInt();

        switch (choice) {
            case 1 -> {
                currentRegularMachine = new RegularVendingMachine();
                currentMaintenance = new Maintenance(currentRegularMachine);
                populateRegularVendingMachine(currentRegularMachine);
                takeInventorySnapshot(currentRegularMachine);
                System.out.println("Regular Vending Machine created and stocked with "
                        + ITEM_NAMES.length + " items.");
            }
            case 2 -> System.out.println(
                    "Special Vending Machine is not available yet -- the SpecialVendingMachine "
                  + "class hasn't been provided. Please create a Regular Vending Machine instead.");
            default -> System.out.println("Invalid option.");
        }
    }

    // Stocks slots 1-12 with the required sandwich-shop items, filled to capacity (10 each).
    private static void populateRegularVendingMachine(RegularVendingMachine machine) {
        for (int i = 0; i < ITEM_NAMES.length && i < RegularVendingMachine.NUM_SLOTS; i++) {
            Item item = new Item(ITEM_NAMES[i], ITEM_PRICES[i], ITEM_CALORIES[i]);
            ItemSlot slot = new ItemSlot(i + 1, item, 10); // filled to the 10-item capacity
            machine.setSlot(i, slot);
        }
    }

    private static void takeInventorySnapshot(RegularVendingMachine machine) {
        ItemSlot[] slots = machine.getSlots();
        startingInventorySnapshot = new int[slots.length];
        for (int i = 0; i < slots.length; i++) {
            startingInventorySnapshot[i] = (slots[i] != null) ? slots[i].getQuantity() : 0;
        }
    }

    // ==========================================================
    // 2. TEST A VENDING MACHINE
    // ==========================================================

    private static void testVendingMachineMenu() {
        if (currentRegularMachine == null) {
            System.out.println("No vending machine has been created yet. Please create one first.");
            return;
        }

        System.out.println("\n--- Test Vending Machine ---");
        System.out.println("1. Vending Features");
        System.out.println("2. Maintenance Features");
        System.out.print("Choose an option: ");

        int choice = readInt();

        switch (choice) {
            case 1 -> vendingFeaturesMenu(currentRegularMachine);
            case 2 -> maintenanceFeaturesMenu(currentRegularMachine, currentMaintenance);
            default -> System.out.println("Invalid option.");
        }
    }

    // ---------------- Vending Features ----------------

    private static void vendingFeaturesMenu(RegularVendingMachine machine) {
        boolean testing = true;

        while (testing) {
            System.out.println("\n--- Vending Features ---");
            System.out.println("1. Display Items");
            System.out.println("2. Purchase an Item");
            System.out.println("3. Insert Money and Cancel (get change back without buying)");
            System.out.println("4. End Vending Features (back to menu)");
            System.out.print("Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> machine.displayItems();
                case 2 -> purchaseFlow(machine);
                case 3 -> cancelFlow();
                case 4 -> testing = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void purchaseFlow(RegularVendingMachine machine) {
        machine.displayItems();

        System.out.print("Enter the slot number you want to buy from (0 to cancel): ");
        int slotNumber = readInt();

        if (slotNumber == 0) {
            System.out.println("Purchase cancelled before payment.");
            return;
        }

        int slotIndex = slotNumber - 1;
        if (slotIndex < 0 || slotIndex >= machine.getSlots().length) {
            System.out.println("Invalid slot number.");
            return;
        }

        double payment = insertMoney();

        if (payment == 0) {
            System.out.println("No money inserted. Purchase cancelled.");
            return;
        }

        machine.purchaseItem(slotIndex, payment);
    }

    private static void cancelFlow() {
        double payment = insertMoney();
        currentRegularMachine.cancelTransaction(payment);
    }

    // Lets the user "insert" bills of different denominations and returns the total.
    private static double insertMoney() {
        double total = 0;
        int[] validDenoms = {1, 5, 10, 20, 50, 100, 500, 1000};

        System.out.println("Insert money (enter 0 when done).");
        while (true) {
            System.out.print("Enter denomination (1,5,10,20,50,100,500,1000) or 0 to finish: ");
            int denom = readInt();

            if (denom == 0) break;

            boolean valid = false;
            for (int d : validDenoms) {
                if (d == denom) { valid = true; break; }
            }
            if (!valid) {
                System.out.println("Not a valid denomination.");
                continue;
            }

            System.out.print("How many PHP " + denom + " bills/coins? ");
            int count = readInt();
            if (count > 0) {
                total += (double) denom * count;
                System.out.println("Running total: PHP " + total);
            }
        }

        return total;
    }

    // ---------------- Maintenance Features ----------------

    private static void maintenanceFeaturesMenu(RegularVendingMachine machine, Maintenance maintenance) {
        boolean testing = true;

        while (testing) {
            System.out.println("\n--- Maintenance Features ---");
            System.out.println("1. Restock an Item");
            System.out.println("2. Set Item Price");
            System.out.println("3. Collect Payments");
            System.out.println("4. Replenish Change");
            System.out.println("5. Print Transaction Summary");
            System.out.println("6. Show Starting vs Ending Inventory (since last restock)");
            System.out.println("7. Display Current Inventory");
            System.out.println("8. End Maintenance Features (back to menu)");
            System.out.print("Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> restockFlow(machine, maintenance);
                case 2 -> setPriceFlow(machine, maintenance);
                case 3 -> maintenance.collectPayments();
                case 4 -> maintenance.replenishChange();
                case 5 -> machine.getTransactionSummary().printSummary();
                case 6 -> showInventoryComparison(machine);
                case 7 -> maintenance.displayInventory();
                case 8 -> testing = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void restockFlow(RegularVendingMachine machine, Maintenance maintenance) {
        machine.displayItems();
        System.out.print("Enter slot number to restock: ");
        int slotNumber = readInt();

        System.out.print("Enter amount to add: ");
        int amount = readInt();

        maintenance.restockItems(slotNumber - 1, amount);
        System.out.println("Restock complete.");

        // A restock marks a new "starting point" for the inventory comparison.
        takeInventorySnapshot(machine);
    }

    private static void setPriceFlow(RegularVendingMachine machine, Maintenance maintenance) {
        machine.displayItems();
        System.out.print("Enter slot number to update price: ");
        int slotNumber = readInt();

        System.out.print("Enter new price (PHP): ");
        double newPrice = readDouble();

        maintenance.setItemPrice(slotNumber - 1, newPrice);
        System.out.println("Price updated.");
    }

    private static void showInventoryComparison(RegularVendingMachine machine) {
        ItemSlot[] slots = machine.getSlots();

        System.out.println("\n=== STARTING vs ENDING INVENTORY (since last restock) ===");
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].getItem() != null) {
                int starting = (startingInventorySnapshot != null) ? startingInventorySnapshot[i] : 0;
                int ending = slots[i].getQuantity();
                System.out.println("Slot " + (i + 1) + " | " + slots[i].getItem().getName()
                        + " | Starting: " + starting + " | Ending: " + ending);
            }
        }
    }

    // ==========================================================
    // Input helpers
    // ==========================================================

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number: ");
            sc.next();
        }
        int value = sc.nextInt();
        return value;
    }

    private static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextDouble();
    }
}