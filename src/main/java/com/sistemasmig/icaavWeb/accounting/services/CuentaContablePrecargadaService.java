/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.models.dto.CCPrecargadasSP;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.CuentaContablePrecargadaManager;
import com.sistemasmig.icaavWeb.accounting.models.CuentaContablePrecargada;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Julio
 */
@Component
public class CuentaContablePrecargadaService {
     private final static Logger logger = LoggerFactory.getLogger(CuentaContablePrecargadaService.class);

    @Autowired
    private CuentaContablePrecargadaManager cuentaContablePrecargadaManager;
    
    public CuentaContablePrecargada getById(Integer CuentaCPId) throws EntityNotExistentException {
        return cuentaContablePrecargadaManager.getById(CuentaCPId);
    }
    
    public PagedResponse<CuentaContablePrecargada> getCuentaContablePrecargada(CuentaContablePrecargada cuentaContablePrecargada,   Paging paging) {
        return cuentaContablePrecargadaManager.getCuentaContablePrecargada(cuentaContablePrecargada, paging);
    }
    
    public List<CuentaContablePrecargada> findAll() {
        return cuentaContablePrecargadaManager.findAll();
    }
    
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public CuentaContablePrecargada createCuentaContablePrecargada(CuentaContablePrecargada cuentaContablePrecargada) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        CuentaContablePrecargada cuentaContablePrecargadaPersisted = cuentaContablePrecargadaManager.createCuentaContablePrecargada(cuentaContablePrecargada);
        return getById(cuentaContablePrecargadaPersisted.getId());
    }
    
    public CuentaContablePrecargada updateCuentaContablePrecargada(Integer cuentaContablePrecargadaId,CuentaContablePrecargada cuentaContablePrecargada) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        CuentaContablePrecargada cuentaContablePrecargadaPersisted = cuentaContablePrecargadaManager.updateCuentaContablePrecargada(cuentaContablePrecargadaId, cuentaContablePrecargada);
        return getById(cuentaContablePrecargadaPersisted.getId());
    }
    
    public void deleteCuentaContablePrecargada(Integer cuentaContablePrecargadaId) throws EntityNotExistentException, BusinessLogicException {
        cuentaContablePrecargadaManager.deleteCuentaContablePrecargada(cuentaContablePrecargadaId);
        
    }
    
    
    public Boolean initialize() {
        try{
            createCuentaContablePrecargadas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createCuentaContablePrecargadas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
    
    public CuentaContablePrecargada findAllSp(Integer pr_id_modelo_contable) throws EntityNotExistentException{
        return cuentaContablePrecargadaManager.getByCuentaC(pr_id_modelo_contable);
    }
    
      public List <CCPrecargadasSP> getCCPrecargadasSP(Integer pr_id_modelo_contable) throws Exception, EntityNotExistentException {
    return cuentaContablePrecargadaManager.getByCCPrecargadasSP(pr_id_modelo_contable);
    }
    
    
    
}
