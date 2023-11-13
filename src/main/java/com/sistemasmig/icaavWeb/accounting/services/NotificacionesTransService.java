package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.NotificacionesTrans;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.NotificacionesTransManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class NotificacionesTransService {
    private final static Logger logger = LoggerFactory.getLogger(NotificacionesTransService.class);

    @Autowired
    private NotificacionesTransManager notificacionesTransManager;
    
    public NotificacionesTrans getById(Integer notificacionesTransId) throws EntityNotExistentException {
        return notificacionesTransManager.getById(notificacionesTransId);
    }
    
    public PagedResponse<NotificacionesTrans> getNotificacionesTrans(NotificacionesTrans notificacionesTrans,   Paging paging) {
        return notificacionesTransManager.getNotificacionesTrans(notificacionesTrans, paging);
    }
    
    public List<NotificacionesTrans> findAll() {
        return notificacionesTransManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public NotificacionesTrans createNotificacionesTrans(NotificacionesTrans notificacionesTrans) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        NotificacionesTrans notificacionesTransPersisted = notificacionesTransManager.createNotificacionesTrans(notificacionesTrans);
        return getById(notificacionesTransPersisted.getId());
    }
    
    public NotificacionesTrans updateNotificacionesTrans(Integer notificacionesTransId,NotificacionesTrans notificacionesTrans) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        NotificacionesTrans notificacionesTransPersisted = notificacionesTransManager.updateNotificacionesTrans(notificacionesTransId, notificacionesTrans);
        return getById(notificacionesTransPersisted.getId());
    }
    
    public void deleteNotificacionesTrans(Integer notificacionesTransId) throws EntityNotExistentException, BusinessLogicException {
        notificacionesTransManager.deleteNotificacionesTrans(notificacionesTransId);
        
    }  
    
    public Boolean initialize() {
        try{
            createNotificacionesTranss();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createNotificacionesTranss() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


