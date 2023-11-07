package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.NotificacionesManager;
import com.sistemasmig.icaavWeb.accounting.models.Notificaciones;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class NotificacionesService {
    private final static Logger logger = LoggerFactory.getLogger(NotificacionesService.class);

    @Autowired
    private NotificacionesManager notificacionesManager;
    
    public Notificaciones getById(Integer notificacionesId) throws EntityNotExistentException {
        return notificacionesManager.getById(notificacionesId);
    }
    
    public PagedResponse<Notificaciones> getNotificaciones(Notificaciones notificaciones,   Paging paging) {
        return notificacionesManager.getNotificaciones(notificaciones, paging);
    }
    
    public List<Notificaciones> findAll() {
        return notificacionesManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Notificaciones createNotificaciones(Notificaciones notificaciones) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Notificaciones notificacionesPersisted = notificacionesManager.createNotificaciones(notificaciones);
        return getById(notificacionesPersisted.getId());
    }
    
    public Notificaciones updateNotificaciones(Integer notificacionesId,Notificaciones notificaciones) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Notificaciones notificacionesPersisted = notificacionesManager.updateNotificaciones(notificacionesId, notificaciones);
        return getById(notificacionesPersisted.getId());
    }
    
    public void deleteNotificaciones(Integer notificacionesId) throws EntityNotExistentException, BusinessLogicException {
        notificacionesManager.deleteNotificaciones(notificacionesId);
        
    }  
    
    public Boolean initialize() {
        try{
            createNotificacioness();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createNotificacioness() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


