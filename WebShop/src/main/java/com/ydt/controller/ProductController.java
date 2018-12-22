package com.ydt.controller;

import com.ydt.entity.Category;
import com.ydt.entity.Product;
import com.ydt.payload.LoginRequest;
import com.ydt.payload.Payload;
import com.ydt.payload.PayloadProduct;
import com.ydt.repository.CategoryRepository;
import com.ydt.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        Payload msg = new Payload();
        try {
            productRepository.save(product);
        }catch (Exception e){
            logger.error("Tạo sản phẩm thất bại !");
            msg = new Payload("False","Tạo sản phẩm thất bại",null);
        }
        msg.setStatus("true");
        msg.setMesssage("Tạo sản phẩm thành công !");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) {
        Payload msg = new Payload();
        try {
            productRepository.save(product);
        }catch (Exception e){
            logger.error("Cập nhật sản phẩm thất bại !");
            msg = new Payload("False","Cập nhật sản phẩm thất bại",null);
        }
        msg.setStatus("true");
        msg.setMesssage("Cập nhật sản phẩm thành công !");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Payload msg = new Payload();
        Product product = productRepository.getOne(id);
        try {
            productRepository.delete(product);
        }catch (Exception e){
            logger.error("Xóa sản phẩm thất bại !");
            msg = new Payload("False","Xóa sản phẩm thất bại",null);
        }

        msg.setStatus("true");
        msg.setMesssage("Xóa sản phẩm thành công !");
        msg.setData(null);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable Long id) {
        Payload msg = new Payload();
        Product p = null;
        try {
            p = productRepository.getOne(id);
        }catch (Exception e){
            logger.error("Lấy sản phẩm thất bại !");
            e.printStackTrace();
            msg = new Payload("False","Lấy sản phẩm thất bại",null);
        }

        msg.setStatus("true");
        msg.setMesssage("Lấy sản phẩm thành công !");
        msg.setData(p);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/getProductByCate")
    public ResponseEntity<?> getProductByCate(@RequestParam("idCate") Long idCate) {
        PayloadProduct msg = new PayloadProduct();
        Map<String,Object> mapRes = null;
        List<Product> p = null;
        try {
            p = productRepository.getProductByCate(idCate);
            List<Object> cateJson =  new ArrayList<>();;
            for(Product c: p) {
                mapRes = new HashMap<>();
                mapRes.put("Menu", c);
                cateJson.add(mapRes);
            }
            msg.setData(cateJson);
        }catch (Exception e){
            logger.error("Lấy sản phẩm thất bại !");
            e.printStackTrace();
            msg = new PayloadProduct(null);
        }
        return ResponseEntity.ok(msg);
    }
    @PostMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam("Menu_ID") Long menu_id) {
        Payload msg = new Payload();
        Product p = null;
        try {
            p = productRepository.getOne(menu_id);
        }catch (Exception e){
            logger.error("Lấy sản phẩm thất bại !");
            e.printStackTrace();
            msg = new Payload("False","Lấy sản phẩm thất bại",null);
        }

        msg.setStatus("true");
        msg.setMesssage("Lấy sản phẩm thành công !");
        msg.setData(p);
        return ResponseEntity.ok(msg);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProduct() {
        Payload msg = new Payload();
        List<Product> p = null;
        try {
            p = productRepository.findAll();
        }catch (Exception e){
            logger.error("Lấy sản phẩm thất bại !");
            msg = new Payload("False","Lấy sản phẩm thất bại",null);
        }

        msg.setStatus("true");
        msg.setMesssage("Lấy sản phẩm thành công !");
        msg.setData(p);
        return ResponseEntity.ok(msg);
    }
}
