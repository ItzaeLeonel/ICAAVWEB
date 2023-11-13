package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Role;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.RoleManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class RoleService {
    private final static Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleManager roleManager;
    
    public Role getById(Integer roleId) throws EntityNotExistentException {
        return roleManager.getById(roleId);
    }
    
    public PagedResponse<Role> getRole(Role role,   Paging paging) {
        return roleManager.getRole(role, paging);
    }
    
    public List<Role> findAll() {
        return roleManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Role createRole(Role role) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Role rolePersisted = roleManager.createRole(role);
        return getById(rolePersisted.getId());
    }
    
    public Role updateRole(Integer roleId,Role role) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Role rolePersisted = roleManager.updateRole(roleId, role);
        return getById(rolePersisted.getId());
    }
    
    public void deleteRole(Integer roleId) throws EntityNotExistentException, BusinessLogicException {
        roleManager.deleteRole(roleId);
        
    }  
    
    public Boolean initialize() {
        try{
            createRoles();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createRoles() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


