package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Mascara;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.MascaraManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class MascaraService {
    private final static Logger logger = LoggerFactory.getLogger(MascaraService.class);

    @Autowired
    private MascaraManager mascaraManager;
    
    public Mascara getById(Integer mascaraId) throws EntityNotExistentException {
        return mascaraManager.getById(mascaraId);
    }
    
    public PagedResponse<Mascara> getMascara(Mascara mascara,   Paging paging) {
        return mascaraManager.getMascara(mascara, paging);
    }
    
    public List<Mascara> findAll() {
        return mascaraManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Mascara createMascara(Mascara mascara) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Mascara mascaraPersisted = mascaraManager.createMascara(mascara);
        return getById(mascaraPersisted.getId());
    }
    
    public Mascara updateMascara(Integer mascaraId,Mascara mascara) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Mascara mascaraPersisted = mascaraManager.updateMascara(mascaraId, mascara);
        return getById(mascaraPersisted.getId());
    }
    
    public void deleteMascara(Integer mascaraId) throws EntityNotExistentException, BusinessLogicException {
        mascaraManager.deleteMascara(mascaraId);
        
    }  
    
    public Boolean initialize() {
        try{
            createMascaras();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createMascaras() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


