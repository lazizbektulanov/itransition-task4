package uz.pdp.task4.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.task4.model.User;
import uz.pdp.task4.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {


    @Value("${spring.sql.init.mode}")
    private String initMode;

    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            userRepository.save(new User("user", "user@gmail.com",
                    new BCryptPasswordEncoder().encode("user"),
                    Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), true));
        }
    }
}
