package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.SubmoduloManager;
import com.sistemasmig.icaavWeb.accounting.models.Submodulo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SubmoduloService {
    private final static Logger logger = LoggerFactory.getLogger(SubmoduloService.class);

    @Autowired
    private SubmoduloManager submoduloManager;
    
    public Submodulo getById(Integer submoduloId) throws EntityNotExistentException {
        return submoduloManager.getById(submoduloId);
    }
    
    public PagedResponse<Submodulo> getSubmodulo(Submodulo submodulo,   Paging paging) {
        return submoduloManager.getSubmodulo(submodulo, paging);
    }
    
    public List<Submodulo> findAll() {
        return submoduloManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Submodulo createSubmodulo(Submodulo submodulo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Submodulo submoduloPersisted = submoduloManager.createSubmodulo(submodulo);
        return getById(submoduloPersisted.getId());
    }
    
    public Submodulo updateSubmodulo(Integer submoduloId,Submodulo submodulo) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Submodulo submoduloPersisted = submoduloManager.updateSubmodulo(submoduloId, submodulo);
        return getById(submoduloPersisted.getId());
    }
    
    public void deleteSubmodulo(Integer submoduloId) throws EntityNotExistentException, BusinessLogicException {
        submoduloManager.deleteSubmodulo(submoduloId);
        
    }  
    
    public Boolean initialize() {
        try{
            createSubmodulos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createSubmodulos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


