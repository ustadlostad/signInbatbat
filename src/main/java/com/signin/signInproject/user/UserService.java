package com.signin.signInproject.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId){

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()){
            throw new IllegalStateException("User not found");
        }

        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String userEmail){

        Optional<User> theUser = userRepository.findUserByEmail(userEmail);


        return null;
    }

    public User saveUser(User user){

        Optional<User> userEmail = userRepository.findUserByEmail(user.getEmail());
        if(userEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        userRepository.save(user);

        return user;
    }

    public void deleteUser(long userId){

        Optional<User> tempUser = userRepository.findById(userId);

        if(!tempUser.isPresent()){
            throw new IllegalStateException("User not found");
        }

        userRepository.deleteById(userId);
        log.info("Deleted user ID is :" +userId);
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

        public String loginUser(String email, String password ){

            String thePassword = "";

            Optional<User> theUser = userRepository.findUserByEmail(email);

            if(theUser.isPresent()){
                thePassword = theUser.get().getPassword();
            }

            if(!password.equals(thePassword) && !password.isEmpty()){
                throw new RuntimeException("password is not correct!");
            }

            return "ok";
    }

    public Optional<User> registerUser(String email,
                                       String firstName,
                                       String lastName,
                                       String dateOfBirth,
                                       String password) {

        Optional<User> theUser = userRepository.findUserByEmail(email);

        if(!theUser.isPresent()){
            theUser.get().setEmail(email);
            theUser.get().setFirstName(firstName);
            theUser.get().setLastName(lastName);
            theUser.get().setDateOfBirth(dateOfBirth);
            theUser.get().setPassword(password);

            return Optional.of(userRepository.save(theUser.get()));

        }else {
            throw new RuntimeException("User is already exist!");
        }

    }
}
