package NHF.Buildings;

import java.io.*;
import java.util.ArrayList;

public class Factories {
    private static ArrayList<Factory> factories = new ArrayList<>();

    public static ArrayList<Factory> getFactories() {
        return factories;
    }

    public static void addFactory(String name) {
        try {
            factories.forEach(factory -> {
                if (factory.getName().equals(name)) {
                    throw new IllegalArgumentException("Name must be unique.");
                }
            });
            factories.add(new Factory(name));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void removeFactory(Factory f) {
        factories.remove(f);
    }

    public static Factory getFactoryByName(String name) {
        for (Factory f : factories) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public static void saveFactories() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Factories.obj"));
            oos.writeObject(factories);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadFactories() {
        try {
            FileInputStream fis = new FileInputStream("Factories.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            factories.addAll((ArrayList<Factory>) ois.readObject());
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        factories.forEach(factory -> {
            System.out.println(factory.getName());
        });
    }
}
