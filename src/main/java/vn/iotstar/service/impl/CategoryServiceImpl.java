package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.model.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByCategoryNameContaining(String categoryName) {
        return categoryRepository.findByCategoryNameContaining(categoryName);
    }

    @Override
    public Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable) {
        return categoryRepository.findByCategoryNameContaining(categoryName, pageable);
    }
}