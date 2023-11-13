/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.CuentasEspeciales;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.model.CuentasEspecialesSP;
import com.sistemasmig.icaavWeb.accounting.model.DemoLista;
import com.sistemasmig.icaavWeb.accounting.services.CuentasEspecialesService;
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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("cuentaEspecial")

public class CuentasEspecialesController {

	@Autowired
	private CuentasEspecialesService cuentasEspecialesService;

	@Operation(summary = "Search CuentasEspeciales by CuentasEspeciales Attributes", description = "This service retrieve CuentasEspeciales information filter by CuentasEspeciales Attributes", tags = {
			"cuentasEspeciales" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	ResponseEntity<PagedResponse<CuentasEspeciales>> getCuentasEspeciales(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@RequestBody(required = true) @Parameter(description = "CuentasEspeciales object - json") CuentasEspeciales cuentasEspeciales,
			@RequestParam(value = "page", required = false, defaultValue = "0") @Parameter(description = "Page to retrieve") Integer page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") @Parameter(description = "Page size to retrieve") Integer pageSize)
			throws BadRequestException, EntityNotExistentException, NoAccessGrantedException {
		try {
			/*
			 * if(!securityService.getGrantAndModule(token,
			 * Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){ throw
			 * new
			 * NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.
			 * GRANT_ACCESS); }
			 */
			Paging paging = new Paging(page, pageSize);
			return new ResponseEntity<>(cuentasEspecialesService.getCuentasEspeciales(cuentasEspeciales, paging),
					HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@Operation(summary = "Search CuentasEspeciales by CuentasEspeciales Id", description = "This service retrieve CuentasEspeciales information filter by CuentasEspeciales Id", tags = {
			"cuentasEspeciales" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CuentasEspeciales getById(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@PathVariable(value = "id") @Parameter(description = "CuentasEspeciales Id - Integer") Integer cuentasEspecialesId)
			throws EntityNotExistentException, BadRequestException, NoAccessGrantedException {

		try {
			/*
			 * if(!securityService.getGrantAndModule(token,
			 * Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){ throw
			 * new
			 * NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.
			 * GRANT_ACCESS); }
			 */
			return cuentasEspecialesService.getById(cuentasEspecialesId);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}

	}

	@Operation(summary = "Create CuentasEspeciales", description = "This service create a new CuentasEspeciales Object", tags = {
			"cuentasEspeciales" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CuentasEspeciales> createCuentasEspeciales(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@Valid @RequestBody(required = true) @Parameter(description = "CuentasEspeciales object - json") CuentasEspeciales cuentasEspeciales)
			throws BusinessLogicException, ExistentEntityException, BadRequestException, EntityNotExistentException,
			NoAccessGrantedException {
		try {
			/*
			 * if(!securityService.getGrantAndModule(token,
			 * Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_CREATE)){ throw
			 * new
			 * NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.
			 * GRANT_CREATE); }
			 */
			return new ResponseEntity<>(cuentasEspecialesService.createCuentasEspeciales(cuentasEspeciales),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}

	}

	@Operation(summary = "Update CuentasEspeciales", description = "This service updates a persited CuentasEspeciales Object", tags = {
			"cuentasEspeciales" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<CuentasEspeciales> updateCuentasEspeciales(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@PathVariable(value = "id") @Parameter(description = "CuentasEspeciales Id - Integer") Integer cuentasEspecialesId,
			@Valid @RequestBody(required = true) @Parameter(description = "CuentasEspeciales object - json") CuentasEspeciales cuentasEspeciales,
			BindingResult bindingResult) throws BusinessLogicException, BadRequestException, EntityNotExistentException,
			ExistentEntityException, NoAccessGrantedException {
		try {
			/*
			 * if(!securityService.getGrantAndModule(token,
			 * Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_UPDATE)){ throw
			 * new
			 * NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.
			 * GRANT_UPDATE); }
			 */

			return new ResponseEntity<>(
					cuentasEspecialesService.updateCuentasEspeciales(cuentasEspecialesId, cuentasEspeciales),
					HttpStatus.OK);
		} catch (Exception ble) {
			throw new BadRequestException(ble.getMessage());
			// throw ble;
		}

	}

	@Operation(summary = "Delete CuentasEspeciales", description = "This service deletes (Logically) a persited CuentasEspeciales Object", tags = {
			"cuentasEspeciales" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteCuentasEspeciales(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@PathVariable(value = "id") @Parameter(description = "CuentasEspeciales Id - Integer") Integer cuentasEspecialesId)
			throws Exception, BusinessLogicException, EntityNotExistentException, ExistentEntityException,
			NoAccessGrantedException {
		try {
			/*
			 * if(!securityService.getGrantAndModule(token,
			 * Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_DELETE)){ throw
			 * new
			 * NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.
			 * GRANT_DELETE); }
			 */

			cuentasEspecialesService.deleteCuentasEspeciales(cuentasEspecialesId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (RuntimeException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@RequestMapping(value = "procedure/cuentasEspecialesSP", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public List<DemoLista> getByCuentaEspecial(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@Parameter(description = "idGrupoEmpresa Id - String") Integer pr_id_grupo_empresa,
			@Parameter(description = "TipoEmpresa Id - String") Integer pr_tipo_cuenta)
			throws EntityNotExistentException, BadRequestException, NoAccessGrantedException {

		try {

			return cuentasEspecialesService.getCuentaEspecialSP(pr_id_grupo_empresa, pr_tipo_cuenta);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

}
