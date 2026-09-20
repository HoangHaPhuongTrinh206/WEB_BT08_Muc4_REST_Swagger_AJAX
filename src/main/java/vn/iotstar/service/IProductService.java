package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Product;

public interface IProductService {

    List<Product> findAll();

    List<Product> findAll(Sort sort);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Optional<Product> findByProductName(String name);

    List<Product> findByCategoryId(Long categoryId, Sort sort);

    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    Product save(Product product);

    void delete(Product product);

    void deleteById(Long id);

    long count();
}