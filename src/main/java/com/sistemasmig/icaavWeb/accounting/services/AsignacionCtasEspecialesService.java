/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.AsignacionCtasEspecialesManager;
import com.sistemasmig.icaavWeb.accounting.models.AsignacionCtasEspeciales;
import com.sistemasmig.icaavWeb.accounting.models.AsignacionCuentasEspDemo;
import java.util.List;
import java.util.Optional;
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
public class AsignacionCtasEspecialesService {
     
    private final static Logger logger = LoggerFactory.getLogger(CuentasEspecialesService.class);

    @Autowired
    private AsignacionCtasEspecialesManager asignacionCtasEspecialesManager;
    
    public AsignacionCtasEspeciales getById(Integer asignacionCtasEspecialesId) throws EntityNotExistentException {
        return asignacionCtasEspecialesManager.getById(asignacionCtasEspecialesId);
    }
   
    
    public PagedResponse<AsignacionCtasEspeciales> getAsignacionCtasEspeciales(AsignacionCtasEspeciales asignacionCtasEspeciales,   Paging paging) {
        return asignacionCtasEspecialesManager.getAsignacionCtasEspeciales(asignacionCtasEspeciales, paging);
    }
    
    public List<AsignacionCtasEspeciales> findAll() {
        return asignacionCtasEspecialesManager.findAll();
    }
    
  
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public AsignacionCtasEspeciales createAsignacionCtasEspeciales(AsignacionCtasEspeciales asignacionCtasEspeciales) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        AsignacionCtasEspeciales asignacionCtasEspecialesPersisted = asignacionCtasEspecialesManager.createAsignacionCtasEspeciales(asignacionCtasEspeciales);
        return getById(asignacionCtasEspecialesPersisted.getId());
    }
    

    
    
    public AsignacionCtasEspeciales updateAsignacionCtasEspeciales(Integer asignacionCtasEspecialesId,AsignacionCtasEspeciales asignacionCtasEspeciales) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        AsignacionCtasEspeciales asignacionCtasEspecialesPersisted = asignacionCtasEspecialesManager.updateAsignacionCtasEspeciales(asignacionCtasEspecialesId, asignacionCtasEspeciales);
        return getById(asignacionCtasEspecialesPersisted.getId());
    }
    
    public void deleteAsignacionCtasEspeciales(Integer asignacionCtasEspeciales) throws EntityNotExistentException, BusinessLogicException {
        asignacionCtasEspecialesManager.deleteAsignacionCtasEspeciales(asignacionCtasEspeciales);
        
    }
    
    
    public Boolean initialize() {
        try{
            createAsignacionCtasEsp();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createAsignacionCtasEsp() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
    /*public AsignacionCtasEspeciales findAllSp(Integer pr_id_empresa, Integer pr_id_cta_especial, Integer pr_id_cuenta_contable) throws EntityNotExistentException{
        return asignacionCtasEspecialesManager.UpdateByCuentaEspecial( pr_id_empresa,  pr_id_cta_especial,  pr_id_cuenta_contable);
    }*/
    
    public AsignacionCuentasEspDemo createSp(Integer pr_id_empresa, Integer pr_id_cta_especial, Integer pr_id_cuenta_contable) throws Exception, EntityNotExistentException {
        System.out.println(pr_id_empresa);
    return asignacionCtasEspecialesManager.createByCuentaEspecialsp(pr_id_empresa,pr_id_cta_especial,pr_id_cuenta_contable);
    }
    /*
     public AsignacionCtasEspeciales findAllSp(Integer pr_id_grupo_empresa, Integer pr_tipo_cuenta) throws EntityNotExistentException {
       
    return asignacionCtasEspecialesManager.getByCuentaEspecialsp(pr_id_grupo_empresa, pr_tipo_cuenta);
        
}*/
}
