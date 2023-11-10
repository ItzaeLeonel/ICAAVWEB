package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.SatCuentaContableManager;
import com.sistemasmig.icaavWeb.accounting.models.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.models.SatCuentaContable;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasSatBG;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasSatCA;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SatCuentaContableService {
    private final static Logger logger = LoggerFactory.getLogger(SatCuentaContableService.class);

    @Autowired
    private SatCuentaContableManager satCuentaContableManager;
    
    public SatCuentaContable getById(Integer satCuentaContableId) throws EntityNotExistentException {
        return satCuentaContableManager.getById(satCuentaContableId);
    }
    
    public PagedResponse<SatCuentaContable> getSatCuentaContable(SatCuentaContable satCuentaContable,   Paging paging) {
        return satCuentaContableManager.getSatCuentaContable(satCuentaContable, paging);
    }
    
    public List<SatCuentaContable> findAll() {
        return satCuentaContableManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public SatCuentaContable createSatCuentaContable(SatCuentaContable satCuentaContable) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        SatCuentaContable satCuentaContablePersisted = satCuentaContableManager.createSatCuentaContable(satCuentaContable);
        return getById(satCuentaContablePersisted.getId());
    }
    
    public SatCuentaContable updateSatCuentaContable(Integer satCuentaContableId,SatCuentaContable satCuentaContable) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        SatCuentaContable satCuentaContablePersisted = satCuentaContableManager.updateSatCuentaContable(satCuentaContableId, satCuentaContable);
        return getById(satCuentaContablePersisted.getId());
    }
    
    public void deleteSatCuentaContable(Integer satCuentaContableId) throws EntityNotExistentException, BusinessLogicException {
        satCuentaContableManager.deleteSatCuentaContable(satCuentaContableId);
        
    }  
    
    public Boolean initialize() {
        try{
            createSatCuentaContables();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createSatCuentaContables() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    public List <ListCuentasSatCA> getCodAgrupadorSAT(String pr_id_codigo_agrupador) throws Exception, EntityNotExistentException {
    return satCuentaContableManager.getByCodAgrupadorSat(pr_id_codigo_agrupador);
    }
    
    public List <ListCuentasSatBG> getBusqGralSsat(String pr_consulta_gral) throws Exception, EntityNotExistentException {
    return satCuentaContableManager.getByBusqGralSat(pr_consulta_gral);
    }
    
    public List<SatCuentaContable> findDigitoCuentacontable(String identificarCuenta) {
    	List<SatCuentaContable> cuentasSatFiltradas = this.findAll().stream()
                .filter(c -> c.getCuentaSat().charAt(0) == identificarCuenta.charAt(0))
                .collect(Collectors.toList());
    	return cuentasSatFiltradas;
    }
}


