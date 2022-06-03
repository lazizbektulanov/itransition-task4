package uz.pdp.task4.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.task4.payload.SignUpDto;
import uz.pdp.task4.service.AuthService;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @ModelAttribute("signUpDto")
    public SignUpDto registerDto() {
        return new SignUpDto();
    }

    @GetMapping("/login")
    public String loginPage() {
        return "sign-in";
    }

    @PostMapping("/signup")
    public String signUpUser(@ModelAttribute("signUpDto") SignUpDto signUpDto,
                             Model model) {
        return authService.signUpUser(signUpDto,model);
    }
}
