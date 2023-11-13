package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ZonaHoraria;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.ZonaHorariaManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ZonaHorariaService {
    private final static Logger logger = LoggerFactory.getLogger(ZonaHorariaService.class);

    @Autowired
    private ZonaHorariaManager zonaHorariaManager;
    
    public ZonaHoraria getById(Integer zonaHorariaId) throws EntityNotExistentException {
        return zonaHorariaManager.getById(zonaHorariaId);
    }
    
    public PagedResponse<ZonaHoraria> getZonaHoraria(ZonaHoraria zonaHoraria,   Paging paging) {
        return zonaHorariaManager.getZonaHoraria(zonaHoraria, paging);
    }
    
    public List<ZonaHoraria> findAll() {
        return zonaHorariaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public ZonaHoraria createZonaHoraria(ZonaHoraria zonaHoraria) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        ZonaHoraria zonaHorariaPersisted = zonaHorariaManager.createZonaHoraria(zonaHoraria);
        return getById(zonaHorariaPersisted.getId());
    }
    
    public ZonaHoraria updateZonaHoraria(Integer zonaHorariaId,ZonaHoraria zonaHoraria) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        ZonaHoraria zonaHorariaPersisted = zonaHorariaManager.updateZonaHoraria(zonaHorariaId, zonaHoraria);
        return getById(zonaHorariaPersisted.getId());
    }
    
    public void deleteZonaHoraria(Integer zonaHorariaId) throws EntityNotExistentException, BusinessLogicException {
        zonaHorariaManager.deleteZonaHoraria(zonaHorariaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createZonaHorarias();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createZonaHorarias() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


