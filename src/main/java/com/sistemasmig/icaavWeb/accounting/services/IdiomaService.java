package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Idioma;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.IdiomaManager;
import com.sistemasmig.icaavWeb.accounting.repositories.IdiomaRepository;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class IdiomaService {
    private final static Logger logger = LoggerFactory.getLogger(IdiomaService.class);

    @Autowired
    private IdiomaManager idiomaManager;
    
    public Idioma getById(Integer idiomaId) throws EntityNotExistentException {
        return idiomaManager.getById(idiomaId);
    }
    
    public PagedResponse<Idioma> getIdioma(Idioma idioma,   Paging paging) {
        return idiomaManager.getIdioma(idioma, paging);
    }
    
    public List<Idioma> findAll() {
        return idiomaManager.findAll();
    }
    
    
   // @Autowired
  //  private IdiomaRepository idiomaRepository;
    
    
    public Idioma findAllSp(String lenguajeIn) throws EntityNotExistentException{
        return idiomaManager.getByIdioma(lenguajeIn.toString());
    }
    
    
    
    
    
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Idioma createIdioma(Idioma idioma) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Idioma idiomaPersisted = idiomaManager.createIdioma(idioma);
        return getById(idiomaPersisted.getId());
    }
    
    public Idioma updateIdioma(Integer idiomaId,Idioma idioma) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Idioma idiomaPersisted = idiomaManager.updateIdioma(idiomaId, idioma);
        return getById(idiomaPersisted.getId());
    }
    
    public void deleteIdioma(Integer idiomaId) throws EntityNotExistentException, BusinessLogicException {
        idiomaManager.deleteIdioma(idiomaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createIdiomas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createIdiomas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


