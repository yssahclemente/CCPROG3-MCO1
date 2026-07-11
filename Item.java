/**
 * Item.java
 *
 * Represents a single product type that can be sold by a vending machine
 * (e.g. "Ham" at PHP 55.00 with 80 calories). An Item holds only the
 * product's descriptive data -- it does not track how many units are in
 * stock; that responsibility belongs to {@link ItemSlot}.
 */

public class Item {

        private String name;
        private double price;
        private int calories;

    /**
     * Constructs a new Item with the given name, price, and calorie count.
     *
     * @param name     the display name of the item (e.g. "Wheat Bread")
     * @param price    the price of the item, in PHP
     * @param calories the calorie count of the item
     */
        Item(String name, double price, int calories) {
            this.name = name;
            this.price = price;
            this.calories = calories;
        }

    /**
     * Returns the display name of this item.
     *
     * @return the item's name
     */
        public String getName() {
            return this.name;
        }

    /**
     * Returns the price of this item.
     *
     * @return the item's price, in PHP
     */
        public double getPrice() {
            return this.price;
        }

    /**
     * Returns the calorie count of this item.
     *
     * @return the item's calorie count
     */
        public int getCalories() {
            return this.calories;
        }

    /**
     * Updates the price of this item. Used by maintenance staff to adjust
     * pricing without having to replace the item entirely.
     *
     * @param price the new price, in PHP
     */
        public void setPrice(double price) {
            this.price = price;
        }
}