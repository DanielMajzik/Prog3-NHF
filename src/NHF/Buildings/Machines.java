package NHF.Buildings;

import NHF.Items.Item;

import java.io.*;
import java.util.ArrayList;

public class Machines {

    private static ArrayList<Machine> machines = new ArrayList<>();

    public static void addMachine(String name, Item outputProduct, double setupTime, double productionTime, Factory location) {
        try {
            machines.forEach(factory -> {
                if (factory.getName().equals(name)) {
                    throw new IllegalArgumentException("Name must be unique.");
                }
            });
            machines.add(new Machine(name, outputProduct, setupTime, productionTime, location));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static Machine getMachineByName(String name) {
        for (Machine machine : machines) {
            if (machine.getName().equals(name)) {
                return machine;
            }
        }
        return null;
    }

    public static void saveMachines() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Machines.obj"));
            oos.writeObject(machines);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadMachines() {
        try {
            FileInputStream fis = new FileInputStream("Machines.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            machines.addAll((ArrayList<Machine>) ois.readObject());
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        machines.forEach(machine -> {
            System.out.println(machine.getName());
        });
    }
}
