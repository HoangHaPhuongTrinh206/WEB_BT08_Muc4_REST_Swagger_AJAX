package vn.iotstar.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.model.Category;
import vn.iotstar.service.ICategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    // Hiển thị form thêm mới
    @GetMapping("add")
    public String add(ModelMap model) {
        model.addAttribute("category", new Category());
        return "admin/categories/addOrEdit";
    }

    // Hiển thị form edit
    @GetMapping("edit/{categoryId}")
    public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Integer categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isPresent()) {
            model.addAttribute("category", optCategory.get());
            return new ModelAndView("admin/categories/addOrEdit", model);
        }
        model.addAttribute("message", "Category is not existed!");
        return new ModelAndView("forward:/admin/categories/searchpaginated", model);
    }

    // Lưu (thêm mới hoặc cập nhật)
    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @ModelAttribute("category") Category category) {
        boolean isEdit = category.getCategoryId() != null;
        categoryService.save(category);
        model.addAttribute("message", isEdit ? "Category is edited!" : "Category is saved!");
        return new ModelAndView("forward:/admin/categories/searchpaginated", model);
    }

    // Xóa
    @GetMapping("delete/{categoryId}")
    public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteById(categoryId);
        model.addAttribute("message", "Category is deleted!");
        return new ModelAndView("forward:/admin/categories/searchpaginated", model);
    }

    // Tìm kiếm + phân trang (trang chính)
    @GetMapping("searchpaginated")
    public String search(ModelMap model,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryName"));

        Page<Category> resultPage;
        if (StringUtils.hasText(categoryName)) {
            resultPage = categoryService.findByCategoryNameContaining(categoryName, pageable);
            model.addAttribute("categoryName", categoryName);
        } else {
            resultPage = categoryService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("categoryPage", resultPage);
        return "admin/categories/searchpaginated";
    }
    
    @GetMapping("ajax")
    public String ajax() {
        return "admin/categories/ajax";   // templates/admin/categories/ajax.html
    }
}