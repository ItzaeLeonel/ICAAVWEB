
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasEspecialesSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.DemoLista;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.CuentasEspecialesManager;
import com.sistemasmig.icaavWeb.accounting.models.CuentasEspeciales;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Julio
 */
@Component
public class CuentasEspecialesService {
    
    private final static Logger logger = LoggerFactory.getLogger(CuentasEspecialesService.class);

    @Autowired
    private CuentasEspecialesManager cuentasEspecialesManager;
    
    public CuentasEspeciales getById(Integer cuentasEspecialesId) throws EntityNotExistentException {
        return cuentasEspecialesManager.getById(cuentasEspecialesId);
    }
   
    
    public PagedResponse<CuentasEspeciales> getCuentasEspeciales(CuentasEspeciales cuentasEspeciales,   Paging paging) {
        return cuentasEspecialesManager.getCuentasEspeciales(cuentasEspeciales, paging);
    }
    
    public List<CuentasEspeciales> findAll() {
        return cuentasEspecialesManager.findAll();
    }
    
  
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public CuentasEspeciales createCuentasEspeciales(CuentasEspeciales cuentasEspeciales) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        CuentasEspeciales cuentasEspecialesPersisted = cuentasEspecialesManager.createCuentasEspeciales(cuentasEspeciales);
        return getById(cuentasEspecialesPersisted.getId());
    }
    

    
    
    public CuentasEspeciales updateCuentasEspeciales(Integer cuentasEspecialesId,CuentasEspeciales cuentasEspeciales) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        CuentasEspeciales cuentasEspecialesPersisted = cuentasEspecialesManager.updateCuentasEspeciales(cuentasEspecialesId, cuentasEspeciales);
        return getById(cuentasEspecialesPersisted.getId());
    }
    
    public void deleteCuentasEspeciales(Integer cuentasEspecialesId) throws EntityNotExistentException, BusinessLogicException {
        cuentasEspecialesManager.deleteCuentasEspeciales(cuentasEspecialesId);
        
    }
    
    
    public Boolean initialize() {
        try{
            createCuentasE();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createCuentasE() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
    public List <DemoLista> getCuentaEspecialSP(Integer pr_id_grupo_empresa, Integer pr_tipo_cuenta) throws Exception, EntityNotExistentException {
    return cuentasEspecialesManager.getByCuentaEspecialSP(pr_id_grupo_empresa, pr_tipo_cuenta);
    }
    
     
    
}
