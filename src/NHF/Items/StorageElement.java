package NHF.Items;

import java.io.Serializable;

public class StorageElement implements Serializable {
    private Item item;
    private int amount;

    public StorageElement(String itemName, String[] parts, int[] partsQuantity, int amount) {

        if (ExistingItems.getItem(itemName) == null) {
            item = new Item(itemName, parts, partsQuantity);
        } else {
            item = ExistingItems.getItem(itemName);
        }
        this.amount = amount;

        System.out.println(item.getName());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setItemByName(String name, String[] parts, int[] partsQuantities) {
        if (ExistingItems.getItem(name) != null) {
            item = ExistingItems.getItem(name);
        } else {
            item = new Item(name, parts, partsQuantities);
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
