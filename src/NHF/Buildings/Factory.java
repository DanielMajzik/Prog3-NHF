package NHF.Buildings;

import NHF.Items.StorageElement;

import java.io.Serializable;
import java.util.ArrayList;

public class Factory extends Factories implements Serializable {
    private String name;
    private ArrayList<StorageElement> storage;
    private ArrayList<Machine> machines;

    public Factory(String name) {
        this.name = name;
        storage = new ArrayList<>();
        machines = new ArrayList<>();
    }

    public ArrayList<StorageElement> getStorage() {
        return storage;
    }

    public void addStorageElement(StorageElement element) {
        if (!storage.contains(element)) {
            storage.add(element);
        } else {
            System.out.println("addStorageElement in Factory says: Element already in storage");
        }
    }

    public StorageElement getStorageElementByName(String name) {
        for (StorageElement element : storage) {
            if (element.getItem().getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    public Machine getMachineByName(String name) {
        for (Machine machine : machines) {
            if (machine.getName().equals(name)) {
                return machine;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMachine(Machine machine) {
        if (!machines.contains(machine)) {
            machines.add(machine);
        }
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }
}
