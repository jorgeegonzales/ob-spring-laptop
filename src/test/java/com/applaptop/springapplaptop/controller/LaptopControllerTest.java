package com.applaptop.springapplaptop.controller;

import com.applaptop.springapplaptop.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @Value("${app.messagetest}")
    String messagetest;

    @BeforeEach
    void setUp(){
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void hello() {
        System.out.println(messagetest);
        ResponseEntity<String>  response = testRestTemplate.getForEntity("/hello",String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Hello my friend!",response.getBody());

    }

    @Test
    void findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "brand": "Lenovo",
                    "model": "Legión 5",
                    "processor": "AMD RYZEN R5",
                    "hardDrive": 512,
                    "ram": 8,
                    "screenSize": 15.6,
                    "operatingSystem": "Windows 11 Home - Español",
                    "madeIn": "China"
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> createResponse = testRestTemplate.exchange("/api/laptops",HttpMethod.POST,request,Laptop.class);
        Laptop createResult = createResponse.getBody();

        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);
        assertEquals(HttpStatus.OK,response.getStatusCode());

       List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println(laptops.size());
    }

    @Test
    void findOneById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "brand": "Lenovo",
                    "model": "Legión 5",
                    "processor": "AMD RYZEN R5",
                    "hardDrive": 512,
                    "ram": 8,
                    "screenSize": 15.6,
                    "operatingSystem": "Windows 11 Home - Español",
                    "madeIn": "China"
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> createResponse = testRestTemplate.exchange("/api/laptops",HttpMethod.POST,request,Laptop.class);
        Laptop createResult = createResponse.getBody();
       ResponseEntity<Laptop> response = testRestTemplate.getForEntity(String.format("/api/laptops/%s", createResult.getId()), Laptop.class);
       assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                     "brand": "Lenovo creado desde Spring Test",
                     "model": "Legión 5",
                     "processor": "AMD RYZEN R5",
                     "hardDrive": 512,
                     "ram": 8,
                     "screenSize": 15.6,
                     "operatingSystem": "Windows 11 Home - Español",
                     "madeIn": "China"
                 }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops",HttpMethod.POST,request,Laptop.class);
        Laptop result = response.getBody();

        assertEquals("Lenovo creado desde Spring Test",result.getBrand());
    }


    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "brand": "Lenovo",
                    "model": "Legión 5",
                    "processor": "AMD RYZEN R5",
                    "hardDrive": 512,
                    "ram": 8,
                    "screenSize": 15.6,
                    "operatingSystem": "Windows 11 Home - Español",
                    "madeIn": "China"
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops",HttpMethod.POST,request,Laptop.class);
        Laptop createResult = response.getBody();
        String jsonup = String.format("""
                {
                    "id": %s,
                    "brand": "Lenovo updated",
                    "model": "Legión 5 updated",
                    "processor": "AMD RYZEN R5",
                    "hardDrive": 512,
                    "ram": 8,
                    "screenSize": 15.6,
                    "operatingSystem": "Windows 11 Home - Español",
                    "madeIn": "China"
                }
                """, createResult.getId());

        HttpEntity<String> requestup = new HttpEntity<>(jsonup,headers);
        ResponseEntity<Laptop> responseup = testRestTemplate.exchange("/api/laptops",HttpMethod.PUT,requestup,Laptop.class);
        Laptop result = responseup.getBody();
        System.out.println(result.getBrand());

        assertEquals("Lenovo updated",result.getBrand());
    }

    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "brand": "Lenovo",
                    "model": "Legión 5",
                    "processor": "AMD RYZEN R5",
                    "hardDrive": 512,
                    "ram": 8,
                    "screenSize": 15.6,
                    "operatingSystem": "Windows 11 Home - Español",
                    "madeIn": "China"
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Laptop> createResponse = testRestTemplate.exchange("/api/laptops",HttpMethod.POST,request,Laptop.class);
        Laptop createResult = createResponse.getBody();

        ResponseEntity<Laptop> response = testRestTemplate.exchange(String.format("/api/laptops/%s",createResult.getId() ),HttpMethod.DELETE,null,Laptop.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteAll() {
         ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops",HttpMethod.DELETE,null,Laptop.class);
         assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}