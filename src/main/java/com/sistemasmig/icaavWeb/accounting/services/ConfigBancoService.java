package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.ConfigBancoManager;
import com.sistemasmig.icaavWeb.accounting.models.ConfigBanco;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ConfigBancoService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigBancoService.class);

    @Autowired
    private ConfigBancoManager configBancoManager;
    
    public ConfigBanco getById(Integer configBancoId) throws EntityNotExistentException {
        return configBancoManager.getById(configBancoId);
    }
    
    public PagedResponse<ConfigBanco> getConfigBanco(ConfigBanco configBanco,   Paging paging) {
        return configBancoManager.getConfigBanco(configBanco, paging);
    }
    
    public List<ConfigBanco> findAll() {
        return configBancoManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public ConfigBanco createConfigBanco(ConfigBanco configBanco) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        ConfigBanco configBancoPersisted = configBancoManager.createConfigBanco(configBanco);
        return getById(configBancoPersisted.getId());
    }
    
    public ConfigBanco updateConfigBanco(Integer configBancoId,ConfigBanco configBanco) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        ConfigBanco configBancoPersisted = configBancoManager.updateConfigBanco(configBancoId, configBanco);
        return getById(configBancoPersisted.getId());
    }
    
    public void deleteConfigBanco(Integer configBancoId) throws EntityNotExistentException, BusinessLogicException {
        configBancoManager.deleteConfigBanco(configBancoId);
        
    }  
    
    public Boolean initialize() {
        try{
            createConfigBancos();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createConfigBancos() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


