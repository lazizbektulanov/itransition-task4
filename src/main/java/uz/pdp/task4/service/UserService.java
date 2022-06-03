package uz.pdp.task4.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.task4.model.User;
import uz.pdp.task4.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    public void changeUserStatus(Long userId, HttpServletRequest request) {
        invalidateSessionOfCurrentUser(userId, request);
        userRepository.changeUserStatus(userId);
    }

    public void deleteUser(Long userId, HttpServletRequest request) {
        invalidateSessionOfCurrentUser(userId, request);
        userRepository.deleteById(userId);
    }

    private void invalidateSessionOfCurrentUser(Long userId, HttpServletRequest request) {
        User deletingUser = userRepository.getById(userId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserUsername = ((UserDetails) principal).getUsername();
        if (deletingUser.getEmail().equals(currentUserUsername)) {
            HttpSession session = request.getSession(false);
            SecurityContextHolder.clearContext();
            if (session != null) {
                session.invalidate();
            }
        }
    }

    public void deleteUsers(List<String> userIds, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        Optional<User> currentUser = userRepository.findByEmail(email);
        for (String userId : userIds) {
            if (Long.valueOf(userId).equals(currentUser.get().getId())) {
                HttpSession session = request.getSession(false);
                SecurityContextHolder.clearContext();
                if (session != null) {
                    session.invalidate();
                }
            }
            userRepository.deleteById(Long.valueOf(userId));
        }
    }

    @Transactional
    public void blockUsers(List<String> userIds, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        Optional<User> currentUser = userRepository.findByEmail(email);
        List<Long> userIdsLongValue = new ArrayList<>();
        for (String userId : userIds) {
            userIdsLongValue.add(Long.valueOf(userId));
            if (Long.valueOf(userId).equals(currentUser.get().getId())) {
                HttpSession session = request.getSession(false);
                SecurityContextHolder.clearContext();
                if (session != null) {
                    session.invalidate();
                }
            }
        }
        userRepository.blockAllUsers(userIdsLongValue);
    }
}
