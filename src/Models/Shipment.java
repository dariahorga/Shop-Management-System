package Models;

import java.util.*;


public class Shipment {
    private int shipmentId;
    private Order order;
    private Date shippingDate;
    private int shipmentIndex;

    public Shipment(Order order) {
        shipmentIndex = shipmentIndex + 1;
        this.shipmentId = shipmentIndex;
        this.order = order;
        this.shippingDate = null;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public Order getOrder() {
        return order;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }
}

