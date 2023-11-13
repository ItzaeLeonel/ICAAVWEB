package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Direccion;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.DireccionManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DireccionService {
    private final static Logger logger = LoggerFactory.getLogger(DireccionService.class);

    @Autowired
    private DireccionManager direccionManager;
    
    public Direccion getById(Integer direccionId) throws EntityNotExistentException {
        return direccionManager.getById(direccionId);
    }
    
    public PagedResponse<Direccion> getDireccion(Direccion direccion,   Paging paging) {
        return direccionManager.getDireccion(direccion, paging);
    }
    
    public List<Direccion> findAll() {
        return direccionManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Direccion createDireccion(Direccion direccion) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Direccion direccionPersisted = direccionManager.createDireccion(direccion);
        return getById(direccionPersisted.getId());
    }
    
    public Direccion updateDireccion(Integer direccionId,Direccion direccion) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Direccion direccionPersisted = direccionManager.updateDireccion(direccionId, direccion);
        return getById(direccionPersisted.getId());
    }
    
    public void deleteDireccion(Integer direccionId) throws EntityNotExistentException, BusinessLogicException {
        direccionManager.deleteDireccion(direccionId);
        
    }  
    
    public Boolean initialize() {
        try{
            createDireccions();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createDireccions() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


