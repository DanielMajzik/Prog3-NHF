package NHF.Orders;

import NHF.Buildings.Machine;
import NHF.Items.Item;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CalculateOrder {

    private static double manufacturingTime = 0;
    private static boolean machineNotFound = false;

    private static ArrayList<Item> requiredItems = new ArrayList<>();
    private static ArrayList<Integer> requiredAmounts = new ArrayList<>();

    public static ArrayList<Item> getRequiredItems() {
        return requiredItems;
    }

    public static ArrayList<Integer> getRequiredAmounts() {
        return requiredAmounts;
    }

    public static void calculateOrder(Order order) {
        manufacturingTime = 0;
        machineNotFound = false;
        requiredItems.clear();
        requiredAmounts.clear();

        System.out.println("Order name: " + order.getName());
        for (int i = 0; i < order.getItems().length; i++) {
            System.out.println(order.getItems()[i].getName() + ", Amount: " + order.getItemsQuantity()[i]);
            calculate(order, order.getItems()[i], order.getItemsQuantity()[i]);
        }
    }

    private static void calculate(Order order, Item item, int requiredAmount) {
        if (!machineNotFound) {
            try {
                if (item.getParts() != null) {
                    boolean found = false;
                    System.out.println("Order calc before machine search. Item: " + item.getName());
                    System.out.println("Get order Factory: " + order.getFactory() + ", Name: " + order.getFactory().getName());
                    for (Machine machine : order.getFactory().getMachines()) {
                        System.out.println("Machine: " + machine.getName() +
                                ", output item: " + machine.getOutputProduct().getName());
                        if (machine.getOutputProduct().getName().equals(item.getName())) {
                            found = true;
                        }
                    }
                    if (!found) {
                        machineNotFound = true;
                        throw new NoSuchElementException("Machine not found in factory");
                    }
                    for (Machine machine : order.getFactory().getMachines()) {
                        if (machine.getOutputProduct().getName().equals(item.getName())) {
                            manufacturingTime += machine.getSetUpTime() +
                                    machine.getProductionTime() * requiredAmount;
                        }
                    }
                    for (int i = 0; i < item.getParts().length; i++) {
                        calculate(order, item.getParts()[i], item.getPartsQuantity()[i] * requiredAmount);
                    }
                } else {
                    requiredItems.add(item);
                    requiredAmounts.add(requiredAmount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static double getManufacturingTime() {
        return manufacturingTime;
    }
}
