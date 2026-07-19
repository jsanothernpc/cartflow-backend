package com.ayushman.Ecommerce.Service;


import com.ayushman.Ecommerce.Entity.User;
import com.ayushman.Ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

     public User registerUser(User user)
     {
         User newUser= userRepository.save(user);
         return newUser;
     }

     public User loginUser(String email,String password)
     {
         User user= userRepository.findByEmail(email);
         if(user!=null && user.getPassword().equals(password))
         {
             return user;
         }
         throw new RuntimeException("Invalid email or password");
     }

     public List<User> getAllUsers()
     {
         return userRepository.findAll();

     }
}
