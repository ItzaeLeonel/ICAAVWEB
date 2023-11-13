/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.controllers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.EmpresaUsuario;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.exceptions.handler.model.ErrorDetails;
import com.sistemasmig.icaavWeb.accounting.services.EmpresaUsuarioService;
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
@RequestMapping("empresaUsuario")

public class EmpresaUsuarioController {
    
    @Autowired
    private EmpresaUsuarioService empresaUsuarioService;
    
    @Operation(summary = "Search EmpresaUsuario by EmpresaUsuario Attributes", description = "This service retrieve EmpresaUsuario information filter by EmpresaUsuario Attributes", tags = { "empresaUsuario" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaUsuario.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    ResponseEntity<PagedResponse<EmpresaUsuario>> getEmpresaUsuario(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @RequestBody(required = true) @Parameter(description="EmpresaUsuario object - json") EmpresaUsuario empresaUsuario,
        @RequestParam(value = "page", required = false, defaultValue = "0") @Parameter(description="Page to retrieve") Integer page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10")  @Parameter(description="Page size to retrieve") Integer pageSize) throws BadRequestException, EntityNotExistentException, NoAccessGrantedException  {
        try {
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            Paging paging = new Paging(page, pageSize);
            return new ResponseEntity<>(empresaUsuarioService.getEmpresaUsuario(empresaUsuario,paging), HttpStatus.OK);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    
    @Operation(summary = "Search EmpresaUsuario by EmpresaUsuario Id", description = "This service retrieve EmpresaUsuario information filter by EmpresaUsuario Id", tags = { "empresaUsuario" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaUsuario.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EmpresaUsuario getById(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                     @PathVariable(value = "id") @Parameter(description="EmpresaUsuario Id - Integer") Integer empresaUsuarioId) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_ACCESS)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_ACCESS);
            }*/
            return empresaUsuarioService.getById(empresaUsuarioId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Create EmpresaUsuario", description = "This service create a new EmpresaUsuario Object", tags = { "empresaUsuario" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaUsuario.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EmpresaUsuario> createEmpresaUsuario(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
            @Valid @RequestBody(required = true) @Parameter(description="EmpresaUsuario object - json") EmpresaUsuario empresaUsuario) throws BusinessLogicException, ExistentEntityException, BadRequestException, EntityNotExistentException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_CREATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_CREATE);
            }*/
            return new ResponseEntity<>(empresaUsuarioService.createEmpresaUsuario(empresaUsuario), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
        
    }
    
    @Operation(summary = "Update EmpresaUsuario", description = "This service updates a persited EmpresaUsuario Object", tags = { "empresaUsuario" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaUsuario.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<EmpresaUsuario> updateEmpresaUsuario(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="EmpresaUsuario Id - Integer") Integer empresaUsuarioId,
                                                 @Valid @RequestBody(required = true) @Parameter(description="EmpresaUsuario object - json") EmpresaUsuario empresaUsuario, BindingResult bindingResult) throws BusinessLogicException, BadRequestException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_UPDATE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_UPDATE);
            }*/
            return new ResponseEntity<>(empresaUsuarioService.updateEmpresaUsuario(empresaUsuarioId, empresaUsuario), HttpStatus.OK);
        }catch (Exception ble) {
            throw new BadRequestException(ble.getMessage());
            //throw ble;
        }
        
 
    }
    
    @Operation(summary = "Delete EmpresaUsuario", description = "This service deletes (Logically) a persited EmpresaUsuario Object", tags = { "empresaUsuario" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaUsuario.class)))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class))))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteEmpresaUsuario(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                 @PathVariable(value = "id") @Parameter(description="EmpresaUsuario Id - Integer") Integer empresaUsuarioId) throws Exception, BusinessLogicException, EntityNotExistentException, ExistentEntityException, NoAccessGrantedException {
        try{
            /*if(!securityService.getGrantAndModule(token, Definitions.MODULE_MIG_SSO_APPLICATIONS, Definitions.GRANT_DELETE)){
                throw new NoAccessGrantedException(Definitions.MODULE_MIG_SSO_APPLICATIONS,Definitions.GRANT_DELETE);
            }*/
            empresaUsuarioService.deleteEmpresaUsuario(empresaUsuarioId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (RuntimeException ex){
            throw new BadRequestException(ex.getMessage());
        } 
    }
}
