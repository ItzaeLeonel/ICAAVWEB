
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemasmig.icaavWeb.accounting.controllers;


import com.sistemasmig.icaavWeb.accounting.exceptions.BadRequestException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.NoAccessGrantedException;
import com.sistemasmig.icaavWeb.accounting.model.ListSelectorCuentas;
import com.sistemasmig.icaavWeb.accounting.services.SelectorCuentaContableService;
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
@RequestMapping("selectorCuentaContable")
public class SelectorCuentaContableController {
    
    
    @Autowired
    private SelectorCuentaContableService selectorCuentaContableService;
    @Transactional(readOnly = false)
     @RequestMapping(value = "procedure/CCPrecargadasSP", method = RequestMethod.POST)
    
    public List<ListSelectorCuentas> getByCCPrecargadasSP(@RequestHeader(value = "token", required = true) @Parameter(description="MIG SSO Token - UUID") String token,
                                                    @Parameter(description="pr_id_modelo_contable - Integer") Integer pr_id_modelo_contable) throws EntityNotExistentException, BadRequestException, NoAccessGrantedException  {
       
        try{
         
            return selectorCuentaContableService.getCCPrecargadasSP(pr_id_modelo_contable);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        } 
    }
    
}
