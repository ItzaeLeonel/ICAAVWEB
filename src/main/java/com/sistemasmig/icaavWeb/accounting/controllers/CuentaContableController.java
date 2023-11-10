/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.models.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasContablesSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.InsertCuentaC;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasContables;
import com.sistemasmig.icaavWeb.accounting.models.enums.CuentaContableEstatusEnum;
import com.sistemasmig.icaavWeb.accounting.services.CuentaContableService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Waldir.Valle
 */
@RestController
@RequestMapping("cuentaContable")

public class CuentaContableController {

	@Autowired
	private CuentaContableService cuentaContableService;

	@Operation(summary = "Search CuentaContable by CuentaContable Attributes", description = "This service retrieve CuentaContable information filter by CuentaContable Attributes", tags = {
			"cuentaContable" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaContable.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	ResponseEntity<PagedResponse<CuentaContable>> getCuentaContable(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@RequestBody(required = true) @Parameter(description = "CuentaContable object - json") CuentaContable cuentaContable,
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
			return new ResponseEntity<>(cuentaContableService.getCuentaContable(cuentaContable, paging), HttpStatus.OK);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@Operation(summary = "Search CuentaContable by CuentaContable Id", description = "This service retrieve CuentaContable information filter by CuentaContable Id", tags = {
			"cuentaContable" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaContable.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CuentaContable getById(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@PathVariable(value = "id") @Parameter(description = "CuentaContable Id - Integer") Integer cuentaContableId)
			throws EntityNotExistentException, BadRequestException, NoAccessGrantedException {

		try {
			/*
			 * if(!securityService.getGrantAndModule(token,
			 * Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){ throw
			 * new
			 * NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.
			 * GRANT_ACCESS); }
			 */
			return cuentaContableService.getById(cuentaContableId);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}

	}

	@Operation(summary = "Create CuentaContable", description = "This service create a new CuentaContable Object", tags = {
			"cuentaContable" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaContable.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CuentaContable> createCuentaContable(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@Valid @RequestBody(required = true) @Parameter(description = "CuentaContable object - json") CuentaContable cuentaContable)
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
			return new ResponseEntity<>(cuentaContableService.createCuentaContable(cuentaContable), HttpStatus.CREATED);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}

	}

	@Operation(summary = "Update CuentaContable", description = "This service updates a persited CuentaContable Object", tags = {
			"cuentaContable" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaContable.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<CuentaContable> updateCuentaContable(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@PathVariable(value = "id") @Parameter(description = "CuentaContable Id - Integer") Integer cuentaContableId,
			@Valid @RequestBody(required = true) @Parameter(description = "CuentaContable object - json") CuentaContable cuentaContable,
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
			return new ResponseEntity<>(cuentaContableService.updateCuentaContable(cuentaContableId, cuentaContable),
					HttpStatus.OK);
		} catch (Exception ble) {
			throw new BadRequestException(ble.getMessage());
			// throw ble;
		}

	}

	@Operation(summary = "Delete CuentaContable", description = "This service deletes (Logically) a persited CuentaContable Object", tags = {
			"cuentaContable" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaContable.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteCuentaContable(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@PathVariable(value = "id") @Parameter(description = "CuentaContable Id - Integer") Integer cuentaContableId)
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
			cuentaContableService.deleteCuentaContable(cuentaContableId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (RuntimeException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "procedure/datosEmpresaSP", method = RequestMethod.POST)

	public List<ListCuentasContables> getByDatosEmpresaSP(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@Parameter(description = "pr_id_grupo_empresa - Integer") Integer pr_id_grupo_empresa)
			throws EntityNotExistentException, BadRequestException, NoAccessGrantedException {

		try {

			return cuentaContableService.getCuentasContablesSP(pr_id_grupo_empresa);
		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@RequestMapping(value = "procedure/createCuentaContable", method = RequestMethod.POST)
	public InsertCuentaC CreateByCuentaContableSP(
			@RequestHeader(value = "token", required = true) @Parameter(description = "MIG SSO Token - UUID") String token,
			@Parameter(description = "idGrupoEmpresa Id - Integer") Integer pr_id_grupo_empresa,
			@Parameter(description = "CuentaSat Id - Integer") Integer pr_id_cuenta_sat,
			@Parameter(description = "Anio Id - Integer") Integer pr_anio,
			@Parameter(description = "Mes Id - Integer") Integer pr_mes,
			@Parameter(description = "NumeroCuenta Id - String") String pr_numero_cuenta,
			@Parameter(description = "TipoCuenta Id - String") String pr_tipo_cuenta,
			@Parameter(description = "Nombre Id - String") String pr_nombre,
			@Parameter(description = "SaldoInicial Id - Double") Double pr_saldo_inicial,
			@Parameter(description = "SaldoFinal Id - Double") Double pr_saldo_final,
			@Parameter(description = "Cargos Id - Double") Double pr_cargos,
			@Parameter(description = "Abonos Id - Double") Double pr_abonos,
			@Parameter(description = "CargosAcumulados Id - Double") Double pr_cargos_acumulado,
			@Parameter(description = "AbonosAcumulados Id - Double") Double pr_abonos_acumulados,
			@Parameter(description = "CuentaXPagar Id - Integer") Integer pr_cuenta_x_pagar,
			@Parameter(description = "Estatus Id - Enum") CuentaContableEstatusEnum pr_estatus,
			@Parameter(description = "NumeroComplementaria Id - String") String pr_numero_complementaria,
			@Parameter(description = "CuentaComplementaria Id - Integer") Integer pr_cuenta_complementaria)
			throws EntityNotExistentException, BadRequestException, NoAccessGrantedException {

		try {

			return cuentaContableService.createSp(pr_id_grupo_empresa, pr_id_cuenta_sat, pr_anio, pr_mes,
					pr_numero_cuenta, pr_tipo_cuenta, pr_nombre, pr_saldo_inicial, pr_saldo_final, pr_cargos, pr_abonos,
					pr_cargos_acumulado, pr_abonos_acumulados, pr_cuenta_x_pagar, pr_estatus, pr_numero_complementaria,
					pr_cuenta_complementaria);

		} catch (Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@Operation(summary = "Obtener Ceuntas contables por el primer digito", description = "This service get (Logically) a persited CuentaContable Object", tags = {
			"cuentaContable" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentaContable.class)))),
			@ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)))) })
	@RequestMapping(value = "/generales/cuentas-complementarias", method = RequestMethod.GET)
	public ResponseEntity<List<CuentaContable>> findDigitoCuentacontable(
			@ModelAttribute CuentaContable cuentaContable) {
		return new ResponseEntity<>(cuentaContableService.findDigitoCuentacontable(cuentaContable), HttpStatus.OK);
	}
}
