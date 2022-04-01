package com.example.springboot.controller;

import com.example.springboot.models.Product;
import com.example.springboot.models.ResponseObject;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path= "api/v1")


public class ProductController {
    //DI  Dependency Injection
    @Autowired
    private  ProductRepository productRepository;


    @GetMapping("/products")
     // This is request : http://localhost:8090/api/v1/Products
    List<Product> getAllProducts()
    {
        return productRepository.findAll();// data lấy ở đâu

        // Luu tai Database  , H2-Database = in-memory database
        // Su dung postman de send request
    }

    // get detail product
    @GetMapping("/products/{id}")
    //Trả về 1 object with : dât , message , status
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query product successfully", foundProduct)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Cannot find product with id =" + id , "")
            );
        }
        // insert new product vs get POST method
        // Postman : Raw , json
    @PostMapping("products/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){
            List<Product> foundProducts = productRepository.findByProductName(newProduct.getProductName().trim());
            if(foundProducts.size()>0) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Product name already taken", "")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert product successfully",productRepository.save(newProduct))

            );
        }
        //update , upsert = update if found , otherwise insert
    @PutMapping("products/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct , @PathVariable Long id) {
        Product updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setYear(newProduct.getYear());
                    product.setPrice(newProduct.getPrice());
                    product.setUrl(newProduct.getUrl());
                    return productRepository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);

                    return productRepository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Product successfully", updateProduct)
        );
    }
    @DeleteMapping("products/{id}")
        ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
            boolean exists = productRepository.existsById(id);
            if(exists) {
                productRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Delete product successfully", "")
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product to delete", "")
            );
        }


        }





