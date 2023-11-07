package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.UsuarioAccesoManager;
import com.sistemasmig.icaavWeb.accounting.models.UsuarioAcceso;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UsuarioAccesoService {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioAccesoService.class);

    @Autowired
    private UsuarioAccesoManager usuarioAccesoManager;
    
    public UsuarioAcceso getById(Integer usuarioAccesoId) throws EntityNotExistentException {
        return usuarioAccesoManager.getById(usuarioAccesoId);
    }
    
    public PagedResponse<UsuarioAcceso> getUsuarioAcceso(UsuarioAcceso usuarioAcceso,   Paging paging) {
        return usuarioAccesoManager.getUsuarioAcceso(usuarioAcceso, paging);
    }
    
    public List<UsuarioAcceso> findAll() {
        return usuarioAccesoManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public UsuarioAcceso createUsuarioAcceso(UsuarioAcceso usuarioAcceso) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        UsuarioAcceso usuarioAccesoPersisted = usuarioAccesoManager.createUsuarioAcceso(usuarioAcceso);
        return getById(usuarioAccesoPersisted.getId());
    }
    
    public UsuarioAcceso updateUsuarioAcceso(Integer usuarioAccesoId,UsuarioAcceso usuarioAcceso) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        UsuarioAcceso usuarioAccesoPersisted = usuarioAccesoManager.updateUsuarioAcceso(usuarioAccesoId, usuarioAcceso);
        return getById(usuarioAccesoPersisted.getId());
    }
    
    public void deleteUsuarioAcceso(Integer usuarioAccesoId) throws EntityNotExistentException, BusinessLogicException {
        usuarioAccesoManager.deleteUsuarioAcceso(usuarioAccesoId);
        
    }  
    
    public Boolean initialize() {
        try{
            createUsuarioAccesos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createUsuarioAccesos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


