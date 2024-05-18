package Models;

import java.util.Map;

public final class Order {
        private final int orderId;
        private final Shipment.Customer customer;
        private boolean isShipped;
        private final int totalPrice;
        private final Map<Integer, Integer> products;
        private static int orderIndex;

        public Order(Shipment.Customer customer, Map<Integer, Integer> products, int totalPrice ) {
            orderIndex=orderIndex+1;
            this.orderId = orderIndex;
            this.customer = customer;
            this.products=products;
            this.totalPrice=totalPrice;
            this.isShipped = false;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getCustomer() {
            return customer.getFirstName() + " "+ customer.getLastName();
        }

       public int getTotalPrice() {
        return totalPrice;
        }

        public boolean isShipped() {
            return isShipped;
        }

        public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

        public Map<Integer, Integer> getProducts() {
        return products;
    }
}
