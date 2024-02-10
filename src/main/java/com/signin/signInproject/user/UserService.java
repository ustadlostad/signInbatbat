package com.signin.signInproject.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new IllegalStateException("User not found");
        }

        return userRepository.findById(userId);
    }

    public User getUserByEmail(String userEmail) {

        User theUser = userRepository.findUserByEmail(userEmail);

        return null;
    }

    public User saveUser(User user) {

        User userEmail = userRepository.findUserByEmail(user.getEmail());
        if (user.getEmail().equals(userEmail)) {
            throw new IllegalStateException("email taken");
        }
        userRepository.save(user);

        return user;
    }

    public void deleteUser(long userId) {

        Optional<User> tempUser = userRepository.findById(userId);

        if (!tempUser.isPresent()) {
            throw new IllegalStateException("User not found");
        }

        userRepository.deleteById(userId);
        log.info("Deleted user ID is :" + userId);
    }

    public Optional<User> updateUser(Long userId, User user) {

        Optional<User> theUser = userRepository.findById(userId);

        if (!theUser.isPresent()) {
            throw new IllegalStateException("User Id: " + userId + " is not present ");
        } else {
            theUser.get().setEmail(user.getEmail());
            theUser.get().setFirstName(user.getFirstName());
            theUser.get().setLastName(user.getLastName());
            theUser.get().setDateOfBirth(user.getDateOfBirth());
            theUser.get().setPassword(user.getPassword());

            return Optional.of(userRepository.save(theUser.get()));
        }
    }

    public ResponseEntity loginUser(String email, String password) {

        String thePassword = "";

        User theUser = userRepository.findUserByEmail(email);

        if (!theUser.equals(null) || !theUser.equals("")) {
            thePassword = theUser.getPassword();
        }

        if (!password.equals(thePassword) && !password.isEmpty()) {
            throw new RuntimeException("password is not correct!");
        }

        return ResponseEntity.ok("User logged In!");
    }

    public User registerUser(String email,
                             String firstName,
                             String lastName,
                             String dateOfBirth,
                             String password) {
        User user = new User();

        User theUser = userRepository.findUserByEmail(email);

        if (theUser == null) {

            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDateOfBirth(dateOfBirth);
            user.setPassword(password);
        } else {
            System.out.println("the user already exist!");
            throw new RuntimeException("User is already exists!");
        }
        return userRepository.save(user);

    }
}

