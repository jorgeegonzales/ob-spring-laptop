package com.applaptop.springapplaptop;

import com.applaptop.springapplaptop.entities.Laptop;
import com.applaptop.springapplaptop.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringapplaptopApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SpringapplaptopApplication.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null,"Lenovo","Legión 5","AMD RYZEN R5",512,8,15.6,"Windows 11 Home - Español","China");
		Laptop laptop2 = new Laptop(null,"Apple","MLXW3E/A","Chip M2 de Apple",256,null,13.6,"macOS","USA");

		System.out.println("El numero de laptops en base de datos: " + repository.findAll().size());

		repository.save(laptop1);
		repository.save(laptop2);

		System.out.println("El numero de laptops en base de datos: " + repository.findAll().size());



	}

}
