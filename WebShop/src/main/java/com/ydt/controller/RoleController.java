package com.ydt.controller;

import com.ydt.dao.RoleObjectControleDAO;
import com.ydt.entity.ObjectControl;
import com.ydt.entity.Roles;
import com.ydt.entity.role;
import com.ydt.payload.Payload;
import com.ydt.repository.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleObjectControleDAO controleDAO;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    private static final Logger getLogger = LoggerFactory.getLogger(RoleController.class);

    @GetMapping("/getAllObjectControlOfRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getProductByCate(@RequestParam("idUser") Integer idUser) {
        Payload msg = new Payload();
        List<ObjectControl> p = null;
        try {
            p = controleDAO.getAllObjectControlOfRole(idUser);
            msg = new Payload("Thành Công","Nhận tất cả quyền của role",p);
        }catch (Exception e){
            logger.error("Lấy sản phẩm thất bại !");
            e.printStackTrace();
            msg = new Payload("Thất bại",e.getMessage(),null);
        }
        return ResponseEntity.ok(msg);
    }
    
    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@Valid @RequestBody role roles) {
        Payload msg = new Payload();
        try {
            rolesRepository.save(roles);
        } catch (Exception e) {
            logger.error("Tạo Role thất bại");
            msg = new Payload("False","Tạo Role thất bại", null);
        }
        msg.setStatus("true");
        msg.setMesssage("Tạo role thành công");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }
    
    @PutMapping("/updateRole")
    public ResponseEntity<?> updateRole(@Valid @RequestBody role roles) {
        Payload msg = new Payload();
        try {
            rolesRepository.save(roles);
        } catch (Exception e) {
            logger.error("Cập nhật role thất bại");
            msg = new Payload("False","Cập nhật role thất bại", null);
        }
        msg.setStatus("true");
        msg.setMesssage("Sửa role thành công");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }
    
    @DeleteMapping("/deleteRole/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable int roleId){
        Payload msg = new Payload();
        role roles = rolesRepository.getOne(roleId);
        try {
            rolesRepository.delete(roles);
        }catch (Exception e) {
            logger.error("Xóa role thất bại");
            msg = new Payload("False","Xóa role thất bại", null);
        }
        msg.setStatus("true");
        msg.setMesssage("Xóa role thành công");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<?> getOneRole(@PathVariable int roleId) {
        Payload msg = new Payload();
        role roles =null;
        try {
            roles = rolesRepository.getOne(roleId);
        } catch (Exception e){
            logger.error("Lấy role thất bại");
            msg = new Payload("False", "Lấy role thất bại", null);
        }
        msg.setStatus("true");
        msg.setMesssage("Lấy role thành công");
        msg.setData(roles);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/getAllRole")
    public ResponseEntity<?> getAllRole() {
        Payload msg = new Payload();
        List<role> roles = null;
        try {
            roles = rolesRepository.findAll();
        } catch (Exception e) {
            logger.error("Lấy danh sách role thất bại");
            msg = new Payload("False", "Lấy danh sách role thất bại", null);
        }
        msg.setStatus("true");
        msg.setMesssage("Lấy danh sách role thành công");
        msg.setData(roles);
        return ResponseEntity.ok(msg);
    }
    
}
