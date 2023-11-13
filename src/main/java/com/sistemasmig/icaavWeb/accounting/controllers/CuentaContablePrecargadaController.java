/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ConfigEmails;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContablePrecargada;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.model.CCPrecargadasSP;
import com.sistemasmig.icaavWeb.accounting.services.ConfigEmailsService;
import com.sistemasmig.icaavWeb.accounting.services.CuentasContablePrecargadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Julio
 */
@RestController
@RequestMapping("cuentaContablePrecargada")
public class CuentaContablePrecargadaController {

	@Autowired
	private CuentasContablePrecargadaService cuentasContablePrecargadaService;

	@PostMapping("/search")
	ResponseEntity<PagedResponse<CuentaContablePrecargada>> getSearch(
			@RequestBody(required = true) CuentaContablePrecargada entity,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize)
			throws BadRequestException, EntityNotExistentException, NoAccessGrantedException {
		try {
			Paging paging = new Paging(page, pageSize);
			return new ResponseEntity<>(
					cuentasContablePrecargadaService.getCuentaContablePrecargada(entity, paging),
					HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<CuentaContablePrecargada>> getFindAll() {
		return new ResponseEntity<>(cuentasContablePrecargadaService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CuentaContablePrecargada> getById(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException {
		return new ResponseEntity<>(cuentasContablePrecargadaService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CuentaContablePrecargada> save(@Valid @RequestBody(required = true) CuentaContablePrecargada entity)
			throws BadRequestException {
		return new ResponseEntity<>(cuentasContablePrecargadaService.save(entity), HttpStatus.CREATED);
	}
	

	@PatchMapping("/{id}")
	public ResponseEntity<CuentaContablePrecargada> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody(required = true) CuentaContablePrecargada entity, BindingResult bindingResult)
			throws EntityNotExistentException, BadRequestException {

		return new ResponseEntity<>(cuentasContablePrecargadaService.update(id, entity), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException, BusinessLogicException {
		cuentasContablePrecargadaService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
