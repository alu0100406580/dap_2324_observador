package ull.es.cereal;

import ull.es.Observer;
import ull.es.Product;

import java.util.List;
import java.util.Objects;

public class ObserverCereals implements Observer {

    String name;
    List<Product> productsList;
    List<Product> productListNew;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ObserverCereals otherObserver = (ObserverCereals) obj;

        return Objects.equals(this.name, otherObserver.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public ObserverCereals(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
    @Override
    public void update(List<Product> products, List<Product> newProductList) {
        this.productsList = products;
        this.productListNew = newProductList;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public List<Product> getProductListNew() {
        return productListNew;
    }
}
