package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.PermisoRole;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.PermisoRoleManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PermisoRoleService {
    private final static Logger logger = LoggerFactory.getLogger(PermisoRoleService.class);

    @Autowired
    private PermisoRoleManager permisoRoleManager;
    
    public PermisoRole getById(Integer permisoRoleId) throws EntityNotExistentException {
        return permisoRoleManager.getById(permisoRoleId);
    }
    
    public PagedResponse<PermisoRole> getPermisoRole(PermisoRole permisoRole,   Paging paging) {
        return permisoRoleManager.getPermisoRole(permisoRole, paging);
    }
    
    public List<PermisoRole> findAll() {
        return permisoRoleManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public PermisoRole createPermisoRole(PermisoRole permisoRole) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        PermisoRole permisoRolePersisted = permisoRoleManager.createPermisoRole(permisoRole);
        return getById(permisoRolePersisted.getId());
    }
    
    public PermisoRole updatePermisoRole(Integer permisoRoleId,PermisoRole permisoRole) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        PermisoRole permisoRolePersisted = permisoRoleManager.updatePermisoRole(permisoRoleId, permisoRole);
        return getById(permisoRolePersisted.getId());
    }
    
    public void deletePermisoRole(Integer permisoRoleId) throws EntityNotExistentException, BusinessLogicException {
        permisoRoleManager.deletePermisoRole(permisoRoleId);
        
    }  
    
    public Boolean initialize() {
        try{
            createPermisoRoles();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createPermisoRoles() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


