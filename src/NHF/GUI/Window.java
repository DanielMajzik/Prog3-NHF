package NHF.GUI;

import NHF.Buildings.Factories;
import NHF.Buildings.Machines;
import NHF.Items.ExistingItems;
import NHF.Orders.ExistingOrders;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class Window extends JFrame {
    private final NHF.GUI.MenuBar menuBar;
    JPanel mainPanel;

    public Window(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int minimumWindowWidth = 800;
        int minimumWindowHeight = 960;
        setMinimumSize(new Dimension(minimumWindowWidth, minimumWindowHeight));

        ArrayList<String[]> menuBarTexts = new ArrayList<>();
        menuBarTexts.add(new String[]{"File",
                "Exit"
        });
        menuBarTexts.add(new String[]{"New",
                "Factory",
                "Order"
        });
        menuBarTexts.add(new String[]{"Manage",
                "Factories",
                "Orders"
        });

        menuBar = new MenuBar(menuBarTexts);
        addMenuBarActionListeners();

        add(menuBar, BorderLayout.PAGE_START);
        mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Last 2 lines of constructor
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addMenuBarActionListeners() {

        /*
         * Exit
         * */
        requireNonNull(menuBar.getMenuItem("Exit")).addActionListener(e -> {
            int a = JOptionPane.showConfirmDialog(
                    getParent(),
                    "Close application?",
                    "Attention",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null
            );

            if (a == JOptionPane.YES_OPTION) {
                Factories.saveFactories();
                ExistingItems.saveExistingItems();
                Machines.saveMachines();
                ExistingOrders.saveOrders();
                System.exit(0);
            }
        });

        /*
         * New Factory
         */
        requireNonNull(menuBar.getMenuItem("Factory")).addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new NewFactory(), BorderLayout.CENTER);
            mainPanel.repaint();
            pack();
        });

        /*
         * New Order
         */
        requireNonNull(menuBar.getMenuItem("Order")).addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new NewOrder());
            mainPanel.repaint();
            pack();
        });

        /*
         * Manage Factories
         */
        requireNonNull(menuBar.getMenuItem("Factories")).addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new ManageFactories());
            mainPanel.repaint();
            pack();
        });

        /*
         * Manage Orders
         */
        requireNonNull(menuBar.getMenuItem("Orders")).addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new ManageOrders());
            mainPanel.repaint();
            pack();
        });
    }
}
