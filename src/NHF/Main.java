package NHF;

import NHF.Buildings.Factories;
import NHF.Buildings.Machines;
import NHF.GUI.Window;
import NHF.Items.ExistingItems;
import NHF.Orders.ExistingOrders;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        Factories.loadFactories();
        ExistingItems.loadExistingItems();
        Machines.loadMachines();
        ExistingOrders.loadOrders();
        new Window("Fuck you");
    }
}
