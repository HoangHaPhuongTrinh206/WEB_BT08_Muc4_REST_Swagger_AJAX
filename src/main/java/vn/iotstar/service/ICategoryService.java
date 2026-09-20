package vn.iotstar.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.model.Category;

public interface ICategoryService {
    Category save(Category category);
    List<Category> findAll();
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findById(Integer id);
    void deleteById(Integer id);
    List<Category> findByCategoryNameContaining(String categoryName);
    Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);
}