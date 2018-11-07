package com.ydt.controller;

import com.ydt.entity.Profile;
import com.ydt.payload.Payload;
import com.ydt.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Profile profile) {
        Payload msg = new Payload();
        try {
            profileRepository.save(profile);
        }catch (Exception e){
            logger.error("Tạo profile thất bại !");
            msg = new Payload("False","Tạo profile thất bại",null);
        }
        msg.setStatus("true");
        msg.setMesssage("Tạo profile thành công !");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }

}
