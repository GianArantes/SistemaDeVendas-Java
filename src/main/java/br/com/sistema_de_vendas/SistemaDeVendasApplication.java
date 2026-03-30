/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação principal do sistema de vendas.
 *
 * Inicializa o contexto Spring Boot e dispara o servidor embutido.
 */
@SpringBootApplication
public class SistemaDeVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeVendasApplication.class, args);
	}

}
