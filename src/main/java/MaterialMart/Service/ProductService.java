package MaterialMart.Service;

import MaterialMart.Model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(Long id);
    Product create(Product p);
    Product update(Long id, Product p);
    void delete(Long id);
    List<Product> findByCategory(String category);

}
