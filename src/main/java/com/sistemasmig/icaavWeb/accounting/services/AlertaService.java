package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.AlertaManager;
import com.sistemasmig.icaavWeb.accounting.models.Alerta;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class AlertaService {
    private final static Logger logger = LoggerFactory.getLogger(AlertaService.class);

    @Autowired
    private AlertaManager alertaManager;
    
    public Alerta getById(Integer alertaId) throws EntityNotExistentException {
        return alertaManager.getById(alertaId);
    }
    
    public PagedResponse<Alerta> getAlerta(Alerta alerta,   Paging paging) {
        return alertaManager.getAlerta(alerta, paging);
    }
    
    public List<Alerta> findAll() {
        return alertaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Alerta createAlerta(Alerta alerta) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Alerta alertaPersisted = alertaManager.createAlerta(alerta);
        return getById(alertaPersisted.getId());
    }
    
    public Alerta updateAlerta(Integer alertaId,Alerta alerta) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Alerta alertaPersisted = alertaManager.updateAlerta(alertaId, alerta);
        return getById(alertaPersisted.getId());
    }
    
    public void deleteAlerta(Integer alertaId) throws EntityNotExistentException, BusinessLogicException {
        alertaManager.deleteAlerta(alertaId);
        
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


