package com.ydt.controller;

import com.ydt.entity.Category;
import com.ydt.entity.Product;
import com.ydt.payload.Payload;
import com.ydt.payload.PayloadProduct;
import com.ydt.payload.SubPayload;
import com.ydt.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CategoryRepository  categoryRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        PayloadProduct msg = new PayloadProduct();
        SubPayload subPayload = new SubPayload();
        List<Category> cateArr = new ArrayList<>();
        Map<String,Object> mapRes = null;
        try {
            cateArr = categoryRepository.findAll();
            List<Object> cateJson =  new ArrayList<>();;
            for(Category c: cateArr) {
                mapRes = new HashMap<>();
                mapRes.put("Category", c);
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
}
