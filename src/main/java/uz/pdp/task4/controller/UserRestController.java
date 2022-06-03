package uz.pdp.task4.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task4.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class UserRestController {


    private final UserService userService;

    @PostMapping("/delete-users")
    public void deleteAllUsers(@RequestBody List<String> userIds,
                               HttpServletRequest request) throws IOException {
        userService.deleteUsers(userIds, request);
    }

    @PostMapping("/block-users")
    public void blockAllUsers(@RequestBody List<String> userIds,
                              HttpServletRequest request) {
        userService.blockUsers(userIds, request);
    }

}
