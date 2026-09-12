package io.corkline.service;

import io.corkline.authentication.PasswordEncryptor;
import io.corkline.authentication.SessionContext;
import io.corkline.enums.Role;
import io.corkline.model.User;
import io.corkline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void addUser(String fullName, String username, String password, Role role) {
        User user = new User(fullName, username, PasswordEncryptor.encrypt(password), role);
        userRepository.save(user);
    }

    public void changePassword(String newPassword) {
        String encodedPassword = PasswordEncryptor.encrypt(newPassword);
        SessionContext.getUser().setPassword(encodedPassword);
        userRepository.save(SessionContext.getUser());
        System.out.println("Your password has been changed!");
    }

    public void setFavoriteStockThreshold(int favoriteStockThreshold) {
        SessionContext.getUser().setFavoriteStockThreshold(favoriteStockThreshold);
        userRepository.save(SessionContext.getUser());
        System.out.println("The favorite stock threshold has been set to " + favoriteStockThreshold + ".");
    }

    public void setPeakAlertLeadDays(int peakAlertLeadDays) {
        SessionContext.getUser().setPeakAlertLeadDays(peakAlertLeadDays);
        userRepository.save(SessionContext.getUser());
        System.out.println("The peak alert lead days have been set to " + peakAlertLeadDays + ".");
    }

    public void deleteUser(String username) {
        Optional<User> toDelete = userRepository.findByUsername(username);
        toDelete.ifPresent(user -> {
            userRepository.delete(user);
            System.out.println("User " + username + " has been deleted!");
        });
    }
}
