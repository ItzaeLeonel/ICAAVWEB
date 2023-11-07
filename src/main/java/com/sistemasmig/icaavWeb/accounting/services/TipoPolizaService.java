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
import com.sistemasmig.icaavWeb.accounting.managers.TipoPolizaManager;
import com.sistemasmig.icaavWeb.accounting.models.TipoPoliza;
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
public class TipoPolizaService {
    private final static Logger logger = LoggerFactory.getLogger(TipoPolizaService.class);

    @Autowired
    private TipoPolizaManager tipoPolizaManager;
    
    public TipoPoliza getById(Integer tipoPolizaId) throws EntityNotExistentException {
        return tipoPolizaManager.getById(tipoPolizaId);
    }
    
    public PagedResponse<TipoPoliza> getTipoPoliza(TipoPoliza tipoPoliza,   Paging paging) {
        return tipoPolizaManager.getTipoPoliza(tipoPoliza, paging);
    }
    
    public List<TipoPoliza> findAll() {
        return tipoPolizaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public TipoPoliza createTipoPoliza(TipoPoliza tipoPoliza) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        TipoPoliza tipoPolizaPersisted = tipoPolizaManager.createTipoPoliza(tipoPoliza);
        return getById(tipoPolizaPersisted.getId());
    }
    
    public TipoPoliza updateTipoPoliza(Integer tipoPolizaId,TipoPoliza tipoPoliza) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        TipoPoliza tipoPolizaPersisted = tipoPolizaManager.updateTipoPoliza(tipoPolizaId, tipoPoliza);
        return getById(tipoPolizaPersisted.getId());
    }
    
    public void deleteTipoPoliza(Integer tipoPolizaId) throws EntityNotExistentException, BusinessLogicException {
        tipoPolizaManager.deleteTipoPoliza(tipoPolizaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createTiposPolizas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createTiposPolizas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
}
