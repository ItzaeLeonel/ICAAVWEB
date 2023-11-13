package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.RoleTrans;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.RoleTransManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class RoleTransService {
    private final static Logger logger = LoggerFactory.getLogger(RoleTransService.class);

    @Autowired
    private RoleTransManager roleTransManager;
    
    public RoleTrans getById(Integer roleTransId) throws EntityNotExistentException {
        return roleTransManager.getById(roleTransId);
    }
    
    public PagedResponse<RoleTrans> getRoleTrans(RoleTrans roleTrans,   Paging paging) {
        return roleTransManager.getRoleTrans(roleTrans, paging);
    }
    
    public List<RoleTrans> findAll() {
        return roleTransManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public RoleTrans createRoleTrans(RoleTrans roleTrans) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        RoleTrans roleTransPersisted = roleTransManager.createRoleTrans(roleTrans);
        return getById(roleTransPersisted.getId());
    }
    
    public RoleTrans updateRoleTrans(Integer roleTransId,RoleTrans roleTrans) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        RoleTrans roleTransPersisted = roleTransManager.updateRoleTrans(roleTransId, roleTrans);
        return getById(roleTransPersisted.getId());
    }
    
    public void deleteRoleTrans(Integer roleTransId) throws EntityNotExistentException, BusinessLogicException {
        roleTransManager.deleteRoleTrans(roleTransId);
        
    }  
    
    public Boolean initialize() {
        try{
            createRoleTranss();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createRoleTranss() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


