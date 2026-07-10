public class Item {

        private String name;
        private double price;
        private int calories;

        Item(String name, double price, int calories) {
            this.name = name;
            this.price = price;
            this.calories = calories;
        }

        public String getName() {
            return this.name;
        }

        public double getPrice() {
            return this.price;
        }

        public int getCalories() {
            return this.calories;
        }

        public setPrice(double price) {
            this.price = price;
        }
}