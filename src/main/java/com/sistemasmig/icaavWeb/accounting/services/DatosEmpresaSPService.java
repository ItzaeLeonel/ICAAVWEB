/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.models.dto.ListDatosEmpresa;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.DatosEmpresaSPManager;
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
public class DatosEmpresaSPService {
      private final static Logger logger = LoggerFactory.getLogger(DatosEmpresaSPService.class);

    @Autowired
    private DatosEmpresaSPManager datosEmpresaSPManager;
    
      public List <ListDatosEmpresa> getDatosEmpresa(Integer pr_id_grupo_empresa) throws Exception, EntityNotExistentException {
    return datosEmpresaSPManager.getByDatosEmpresa(pr_id_grupo_empresa);
    }
    
}
