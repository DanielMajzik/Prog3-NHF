package NHF.GUI;

import NHF.Orders.CalculateOrder;
import NHF.Orders.ExistingOrders;
import NHF.Orders.Order;

import javax.swing.*;
import java.awt.*;

public class ManageOrders extends JPanel {

    private Order currentOrder = null;

    private JLabel title = new JLabel("Manage Orders");

    private JComboBox ordersBox = new JComboBox();
    private JLabel ordersNameLabel = new JLabel("Name:");
    private JTextField ordersNameField = new JTextField(30);
    private JButton renameButton = new JButton("Rename");


    private JLabel orderDetailsLabel = new JLabel("Order Details");

    private JLabel orderItemsLabel = new JLabel("Ordered Items");
    private JPanel orderedItemsPanel = new JPanel();
    private JLabel manufacturingTime = new JLabel();
    private JLabel factoryLabel = new JLabel();
    private JLabel orderDeadlineLabel = new JLabel();
    private JLabel isPossibleLabel = new JLabel();

    public ManageOrders() {

        initComponents();
        defineLayout();
        createActionListeners();
        buildOrderBox();
        if (ordersBox.getSelectedItem() != null) {
            buildOrderDetails();
        }

    }

    private void initComponents() {
        title.setFont(new Font("Sans Serif", Font.PLAIN, 36));

        orderDetailsLabel.setFont(new Font("Sans Serif", Font.PLAIN, 28));

        orderItemsLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));

        factoryLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));

        orderedItemsPanel.setLayout(new BoxLayout(orderedItemsPanel, BoxLayout.PAGE_AXIS));
    }

    private void defineLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addComponent(ordersBox)

                .addGroup(layout.createSequentialGroup()
                        .addComponent(ordersNameLabel)
                        .addComponent(ordersNameField))

                .addComponent(renameButton)
                .addComponent(orderDetailsLabel)
                .addComponent(factoryLabel)
                .addComponent(orderItemsLabel)
                .addComponent(orderedItemsPanel)
                .addComponent(manufacturingTime)
                .addComponent(orderDeadlineLabel)
                .addComponent(isPossibleLabel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addComponent(ordersBox)

                .addGroup(layout.createParallelGroup()
                        .addComponent(ordersNameLabel)
                        .addComponent(ordersNameField))

                .addComponent(renameButton)
                .addComponent(renameButton)
                .addComponent(orderDetailsLabel)
                .addComponent(factoryLabel)
                .addComponent(orderItemsLabel)
                .addComponent(orderedItemsPanel)
                .addComponent(manufacturingTime)
                .addComponent(orderDeadlineLabel)
                .addComponent(isPossibleLabel)
        );
    }

    private void createActionListeners() {
        ordersBox.addActionListener(e -> {
            if (ordersBox.getSelectedItem() != null) {
                ordersNameField.setText(ordersBox.getSelectedItem().toString());
                currentOrder = ExistingOrders.getOrderByName(ordersBox.getSelectedItem().toString());
                buildOrderDetails();
            } else {
                currentOrder = null;
            }
        });

        renameButton.addActionListener(e -> {
            if (ordersBox.getSelectedItem() != null) {
                ExistingOrders.getOrderByName(ordersBox.getSelectedItem().toString())
                        .setName(ordersNameField.getText());
                buildOrderBox();
            }
        });
    }

    private void buildOrderBox() {
        if (ExistingOrders.getOrders().size() > 0) {
            ordersBox.removeAllItems();
            ExistingOrders.getOrders().forEach(order -> {
                ordersBox.addItem(order.getName());
            });
            currentOrder = ExistingOrders.getOrderByName(ordersBox.getSelectedItem().toString());
        } else {
            currentOrder = null;
        }
    }

    private void buildOrderDetails() {
        if (currentOrder != null) {
            try {
                orderedItemsPanel.removeAll();
                factoryLabel.setText("Factory: " + currentOrder.getFactory().getName());
                for (int i = 0; i < currentOrder.getItems().length; i++) {
                    JLabel label = new JLabel(
                            "Item name: " + currentOrder.getItems()[i].getName() +
                                    ", Amount: " + currentOrder.getItemsQuantity()[i]);

                    label.setFont(new Font("Sans Serif", Font.PLAIN, 18));
                    orderedItemsPanel.add(label);
                }

                CalculateOrder.calculateOrder(currentOrder);
                manufacturingTime.setText("Manufacturing time: " + String.format("%.2f", CalculateOrder.getManufacturingTime()));
                orderDeadlineLabel.setText("Deadline: " + String.format("%.2f", currentOrder.getTime()));

                if (currentOrder.getTime() - CalculateOrder.getManufacturingTime() < 0) {
                    isPossibleLabel.setText("The order needs " +
                            String.format("%.2f", CalculateOrder.getManufacturingTime() - currentOrder.getTime()) +
                            " more time");
                }

                orderedItemsPanel.add(new JLabel("Required Items: "));

                for (int i = 0; i < CalculateOrder.getRequiredItems().size(); i++) {
                    orderedItemsPanel.add(new JLabel("Required Item: " +
                            CalculateOrder.getRequiredItems().get(i).getName() + ", Amount: " +
                            CalculateOrder.getRequiredAmounts().get(i)
                    ));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
