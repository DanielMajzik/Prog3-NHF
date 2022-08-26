package NHF.GUI;

import NHF.Buildings.Factories;
import NHF.Buildings.Factory;
import NHF.Buildings.Machines;
import NHF.Items.ExistingItems;
import NHF.Items.StorageElement;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

public class ManageFactories extends JPanel {

    private int fieldLength = 20;
    private Factory currentFactory;

    private JLabel title = new JLabel("Add Factory");
    private JLabel titleItem = new JLabel("Item");
    private JLabel titleMachine = new JLabel("Machine");

    private JLabel factoryLabel = new JLabel("Factory:");
    private JComboBox factoryNameBox = new JComboBox();
    private JLabel factoryNameLabel = new JLabel("Name:");
    private JTextField factoryNameField = new JTextField(fieldLength);

    private JButton renameFactoryButton = new JButton("Rename");


    private JLabel itemLabel = new JLabel("Item:");
    private JComboBox itemBox = new JComboBox();
    private JLabel itemNameLabel = new JLabel("Name:");
    private JTextField itemNameField = new JTextField();
    private JLabel itemPartsLabel = new JLabel("Parts:");
    private JTextField itemPartsField = new JTextField();
    private JLabel itemPartsQuantityLabel = new JLabel("Parts Quantity:");
    private JTextField itemPartsQuantityField = new JTextField();
    private JLabel currentAmountLabel = new JLabel("Amount in storage:");
    private JTextField currentAmountFiled = new JTextField();

    private JButton addItemButton = new JButton("Add Item");
    private JButton updateItemButton = new JButton("Update Item");
    private JButton deleteItemButton = new JButton("Delete Item");


    private JLabel machineLabel = new JLabel("Machine:");
    private JComboBox machineBox = new JComboBox();
    private JLabel machineNameLabel = new JLabel("Name:");
    private JTextField machineNameField = new JTextField();
    private JLabel outputItemLabel = new JLabel("Output Item:");
    private JTextField outputItemField = new JTextField();
    private JLabel setupTimeLabel = new JLabel("Setup time:");
    private JTextField setupTimeField = new JTextField();
    private JLabel productionTimeLabel = new JLabel("Production Time:");
    private JTextField productionTimeField = new JTextField();

    private JButton addMachineButton = new JButton("Add Machine");
    private JButton deleteMachineButton = new JButton("Delete Machine");

    public ManageFactories() {

        initComponents();
        createActionListeners();
        defineLayout();

        buildFactoriesComboBox();
        buildItemsComboBox();
        buildMachinesComboBox();
        fillFactoryFields();
        fillItemsFields();
        fillMachinesFields();
    }

