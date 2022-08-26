package NHF.Items;

import java.io.Serializable;

public class Item implements Serializable {
    String name;
    Item[] parts;
    int[] partsQuantity;

    public Item(String name, String[] parts, int[] partsQuantity) {
        this.name = name;
        if (parts != null) {
            if (parts.length != partsQuantity.length) {
                throw new IllegalArgumentException("Number of parts and quantities must be equal");
            }

            for (String part : parts) {
                if (ExistingItems.getItem(part) == null) {
                    throw new IllegalArgumentException("Part \"" + part + "\" is not an existing Item");
                }
            }

            this.parts = new Item[parts.length];
            for (int i = 0; i < parts.length; i++) {
                this.parts[i] = ExistingItems.getItem(parts[i]);
            }

            this.partsQuantity = new int[partsQuantity.length];
            System.arraycopy(partsQuantity, 0, this.partsQuantity, 0, parts.length);
        } else {
            this.parts = null;
            this.partsQuantity = null;
        }
        ExistingItems.items.add(this);
        System.out.println(ExistingItems.items.size());
    }

    public String getName() {
        return name;
    }

    public String getPartsQuantityAsString() {
        if (partsQuantity != null) {
            StringBuilder value = new StringBuilder();
            for (int partQuantity : partsQuantity) {
                value.append(partQuantity).append(",");
            }
            value.deleteCharAt(value.length() - 1);
            return value.toString();
        }
        return null;
    }

    public String getPartsAsString() {
        if (parts != null) {
            StringBuilder value = new StringBuilder();
            for (Item part : parts) {
                value.append(part.getName()).append(",");
            }
            value.deleteCharAt(value.length() - 1);
            return value.toString();
        }
        return null;
    }

    public Item[] getParts() {
        return parts;
    }

    public int[] getPartsQuantity() {
        return partsQuantity;
    }
}
