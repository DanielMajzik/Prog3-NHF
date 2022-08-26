package NHF.Buildings;

import NHF.Items.ExistingItems;
import NHF.Items.Item;
import NHF.Items.StorageElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactoryTest {

    Factory F1;
    StorageElement SE1;
    StorageElement SE2;
    Machine M1;
    Item I1;

    @BeforeEach
    void setUp() {
        F1 = new Factory("F1");
        I1 = new Item("Item1", null, null);
        ExistingItems.items.add(I1);
        ExistingItems.items.add(new Item("Item2", null, null));
        ExistingItems.items.add(new Item("Item3", null, null));
        SE1 = new StorageElement("Item3", null, null, 7);
        SE2 = new StorageElement("Item4", null, null, 13);
        F1.getStorage().add(new StorageElement("Item1", null, null, 5));
        F1.getStorage().add(new StorageElement("Item2", null, null, 8));
        F1.getStorage().add(SE2);
        Machines.addMachine("M1", I1, 0.1, 0.6, F1);
        M1 = Machines.getMachineByName("M1");
        F1.addMachine(M1);
    }

    @AfterEach
    void tearDown() {
        F1 = null;
    }

    @Test
    void getStorage() {
        assertEquals("Item1", F1.getStorage().get(0).getItem().getName());
        assertEquals(5, F1.getStorage().get(0).getAmount());
        assertEquals("Item2", F1.getStorage().get(1).getItem().getName());
        assertEquals(8, F1.getStorage().get(1).getAmount());
    }

    @Test
    void addStorageElement() {
        F1.addStorageElement(SE1);
        assertEquals(SE1, F1.getStorage().get(2));
    }

    @Test
    void getStorageElementByName() {
        assertEquals(SE2, F1.getStorageElementByName("Item4"));
    }

    @Test
    void getMachineByName() {
        assertEquals(M1, F1.getMachineByName("M1"));
    }

    @Test
    void getName() {
        assertEquals("F1", F1.getName());
    }
}