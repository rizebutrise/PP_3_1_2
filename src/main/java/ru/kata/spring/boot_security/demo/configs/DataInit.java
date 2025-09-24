package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInit {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInit(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role roleUser = new Role("USER");
        Role roleAdmin = new Role("ADMIN");

        Role savedRoleUser = userService.saveRole(roleUser);
        Role savedRoleAdmin = userService.saveRole(roleAdmin);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@example.com");
        admin.setName("Admin");
        admin.setRoles(Set.of(savedRoleAdmin, savedRoleUser));

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@example.com");
        user.setName("User");
        user.setRoles(Set.of(savedRoleUser));

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}