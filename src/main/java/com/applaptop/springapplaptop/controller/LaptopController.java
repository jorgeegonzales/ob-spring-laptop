package com.applaptop.springapplaptop.controller;

import com.applaptop.springapplaptop.entities.Laptop;
import com.applaptop.springapplaptop.repository.LaptopRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    private LaptopRepository laptopRepository;

    @Value("${app.varexample}")
    String varexample;

    public LaptopController(LaptopRepository laptopRepository) {

        this.laptopRepository = laptopRepository;
    }

    @Value("${app.messagedev}")
    String messagedev;
    @GetMapping("/hola")
    public String hello(){
        System.out.println(messagedev);
        return "Hello my friend!";
    }
    @GetMapping("/api/laptops")
    @ApiOperation("Listar los libros de la Base de Datos")
    public List<Laptop> findAll() {

        return laptopRepository.findAll();
    }

    @GetMapping("/api/laptops/{id}")
    @ApiOperation("Buscar un libro por clave primaria id Long")
    public ResponseEntity<Laptop> findOneById(@ApiParam("Clave primaria tipo Long") @PathVariable Long id) {
        System.out.println(varexample);
       Optional<Laptop> laptopOpt = laptopRepository.findById(id);

       if(laptopOpt.isPresent()) {
           return ResponseEntity.ok(laptopOpt.get());
       } else {
           return ResponseEntity.notFound().build();
       }

    }

    @PostMapping("/api/laptops")
    @ApiOperation("Crear un libro en Base de Datos")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));
        if(laptop.getId() != null) {
            log.warn("trying to create a book with id ");
            return ResponseEntity.badRequest().build();
        }
        Laptop result = laptopRepository.save(laptop);
        //guardar el libro recibido por parametro en la base de datos
        return ResponseEntity.ok(result);// El libro devuelto tiene una clave primaria

    }

    @PutMapping("/api/laptops")
    @ApiOperation("Actualizar un libro existente en Base de Datos")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if (laptop.getId() == null){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }
        if (!laptopRepository.existsById(laptop.getId())){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }

         Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @ApiIgnore("Ignorar este metodo para que no aparezca en la documentacion de la api Swagger")
    @DeleteMapping("/api/laptops/{id}")
    @ApiOperation("Borrar un libro en Base de Datos")
    public ResponseEntity<Laptop> delete(@PathVariable Long id) {
        if (!laptopRepository.existsById(id)) {
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiIgnore("Ignorar este metodo para que no aparezca en la documentacion de la api Swagger")
    @DeleteMapping("/api/laptops")
    @ApiOperation("Borrar todos los libros de la Base de Datos")
    public ResponseEntity<Laptop> deleteAll() {
        log.info("REST Request for delete all books");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
