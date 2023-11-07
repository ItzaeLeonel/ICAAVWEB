/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.controllers;

import com.sistemasmig.icaavWeb.accounting.models.dto.ListDatosEmpresa;
import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.services.DatosEmpresaSPService;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Julio
 */
@RestController
@RequestMapping("datosEmpresa")
public class DatosEmpresaSPController {
        
     @Autowired
    private DatosEmpresaSPService datosEmpresaSPService;
    @Transactional(readOnly = false)
     @RequestMapping(value = "procedure/datosEmpresaSP", method = RequestMethod.POST)
    
    public List<ListDatosEmpresa> getByDatosEmpresaSP(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                    @Parameter(description="pr_id_grupo_empresa - Integer") Integer pr_id_grupo_empresa) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
         
            return datosEmpresaSPService.getDatosEmpresa(pr_id_grupo_empresa);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
    }
    
}
