package com.example.Edumate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Edumate.model.User;
import com.example.Edumate.repository.UserRepo;
 //finds user in database and returns it to the security
@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user=userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        return new CustomUserDetails(user);
        
    }
}
