/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.PersonalizacionCuenta;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.PersonalizacionCuentaManager;
import com.sistemasmig.icaavWeb.accounting.managers.PersonalizacionCuentaManager;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Julio
 */
@Component
public class PersonalizacionCuentaService {
    
     private final static Logger logger = LoggerFactory.getLogger(PersonalizacionCuentaService.class);

    @Autowired
    private PersonalizacionCuentaManager personalizacionCuentaManager;
    
    public PersonalizacionCuenta getById(Integer CuentaCPId) throws EntityNotExistentException {
        return personalizacionCuentaManager.getById(CuentaCPId);
    }
    
    public PagedResponse<PersonalizacionCuenta> getPersonalizacionCuenta(PersonalizacionCuenta personalizacionCuenta,   Paging paging) {
        return personalizacionCuentaManager.getPersonalizacionCuenta(personalizacionCuenta, paging);
    }
    
    public List<PersonalizacionCuenta> findAll() {
        return personalizacionCuentaManager.findAll();
    }
    
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public PersonalizacionCuenta createPersonalizacionCuenta(PersonalizacionCuenta personalizacionCuenta) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        PersonalizacionCuenta personalizacionCuentaPersisted = personalizacionCuentaManager.createPersonalizacionCuenta(personalizacionCuenta);
        return getById(personalizacionCuentaPersisted.getId());
    }
    
    public PersonalizacionCuenta updatePersonalizacionCuenta(Integer personalizacionCuentaId,PersonalizacionCuenta personalizacionCuenta) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        PersonalizacionCuenta personalizacionCuentaPersisted = personalizacionCuentaManager.updatePersonalizacionCuenta(personalizacionCuentaId, personalizacionCuenta);
        return getById(personalizacionCuentaPersisted.getId());
    }
    
    public void deletePersonalizacionCuenta(Integer personalizacionCuentaId) throws EntityNotExistentException, BusinessLogicException {
        personalizacionCuentaManager.deletePersonalizacionCuenta(personalizacionCuentaId);
        
    }
    
    
    public Boolean initialize() {
        try{
            createPersonalizacionCuentas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createPersonalizacionCuentas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }

}
