package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.UsuarioRecuperacionManager;
import com.sistemasmig.icaavWeb.accounting.models.UsuarioRecuperacion;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UsuarioRecuperacionService {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioRecuperacionService.class);

    @Autowired
    private UsuarioRecuperacionManager usuarioRecuperacionManager;
    
    public UsuarioRecuperacion getById(Integer usuarioRecuperacionId) throws EntityNotExistentException {
        return usuarioRecuperacionManager.getById(usuarioRecuperacionId);
    }
    
    public PagedResponse<UsuarioRecuperacion> getUsuarioRecuperacion(UsuarioRecuperacion usuarioRecuperacion,   Paging paging) {
        return usuarioRecuperacionManager.getUsuarioRecuperacion(usuarioRecuperacion, paging);
    }
    
    public List<UsuarioRecuperacion> findAll() {
        return usuarioRecuperacionManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public UsuarioRecuperacion createUsuarioRecuperacion(UsuarioRecuperacion usuarioRecuperacion) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        UsuarioRecuperacion usuarioRecuperacionPersisted = usuarioRecuperacionManager.createUsuarioRecuperacion(usuarioRecuperacion);
        return getById(usuarioRecuperacionPersisted.getId());
    }
    
    public UsuarioRecuperacion updateUsuarioRecuperacion(Integer usuarioRecuperacionId,UsuarioRecuperacion usuarioRecuperacion) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        UsuarioRecuperacion usuarioRecuperacionPersisted = usuarioRecuperacionManager.updateUsuarioRecuperacion(usuarioRecuperacionId, usuarioRecuperacion);
        return getById(usuarioRecuperacionPersisted.getId());
    }
    
    public void deleteUsuarioRecuperacion(Integer usuarioRecuperacionId) throws EntityNotExistentException, BusinessLogicException {
        usuarioRecuperacionManager.deleteUsuarioRecuperacion(usuarioRecuperacionId);
        
    }  
    
    public Boolean initialize() {
        try{
            createUsuarioRecuperacions();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createUsuarioRecuperacions() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


