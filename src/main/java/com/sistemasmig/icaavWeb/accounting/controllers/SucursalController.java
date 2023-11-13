/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Sucursales;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.services.SucursalService;
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
 * @author Julio
 */
@RestController
@RequestMapping("sucursal")
public class SucursalController {
    
    @Autowired
    private SucursalService sucursalService;
    
    
    @Operation(summary = "Search Usuario by Sucursales Attributes", description = "This service retrieve Sucursales information filter by Sucursales Attributes", tags = { "sucursales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sucursales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    ResponseEntity<PagedResponse<Sucursales>> getSucursales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @RequestBody(required = true) @Parameter(description="Usuario object - json") Sucursales sucursales,
        @RequestParam(value = "page", required = false, defaultValue = "0") @Parameter(description="Page to retrieve") Integer page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10")  @Parameter(description="Page size to retrieve") Integer pageSize) throws BadRequestException, EntityNotExistentException, NoAccessGrantedException  {
        try {
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            Paging paging = new Paging(page, pageSize);
            return new ResponseEntity<>(sucursalService.getSucursal(sucursales, paging), HttpStatus.OK);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    } 
    
    @Operation(summary = "Search Sucursales by Sucursales Id", description = "This service retrieve Sucursales information filter by Sucursales Id", tags = { "sucursales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sucursales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Sucursales getById(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                     @PathVariable(value = "id") @Parameter(description="Sucursales Id - Integer") Integer sucursalId) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
           
           return sucursalService.getById(sucursalId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    
     @Operation(summary = "Create Sucursal", description = "This service create a new Sucursal Object", tags = { "sucursales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sucursales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Sucursales> createSucursal(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @Valid @RequestBody(required = true) @Parameter(description="Sucursal object - json") Sucursales sucursales) throws BusinessLogicException, ExistentEntityException, BadRequestException, EntityNotExistentException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_CREATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_CREATE);
            }*/
            return new ResponseEntity<>(sucursalService.createSucursal(sucursales), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Update EmpresaUsuario", description = "This service updates a persited EmpresaUsuario Object", tags = { "sucursales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sucursales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Sucursales> updateSucursales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="Sucursales Id - Integer") Integer sucursalId,
                                                 @Valid @RequestBody(required = true) @Parameter(description="Sucursales object - json") Sucursales sucursales, BindingResult bindingResult) throws BusinessLogicException, BadRequestException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_UPDATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_UPDATE);
            }*/
            return new ResponseEntity<>(sucursalService.updateSucursales(sucursalId, sucursales), HttpStatus.OK);
        }catch (Exception ble) {
            throw new BadRequestException(ble.getMessage());
            //throw ble;
        }
    }
    
    
        @Operation(summary = "Delete Sucursales", description = "This service deletes (Logically) a persited Sucursales Object", tags = { "Sucursales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sucursales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteSucursales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="Sucursales Id - Integer") Integer sucursalId) throws Exception, BusinessLogicException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_DELETE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_DELETE);
            }*/
            sucursalService.deleteSucursales(sucursalId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (RuntimeException ex){
            throw new BadRequestException(ex.getMessage());
        } 
    }
    
}
