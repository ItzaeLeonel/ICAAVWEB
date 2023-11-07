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
import com.sistemasmig.icaavWeb.accounting.managers.ClasificacionCtasManager;
import com.sistemasmig.icaavWeb.accounting.models.ClasificacionCtas;
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
public class ClasificacionCtasService {
    private final static Logger logger = LoggerFactory.getLogger(ClasificacionCtasService.class);

    @Autowired
    private ClasificacionCtasManager clasificacionCtasManager;
    
    public ClasificacionCtas getById(Integer clasificacionCtasId) throws EntityNotExistentException {
        return clasificacionCtasManager.getById(clasificacionCtasId);
    }
    
    public PagedResponse<ClasificacionCtas> getClasificacionCtas(ClasificacionCtas clasificacionCtas,   Paging paging) {
        return clasificacionCtasManager.getClasificacionCtas(clasificacionCtas, paging);
    }
    
    public List<ClasificacionCtas> findAll() {
        return clasificacionCtasManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public ClasificacionCtas createClasificacionCtas(ClasificacionCtas clasificacionCtas) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        ClasificacionCtas clasificacionCtasPersisted = clasificacionCtasManager.createClasificacionCtas(clasificacionCtas);
        return getById(clasificacionCtasPersisted.getId());
    }
    
    public ClasificacionCtas updateClasificacionCtas(Integer clasificacionCtasId,ClasificacionCtas clasificacionCtas) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        ClasificacionCtas clasificacionCtasPersisted = clasificacionCtasManager.updateClasificacionCtas(clasificacionCtasId, clasificacionCtas);
        return getById(clasificacionCtasPersisted.getId());
    }
    
    public void deleteClasificacionCtas(Integer clasificacionCtasId) throws EntityNotExistentException, BusinessLogicException {
        clasificacionCtasManager.deleteClasificacionCtas(clasificacionCtasId);
        
    }  
    
    public Boolean initialize() {
        try{
            createClasificacionCtas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createClasificacionCtas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
}
