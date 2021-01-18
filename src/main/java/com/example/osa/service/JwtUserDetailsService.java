package com.example.osa.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.osa.repository.UserRepository;
import com.example.osa.entity.UserEntity;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository UserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        try {
            userEntity = UserRepository.findByUsername(username);

            return new User(userEntity.getUsername(),userEntity.getPassword(),new ArrayList<>());
          
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
       
    }
}
