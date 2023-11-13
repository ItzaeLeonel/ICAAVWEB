package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Estilo;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.EstiloManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EstiloService {
    private final static Logger logger = LoggerFactory.getLogger(EstiloService.class);

    @Autowired
    private EstiloManager estiloManager;
    
    public Estilo getById(Integer estiloId) throws EntityNotExistentException {
        return estiloManager.getById(estiloId);
    }
    
    public PagedResponse<Estilo> getEstilo(Estilo estilo,   Paging paging) {
        return estiloManager.getEstilo(estilo, paging);
    }
    
    public List<Estilo> findAll() {
        return estiloManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Estilo createEstilo(Estilo estilo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Estilo estiloPersisted = estiloManager.createEstilo(estilo);
        return getById(estiloPersisted.getId());
    }
    
    public Estilo updateEstilo(Integer estiloId,Estilo estilo) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Estilo estiloPersisted = estiloManager.updateEstilo(estiloId, estilo);
        return getById(estiloPersisted.getId());
    }
    
    public void deleteEstilo(Integer estiloId) throws EntityNotExistentException, BusinessLogicException {
        estiloManager.deleteEstilo(estiloId);
        
    }  
    
    public Boolean initialize() {
        try{
            createEstilos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createEstilos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


