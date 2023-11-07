package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.TipoPermisoManager;
import com.sistemasmig.icaavWeb.accounting.models.TipoPermiso;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TipoPermisoService {
    private final static Logger logger = LoggerFactory.getLogger(TipoPermisoService.class);

    @Autowired
    private TipoPermisoManager tipoPermisoManager;
    
    public TipoPermiso getById(Integer tipoPermisoId) throws EntityNotExistentException {
        return tipoPermisoManager.getById(tipoPermisoId);
    }
    
    public PagedResponse<TipoPermiso> getTipoPermiso(TipoPermiso tipoPermiso,   Paging paging) {
        return tipoPermisoManager.getTipoPermiso(tipoPermiso, paging);
    }
    
    public List<TipoPermiso> findAll() {
        return tipoPermisoManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public TipoPermiso createTipoPermiso(TipoPermiso tipoPermiso) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        TipoPermiso tipoPermisoPersisted = tipoPermisoManager.createTipoPermiso(tipoPermiso);
        return getById(tipoPermisoPersisted.getId());
    }
    
    public TipoPermiso updateTipoPermiso(Integer tipoPermisoId,TipoPermiso tipoPermiso) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        TipoPermiso tipoPermisoPersisted = tipoPermisoManager.updateTipoPermiso(tipoPermisoId, tipoPermiso);
        return getById(tipoPermisoPersisted.getId());
    }
    
    public void deleteTipoPermiso(Integer tipoPermisoId) throws EntityNotExistentException, BusinessLogicException {
        tipoPermisoManager.deleteTipoPermiso(tipoPermisoId);
        
    }  
    
    public Boolean initialize() {
        try{
            createTipoPermisos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createTipoPermisos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


