package uz.pdp.task4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.task4.model.User;
import uz.pdp.task4.payload.SignUpDto;
import uz.pdp.task4.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService,
        ApplicationListener<AuthenticationSuccessEvent>{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  userRepository.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException("Username not found")
        );
    }

    public String signUpUser(SignUpDto signUpDto, Model model) {
        if(!userRepository.existsUserByEmail(signUpDto.getEmail())){
            if (signUpDto.getPassword().equals(signUpDto.getConfirmPassword())){
                User user = new User();
                user.setName(signUpDto.getName());
                user.setEmail(signUpDto.getEmail());
                user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
                user.setRegistrationTime(Timestamp.from(Instant.now()));
                user.setLastLoginTime(Timestamp.from(Instant.now()));
                user.setEnabled(true);
                model.addAttribute("success","Successfully registered");
                userRepository.save(user);
            }else {
                model.addAttribute("error", "Password confirmation failed");
            }
        }else {
            model.addAttribute("error","This user already exists");
        }
        return "sign-in";
    }


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        user.setLastLoginTime(Timestamp.from(Instant.now()));
        userRepository.save(user);
    }

}
