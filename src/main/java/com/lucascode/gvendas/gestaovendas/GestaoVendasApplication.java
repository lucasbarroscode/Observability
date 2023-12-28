package com.lucascode.gvendas.gestaovendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

//especifica a onde esta o pacote e suas entidades se nao forem especificados
@EntityScan(basePackages = {"com.lucascode.gvendas.gestaovendas.entidade"})
//especifica a onde esta o pacote dos repositorios se nao forem especificados
@EnableJpaRepositories(basePackages = {"com.lucascode.gvendas.gestaovendas.repository"})

//especifica a onde esta o pacote dos beans, controller e services
//@ComponentScan
@SpringBootApplication
public class GestaoVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVendasApplication.class, args);
	}

}
