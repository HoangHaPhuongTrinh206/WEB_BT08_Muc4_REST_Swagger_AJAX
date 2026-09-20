package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Category;

public interface ICategoryService {

    <S extends Category> S save(S entity);

    Optional<Category> findByCategoryName(String name);

    List<Category> findAll();

    Page<Category> findAll(Pageable pageable);

    List<Category> findAll(Sort sort);

    List<Category> findAllById(Iterable<Long> ids);

    Optional<Category> findById(Long id);

    <S extends Category> Optional<S> findOne(Example<S> example);

    long count();

    void deleteById(Long id);

    void delete(Category entity);

    List<Category> findByCategoryNameContaining(String name);

    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}