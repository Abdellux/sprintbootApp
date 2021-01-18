package com.example.osa.controller;
//import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.osa.service.JwtUserDetailsService;

import com.example.osa.config.JwtTokenUtil;
import com.example.osa.entity.*;
import com.example.osa.entity.JwtResponse;
import com.example.osa.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
public class JwtAuthController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> postAuthenticate(@RequestBody UserLoginEntity userLoginEntity) throws Exception {
        
        authenticate(userLoginEntity.getUsername(), userLoginEntity.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userLoginEntity.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<String> postRegister(@RequestBody UserRegisterEntity userRegisterEntity) throws Exception {

        UserEntity user = userRepository.findByUsername(userRegisterEntity.getUsername());
        
        if( user == null) {

            user = new UserEntity(userRegisterEntity.getUsername(),
                                 userRegisterEntity.getEmail(),bCryptPasswordEncoder.encode(userRegisterEntity.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        }
        
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
       
    }

    @GetMapping(value="/api/ressource")
    public String getMethodName() {
        return "It's work";
    }
    

}