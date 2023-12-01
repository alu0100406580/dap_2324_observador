package ull.es;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InitGui {

    private ChocolateObservable choco;
    private CerealObservable cereal;
    private JFrame frame;
    private JButton createUser, updateList;
    private JTextArea textArea;
    private JTextField textField;

    public InitGui() {

        this.choco = new ChocolateObservable();
        this.cereal = new CerealObservable();

        // Crear el marco (ventana principal)
        this.frame = new JFrame("Ventana con Botón y TextArea");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear un área de texto no editable
        this.textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Crear un subpanel para los botones con Layout de flujo
        JPanel subpanelBotones = new JPanel(new GridLayout(1, 2));

        // Crear un cuadro de texto
        this.textField = new JTextField();
        this.textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.textField.getPreferredSize().height));
        subpanelBotones.add(textField);

        // Crear dos botones y agregarlos al subpanel
        this.createUser = new JButton("Chocolate");
        subpanelBotones.add(createUser);

        this.updateList = new JButton("Cereales");
        subpanelBotones.add(updateList);

        // Agregar el subpanel de botones al borde inferior de la ventana
        frame.add(subpanelBotones, BorderLayout.SOUTH);

        // Acción del primer botón
        createUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! textField.getText().isEmpty()) {
                    String userName = textField.getText();
                    Observer observer = new ObserverChocolate(userName);
                    observer.update(choco.products, choco.newProducts);
                    choco.addObserver(observer);
                    new ObserverChocolateWindow(observer);
                    writeList();
                }
            }
        });

        // Acción del segundo botón
        updateList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! textField.getText().isEmpty()) {
                    String userName = textField.getText();
                    Observer observer = new ObserverCereals(userName);
                    observer.update(cereal.products, cereal.newProducts);
                    cereal.addObserver(observer);
                    new ObserverCerealWindow(observer);
                    writeList();
                }
            }
        });

        // Hacer visible la ventana
        frame.setVisible(true);
    }

    private void createUser(String user) {

    }

    private void writeList() {
        this.textArea.setText("");
        for(String observerNames : this.choco.getListObservers()) {
            this.textArea.append(observerNames + " -> Suscrito a chocolates." + "\n");
        }
        for(String observerNames : this.cereal.getListObservers()) {
            this.textArea.append(observerNames + " -> Suscrito a cereales." + "\n");
        }
    }
}
