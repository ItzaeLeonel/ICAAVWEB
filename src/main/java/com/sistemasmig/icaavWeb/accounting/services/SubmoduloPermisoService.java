package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.SubmoduloPermisoManager;
import com.sistemasmig.icaavWeb.accounting.models.SubmoduloPermiso;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SubmoduloPermisoService {
    private final static Logger logger = LoggerFactory.getLogger(SubmoduloPermisoService.class);

    @Autowired
    private SubmoduloPermisoManager submoduloPermisoManager;
    
    public SubmoduloPermiso getById(Integer submoduloPermisoId) throws EntityNotExistentException {
        return submoduloPermisoManager.getById(submoduloPermisoId);
    }
    
    public PagedResponse<SubmoduloPermiso> getSubmoduloPermiso(SubmoduloPermiso submoduloPermiso,   Paging paging) {
        return submoduloPermisoManager.getSubmoduloPermiso(submoduloPermiso, paging);
    }
    
    public List<SubmoduloPermiso> findAll() {
        return submoduloPermisoManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public SubmoduloPermiso createSubmoduloPermiso(SubmoduloPermiso submoduloPermiso) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        SubmoduloPermiso submoduloPermisoPersisted = submoduloPermisoManager.createSubmoduloPermiso(submoduloPermiso);
        return getById(submoduloPermisoPersisted.getId());
    }
    
    public SubmoduloPermiso updateSubmoduloPermiso(Integer submoduloPermisoId,SubmoduloPermiso submoduloPermiso) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        SubmoduloPermiso submoduloPermisoPersisted = submoduloPermisoManager.updateSubmoduloPermiso(submoduloPermisoId, submoduloPermiso);
        return getById(submoduloPermisoPersisted.getId());
    }
    
    public void deleteSubmoduloPermiso(Integer submoduloPermisoId) throws EntityNotExistentException, BusinessLogicException {
        submoduloPermisoManager.deleteSubmoduloPermiso(submoduloPermisoId);
        
    }  
    
    public Boolean initialize() {
        try{
            createSubmoduloPermisos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createSubmoduloPermisos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


