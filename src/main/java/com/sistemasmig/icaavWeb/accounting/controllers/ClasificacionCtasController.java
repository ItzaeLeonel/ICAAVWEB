/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ClasificacionCtas;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.services.ClasificacionesCtasService;


import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Julio
 */
@RestController
@RequestMapping("clasificacionCtas")
public class ClasificacionCtasController {
    
	@Autowired
	private ClasificacionesCtasService clasificacionesCtasService;

	@PostMapping("/search")
	ResponseEntity<PagedResponse<ClasificacionCtas>> getSearch(
			@RequestBody(required = true) ClasificacionCtas entity,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize)
			throws BadRequestException, EntityNotExistentException, NoAccessGrantedException {
		try {
			Paging paging = new Paging(page, pageSize);
			return new ResponseEntity<>(
					clasificacionesCtasService.getClasificacionCtas(entity, paging),
					HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<ClasificacionCtas>> getFindAll() {
		return new ResponseEntity<>(clasificacionesCtasService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClasificacionCtas> getById(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException {
		return new ResponseEntity<>(clasificacionesCtasService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ClasificacionCtas> save(@Valid @RequestBody(required = true) ClasificacionCtas entity)
			throws BadRequestException {
		return new ResponseEntity<>(clasificacionesCtasService.save(entity), HttpStatus.CREATED);
	}
	

	@PatchMapping("/{id}")
	public ResponseEntity<ClasificacionCtas> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody(required = true) ClasificacionCtas entity, BindingResult bindingResult)
			throws EntityNotExistentException, BadRequestException {

		return new ResponseEntity<>(clasificacionesCtasService.update(id, entity), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException, BusinessLogicException {
		clasificacionesCtasService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
    
}
