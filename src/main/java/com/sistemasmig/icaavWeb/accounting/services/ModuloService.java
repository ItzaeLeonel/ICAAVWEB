package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.ModuloManager;
import com.sistemasmig.icaavWeb.accounting.models.Modulo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ModuloService {
    private final static Logger logger = LoggerFactory.getLogger(ModuloService.class);

    @Autowired
    private ModuloManager moduloManager;
    
    public Modulo getById(Integer moduloId) throws EntityNotExistentException {
        return moduloManager.getById(moduloId);
    }
    
    public PagedResponse<Modulo> getModulo(Modulo modulo,   Paging paging) {
        return moduloManager.getModulo(modulo, paging);
    }
    
    public List<Modulo> findAll() {
        return moduloManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Modulo createModulo(Modulo modulo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Modulo moduloPersisted = moduloManager.createModulo(modulo);
        return getById(moduloPersisted.getId());
    }
    
    public Modulo updateModulo(Integer moduloId,Modulo modulo) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Modulo moduloPersisted = moduloManager.updateModulo(moduloId, modulo);
        return getById(moduloPersisted.getId());
    }
    
    public void deleteModulo(Integer moduloId) throws EntityNotExistentException, BusinessLogicException {
        moduloManager.deleteModulo(moduloId);
        
    }  
    
    public Boolean initialize() {
        try{
            createModulos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createModulos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