    // A C T I O N L I S T E N E R S ///////////////////////////////////////////////////////////////////////////////////
    private void createActionListeners() {
        factoryNameBox.addActionListener(e -> {
            switchFactory();
        });

        renameFactoryButton.addActionListener(e -> {
            if (currentFactory != null && factoryNameBox.getSelectedItem() != null) {
                currentFactory.setName(factoryNameField.getText());

                buildFactoriesComboBox();
                switchFactory();
            }
        });

        // Items ///////////////////////////////////////////////////////////////////////////////////////////////////////

        itemBox.addActionListener(e -> {
            fillItemsFields();
        });

        addItemButton.addActionListener(e -> {
            if (currentFactory != null) {
                System.out.println("AddItemButton: Current Factory:" + currentFactory.getName());
                try {
                    // Convert parts information
                    int[] itemQuantities = null;
                    String[] itemParts = null;
                    if (!itemPartsQuantityField.getText().equals("")) {
                        String[] itemQuantitiesString = itemPartsQuantityField.getText().split(",");
                        itemQuantities = new int[itemQuantitiesString.length];

                        for (int i = 0; i < itemQuantitiesString.length; i++) {
                            itemQuantities[i] = Integer.parseInt(itemQuantitiesString[i]);
                        }
                        itemParts = itemPartsField.getText().split(",");
                    }

                    if (currentFactory.getStorageElementByName(itemNameField.getText()) == null) {

                        currentFactory.addStorageElement(new StorageElement(
                                itemNameField.getText(),
                                itemParts,
                                itemQuantities,
                                Integer.parseInt(currentAmountFiled.getText()))
                        );

                        buildItemsComboBox();
                        fillItemsFields();

                    } else {
                        System.out.println("AddItemButton says: Item is already in the list");
                    }

                } catch (NumberFormatException addItemButtonExceoption) {
                    addItemButtonExceoption.printStackTrace();
                }
            }
        });

        updateItemButton.addActionListener(e -> {
            if (currentFactory != null) {
                try {
                    if (currentFactory.getStorageElementByName(itemNameField.getText()) == null) {
                        throw new IllegalArgumentException("updateItemButton says: No such item in storage.");
                    }
                    if (ExistingItems.getItem(itemNameField.getText()) == null) {
                        throw new IllegalArgumentException("updateItemButton says: No such item exists.");
                    }

                    // Convert parts information
                    int[] itemQuantities = null;
                    String[] itemParts = null;

                    if (!(itemPartsQuantityField.getText().equals("") || itemPartsField.getText().equals(""))) {
                        String[] itemQuantitiesString = itemPartsQuantityField.getText().split(",");
                        itemQuantities = new int[itemQuantitiesString.length];

                        for (int i = 0; i < itemQuantitiesString.length; i++) {
                            itemQuantities[i] = Integer.parseInt(itemQuantitiesString[i]);
                        }
                        itemParts = itemPartsField.getText().split(",");
                    }

                    currentFactory.getStorageElementByName(itemNameField.getText())
                            .setItemByName(itemNameField.getText(), itemParts, itemQuantities);

                    buildItemsComboBox();
                    fillItemsFields();

                } catch (Exception updateItemButtonException) {
                    updateItemButtonException.printStackTrace();
                }
            }
        });

        deleteItemButton.addActionListener(e -> {
            if (currentFactory != null) {
                try {
                    if (currentFactory.getStorageElementByName(itemNameField.getText()) == null) {
                        throw new IllegalArgumentException("deleteItemButton says: No such item in storage.");
                    } else {
                        currentFactory.getStorage()
                                .remove(currentFactory.getStorageElementByName(itemNameField.getText()));

                        buildItemsComboBox();
                        fillItemsFields();
                    }
                } catch (Exception deleteItemButtonException) {
                    deleteItemButtonException.printStackTrace();
                }
            }
        });

        // Machines ////////////////////////////////////////////////////////////////////////////////////////////////////

        machineBox.addActionListener(e -> {
            fillMachinesFields();
        });

        addMachineButton.addActionListener(e -> {
            if (currentFactory != null) {
                try {
                    if (currentFactory.getMachineByName(machineNameField.getText()) == null) {
                        if (Machines.getMachineByName(machineNameField.getText()) == null) {
                            // Search for existing outputProduct
                            if (ExistingItems.getItem(outputItemField.getText()) == null) {
                                throw new NoSuchElementException("addMachineButton says: No such machine");
                            }

                            Machines.addMachine(machineNameField.getText(),
                                    ExistingItems.getItem(outputItemField.getText()),
                                    Double.parseDouble(setupTimeField.getText()),
                                    Double.parseDouble(productionTimeField.getText()),
                                    currentFactory);
                        }
                        currentFactory.addMachine(Machines.getMachineByName(machineNameField.getText()));
                        buildMachinesComboBox();
                        fillMachinesFields();
                    } else {
                        System.out.println("addMachineButton says: Machine is already in the factory");
                    }

                } catch (Exception addMachineButtonException) {
                    addMachineButtonException.printStackTrace();
                }
            }
        });

        deleteMachineButton.addActionListener(e -> {
            if (currentFactory != null) {
                try {
                    if (currentFactory.getStorageElementByName(itemNameField.getText()) == null) {
                        throw new IllegalArgumentException("deleteMachineButton says: No such machine in factory.");
                    } else {
                        currentFactory.getMachines()
                                .remove(currentFactory
                                        .getMachineByName(machineBox
                                                .getSelectedItem()
                                                .toString()
                                        )
                                );
                        buildMachinesComboBox();
                        fillMachinesFields();
                    }
                } catch (Exception deleteMachineButtonException) {
                    deleteMachineButtonException.printStackTrace();
                }
            }
        });
    }

    // D E F I N E   L A Y O U T ///////////////////////////////////////////////////////////////////////////////////////
    private void defineLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()

