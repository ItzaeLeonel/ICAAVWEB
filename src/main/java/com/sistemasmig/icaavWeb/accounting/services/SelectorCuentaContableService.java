/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.models.dto.ListSelectorCuentas;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.SelectorCuentaContableManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julio
 */

@Component
public class SelectorCuentaContableService {
    private final static Logger logger = LoggerFactory.getLogger(SelectorCuentaContableService.class);

    @Autowired
    private SelectorCuentaContableManager selectorcuentaContableManager;
    
      public List <ListSelectorCuentas> getCCPrecargadasSP(Integer pr_id_modelo_contable) throws Exception, EntityNotExistentException {
    return selectorcuentaContableManager.getBySelectorCuentaSP(pr_id_modelo_contable);
    }
    
}
