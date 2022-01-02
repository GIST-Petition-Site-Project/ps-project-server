package com.example.gistcompetitioncnserver;

import com.example.gistcompetitioncnserver.user.User;
import com.example.gistcompetitioncnserver.user.UserRepository;
import com.example.gistcompetitioncnserver.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    public final static User ADMIN = new User(1L, "admin@gist.ac.kr", "test1234!", UserRole.ADMIN, true);

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.save(ADMIN);
    }
}
