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
import com.sistemasmig.icaavWeb.accounting.managers.AlertaManager;
import com.sistemasmig.icaavWeb.accounting.managers.AsignacionTipoPolizasManager;
import com.sistemasmig.icaavWeb.accounting.models.Alerta;
import com.sistemasmig.icaavWeb.accounting.models.AsignacionTipoPolizas;
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
public class AsignacionTipoPolizasService {
    private final static Logger logger = LoggerFactory.getLogger(AlertaService.class);

    @Autowired
    private AsignacionTipoPolizasManager asignacionTipoPolizasManager;
    
    public AsignacionTipoPolizas getById(Integer alertaId) throws EntityNotExistentException {
        return asignacionTipoPolizasManager.getById(alertaId);
    }
    
    public PagedResponse<AsignacionTipoPolizas> getAsignacionTipoPolizas(AsignacionTipoPolizas asignacionTipoPolizas,   Paging paging) {
        return asignacionTipoPolizasManager.getAsignacionTipoPolizas(asignacionTipoPolizas, paging);
    }
    
    public List<AsignacionTipoPolizas> findAll() {
        return asignacionTipoPolizasManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public AsignacionTipoPolizas createAsignacionTipoPolizas(AsignacionTipoPolizas asignacionTipoPolizas) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        AsignacionTipoPolizas asignacionTipoPolizasPersisted = asignacionTipoPolizasManager.createAsignacionTipoPolizas(asignacionTipoPolizas);
        return getById(asignacionTipoPolizasPersisted.getId());
    }
    
    public AsignacionTipoPolizas updateAsignacionTipoPolizas(Integer asignacionTipoPolizaId,AsignacionTipoPolizas asignacionTipoPolizas) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        AsignacionTipoPolizas asignacionTipoPolizasPersisted = asignacionTipoPolizasManager.updateAsignacionTipoPolizas(asignacionTipoPolizaId, asignacionTipoPolizas);
        return getById(asignacionTipoPolizasPersisted.getId());
    }
    
   public void deleteAsignacionTipoPolizas(Integer asignacionTipoPolizasId) throws EntityNotExistentException, BusinessLogicException {
        asignacionTipoPolizasManager.deleteasignacionTipoPolizas(asignacionTipoPolizasId);
        
    }  
    
    public Boolean initialize() {
        try{
            createAlertas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createAlertas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }

    
}
