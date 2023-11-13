/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.controllers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.TipoPaquete;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.services.TipoPaqueteService;
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
@RequestMapping("tipoPaquete")

public class TipoPaqueteController {
    
    @Autowired
    private TipoPaqueteService tipoPaqueteService;
    
    @Operation(summary = "Search TipoPaquete by TipoPaquete Attributes", description = "This service retrieve TipoPaquete information filter by TipoPaquete Attributes", tags = { "tipoPaquete" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoPaquete.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    ResponseEntity<PagedResponse<TipoPaquete>> getTipoPaquete(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @RequestBody(required = true) @Parameter(description="TipoPaquete object - json") TipoPaquete tipoPaquete,
        @RequestParam(value = "page", required = false, defaultValue = "0") @Parameter(description="Page to retrieve") Integer page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10")  @Parameter(description="Page size to retrieve") Integer pageSize) throws BadRequestException, EntityNotExistentException, NoAccessGrantedException  {
        try {
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            Paging paging = new Paging(page, pageSize);
            return new ResponseEntity<>(tipoPaqueteService.getTipoPaquete(tipoPaquete,paging), HttpStatus.OK);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    
    @Operation(summary = "Search TipoPaquete by TipoPaquete Id", description = "This service retrieve TipoPaquete information filter by TipoPaquete Id", tags = { "tipoPaquete" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoPaquete.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TipoPaquete getById(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                     @PathVariable(value = "id") @Parameter(description="TipoPaquete Id - Integer") Integer tipoPaqueteId) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            return tipoPaqueteService.getById(tipoPaqueteId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Create TipoPaquete", description = "This service create a new TipoPaquete Object", tags = { "tipoPaquete" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoPaquete.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TipoPaquete> createTipoPaquete(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @Valid @RequestBody(required = true) @Parameter(description="TipoPaquete object - json") TipoPaquete tipoPaquete) throws BusinessLogicException, ExistentEntityException, BadRequestException, EntityNotExistentException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_CREATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_CREATE);
            }*/
            return new ResponseEntity<>(tipoPaqueteService.createTipoPaquete(tipoPaquete), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Update TipoPaquete", description = "This service updates a persited TipoPaquete Object", tags = { "tipoPaquete" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoPaquete.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<TipoPaquete> updateTipoPaquete(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="TipoPaquete Id - Integer") Integer tipoPaqueteId,
                                                 @Valid @RequestBody(required = true) @Parameter(description="TipoPaquete object - json") TipoPaquete tipoPaquete, BindingResult bindingResult) throws BusinessLogicException, BadRequestException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_UPDATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_UPDATE);
            }*/
            return new ResponseEntity<>(tipoPaqueteService.updateTipoPaquete(tipoPaqueteId, tipoPaquete), HttpStatus.OK);
        }catch (Exception ble) {
            throw new BadRequestException(ble.getMessage());
            //throw ble;
        }
        
 
    }
    
    @Operation(summary = "Delete TipoPaquete", description = "This service deletes (Logically) a persited TipoPaquete Object", tags = { "tipoPaquete" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoPaquete.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTipoPaquete(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="TipoPaquete Id - Integer") Integer tipoPaqueteId) throws Exception, BusinessLogicException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_DELETE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_DELETE);
            }*/
            tipoPaqueteService.deleteTipoPaquete(tipoPaqueteId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (RuntimeException ex){
            throw new BadRequestException(ex.getMessage());
        } 
    }
}
