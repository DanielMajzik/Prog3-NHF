package NHF.GUI;

import javax.swing.*;
import java.util.ArrayList;

public class MenuBar extends JMenuBar {
    private ArrayList<String[]> menuBarTexts;

    public MenuBar(ArrayList<String[]> menuBarTexts) {
        this.menuBarTexts = menuBarTexts;
        initComponents();
        //setBackground(Color.BLACK);
    }

    private void initComponents() {
        menuBarTexts.forEach(strings -> {
            JMenu menu = new JMenu(strings[0]);
            if (strings.length > 1) {
                for (int i = 1; i < strings.length; i++) {
                    menu.add(new JMenuItem(strings[i]));
                }
            }
            add(menu);
        });
        setVisible(true);
    }

    public JMenuItem getMenuItem(String name) {

        for (int i = 0; i < getComponentCount(); i++) {
            for (int j = 0; j < getMenu(i).getItemCount(); j++) {
                if (getMenu(i).getItem(j).getText().equals(name)) {
                    return getMenu(i).getItem(j);
                }
            }
        }
        System.out.println("JMenuItem cannot be found.");
        return null;
    }

    public void updateContent(ArrayList<String[]> menuBarTexts) {
        this.menuBarTexts = menuBarTexts;
        removeAll();
        initComponents();
    }
}
