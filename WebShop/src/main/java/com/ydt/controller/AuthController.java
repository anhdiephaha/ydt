package com.ydt.controller;


import com.ydt.dao.RoleObjectControleDAO;
import com.ydt.dao.UserDAO;
import com.ydt.entity.*;
import com.ydt.payload.*;
import com.ydt.repository.*;
import com.ydt.security.UserPrincipal;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ydt.exception.AppException;
import com.ydt.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    public JavaMailSender emailSender;

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
        Users user = null;
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Department department = departmentRepository.getOne(signUpRequest.getIdDepartment());
        PositionUser positionUser = positionRepository.getOne(signUpRequest.getIdPosition());
        try {
            if (signUpRequest.getId() == 0) {
                if (userRepository.existsByUserName(signUpRequest.getUsername())) {
                    return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                            HttpStatus.BAD_REQUEST);
                }

                user = new Users(signUpRequest.getName(), signUpRequest.getPassword(), signUpRequest.getUsername(), signUpRequest.getGioiTinh(), fmt.parse(signUpRequest.getNgayBatDau()),
                        fmt.parse(signUpRequest.getNgaySinh()), signUpRequest.getSdt(), signUpRequest.getDiaChi(),department,positionUser,signUpRequest.getEmail());

            } else {
                user = new Users(signUpRequest.getId(), signUpRequest.getName(), signUpRequest.getPassword(), signUpRequest.getUsername(), signUpRequest.getGioiTinh(), fmt.parse(signUpRequest.getNgayBatDau()),
                        fmt.parse(signUpRequest.getNgaySinh()), signUpRequest.getSdt(), signUpRequest.getDiaChi(),department,positionUser,signUpRequest.getEmail());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Creating user's account

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users result = userRepository.save(user);

        RoleUser roleUser = new RoleUser();
        RoleUserId roleUserId = new RoleUserId();
        Set<RoleUser> roleUsers = new HashSet<>();
        RoleUser rs = null;
        for (int i = 0; i < signUpRequest.getRoles().size(); i++) {
            if (roleUserRepository.existsUserIdAndRoleId(result.getUserId(), signUpRequest.getRoles().get(i)) == 0) {
                roleUserId.setRoleId(signUpRequest.getRoles().get(i));
                roleUserId.setUserId(result.getUserId());
                roleUser.setId(roleUserId);
//            roleUsers.add(roleUser);
                rs = roleUserRepository.save(roleUser);
            }
        }
        if (rs != null) {
            logger.info("Insert to user_role successfully");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getFullName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
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
        for (int i = 0; i < signUpRequest.getRoles().size(); i++) {
            roleUserId.setRoleId(signUpRequest.getRoles().get(i));
            roleUserId.setUserId(result.getUserId());
            roleUser.setId(roleUserId);
//            roleUsers.add(roleUser);
            rs = roleUserRepository.save(roleUser);
        }
        if (rs != null) {
            logger.info("Insert to user_role successfully");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getFullName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }


    @GetMapping("/getAllDepartment")
    public ResponseEntity<?> getAllDepartment() {
        Payload msg = new Payload();
        List<Department> d = null;
        try {
            d = departmentRepository.findAll();
        } catch (Exception e) {
            logger.error("Lấy phòng ban thất bại !");
            msg = new Payload("False", "Lấy phòng ban thất bại", null);
        }

        msg.setStatus("true");
        msg.setMesssage("Lấy phòng ban thành công !");
        msg.setData(d);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/getAllPosition")
    public ResponseEntity<?> getAllPosition() {
        Payload msg = new Payload();
        List<PositionUser> p = null;
        try {
            p = positionRepository.findAll();
        } catch (Exception e) {
            logger.error("Lấy vị trí thất bại !");
            msg = new Payload("False", "Lấy vị trí thất bại", null);
        }

        msg.setStatus("true");
        msg.setMesssage("Lấy vị trí thành công !");
        msg.setData(p);
        return ResponseEntity.ok(msg);
    }


    @GetMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("userName") String userName) {
        Payload msg = new Payload();
        Users userForgot = userRepository.findByUserName(userName);
        try {
            if(userForgot != null){
                String newPass = RandomStringUtils.random(10, false, true);
//                String newPass = passwordEncoder.encode("123456");
                SimpleMailMessage message = new SimpleMailMessage();

                message.setTo(userForgot.getEmail());
                message.setSubject("Đặt lại mật khẩu cho tài khoản admin YenDT Shop");
                StringBuffer sb = new StringBuffer();
                sb.append("Xin chào "+userForgot.getFullName()+"!");
                sb.append("\n");
                sb.append("Chúng tôi đã thực hiện thay đổi mật khẩu tài khoản của bạn. Mật khẩu mới của bạn là: " + newPass);
                sb.append("\n");
                sb.append("Nếu bạn đã nhận được mật khẩu vui lòng xóa mail này");
                sb.append("\n");
                sb.append("YenDT Shop");
                message.setText(sb.toString());

                // Send Message!
                this.emailSender.send(message);
                //save to database
                userForgot.setPassword(passwordEncoder.encode(newPass));
                userRepository.save(userForgot);
                logger.info("Success sent mail and reset password");
            }

        } catch (Exception e) {
            logger.error("Đặt lại mật khẩu thất bại !");
            msg = new Payload("False", "Đặt lại mật khẩu thất bại", null);
        }

        msg.setStatus("true");
        msg.setMesssage("Đặt lại mật khẩu thành công");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }


    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteRole(@PathVariable int userId){
        Payload msg = new Payload();
        Users users = userRepository.getOne(userId);
        try {
            userRepository.delete(users);
        }catch (Exception e) {
            logger.error("Xóa user thất bại");
            msg = new Payload("False","Xóa user thất bại", null);
        }
        msg.setStatus("true");
        msg.setMesssage("Xóa user thành công");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }


}
