import java.util.Scanner;

public class Driver {

    private static final Scanner sc = new Scanner(System.in);
    private static final String LINE  = "======================================================";
    private static final String THIN  = "------------------------------------------------------";

    private static RegularVendingMachine currentRegularMachine = null;
    private static Maintenance currentMaintenance = null;

    private static int[] startingInventorySnapshot = null;

    // Names for the 12 required items (index 0 = Slot 1)
    private static final String[] ITEM_NAMES = {
        "Italian Bread", "Wheat Bread", "Ham", "Turkey", "Roast Beef",
        "American Cheese", "Cheddar Cheese", "Lettuce", "Tomato",
        "Cucumber", "Mayonnaise", "Ranch"
    };

    private static final double[] ITEM_PRICES   = {45, 45, 55, 60, 65, 30, 35, 15, 15, 15, 20, 20};
    private static final int[]    ITEM_CALORIES = {120, 110, 80, 70, 90, 100, 110, 5, 10, 8, 90, 100};

    public static void main(String[] args) {
        printBanner("SANDWICH SHOP VENDING MACHINE", "Welcome!");
        boolean running = true;

        while (running) {
            printHeader("MAIN MENU");
            printOption("1", "Create a Vending Machine");
            printOption("2", "Test a Vending Machine");
            printOption("3", "Exit");
            System.out.print("\n> Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> createVendingMachineMenu();
                case 2 -> testVendingMachineMenu();
                case 3 -> {
                    System.out.println();
                    System.out.println(LINE);
                    System.out.println("  Thanks for stopping by. Goodbye!");
                    System.out.println(LINE);
                    running = false;
                }
                default -> printError("That's not a valid option -- please choose 1, 2, or 3.");
            }
        }

        sc.close();
    }

    // ==========================================================
    // 1. CREATE A VENDING MACHINE
    // ==========================================================

