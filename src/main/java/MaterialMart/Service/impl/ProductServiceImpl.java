package MaterialMart.Service.impl;



import MaterialMart.Model.Product;
import MaterialMart.Repository.ProductRepository;
import MaterialMart.Service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    public ProductServiceImpl(ProductRepository repo) { this.repo = repo; }

    @Override
    public List<Product> getAll() { return repo.findAll(); }

    @Override
    public Product getById(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Not found")); }

    @Override
    public Product create(Product p) {
        System.out.println(p);
        return repo.save(p); }

    @Override
    public Product update(Long id, Product p) {
        Product e = getById(id);
        e.setName(p.getName());
        e.setCategory(p.getCategory());
        e.setPrice(p.getPrice());
        e.setStock(p.getStock());
        e.setDescription(p.getDescription());
        e.setImageUrl(p.getImageUrl());
        return repo.save(e);
    }

    @Override
    public void delete(Long id) { repo.deleteById(id); }

    @Override
    public List<Product> findByCategory(String category) { return repo.findByCategoryContainingIgnoreCase(category); }
}