                .addComponent(title)
                // Factory Name
                .addGroup(layout.createSequentialGroup()
                        .addComponent(factoryLabel)
                        .addComponent(factoryNameBox))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(factoryNameLabel)
                        .addComponent(factoryNameField)
                        .addComponent(renameFactoryButton))

                .addComponent(titleItem)
                // Items
                .addGroup(layout.createSequentialGroup()
                        .addComponent(itemLabel)
                        .addComponent(itemBox))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(itemNameLabel)
                        .addComponent(itemNameField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(itemPartsLabel)
                        .addComponent(itemPartsField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(itemPartsQuantityLabel)
                        .addComponent(itemPartsQuantityField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(currentAmountLabel)
                        .addComponent(currentAmountFiled))

                // Item Buttons
                .addGroup(layout.createSequentialGroup()
                        .addComponent(addItemButton)
                        .addComponent(updateItemButton)
                        .addComponent(deleteItemButton))

                .addComponent(titleMachine)
                // Machine
                .addGroup(layout.createSequentialGroup()
                        .addComponent(machineLabel)
                        .addComponent(machineBox))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(machineNameLabel)
                        .addComponent(machineNameField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(outputItemLabel)
                        .addComponent(outputItemField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(setupTimeLabel)
                        .addComponent(setupTimeField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(productionTimeLabel)
                        .addComponent(productionTimeField))

                .addGroup(layout.createSequentialGroup()
                        .addComponent(addMachineButton)
                        .addComponent(deleteMachineButton))

        );

        layout.setVerticalGroup(layout.createSequentialGroup()

                .addComponent(title)

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(factoryLabel)
                        .addComponent(factoryNameBox))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(factoryNameLabel)
                        .addComponent(factoryNameField)
                        .addComponent(renameFactoryButton))

                .addGap(50)

                .addComponent(titleItem)

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(itemLabel)
                        .addComponent(itemBox))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(itemNameLabel)
                        .addComponent(itemNameField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(itemPartsLabel)
                        .addComponent(itemPartsField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(itemPartsQuantityLabel)
                        .addComponent(itemPartsQuantityField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(currentAmountLabel)
                        .addComponent(currentAmountFiled))

                .addGap(30)

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(addItemButton)
                        .addComponent(updateItemButton)
                        .addComponent(deleteItemButton))

                .addGap(50)

                .addComponent(titleMachine)

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(machineLabel)
                        .addComponent(machineBox))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(machineNameLabel)
                        .addComponent(machineNameField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(outputItemLabel)
                        .addComponent(outputItemField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(setupTimeLabel)
                        .addComponent(setupTimeField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(productionTimeLabel)
                        .addComponent(productionTimeField))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(addMachineButton)
                        .addComponent(deleteMachineButton))
        );
    }

    // F U N C T I O N S ///////////////////////////////////////////////////////////////////////////////////////////////
    private void initComponents() {
        title.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        title.setHorizontalTextPosition(JLabel.CENTER);

        titleItem.setFont(new Font("Sans Serif", Font.PLAIN, 28));
        titleItem.setHorizontalTextPosition(JLabel.CENTER);

        titleMachine.setFont(new Font("Sans Serif", Font.PLAIN, 28));
        titleMachine.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void buildFactoriesComboBox() {
        if (Factories.getFactories().size() > 0) {
            factoryNameBox.removeAllItems();
            for (Factory factory : Factories.getFactories()) {
                factoryNameBox.addItem(factory.getName());
            }
            currentFactory = Factories.getFactoryByName((String) factoryNameBox.getSelectedItem());
        }
    }

    private void buildItemsComboBox() {
        if (currentFactory != null) {
            itemBox.removeAllItems();
            currentFactory.getStorage().forEach(storageElement -> {
                itemBox.addItem(storageElement.getItem().getName());
            });
        }
    }

    private void buildMachinesComboBox() {
        if (currentFactory != null) {
            machineBox.removeAllItems();
            currentFactory.getMachines().forEach(machine -> {
                machineBox.addItem(machine.getName());
            });
        }
    }

    private void fillFactoryFields() {
        if (currentFactory != null) {
            factoryNameField.setText(currentFactory.getName());
        }
    }

    private void fillItemsFields() {
        if (currentFactory != null &&
                itemBox.getSelectedItem() != null) {

            itemNameField.setText(currentFactory
                    .getStorageElementByName(itemBox.getSelectedItem().toString())
                    .getItem().getName()
            );
            itemPartsField.setText(currentFactory
                    .getStorageElementByName(itemBox.getSelectedItem().toString())
                    .getItem().getPartsAsString()
            );
            itemPartsQuantityField.setText(currentFactory
                    .getStorageElementByName(itemBox.getSelectedItem().toString())
                    .getItem().getPartsQuantityAsString()
            );
            currentAmountFiled.setText(
                    Integer.toString(currentFactory
                            .getStorageElementByName(itemBox.getSelectedItem().toString())
                            .getAmount()
                    )
            );
        } else {
            itemNameField.setText("");
            itemPartsField.setText("");
            itemPartsQuantityField.setText("");
            currentAmountFiled.setText("");
        }
    }

    private void fillMachinesFields() {
        if (currentFactory != null &&
                machineBox.getSelectedItem() != null) {
            machineNameField.setText(machineBox.getSelectedItem().toString());
            outputItemField.setText(currentFactory
                    .getMachineByName(machineBox
                            .getSelectedItem()
                            .toString())
                    .getOutputProduct().getName()
            );

            setupTimeField.setText(Double.toString(currentFactory
                    .getMachineByName(machineBox
                            .getSelectedItem()
                            .toString()).getSetUpTime())
            );

            productionTimeField.setText(Double.toString(currentFactory
                    .getMachineByName(machineBox
                            .getSelectedItem()
                            .toString()).getProductionTime())
            );
        } else {
            machineNameField.setText("");
            outputItemField.setText("");
            setupTimeField.setText("");
            productionTimeField.setText("");
        }
    }

    private void switchFactory() {
        currentFactory = Factories.getFactoryByName((String) factoryNameBox.getSelectedItem());
        fillFactoryFields();
        buildItemsComboBox();
        fillItemsFields();
        buildMachinesComboBox();
        fillMachinesFields();
    }
}