    private static void createVendingMachineMenu() {
        printHeader("CREATE A VENDING MACHINE");
        printOption("1", "Regular Vending Machine");
        printOption("2", "Special Vending Machine");
        System.out.print("\n> Choose a type: ");

        int choice = readInt();

        switch (choice) {
            case 1 -> {
                currentRegularMachine = new RegularVendingMachine();
                currentMaintenance = new Maintenance(currentRegularMachine);
                populateRegularVendingMachine(currentRegularMachine);
                takeInventorySnapshot(currentRegularMachine);
                printSuccess("Regular Vending Machine created and fully stocked with "
                        + ITEM_NAMES.length + " items!");
            }
            case 2 -> printError(
                    "Special Vending Machine isn't available yet -- that class hasn't\n"
                  + "  been provided. Try creating a Regular Vending Machine instead.");
            default -> printError("That's not a valid type -- please choose 1 or 2.");
        }
    }

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
            printError("No vending machine has been created yet. Please create one first.");
            return;
        }

        printHeader("TEST VENDING MACHINE");
        printOption("1", "Vending Features");
        printOption("2", "Maintenance Features");
        System.out.print("\n> Choose an option: ");

        int choice = readInt();

        switch (choice) {
            case 1 -> vendingFeaturesMenu(currentRegularMachine);
            case 2 -> maintenanceFeaturesMenu(currentRegularMachine, currentMaintenance);
            default -> printError("That's not a valid option -- please choose 1 or 2.");
        }
    }

    // ---------------- Vending Features ----------------

    private static void vendingFeaturesMenu(RegularVendingMachine machine) {
        boolean testing = true;

        while (testing) {
            printHeader("VENDING FEATURES");
            printOption("1", "Display Items");
            printOption("2", "Purchase an Item");
            printOption("3", "Insert Money and Cancel (get your money back, no purchase)");
            printOption("4", "End Vending Features (back to main menu)");
            System.out.print("\n> Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> displayItems(machine);
                case 2 -> purchaseFlow(machine);
                case 3 -> cancelFlow();
                case 4 -> testing = false;
                default -> printError("That's not a valid option -- please choose 1-4.");
            }
        }
    }

    private static void purchaseFlow(RegularVendingMachine machine) {
        displayItems(machine);

        System.out.print("\n> Enter the slot number you'd like to buy (0 to cancel): ");
        int slotNumber = readInt();

        if (slotNumber == 0) {
            printInfo("No worries -- purchase cancelled.");
            return;
        }

        int slotIndex = slotNumber - 1;
        if (slotIndex < 0 || slotIndex >= machine.getSlots().length) {
            printError("There's no slot " + slotNumber + " on this machine.");
            return;
        }

        ItemSlot slot = machine.getSlots()[slotIndex];
        if (slot == null || slot.getItem() == null) {
            printError("Slot " + slotNumber + " is empty.");
            return;
        }
        if (!slot.isAvailable()) {
            printError(slot.getItem().getName() + " is out of stock. Please pick another item.");
            return;
        }

        System.out.println("\n  You selected: " + slot.getItem().getName()
                + " (PHP " + money(slot.getItem().getPrice()) + ")");

        double payment = insertMoney();

        if (payment == 0) {
            printInfo("No money inserted -- purchase cancelled.");
            return;
        }

        System.out.println(THIN);
        boolean success = machine.purchaseItem(slotIndex, payment);
        System.out.println(THIN);

        if (success) {
            printSuccess("Enjoy your " + slot.getItem().getName() + "!");
        }
    }

    private static void cancelFlow() {
        printInfo("Insert money below, then it will be returned to you in full.");
        double payment = insertMoney();
        System.out.println(THIN);
        currentRegularMachine.cancelTransaction(payment);
        System.out.println(THIN);
    }

    private static double insertMoney() {
        double total = 0;
        int[] validDenoms = {1, 5, 10, 20, 50, 100, 500, 1000};

        System.out.println("\n  Insert money below (enter 0 as the denomination when you're done).");
        System.out.println("  Accepted denominations: 1, 5, 10, 20, 50, 100, 500, 1000");
        while (true) {
            System.out.print("  > Denomination (0 to finish): ");
            int denom = readInt();

            if (denom == 0) break;

            boolean valid = false;
            for (int d : validDenoms) {
                if (d == denom) { valid = true; break; }
            }
            if (!valid) {
                printError("PHP " + denom + " isn't an accepted denomination.");
                continue;
            }

            System.out.print("    How many PHP " + denom + " bills/coins? ");
            int count = readInt();
            if (count > 0) {
                total += (double) denom * count;
                System.out.println("    Running total: PHP " + money(total));
            }
        }

        System.out.println("  Total inserted: PHP " + money(total));
        return total;
    }

    // ---------------- Maintenance Features ----------------

    private static void maintenanceFeaturesMenu(RegularVendingMachine machine, Maintenance maintenance) {
        boolean testing = true;

        while (testing) {
            printHeader("MAINTENANCE FEATURES");
            printOption("1", "Restock an Item");
            printOption("2", "Set Item Price");
            printOption("3", "Collect Payments");
            printOption("4", "Replenish Change");
            printOption("5", "Print Transaction Summary");
            printOption("6", "Show Starting vs Ending Inventory (since last restock)");
            printOption("7", "Display Current Inventory");
            printOption("8", "End Maintenance Features (back to main menu)");
            System.out.print("\n> Choose an option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> restockFlow(machine, maintenance);
                case 2 -> setPriceFlow(machine, maintenance);
                case 3 -> {
                    System.out.println(THIN);
                    maintenance.collectPayments();
                    System.out.println(THIN);
                }
                case 4 -> {
                    System.out.println(THIN);
                    maintenance.replenishChange();
                    System.out.println(THIN);
                }
                case 5 -> printTransactionSummaryNicely(machine);
                case 6 -> showInventoryComparison(machine);
                case 7 -> displayItems(machine);
                case 8 -> testing = false;
                default -> printError("That's not a valid option -- please choose 1-8.");
            }
        }
    }

    private static void restockFlow(RegularVendingMachine machine, Maintenance maintenance) {
        displayItems(machine);
        System.out.print("\n> Enter slot number to restock: ");
        int slotNumber = readInt();

        System.out.print("> Enter amount to add: ");
        int amount = readInt();

        maintenance.restockItems(slotNumber - 1, amount);
        printSuccess("Slot " + slotNumber + " restocked.");

        // A restock marks a new "starting point" for the inventory comparison.
        takeInventorySnapshot(machine);
    }

    private static void setPriceFlow(RegularVendingMachine machine, Maintenance maintenance) {
        displayItems(machine);
        System.out.print("\n> Enter slot number to update price: ");
        int slotNumber = readInt();

        System.out.print("> Enter new price (PHP): ");
        double newPrice = readDouble();

        maintenance.setItemPrice(slotNumber - 1, newPrice);
        printSuccess("Slot " + slotNumber + " price updated to PHP " + money(newPrice) + ".");
    }

    private static void showInventoryComparison(RegularVendingMachine machine) {
        ItemSlot[] slots = machine.getSlots();

        printHeader("STARTING vs ENDING INVENTORY (since last restock)");
        System.out.printf("  %-6s %-20s %-10s %-10s%n", "Slot", "Item", "Starting", "Ending");
        System.out.println("  " + THIN);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].getItem() != null) {
                int starting = (startingInventorySnapshot != null) ? startingInventorySnapshot[i] : 0;
                int ending = slots[i].getQuantity();
                System.out.printf("  %-6d %-20s %-10d %-10d%n",
                        (i + 1), slots[i].getItem().getName(), starting, ending);
            }
        }
    }

    private static void printTransactionSummaryNicely(RegularVendingMachine machine) {
        printHeader("TRANSACTION SUMMARY");
        TransactionSummary summary = machine.getTransactionSummary();
        int[] itemsSold = summary.getItemsSold();
        ItemSlot[] slots = machine.getSlots();

        System.out.printf("  %-6s %-20s %-10s%n", "Slot", "Item", "Sold");
        System.out.println("  " + THIN);
        for (int i = 0; i < itemsSold.length; i++) {
            String name = (slots[i] != null && slots[i].getItem() != null)
                    ? slots[i].getItem().getName() : "(empty)";
            System.out.printf("  %-6d %-20s %-10d%n", (i + 1), name, itemsSold[i]);
        }
        System.out.println("  " + THIN);
        System.out.println("  Total Sales Revenue: PHP " + money(summary.getTotalSales()));
    }

    // ==========================================================
    // Display helpers
    // ==========================================================

    private static void displayItems(RegularVendingMachine machine) {
        printHeader("ITEMS AVAILABLE");
        ItemSlot[] slots = machine.getSlots();

        System.out.printf("  %-6s %-18s %-10s %-10s %-8s %s%n",
                "Slot", "Item", "Price", "Calories", "Qty", "Status");
        System.out.println("  " + THIN);

        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].getItem() != null) {
                Item item = slots[i].getItem();
                String status = slots[i].isAvailable() ? "AVAILABLE" : "OUT OF STOCK";
                System.out.printf("  %-6d %-18s %-10s %-10d %-8d %s%n",
                        (i + 1),
                        item.getName(),
                        "PHP " + money(item.getPrice()),
                        item.getCalories(),
                        slots[i].getQuantity(),
                        status);
            } else {
                System.out.printf("  %-6d %-18s%n", (i + 1), "-- empty slot --");
            }
        }
    }

    // ==========================================================
    // Console formatting helpers
    // ==========================================================

    private static void printBanner(String title, String subtitle) {
        System.out.println(LINE);
        System.out.println("  " + title);
        System.out.println("  " + subtitle);
        System.out.println(LINE);
    }

    private static void printHeader(String title) {
        System.out.println();
        System.out.println(LINE);
        System.out.println("  " + title);
        System.out.println(LINE);
    }

    private static void printOption(String number, String label) {
        System.out.println("  [" + number + "] " + label);
    }

    private static void printSuccess(String message) {
        System.out.println("  \u2713 " + message);
    }

    private static void printError(String message) {
        System.out.println("  \u2717 " + message);
    }

    private static void printInfo(String message) {
        System.out.println("  " + message);
    }

    private static String money(double amount) {
        return String.format("%.2f", amount);
    }

    // ==========================================================
    // Input helpers
    // ==========================================================

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("  Please enter a valid whole number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("  Please enter a valid number: ");
            sc.next();
        }
        return sc.nextDouble();
    }
}