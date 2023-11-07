package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.ConfigEmailsManager;
import com.sistemasmig.icaavWeb.accounting.models.ConfigEmails;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ConfigEmailsService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigEmailsService.class);

    @Autowired
    private ConfigEmailsManager configEmailsManager;
    
    public ConfigEmails getById(Integer configEmailsId) throws EntityNotExistentException {
        return configEmailsManager.getById(configEmailsId);
    }
    
    public PagedResponse<ConfigEmails> getConfigEmails(ConfigEmails configEmails,   Paging paging) {
        return configEmailsManager.getConfigEmails(configEmails, paging);
    }
    
    public List<ConfigEmails> findAll() {
        return configEmailsManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public ConfigEmails createConfigEmails(ConfigEmails configEmails) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        ConfigEmails configEmailsPersisted = configEmailsManager.createConfigEmails(configEmails);
        return getById(configEmailsPersisted.getId());
    }
    
    public ConfigEmails updateConfigEmails(Integer configEmailsId,ConfigEmails configEmails) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        ConfigEmails configEmailsPersisted = configEmailsManager.updateConfigEmails(configEmailsId, configEmails);
        return getById(configEmailsPersisted.getId());
    }
    
    public void deleteConfigEmails(Integer configEmailsId) throws EntityNotExistentException, BusinessLogicException {
        configEmailsManager.deleteConfigEmails(configEmailsId);
        
    }  
    
    public Boolean initialize() {
        try{
            createConfigEmailss();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createConfigEmailss() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


