package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.ConfigMonedaManager;
import com.sistemasmig.icaavWeb.accounting.models.ConfigMoneda;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ConfigMonedaService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigMonedaService.class);

    @Autowired
    private ConfigMonedaManager configMonedaManager;
    
    public ConfigMoneda getById(Integer configMonedaId) throws EntityNotExistentException {
        return configMonedaManager.getById(configMonedaId);
    }
    
    public PagedResponse<ConfigMoneda> getConfigMoneda(ConfigMoneda configMoneda,   Paging paging) {
        return configMonedaManager.getConfigMoneda(configMoneda, paging);
    }
    
    public List<ConfigMoneda> findAll() {
        return configMonedaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public ConfigMoneda createConfigMoneda(ConfigMoneda configMoneda) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        ConfigMoneda configMonedaPersisted = configMonedaManager.createConfigMoneda(configMoneda);
        return getById(configMonedaPersisted.getId());
    }
    
    public ConfigMoneda updateConfigMoneda(Integer configMonedaId,ConfigMoneda configMoneda) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        ConfigMoneda configMonedaPersisted = configMonedaManager.updateConfigMoneda(configMonedaId, configMoneda);
        return getById(configMonedaPersisted.getId());
    }
    
    public void deleteConfigMoneda(Integer configMonedaId) throws EntityNotExistentException, BusinessLogicException {
        configMonedaManager.deleteConfigMoneda(configMonedaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createConfigMonedas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createConfigMonedas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


