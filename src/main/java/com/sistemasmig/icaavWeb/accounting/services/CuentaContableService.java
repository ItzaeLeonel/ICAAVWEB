package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.CuentaContableManager;
import com.sistemasmig.icaavWeb.accounting.models.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasContablesSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.InsertCuentaC;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasContables;
import com.sistemasmig.icaavWeb.accounting.models.enums.CuentaContableEstatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CuentaContableService {
    private final static Logger logger = LoggerFactory.getLogger(CuentaContableService.class);

    @Autowired
    private CuentaContableManager cuentaContableManager;
    
    public CuentaContable getById(Integer cuentaContableId) throws EntityNotExistentException {
        return cuentaContableManager.getById(cuentaContableId);
    }
    
    public PagedResponse<CuentaContable> getCuentaContable(CuentaContable cuentaContable,   Paging paging) {
        return cuentaContableManager.getCuentaContable(cuentaContable, paging);
    }
    
    public List<CuentaContable> findAll() {
        return cuentaContableManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public CuentaContable createCuentaContable(CuentaContable cuentaContable) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        CuentaContable cuentaContablePersisted = cuentaContableManager.createCuentaContable(cuentaContable);
        return getById(cuentaContablePersisted.getId());
    }
    
    public CuentaContable updateCuentaContable(Integer cuentaContableId,CuentaContable cuentaContable) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        CuentaContable cuentaContablePersisted = cuentaContableManager.updateCuentaContable(cuentaContableId, cuentaContable);
        return getById(cuentaContablePersisted.getId());
    }
    
    public void deleteCuentaContable(Integer cuentaContableId) throws EntityNotExistentException, BusinessLogicException {
        cuentaContableManager.deleteCuentaContable(cuentaContableId);
        
    }  
    
    public Boolean initialize() {
        try{
            createCuentaContables();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createCuentaContables() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
    
    public List <ListCuentasContables> getCuentasContablesSP(Integer pr_id_grupo_empresa) throws Exception, EntityNotExistentException {
    return cuentaContableManager.getCuentasContablesSP(pr_id_grupo_empresa);
    }
    
    public InsertCuentaC createSp(Integer pr_id_empresa, Integer pr_id_cuenta_sat,Integer pr_anio, Integer pr_mes, String pr_numero_cuenta,String pr_tipo_cuenta,String pr_nombre, Double pr_saldo_inicial, Double pr_saldo_final, Double pr_cargos, Double pr_abonos, Double pr_cargos_acumulado, Double pr_abonos_acumulados,Integer pr_cuenta_x_pagar, CuentaContableEstatusEnum pr_estatus,String pr_numero_complementaria, Integer pr_cuenta_complementaria) throws Exception, EntityNotExistentException {
        System.out.println(pr_estatus);
    return cuentaContableManager.createByCuentaContableSP(pr_id_empresa, pr_id_cuenta_sat, pr_anio, pr_mes, pr_numero_cuenta, pr_tipo_cuenta, pr_nombre, pr_saldo_inicial, pr_saldo_final, pr_cargos, pr_abonos, pr_cargos_acumulado, pr_abonos_acumulados, pr_cuenta_x_pagar, pr_estatus, pr_numero_complementaria, pr_cuenta_complementaria);
         
    }
    
    public List<CuentaContable> findDigitoCuentacontable(CuentaContable cuentaContable) {
    	List<CuentaContable> cuentasFiltradas = new ArrayList<>();
    	
    	if (cuentaContable.getNumeroCuenta() != null) {
    		cuentasFiltradas = this.findAll().stream()
                    .filter(c -> c.getNumeroCuenta().charAt(0) == cuentaContable.getNumeroCuenta().charAt(0))
                    .collect(Collectors.toList());
    	}
    	
    	if (cuentaContable.getTipoCuenta() != null) {
    		cuentasFiltradas = this.findAll().stream()
                    .filter(c -> c.getTipoCuenta().equals(cuentaContable.getTipoCuenta()))
                    .collect(Collectors.toList());
    	}
    	return cuentasFiltradas;
    }

}

