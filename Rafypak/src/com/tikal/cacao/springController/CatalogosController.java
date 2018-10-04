/**
 * 
 */
package com.tikal.cacao.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Tikal
 *
 */
public interface CatalogosController {
	
	void crear(HttpServletResponse response, HttpServletRequest request, String json);
	
	void modificar(HttpServletResponse response, HttpServletRequest request, @RequestBody String json);
	
	void consultarTodos(HttpServletResponse response, HttpServletRequest request) throws IOException;
}
