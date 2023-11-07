package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.GrupoManager;
import com.sistemasmig.icaavWeb.accounting.models.Grupo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class GrupoService {
    private final static Logger logger = LoggerFactory.getLogger(GrupoService.class);

    @Autowired
    private GrupoManager grupoManager;
    
    public Grupo getById(Integer grupoId) throws EntityNotExistentException {
        return grupoManager.getById(grupoId);
    }
    
    public PagedResponse<Grupo> getGrupo(Grupo grupo,   Paging paging) {
        return grupoManager.getGrupo(grupo, paging);
    }
    
    public List<Grupo> findAll() {
        return grupoManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Grupo createGrupo(Grupo grupo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Grupo grupoPersisted = grupoManager.createGrupo(grupo);
        return getById(grupoPersisted.getId());
    }
    
    public Grupo updateGrupo(Integer grupoId,Grupo grupo) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Grupo grupoPersisted = grupoManager.updateGrupo(grupoId, grupo);
        return getById(grupoPersisted.getId());
    }
    
    public void deleteGrupo(Integer grupoId) throws EntityNotExistentException, BusinessLogicException {
        grupoManager.deleteGrupo(grupoId);
        
    }  
    
    public Boolean initialize() {
        try{
            createGrupos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createGrupos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


