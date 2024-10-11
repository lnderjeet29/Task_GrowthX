package com.growthx.Task.services.Impl;

import com.growthx.Task.dto.LoginRequest;
import com.growthx.Task.model.User;
import com.growthx.Task.repository.UserRepository;
import com.growthx.Task.security.SecurityConfiguration;
import com.growthx.Task.services.JWTServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private JWTServices jwtServices;

    public void registerUser(User user) {
        try {
            if (userRepository.findByEmail(user.getUsername()) != null) {
                throw new Exception("User already exists with username: " + user.getUsername());
            }
            user.setPassword(securityConfiguration.passwordEncoder().encode(user.getPassword()));
            user.setRoles("USER");
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found!"));
            }
        };
    }
    public User findUser(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not Found!"));
    }
    public String loginUser(LoginRequest loginRequest) throws Exception {
        User user = userRepository.findByEmail(loginRequest.getUser_name()).orElseThrow(()-> new UsernameNotFoundException("User not found!"));
        if (user == null || !securityConfiguration.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception("Invalid username or password.");
        }
        return jwtServices.generateToken(user); // Generate JWT token
    }
    public void registerAdmin(User user) {
        user.setPassword(securityConfiguration.passwordEncoder().encode(user.getPassword()));
        user.setRoles("ADMIN");
        userRepository.save(user);
    }
    /**
     * Fetch all users who have the role "ADMIN".
     *
     * @return List of users with the role "ADMIN".
     */
    public List<User> getAllAdmins() {
        // Query the repository to find users with the role "ADMIN"
        return userRepository.findByRoles("ADMIN");
    }
}