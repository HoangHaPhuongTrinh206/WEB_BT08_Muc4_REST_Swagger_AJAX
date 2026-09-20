package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.ProductDao;
import vn.iotstar.dao.impl.ProductDaoImpl;
import vn.iotstar.model.Product;
import vn.iotstar.service.ProductService;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product get(int id) {
        return productDao.get(id);
    }

    @Override
    public void insert(Product product) {
        productDao.insert(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(int id) {
        productDao.delete(id);
    }

    @Override
    public List<Product> get10NewestProducts() {
        return productDao.get10NewestProducts();
    }

    @Override
    public List<Product> getProductsByPage(int offset, int limit) {
        return productDao.getProductsByPage(offset, limit);
    }

    @Override
    public long getTotalProducts() {
        return productDao.getTotalProducts();
    }
}