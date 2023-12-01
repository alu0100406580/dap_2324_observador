package ull.es;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Product {

    private int id;
    private String name;
    private String url;
    private String imageUrl;
    private double price;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product otherProduct = (Product) obj;
        return id == otherProduct.id && price == otherProduct.price;
    }
    private void sleepExponentially(int retries) {
        try {
            long waitTime = (long) Math.pow(3, retries) * 1000; // Espera 2, 4, 8, ... segundos
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private JsonNode readJsonFromUrlWithRetries(URL url, int maxRetries) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        int retries = 0;
        while (true) {
            try {
                return objectMapper.readTree(url);
            } catch (IOException e) {
                if (++retries >= maxRetries) {
                    throw e; // Si alcanza el máximo de intentos, lanza la excepción
                }
                // Espera antes de reintentar (puedes ajustar el tiempo de espera según tus necesidades)
                sleepExponentially(retries);
            }
        }
    }
        Product(String url) {
        try {
            //JsonNode jsonNode = readJsonFromUrl(url);
            JsonNode jsonNode = readJsonFromUrlWithRetries(new URL(url), 3);
            this.id = jsonNode.get("id").asInt();
            this.imageUrl = jsonNode.path("photos").get(0).path("regular").asText();
            this.price = jsonNode.path("price_instructions").path("reference_price").asDouble();
            this.name = jsonNode.path("display_name").asText();
            this.url = jsonNode.path("share_url").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }
}
