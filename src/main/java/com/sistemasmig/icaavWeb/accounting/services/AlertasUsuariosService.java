package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.AlertasUsuariosManager;
import com.sistemasmig.icaavWeb.accounting.models.AlertasUsuarios;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class AlertasUsuariosService {
    private final static Logger logger = LoggerFactory.getLogger(AlertasUsuariosService.class);

    @Autowired
    private AlertasUsuariosManager alertasUsuariosManager;
    
    public AlertasUsuarios getById(Integer alertasUsuariosId) throws EntityNotExistentException {
        return alertasUsuariosManager.getById(alertasUsuariosId);
    }
    
    public PagedResponse<AlertasUsuarios> getAlertasUsuarios(AlertasUsuarios alertasUsuarios,   Paging paging) {
        return alertasUsuariosManager.getAlertasUsuarios(alertasUsuarios, paging);
    }
    
    public List<AlertasUsuarios> findAll() {
        return alertasUsuariosManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public AlertasUsuarios createAlertasUsuarios(AlertasUsuarios alertasUsuarios) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        AlertasUsuarios alertasUsuariosPersisted = alertasUsuariosManager.createAlertasUsuarios(alertasUsuarios);
        return getById(alertasUsuariosPersisted.getId());
    }
    
    public AlertasUsuarios updateAlertasUsuarios(Integer alertasUsuariosId,AlertasUsuarios alertasUsuarios) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        AlertasUsuarios alertasUsuariosPersisted = alertasUsuariosManager.updateAlertasUsuarios(alertasUsuariosId, alertasUsuarios);
        return getById(alertasUsuariosPersisted.getId());
    }
    
    public void deleteAlertasUsuarios(Integer alertasUsuariosId) throws EntityNotExistentException, BusinessLogicException {
        alertasUsuariosManager.deleteAlertasUsuarios(alertasUsuariosId);
        
    }  
    
    public Boolean initialize() {
        try{
            createAlertasUsuarioss();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createAlertasUsuarioss() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


