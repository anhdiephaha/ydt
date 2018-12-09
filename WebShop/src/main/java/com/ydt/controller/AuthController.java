package com.ydt.controller;


import com.ydt.dao.RoleObjectControleDAO;
import com.ydt.dao.UserDAO;
import com.ydt.entity.RoleUser;
import com.ydt.entity.RoleUserId;
import com.ydt.entity.Roles;
import com.ydt.entity.Users;
import com.ydt.payload.*;
import com.ydt.repository.RoleUserRepository;
import com.ydt.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ydt.exception.AppException;
import com.ydt.repository.RoleRepository;
import com.ydt.repository.UserRepository;
import com.ydt.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RoleObjectControleDAO controleDAO;

    @Autowired
    RoleUserRepository roleUserRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse message = new LoginResponse();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(authentication);

        message.setAccessToken(jwt);
        Users u = userDAO.getUserById(user.getId());
        List<Roles> roles = controleDAO.getRoleByUser(user.getId());
        u.setRoles(roles);
//        u.set(u.getRoles().get(0).getId());
        message.setUsername(u);

//        List<Roles> lstRole = controleDAO.getRoleByUser(user.getId());
//        message.setRoles(lstRole);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUserName(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        Users user = new Users(signUpRequest.getName(), signUpRequest.getUsername(),
                 signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users result = userRepository.save(user);

        RoleUser roleUser = new RoleUser();
        RoleUserId roleUserId = new RoleUserId();
        Set<RoleUser> roleUsers = new HashSet<>();
        RoleUser rs = null;
        for(int i=0;i<signUpRequest.getRoles().size();i++){
            roleUserId.setRoleId(signUpRequest.getRoles().get(i));
            roleUserId.setUserId(result.getUserId());
            roleUser.setId(roleUserId);
//            roleUsers.add(roleUser);
            rs = roleUserRepository.save(roleUser);
        }
        if(rs != null){
            logger.info("Insert to user_role successfully");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getFullName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
