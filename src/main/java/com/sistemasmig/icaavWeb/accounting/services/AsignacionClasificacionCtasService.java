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
import com.sistemasmig.icaavWeb.accounting.managers.AsignacionClasificacionCtasManager;
import com.sistemasmig.icaavWeb.accounting.models.Alerta;
import com.sistemasmig.icaavWeb.accounting.models.AsignacionClasificacionCtas;
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
public class AsignacionClasificacionCtasService {
    private final static Logger logger = LoggerFactory.getLogger(AsignacionClasificacionCtasService.class);
    
    @Autowired
    private AsignacionClasificacionCtasManager asignacionClasificacionCtasManager;
    
    public AsignacionClasificacionCtas getById(Integer asignacionClasificacionCtasManagerId) throws EntityNotExistentException {
        return asignacionClasificacionCtasManager.getById(asignacionClasificacionCtasManagerId);
    }
    
    
    public PagedResponse<AsignacionClasificacionCtas> getAsignacionClasificacionCtas(AsignacionClasificacionCtas asignacionClasificacionCtas,   Paging paging) {
        return asignacionClasificacionCtasManager.getAsignacionClasificacionCtas(asignacionClasificacionCtas, paging);
    }
    
    public List<AsignacionClasificacionCtas> findAll() {
        return asignacionClasificacionCtasManager.findAll();
    }
    
    
    
    
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public AsignacionClasificacionCtas createAsignacionClasificacionCtas(AsignacionClasificacionCtas asignacionClasificacionCtas) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        AsignacionClasificacionCtas asignacionClasificacionCtasPersisted = asignacionClasificacionCtasManager.createAsignacionClasificacionCtas(asignacionClasificacionCtas);
        return getById(asignacionClasificacionCtasPersisted.getId());
    }
    
    public AsignacionClasificacionCtas updateAsignacionClasificacionCtas(Integer asignacionClasificacionCtasId,AsignacionClasificacionCtas asignacionClasificacionCtas) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        AsignacionClasificacionCtas asignacionClasificacionCtasPersisted = asignacionClasificacionCtasManager.updateAsignacionClasificacionCtas(asignacionClasificacionCtasId, asignacionClasificacionCtas);
        return getById(asignacionClasificacionCtasPersisted.getId());
    }
    
    public void deleteAsignacionClasificacionCtas(Integer asignacionClasificacionCtasId) throws EntityNotExistentException, BusinessLogicException {
        asignacionClasificacionCtasManager.deleteAsignacionClasificacionCtas(asignacionClasificacionCtasId);
        
    }
    
    public Boolean initialize() {
        try{
            createAsignacionClasificacionCtas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createAsignacionClasificacionCtas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
}
