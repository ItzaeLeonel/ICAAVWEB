package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.TmpMascara;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.TmpMascaraManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TmpMascaraService {
    private final static Logger logger = LoggerFactory.getLogger(TmpMascaraService.class);

    @Autowired
    private TmpMascaraManager tmpMascaraManager;
    
    public TmpMascara getById(Integer tmpMascaraId) throws EntityNotExistentException {
        return tmpMascaraManager.getById(tmpMascaraId);
    }
    
    public PagedResponse<TmpMascara> getTmpMascara(TmpMascara tmpMascara,   Paging paging) {
        return tmpMascaraManager.getTmpMascara(tmpMascara, paging);
    }
    
    public List<TmpMascara> findAll() {
        return tmpMascaraManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public TmpMascara createTmpMascara(TmpMascara tmpMascara) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        TmpMascara tmpMascaraPersisted = tmpMascaraManager.createTmpMascara(tmpMascara);
        return getById(tmpMascaraPersisted.getId());
    }
    
    public TmpMascara updateTmpMascara(Integer tmpMascaraId,TmpMascara tmpMascara) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        TmpMascara tmpMascaraPersisted = tmpMascaraManager.updateTmpMascara(tmpMascaraId, tmpMascara);
        return getById(tmpMascaraPersisted.getId());
    }
    
    public void deleteTmpMascara(Integer tmpMascaraId) throws EntityNotExistentException, BusinessLogicException {
        tmpMascaraManager.deleteTmpMascara(tmpMascaraId);
        
    }  
    
    public Boolean initialize() {
        try{
            createTmpMascaras();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createTmpMascaras() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


