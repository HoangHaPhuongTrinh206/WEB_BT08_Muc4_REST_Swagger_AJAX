package vn.iotstar.controller.api;
 
import java.util.Optional;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import vn.iotstar.entity.Category;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IStorageService;
 
@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {
 
    @Autowired
    private ICategoryService categoryService;
 
    @Autowired
    private IStorageService storageService;
 
    // ---------- GET: danh sách ----------
    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<Response>(
                new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
    }
 
    // ---------- POST: lấy 1 category theo id ----------
    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(true, "Thành công", category.get()), HttpStatus.OK);
        }
        return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
    }
 
    // ---------- POST: thêm ----------
    @PostMapping(path = "/addCategory")
    public ResponseEntity<?> addCategory(
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {
    	Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Category đã tồn tại trong hệ thống", optCategory.get()),
                    HttpStatus.BAD_REQUEST);
        }
 
        Category category = new Category();
        // kiểm tra tồn tại file, lưu file
        if (icon != null && !icon.isEmpty()) {
            String uuString = UUID.randomUUID().toString();
            category.setIcon(storageService.getSorageFilename(icon, uuString));
            storageService.store(icon, category.getIcon());
        }
        category.setCategoryName(categoryName);
        categoryService.save(category);
 
        return new ResponseEntity<Response>(new Response(true, "Thêm thành công", category), HttpStatus.OK);
    }
 
    // ---------- PUT: cập nhật ----------
    @PutMapping(path = "/updateCategory")
    public ResponseEntity<?> updateCategory(
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {
 
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }
 
        Category category = optCategory.get();
        if (icon != null && !icon.isEmpty()) {
            String uuString = UUID.randomUUID().toString();
            category.setIcon(storageService.getSorageFilename(icon, uuString));
            storageService.store(icon, category.getIcon());
        }
        category.setCategoryName(categoryName);
        categoryService.save(category);
 
        return new ResponseEntity<Response>(new Response(true, "Cập nhật thành công", category), HttpStatus.OK);
    }
 
    // ---------- DELETE: xóa ----------
    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }
        categoryService.delete(optCategory.get());
        return new ResponseEntity<Response>(new Response(true, "Xóa thành công", optCategory.get()), HttpStatus.OK);
    }
}