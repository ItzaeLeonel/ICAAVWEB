/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.sistemasmig.icaavWeb.accounting.models.AsignacionCtasEspeciales;
import com.sistemasmig.icaavWeb.accounting.models.AsignacionCuentasEspDemo;
import com.sistemasmig.icaavWeb.accounting.models.CuentasEspeciales;
import com.sistemasmig.icaavWeb.accounting.services.AsignacionCtasEspecialesService;
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
@RequestMapping("asignacionCtasEspeciales")

public class AsignacionCtasEspecialesController {
      
    @Autowired
    private AsignacionCtasEspecialesService asignacionCtasEspecialesService;
    
    
    
    @Operation(summary = "Search AsignacionCtasEspeciales by AsignacionCtasEspeciales Attributes", description = "This service retrieve AsignacionCtasEspeciales information filter by CuentasEspeciales Attributes", tags = { "asignacionCtasEspeciales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    ResponseEntity<PagedResponse<AsignacionCtasEspeciales>> getAsignacionCtasEspeciales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @RequestBody(required = true) @Parameter(description="AsignacionCtasEspeciales object - json") AsignacionCtasEspeciales asignacionCtasEspeciales,
        @RequestParam(value = "page", required = false, defaultValue = "0") @Parameter(description="Page to retrieve") Integer page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10")  @Parameter(description="Page size to retrieve") Integer pageSize) throws BadRequestException, EntityNotExistentException, NoAccessGrantedException  {
        try {
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            Paging paging = new Paging(page, pageSize);
            return new ResponseEntity<>(asignacionCtasEspecialesService.getAsignacionCtasEspeciales(asignacionCtasEspeciales, paging), HttpStatus.OK);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    
    
    
    @Operation(summary = "Search AsignacionCtasEspeciales by AsignacionCtasEspeciales Id", description = "This service retrieve AsignacionCtasEspeciales information filter by AsignacionCtasEspeciales Id", tags = { "asignacionCtasEspeciales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuentasEspeciales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AsignacionCtasEspeciales getById(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                     @PathVariable(value = "id") @Parameter(description="AsignacionCtasEspeciales Id - Integer") Integer asignacionCtasEspecialesId) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            return asignacionCtasEspecialesService.getById(asignacionCtasEspecialesId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Create AsignacionCtasEspeciales", description = "This service create a new AsignacionCtasEspeciales Object", tags = { "AsignacionCtasEspeciales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AsignacionCtasEspeciales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AsignacionCtasEspeciales> createAsignacionCtasEspeciales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @Valid @RequestBody(required = true) @Parameter(description="AsignacionCtasEspeciales object - json") AsignacionCtasEspeciales asignacionCtasEspeciales) throws BusinessLogicException, ExistentEntityException, BadRequestException, EntityNotExistentException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_CREATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_CREATE);
            }*/
            return new ResponseEntity<>(asignacionCtasEspecialesService.createAsignacionCtasEspeciales(asignacionCtasEspeciales), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
     @Operation(summary = "Update AsignacionCtasEspeciales", description = "This service updates a persited AsignacionCtasEspeciales Object", tags = { "asignacionCtasEspeciales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AsignacionCtasEspeciales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
     
     
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<AsignacionCtasEspeciales> updateAsignacionCtasEspeciales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="AsignacionCtasEspeciales Id - Integer") Integer asignacionCtasEspecialesId,
                                                 @Valid @RequestBody(required = true) @Parameter(description="AsignacionCtasEspeciales object - json") AsignacionCtasEspeciales asignacionCtasEspeciales, BindingResult bindingResult) throws BusinessLogicException, BadRequestException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_UPDATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_UPDATE);
            }*/
    
            return new ResponseEntity<>(asignacionCtasEspecialesService.updateAsignacionCtasEspeciales(asignacionCtasEspecialesId, asignacionCtasEspeciales), HttpStatus.OK);
        }catch (Exception ble) {
            throw new BadRequestException(ble.getMessage());
            //throw ble;
        }
     
    }
    
    @Operation(summary = "Delete AsignacionCtasEspeciales", description = "This service deletes (Logically) a persited AsignacionCtasEspeciales Object", tags = { "asignacionCtasEspeciales" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AsignacionCtasEspeciales.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteAsignacionCtasEspeciales(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="AsignacionCtasEspeciales Id - Integer") Integer asignacionCtasEspecialesId) throws Exception, BusinessLogicException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
           
  
           asignacionCtasEspecialesService.deleteAsignacionCtasEspeciales(asignacionCtasEspecialesId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (RuntimeException ex){
            throw new BadRequestException(ex.getMessage());
        } 
    }
    
    
    
    @RequestMapping(value = "procedure/asignacionce", method = RequestMethod.POST)
    public AsignacionCuentasEspDemo UpdateByCuentaEspecial(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                    @Parameter(description="idEmpresa Id - String") Integer pr_id_empresa, @Parameter(description="CuentasEspeciales Id - String")Integer  pr_id_cta_especial,  @Parameter(description="CuentaContable Id - String")Integer pr_id_cuenta_contable) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
          
            return asignacionCtasEspecialesService.createSp(pr_id_empresa, pr_id_cta_especial, pr_id_cuenta_contable );
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
    }
  
}
