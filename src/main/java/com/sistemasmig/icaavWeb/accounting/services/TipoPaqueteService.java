package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.TipoPaqueteManager;
import com.sistemasmig.icaavWeb.accounting.models.TipoPaquete;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TipoPaqueteService {
    private final static Logger logger = LoggerFactory.getLogger(TipoPaqueteService.class);

    @Autowired
    private TipoPaqueteManager tipoPaqueteManager;
    
    public TipoPaquete getById(Integer tipoPaqueteId) throws EntityNotExistentException {
        return tipoPaqueteManager.getById(tipoPaqueteId);
    }
    
    public PagedResponse<TipoPaquete> getTipoPaquete(TipoPaquete tipoPaquete,   Paging paging) {
        return tipoPaqueteManager.getTipoPaquete(tipoPaquete, paging);
    }
    
    public List<TipoPaquete> findAll() {
        return tipoPaqueteManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public TipoPaquete createTipoPaquete(TipoPaquete tipoPaquete) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        TipoPaquete tipoPaquetePersisted = tipoPaqueteManager.createTipoPaquete(tipoPaquete);
        return getById(tipoPaquetePersisted.getId());
    }
    
    public TipoPaquete updateTipoPaquete(Integer tipoPaqueteId,TipoPaquete tipoPaquete) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        TipoPaquete tipoPaquetePersisted = tipoPaqueteManager.updateTipoPaquete(tipoPaqueteId, tipoPaquete);
        return getById(tipoPaquetePersisted.getId());
    }
    
    public void deleteTipoPaquete(Integer tipoPaqueteId) throws EntityNotExistentException, BusinessLogicException {
        tipoPaqueteManager.deleteTipoPaquete(tipoPaqueteId);
        
    }  
    
    public Boolean initialize() {
        try{
            createTipoPaquetes();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createTipoPaquetes() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


