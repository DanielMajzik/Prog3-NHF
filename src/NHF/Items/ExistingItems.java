package NHF.Items;

import java.io.*;
import java.util.ArrayList;

public class ExistingItems implements Serializable {
    public static ArrayList<Item> items = new ArrayList<>();

    public static Item getItem(String name) {
        System.out.println("ExistingItems.getItem says: List size is " + items.size());
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static void saveExistingItems() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ExistingItems.obj"));
            oos.writeObject(items);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadExistingItems() {
        try {
            FileInputStream fis = new FileInputStream("ExistingItems.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            items.addAll((ArrayList<Item>) ois.readObject());
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        items.forEach(item -> {
            System.out.println(item.getName());
        });
    }
}
