package ull.es;

import java.util.List;

public interface Observer {
    void update(List<Product> products, List<Product> newProductList);
}
