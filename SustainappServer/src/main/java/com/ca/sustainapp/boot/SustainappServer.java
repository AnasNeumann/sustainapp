package com.ca.sustainapp.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe de démarrage de la plateforme serveur
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 20/01/2017
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.ca.sustainapp"})
@EntityScan(basePackages = {"com.ca.sustainapp.entities"})
@EnableJpaRepositories(basePackages = {"com.ca.sustainapp.repositories"})
public class SustainappServer {

	/**
	 * Methode principale de démarrage de la plateforme
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SustainappServer.class, args);
	}
}