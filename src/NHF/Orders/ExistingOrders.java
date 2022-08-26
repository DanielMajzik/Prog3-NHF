package NHF.Orders;

import NHF.Buildings.Factory;
import NHF.Items.Item;

import java.io.*;
import java.util.ArrayList;

public class ExistingOrders {
    private static ArrayList<Order> orders = new ArrayList<>();

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    public static void addOrder(String name, Item[] items, int[] quantities, double time, Factory factory) {
        orders.add(new Order(name, items, quantities, time, factory));
    }

    public static Order getOrderByName(String name) {
        for (Order order : orders) {
            if (order.getName().equals(name)) {
                return order;
            }
        }
        return null;
    }

    public static void saveOrders() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Orders.obj"));
            oos.writeObject(orders);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadOrders() {
        try {
            FileInputStream fis = new FileInputStream("Orders.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            orders.addAll((ArrayList<Order>) ois.readObject());
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        orders.forEach(order -> {
            System.out.println(order.getName());
        });
    }
}
