package NHF.Orders;

import NHF.Buildings.Factories;
import NHF.Buildings.Factory;
import NHF.Buildings.Machine;
import NHF.Buildings.Machines;
import NHF.Items.ExistingItems;
import NHF.Items.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateOrderTest {

    Factory F1;
    Machine M1;
    Item B1;
    Item B2;
    Item C1;
    Order O1;

    @BeforeEach
    void setUp() {
        Factories.addFactory("F1");
        F1 = Factories.getFactoryByName("F1");
        B1 = new Item("B1", null, null);
        B2 = new Item("B2", null, null);
        C1 = new Item("C1", new String[]{"B1", "B2"}, new int[]{2, 4});
        ExistingItems.items.add(B1);
        ExistingItems.items.add(B2);
        ExistingItems.items.add(C1);
        Machines.addMachine("M1", C1, 1, 3, F1);
        M1 = Machines.getMachineByName("M1");
        F1.addMachine(M1);
        O1 = new Order("O1", new Item[]{C1}, new int[]{4}, 16, F1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRequiredItems() {
        CalculateOrder.calculateOrder(O1);
        assertEquals(B1, CalculateOrder.getRequiredItems().get(0));
        assertEquals(B2, CalculateOrder.getRequiredItems().get(1));
    }

    @Test
    void getRequiredAmounts() {
        CalculateOrder.calculateOrder(O1);
        assertEquals(8, CalculateOrder.getRequiredAmounts().get(0));
        assertEquals(16, CalculateOrder.getRequiredAmounts().get(1));
    }

    @Test
    void calculateOrder() {
        CalculateOrder.calculateOrder(O1);
        assertEquals(13, CalculateOrder.getManufacturingTime());

    }

    @Test
    void getManufacturingTime() {
        assertEquals(0, CalculateOrder.getManufacturingTime());
    }
}