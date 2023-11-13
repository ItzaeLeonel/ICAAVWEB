/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionClasificacionCtas;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.services.AsignacionesClasificacionCtasService;

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
@RequestMapping("/asignaciones-clasificaciones-cuentas")
public class AsignacionesClasificacionCtasController {
    
	@Autowired
	private  AsignacionesClasificacionCtasService asignacionesClasificacionCtasService;

	@PostMapping("/search")
	ResponseEntity<PagedResponse<AsignacionClasificacionCtas>> getSearch(@RequestBody(required = true) AsignacionClasificacionCtas entity,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize)
			throws BadRequestException, EntityNotExistentException, NoAccessGrantedException {
		try {
			Paging paging = new Paging(page, pageSize);
			return new ResponseEntity<>(asignacionesClasificacionCtasService.getAsignacionClasificacionCtas(entity, paging), HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}
	
	@GetMapping()
	public ResponseEntity< List<AsignacionClasificacionCtas>> getFindAll() {
		return new ResponseEntity<>(asignacionesClasificacionCtasService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AsignacionClasificacionCtas> getById(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException {
		return new ResponseEntity<>(asignacionesClasificacionCtasService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<AsignacionClasificacionCtas> save(@Valid @RequestBody(required = true) AsignacionClasificacionCtas entity)
			throws BadRequestException {
		return new ResponseEntity<>(asignacionesClasificacionCtasService.save(entity), HttpStatus.CREATED);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<AsignacionClasificacionCtas> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody(required = true) AsignacionClasificacionCtas entity, BindingResult bindingResult)
			throws EntityNotExistentException, BadRequestException {

		return new ResponseEntity<>(asignacionesClasificacionCtasService.update(id, entity), HttpStatus.OK);

	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable(value = "id") Integer id) throws EntityNotExistentException, BusinessLogicException{
		asignacionesClasificacionCtasService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
