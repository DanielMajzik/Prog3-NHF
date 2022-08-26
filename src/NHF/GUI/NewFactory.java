package NHF.GUI;

import NHF.Buildings.Factories;

import javax.swing.*;
import java.awt.*;

public class NewFactory extends JPanel {

    public NewFactory() {
        JLabel title = new JLabel("New Factory");
        title.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        title.setHorizontalTextPosition(JLabel.CENTER);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JButton addButton = new JButton("Add Factory");

        // AddButton ActionListener
        addButton.addActionListener(e -> {
            Factories.addFactory(nameField.getText());
            System.out.println(Factories.getFactories().get(Factories.getFactories().size() - 1).getName());
        });

        // Layout //////////////////////////////////////////////////////////////////////////////////////////////////////
        GroupLayout groupLayout = new GroupLayout(this);
        setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(title)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(nameLabel)
                                .addComponent(nameField))
                        .addComponent(addButton)
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(title)
                        .addGap(50)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(nameLabel)
                                .addComponent(nameField))
                        .addComponent(addButton)
        );
    }
}
