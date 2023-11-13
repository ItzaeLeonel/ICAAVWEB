package com.sistemasmig.icaavWeb.accounting.services;


import com.google.gson.Gson;
import com.sistemasmig.icaavWeb.accounting.Definitions;
import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Empresa;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.EmpresaManager;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EmpresaService {
    private final static Logger logger = LoggerFactory.getLogger(EmpresaService.class);

    @Autowired
    private EmpresaManager empresaManager;
    
    public Empresa getById(Integer empresaId) throws EntityNotExistentException {
        return empresaManager.getById(empresaId);
    }
    
    public PagedResponse<Empresa> getEmpresa(Empresa empresa,   Paging paging) {
        return empresaManager.getEmpresa(empresa, paging);
    }
    
    public List<Empresa> findAll() {
        return empresaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Empresa createEmpresa(Empresa empresa) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Empresa empresaPersisted = empresaManager.createEmpresa(empresa);
        return getById(empresaPersisted.getId());
    }
    
    public Empresa updateEmpresa(Integer empresaId,Empresa empresa) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Empresa empresaPersisted = empresaManager.updateEmpresa(empresaId, empresa);
        return getById(empresaPersisted.getId());
    }
    
    public void deleteEmpresa(Integer empresaId) throws EntityNotExistentException, BusinessLogicException {
        empresaManager.deleteEmpresa(empresaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createEmpresas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createEmpresas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


