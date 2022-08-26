package NHF.Buildings;

import NHF.Items.Item;

import java.io.Serializable;

public class Machine implements Serializable {
    private String name;
    private Item outputProduct;
    private double setUpTime;
    private double productionTime;
    private final Factory location;

    public Machine(String name, Item outputProduct, double setUpTime, double productionTime, Factory location) {
        this.name = name;
        this.outputProduct = outputProduct;
        if (setUpTime < 0 || productionTime < 0) {
            throw new IllegalArgumentException("Setup Time and Production time cannot be negative.");
        }
        this.setUpTime = setUpTime;
        this.productionTime = productionTime;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getOutputProduct() {
        return outputProduct;
    }

    public void setOutputProduct(Item outputProduct) {
        this.outputProduct = outputProduct;
    }

    public double getSetUpTime() {
        return setUpTime;
    }

    public void setSetUpTime(double setUpTime) {
        if (setUpTime > 0) {
            this.setUpTime = setUpTime;
        }
    }

    public double getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(double productionTime) {
        if (productionTime > 0) {
            this.productionTime = productionTime;
        }
    }

    public Factory getLocation() {
        return location;
    }
}
