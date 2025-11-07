package com.practice.localiza.controller;
import com.practice.localiza.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.practice.localiza.entity.Brand;



import java.util.List;

@RestController
@RequestMapping("/api/brand")
@CrossOrigin("*") // posso colobrand "http://localhost:4200") permitindo só requisições dessa origim
public class BrandController {


    @Autowired
    private BrandService brandService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Brand brand) {
            try {
            Brand savedBrand = brandService.save(brand);
                return new ResponseEntity<>("Brand saved successfully", HttpStatus.CREATED);
            } catch (Exception e) {
            return new ResponseEntity<String>("Error on saving",  HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<Brand>> findAll() {
        try {
            List<Brand> list = brandService.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> findById(@PathVariable Long id) {
        try {
            Brand brand = this.brandService.findById(id);
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id, @RequestBody Brand brandNewData) {
        try {
            Brand brandUpdate = this.brandService.update(id, brandNewData);
            return new ResponseEntity<>(brandUpdate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            this.brandService.deleteById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);

        } catch (Exception e) {
            String errorMessage = "Error: Could not process delete request for ID " + id + "Error: "+ e;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);        }
    }
    @GetMapping("/findByName")
    public ResponseEntity<List<Brand>> findByName(@RequestParam String name) {
        try{
            List<Brand> list = brandService.findByName(name);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
