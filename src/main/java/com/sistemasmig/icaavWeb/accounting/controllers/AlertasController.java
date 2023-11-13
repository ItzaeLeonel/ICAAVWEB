/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Alerta;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.services.AlertasService;


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
 * @author Waldir.Valle
 */
@RestController
@RequestMapping("/alertas")

public class AlertasController {

	@Autowired
	private AlertasService alertaService;

	@PostMapping("/search")
	ResponseEntity<PagedResponse<Alerta>> getSearch(@RequestBody(required = true) Alerta entity,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize)
			throws BadRequestException, EntityNotExistentException, NoAccessGrantedException {
		try {
			Paging paging = new Paging(page, pageSize);
			return new ResponseEntity<>(alertaService.getAlerta(entity, paging), HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}
	
	@GetMapping()
	public ResponseEntity< List<Alerta>> getFindAll() {
		return new ResponseEntity<>(alertaService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Alerta> getById(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException {
		return new ResponseEntity<>(alertaService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Alerta> save(@Valid @RequestBody(required = true) Alerta entity)
			throws BadRequestException {
		return new ResponseEntity<>(alertaService.save(entity), HttpStatus.CREATED);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<Alerta> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody(required = true) Alerta entity, BindingResult bindingResult)
			throws EntityNotExistentException, BadRequestException {

		return new ResponseEntity<>(alertaService.update(id, entity), HttpStatus.OK);

	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException, BadRequestException {
		alertaService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
