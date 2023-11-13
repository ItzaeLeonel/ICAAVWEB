/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;

import com.sistemasmig.icaavWeb.accounting.services.CuentasContableService;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/cuentas-contables")

public class CuentasContableController {

	@Autowired
	private CuentasContableService cuentasContableService;

	@PostMapping("/search")
	ResponseEntity<PagedResponse<CuentaContable>> getSearch(
			@RequestBody(required = true) CuentaContable entity,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize)
			throws BadRequestException, EntityNotExistentException, NoAccessGrantedException {
		try {
			Paging paging = new Paging(page, pageSize);
			return new ResponseEntity<>(
					cuentasContableService.getCuentaContable(entity, paging),
					HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<CuentaContable>> getFindAll() {
		return new ResponseEntity<>(cuentasContableService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CuentaContable> getById(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException {
		return new ResponseEntity<>(cuentasContableService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> save(
			@Valid @RequestBody(required = true) CuentaContable entity) throws BadRequestException {
		return new ResponseEntity<>(cuentasContableService.save(entity), HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody(required = true) CuentaContable entity, BindingResult bindingResult)
			throws EntityNotExistentException, BadRequestException {

		return new ResponseEntity<>(cuentasContableService.update(id, entity), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable(value = "id") Integer id)
			throws EntityNotExistentException, BusinessLogicException {
		cuentasContableService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/generales/cuentas-complementarias")
	public ResponseEntity<List<CuentaContable>> findDigitoCuentacontable(
			@ModelAttribute CuentaContable cuentaContable) {
		return new ResponseEntity<>(cuentasContableService.findDigitoCuentacontable(cuentaContable), HttpStatus.OK);
	}
}
