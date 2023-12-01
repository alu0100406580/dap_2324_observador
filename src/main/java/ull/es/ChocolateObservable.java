package ull.es;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ChocolateObservable implements Observable {

    String url = "https://tienda.mercadona.es/api/categories/92";
    List<Product> products;

    List<Product> newProducts;
    ScheduledExecutorService scheduler;
    Set<Observer> observerSet;

    ChocolateObservable () {
        this.observerSet = new HashSet<>();
        this.products = new ArrayList<>();
        this.newProducts = new ArrayList<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.scheduler.scheduleAtFixedRate(
                this::updateProducts, // Expresión lambda que ejecuta tu método
                0, // Retraso inicial (en este caso, 0 segundos)
                180, // Intervalo de ejecución en segundos
                TimeUnit.SECONDS
        );
        this.updateProducts();
    }
    @Override
    public void addObserver(Observer o) {
        observerSet.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observerSet.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observerSet) {
            observer.update(this.products, this.newProducts);
            System.out.println("Se ha actualizado " + ((ObserverChocolate) observer).getName());
        }
    }

    List<String> getListObservers() {
        List<String> observerNames = new ArrayList<>();
        for (Observer observer : this.observerSet) {
            if(observer instanceof ObserverChocolate)
                observerNames.add(((ObserverChocolate) observer).getName());
        }
        return observerNames;
    }

    private void updateProducts() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new URL(this.url));
            List<Product> listProductsTemp = new ArrayList<Product>();
            // Suponiendo que las categorías están directamente bajo el nodo raíz
            for (JsonNode category : jsonNode.get("categories")) {
                // Iterar sobre los productos, suponiendo que están bajo el campo "products"
                for (JsonNode product : category.get("products")) {
                    // Obtener el id del producto
                    String productId = product.get("id").asText();
                    String urlProduct = "https://tienda.mercadona.es/api/products/" + productId;
                    Product productToAdd = new Product(urlProduct);
                    listProductsTemp.add(productToAdd);
                }
            }
            saveProducts(listProductsTemp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveProducts(List<Product> productsInput) {
        if(this.products.isEmpty()) {
            this.products = productsInput;
        } else {
            if(this.products != productsInput) {
                List<Product> commonElements = new ArrayList<>(productsInput);
                List<Product> newListProductInput = new ArrayList<>(productsInput);
                commonElements.retainAll(this.products);
                newListProductInput.removeAll(commonElements);
                this.newProducts = newListProductInput;
                this.products = productsInput;
                this.notifyObserver();
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getNewProducts() {
        return newProducts;
    }
}
