package NHF.Orders;

import NHF.Buildings.Factory;
import NHF.Items.Item;
import java.io.Serializable;

public class Order implements Serializable {
    private String name;
    private Item[] items;
    private int[] itemsQuantity;
    private double time;
    private Factory factory;

    public Order(String name, Item[] items, int[] itemsQuantity, double time, Factory factory) {
        this.name = name;
        this.items = items;
        this.itemsQuantity = itemsQuantity;
        this.time = time;
        this.factory = factory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item[] getItems() {
        return items;
    }

    public int[] getItemsQuantity() {
        return itemsQuantity;
    }

    public double getTime() {
        return time;
    }

    public Factory getFactory() {
        return factory;
    }
}
