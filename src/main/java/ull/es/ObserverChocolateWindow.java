package ull.es;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ObserverChocolateWindow extends JFrame {

    private ObserverChocolate observer;
    private int index;
    private JLabel imageLabel;
    private JTextArea textArea;
    private JButton prevButton, nextButton, newProductsButton;

    public ObserverChocolateWindow(Observer observerInput) {
        index = 0;
        this.observer = (ObserverChocolate) observerInput;
        setTitle("Productos Suscrito");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de navegación de imágenes
        JPanel imageNavPanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageNavPanel.add(imageLabel, BorderLayout.CENTER);

        // Botones para navegar entre imágenes
        prevButton = new JButton("<");
        nextButton = new JButton(">");
            imageNavPanel.add(prevButton, BorderLayout.WEST);
            imageNavPanel.add(nextButton, BorderLayout.EAST);

        // Área de texto
        textArea = new JTextArea("Texto");
            imageNavPanel.add(textArea, BorderLayout.SOUTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newProductsButton = new JButton("Productos nuevos");
            buttonPanel.add(newProductsButton);

        // Agregar paneles al JFrame
        add(imageNavPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Agregar funcionalidad a los botones
            prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cargar la imagen anterior
                if(index > 0) {
                    index--;
                    String urlImage;
                    if(newProductsButton.getText().contains("nuevos")) {
                        urlImage = observer.getProductsList().get(index).getImageUrl();
                    } else {
                        urlImage = observer.getProductListNew().get(index).getImageUrl();
                    }
                    //String urlImage = observer.getProductsList().get(index).getImageUrl();
                    if(! urlImage.isEmpty()) {
                        loadImage(urlImage);
                    }
                    loadData();
                }
            }
        });

            nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int maxSize;
                if(newProductsButton.getText().contains("nuevos")) {
                    maxSize = observer.getProductsList().size();
                } else {
                    maxSize = observer.getProductListNew().size();
                }
                if(index + 1 < maxSize) {
                    index++;
                    String urlImage;
                    if(newProductsButton.getText().contains("nuevos")) {
                        urlImage = observer.getProductsList().get(index).getImageUrl();
                    } else {
                        urlImage = observer.getProductListNew().get(index).getImageUrl();
                    }
                    // Cargar la siguiente imagen
                    if (!urlImage.isEmpty()) {
                        loadImage(urlImage);
                    }
                    loadData();
                }
            }
        });

            newProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! observer.getProductListNew().isEmpty() && newProductsButton.getText().contains("nuevos")) {
                    newProductsButton.setText("Lista productos");
                    index = 0;
                    String urlImage = observer.getProductListNew().get(index).getImageUrl();
                    if (!urlImage.isEmpty()) {
                        loadImage(urlImage);
                    }
                    loadData();
                } else if(newProductsButton.getText().contains("productos")) {
                    newProductsButton.setText("Productos nuevos");
                    index = 0;
                    String urlImage = observer.getProductsList().get(index).getImageUrl();
                    if (!urlImage.isEmpty()) {
                        loadImage(urlImage);
                    }
                    loadData();
                }
            }
        });
        String urlImage = this.observer.getProductsList().get(this.index).getImageUrl();
        this.loadData();
        loadImage(urlImage);
        this.setVisible(true);
    }

    private void loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            ImageIcon imageIcon = new ImageIcon(url);
            imageLabel.setIcon(imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setText("No se pudo cargar la imagen");
        }
    }

    private void loadData() {
        StringBuilder textAreaBuilder = new StringBuilder();
        int size;
        int id;
        String name;
        String url;
        double price;
        if(newProductsButton.getText().contains("nuevos")) {
            size = observer.getProductsList().size();
            id = observer.getProductsList().get(this.index).getId();
            name = observer.getProductsList().get(this.index).getName();
            url = observer.getProductsList().get(this.index).getUrl();
            price = observer.getProductsList().get(this.index).getPrice();
        } else {
            size = observer.getProductListNew().size();
            id = observer.getProductListNew().get(this.index).getId();
            name = observer.getProductListNew().get(this.index).getName();
            url = observer.getProductListNew().get(this.index).getUrl();
            price = observer.getProductListNew().get(this.index).getPrice();
        }
        textAreaBuilder.append(this.index + 1).append("/").append(size).append(" \n");
        textAreaBuilder.append("ID: ").append(id).append(" \n");
        textAreaBuilder.append("Nombre: ").append(name).append("\n");
        textAreaBuilder.append("Url: ").append(url).append("\n");
        textAreaBuilder.append("Precio: ").append(price).append("€\n");
        this.textArea.setText(textAreaBuilder.toString());
    }
}
