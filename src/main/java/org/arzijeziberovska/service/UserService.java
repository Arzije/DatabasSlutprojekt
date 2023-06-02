package org.arzijeziberovska.service;

import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.UserRepository;


public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //validerar att användaren finns och att lösenordet stämmer
    public User authenticateUser(String ssn, String password) {
        User user = userRepository.getUserBySSN(ssn);

        if (user != null && verifyUserCredentials(password, user.getPassword())) {
            System.out.println("""
                    You are now logged in!
                    """);
            return user;
        } else {
            System.out.println("Wrong credentials! Please try again.");
        }
        return null;
    }

    private boolean verifyUserCredentials(String password, String hashedPassword) {
        return PasswordService.Verify(password, hashedPassword);
    }

    // kollar om användare med det SSN redan finns, om inte så skapas en ny användare
        public void createUser(String name, String SSN, String email, String address, String phone, String password) {

            if (userRepository.getUserBySSN(SSN) != null || userRepository.getUserByEmail(email) != null) {
                System.out.println("User with SSN " + SSN + " or with email " + email + " already exists");
                System.out.println();

            } else {
                User newUser = new User(PasswordService.Hash(password), email, phone, address, name, SSN);
                userRepository.saveUser(newUser);
                System.out.println("");
            }
        }

        // ger användaren möjlighet att välja vilken info som ska uppdateras
    public void updateUserInfo(User authenticatedUser,
                               String newPassword,
                               String email, String phone,
                               String address,
                               String name) {
        User updatedUser =
                new User(authenticatedUser.getPassword(),
                        authenticatedUser.getEmail(),
                        authenticatedUser.getPhoneNumber(),
                        authenticatedUser.getAddress(),
                        authenticatedUser.getName(),
                        authenticatedUser.getSSN());

        if (!newPassword.isEmpty()) {
            String hashedPassword = PasswordService.Hash(newPassword);
            updatedUser.setPassword(hashedPassword);
        }

        if (!email.isEmpty()) {
            updatedUser.setEmail(email);
        }

        if (!phone.isEmpty()) {
            updatedUser.setPhoneNumber(phone);
        }

        if (!address.isEmpty()) {
            updatedUser.setAddress(address);
        }

        if (!name.isEmpty()) {
            updatedUser.setName(name);
        }

        userRepository.updateUser(updatedUser);
    }
}





