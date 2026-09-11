package io.corkline.service;

import io.corkline.authentication.PasswordEncryptor;
import io.corkline.authentication.SessionContext;
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

    public void changePassword(String newPassword) {
        String encodedPassword = PasswordEncryptor.encrypt(newPassword);
        SessionContext.getUser().setPassword(encodedPassword);
        userRepository.save(SessionContext.getUser());
        System.out.println("Your password has been changed!");
    }

}
