package vn.iotstar.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.model.User;
import vn.iotstar.repository.UserRepository;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users = (keyword != null && !keyword.isBlank())
                ? userRepository.findByUserNameContainingOrEmailContaining(keyword, keyword)
                : userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "admin/user-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(new Date(System.currentTimeMillis()));
        }
        userRepository.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(new User()));
        return "admin/user-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user";
    }
}