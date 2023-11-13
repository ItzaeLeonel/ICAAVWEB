/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Empresa;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.services.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @author Waldir.Valle
 */
@RestController
@RequestMapping("empresa")

public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @Operation(summary = "Search Empresa by Empresa Attributes", description = "This service retrieve Empresa information filter by Empresa Attributes", tags = { "empresa" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empresa.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    ResponseEntity<PagedResponse<Empresa>> getEmpresa(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @RequestBody(required = true) @Parameter(description="Empresa object - json") Empresa empresa,
        @RequestParam(value = "page", required = false, defaultValue = "0") @Parameter(description="Page to retrieve") Integer page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10")  @Parameter(description="Page size to retrieve") Integer pageSize) throws BadRequestException, EntityNotExistentException, NoAccessGrantedException  {
        try {
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            Paging paging = new Paging(page, pageSize);
            return new ResponseEntity<>(empresaService.getEmpresa(empresa,paging), HttpStatus.OK);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    
    @Operation(summary = "Search Empresa by Empresa Id", description = "This service retrieve Empresa information filter by Empresa Id", tags = { "empresa" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empresa.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Empresa getById(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                     @PathVariable(value = "id") @Parameter(description="Empresa Id - Integer") Integer empresaId) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            return empresaService.getById(empresaId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Create Empresa", description = "This service create a new Empresa Object", tags = { "empresa" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empresa.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Empresa> createEmpresa(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @Valid @RequestBody(required = true) @Parameter(description="Empresa object - json") Empresa empresa) throws BusinessLogicException, ExistentEntityException, BadRequestException, EntityNotExistentException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_CREATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_CREATE);
            }*/
            return new ResponseEntity<>(empresaService.createEmpresa(empresa), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Update Empresa", description = "This service updates a persited Empresa Object", tags = { "empresa" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empresa.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Empresa> updateEmpresa(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="Empresa Id - Integer") Integer empresaId,
                                                 @Valid @RequestBody(required = true) @Parameter(description="Empresa object - json") Empresa empresa, BindingResult bindingResult) throws BusinessLogicException, BadRequestException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_UPDATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_UPDATE);
            }*/
            return new ResponseEntity<>(empresaService.updateEmpresa(empresaId, empresa), HttpStatus.OK);
        }catch (Exception ble) {
            throw new BadRequestException(ble.getMessage());
            //throw ble;
        }
        
 
    }
    
    @Operation(summary = "Delete Empresa", description = "This service deletes (Logically) a persited Empresa Object", tags = { "empresa" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empresa.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteEmpresa(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="Empresa Id - Integer") Integer empresaId) throws Exception, BusinessLogicException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_DELETE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_DELETE);
            }*/
            empresaService.deleteEmpresa(empresaId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (RuntimeException ex){
            throw new BadRequestException(ex.getMessage());
        } 
    }
}
