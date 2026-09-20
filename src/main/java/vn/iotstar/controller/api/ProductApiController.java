package vn.iotstar.controller.api;
 
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IStorageService;
 
@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {
 
    @Autowired private IProductService productService;
    @Autowired private ICategoryService categoryService;
    @Autowired private IStorageService storageService;
 
    // ---------- GET: danh sách ----------
    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(
                new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }
 
    // ---------- POST: lấy 1 product ----------
    @PostMapping(path = "/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam("id") Long id) {
        Optional<Product> opt = productService.findById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<Response>(new Response(true, "Thành công", opt.get()), HttpStatus.OK);
        }
        return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null), HttpStatus.NOT_FOUND);
    }
 
    // ---------- POST: thêm ----------
    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("discount") Double discount,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("status") Short status) {
 
        Optional<Product> optProduct = productService.findByProductName(productName);
        if (optProduct.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Sản phẩm này đã tồn tại trong hệ thống", optProduct.get()),
                    HttpStatus.BAD_REQUEST);
        }
        Optional<Category> optCate = categoryService.findById(categoryId);
        if (optCate.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }
 
        Product product = new Product();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setStatus(status);
        product.setCategory(optCate.get());          // xử lý category liên quan product
        product.setCreateDate(new Date());
 
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImages(saveImage(imageFile));  // lưu file vào trường images
        }
 
        Product saved = productService.save(product);
        return new ResponseEntity<Response>(new Response(true, "Thêm thành công", saved), HttpStatus.OK);
    }
 
    // ---------- PUT: cập nhật ----------
    @PutMapping(path = "/updateProduct")
    public ResponseEntity<?> updateProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("discount") Double discount,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("status") Short status) {
 
        Optional<Product> opt = productService.findById(productId);
        if (opt.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
        }
        Optional<Category> optCate = categoryService.findById(categoryId);
        if (optCate.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }
 
        Product product = opt.get();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setStatus(status);
        product.setCategory(optCate.get());
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImages(saveImage(imageFile));  // không chọn ảnh mới -> giữ ảnh cũ
        }
 
        productService.save(product);
        return new ResponseEntity<Response>(new Response(true, "Cập nhật thành công", product), HttpStatus.OK);
    }
 
    // ---------- DELETE: xóa ----------
    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Long productId) {
        Optional<Product> opt = productService.findById(productId);
        if (opt.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
        }
        productService.delete(opt.get());
        return new ResponseEntity<Response>(new Response(true, "Xóa thành công", opt.get()), HttpStatus.OK);
    }
    
    // ---------- hàm dùng chung: lưu file, trả về tên file ----------
    private String saveImage(MultipartFile file) {
        String fileName = storageService.getSorageFilename(file, UUID.randomUUID().toString());
        storageService.store(file, fileName);
        return fileName;
    }
}