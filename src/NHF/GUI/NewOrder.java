package NHF.GUI;

import NHF.Buildings.Factories;
import NHF.Items.ExistingItems;
import NHF.Items.Item;
import NHF.Orders.ExistingOrders;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class NewOrder extends JPanel {

    private JLabel title = new JLabel("New Order");

    private JLabel nameLabel = new JLabel("Name");
    private JTextField nameField = new JTextField(30);

    private JLabel itemsLabel = new JLabel("Items:");
    private JTextField itemsField = new JTextField();

    private JLabel itemsQuantityLabel = new JLabel("Quantity:");
    private JTextField itemsQuantityField = new JTextField();

    private JLabel timeLabel = new JLabel("Time:");
    private JTextField timeField = new JTextField();

    private JLabel factoryLabel = new JLabel("Factory:");
    private JTextField factoryField = new JTextField();

    private JButton addButton = new JButton("Add Order");

    public NewOrder() {

        initComponents();
        defineLayout();
        createActionListeners();

    }

    private void initComponents() {
        title.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        title.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void defineLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addComponent(nameField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(itemsLabel)
                        .addComponent(itemsField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(itemsQuantityLabel)
                        .addComponent(itemsQuantityField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(timeLabel)
                        .addComponent(timeField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(factoryLabel)
                        .addComponent(factoryField))

                .addComponent(addButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nameLabel)
                        .addComponent(nameField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(itemsLabel)
                        .addComponent(itemsField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(itemsQuantityLabel)
                        .addComponent(itemsQuantityField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(timeLabel)
                        .addComponent(timeField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(factoryLabel)
                        .addComponent(factoryField))

                .addComponent(addButton)
        );
    }

    private void createActionListeners() {
        addButton.addActionListener(e -> {
            try {
                String[] itemsList = itemsField.getText().split(",");
                String[] itemsQuantityListAsStrings = itemsQuantityField.getText().split(",");

                if (itemsList.length != itemsQuantityListAsStrings.length) {
                    throw new IllegalArgumentException("List of items and quantities do not have the same size");
                }

                int[] itemsQuantityList = new int[itemsQuantityListAsStrings.length];

                for (String item : itemsList) {
                    if (ExistingItems.getItem(item) == null) {
                        throw new NoSuchElementException("Item " + item + " does not exist");
                    }
                }

                Item[] items = new Item[itemsList.length];

                for (int i = 0; i < itemsList.length; i++) {
                    items[i] = ExistingItems.getItem(itemsList[i]);
                }

                for (int i = 0; i < itemsQuantityListAsStrings.length; i++) {
                    itemsQuantityList[i] = Integer.parseInt(itemsQuantityListAsStrings[i]);
                }

                double time = Double.parseDouble(timeField.getText());

                if (Factories.getFactoryByName(factoryField.getText()) == null) {
                    throw new NoSuchElementException("Factory does not exist");
                }

                ExistingOrders.addOrder(
                        nameField.getText(),
                        items,
                        itemsQuantityList,
                        time,
                        Factories.getFactoryByName(factoryField.getText())
                );

            } catch (Exception addButtonException) {
                addButtonException.printStackTrace();
            }
        });
    }
}
