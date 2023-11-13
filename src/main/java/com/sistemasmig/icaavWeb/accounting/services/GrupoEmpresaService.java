package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.GrupoEmpresa;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.GrupoEmpresaManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class GrupoEmpresaService {
    private final static Logger logger = LoggerFactory.getLogger(GrupoEmpresaService.class);

    @Autowired
    private GrupoEmpresaManager grupoEmpresaManager;
    
    public GrupoEmpresa getById(Integer grupoEmpresaId) throws EntityNotExistentException {
        return grupoEmpresaManager.getById(grupoEmpresaId);
    }
    
    public PagedResponse<GrupoEmpresa> getGrupoEmpresa(GrupoEmpresa grupoEmpresa,   Paging paging) {
        return grupoEmpresaManager.getGrupoEmpresa(grupoEmpresa, paging);
    }
    
    public List<GrupoEmpresa> findAll() {
        return grupoEmpresaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public GrupoEmpresa createGrupoEmpresa(GrupoEmpresa grupoEmpresa) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        GrupoEmpresa grupoEmpresaPersisted = grupoEmpresaManager.createGrupoEmpresa(grupoEmpresa);
        return getById(grupoEmpresaPersisted.getId());
    }
    
    public GrupoEmpresa updateGrupoEmpresa(Integer grupoEmpresaId,GrupoEmpresa grupoEmpresa) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        GrupoEmpresa grupoEmpresaPersisted = grupoEmpresaManager.updateGrupoEmpresa(grupoEmpresaId, grupoEmpresa);
        return getById(grupoEmpresaPersisted.getId());
    }
    
    public void deleteGrupoEmpresa(Integer grupoEmpresaId) throws EntityNotExistentException, BusinessLogicException {
        grupoEmpresaManager.deleteGrupoEmpresa(grupoEmpresaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createGrupoEmpresas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createGrupoEmpresas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


