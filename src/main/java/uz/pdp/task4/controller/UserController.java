package uz.pdp.task4.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.task4.model.User;
import uz.pdp.task4.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user_management")
    public String userManagementPage(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "user-management";
    }

    @GetMapping("/changeStatus/{userId}")
    public String changeUserStatus(@PathVariable Long userId, HttpServletRequest request) {
        userService.changeUserStatus(userId, request);
        return "redirect:/user_management";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        userService.deleteUser(userId, request);
        return "redirect:/user_management";
    }
}
