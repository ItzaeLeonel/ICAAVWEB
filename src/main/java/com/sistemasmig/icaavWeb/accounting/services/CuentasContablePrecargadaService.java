/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ConfigMoneda;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContablePrecargada;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.CuentasContablePrecargadaManager;
import com.sistemasmig.icaavWeb.accounting.model.CCPrecargadasSP;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
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
public class CuentasContablePrecargadaService {
     private final static Logger logger = LoggerFactory.getLogger(CuentasContablePrecargadaService.class);

    @Autowired
    private CuentasContablePrecargadaManager cuentaContablePrecargadaManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public CuentaContablePrecargada getById(Integer id) throws EntityNotExistentException {
    	CuentaContablePrecargada cuentaContablePrecargada = cuentaContablePrecargadaManager.getById(id);
		if (cuentaContablePrecargada != null && !cuentaContablePrecargada.getBorrado()) {
			return cuentaContablePrecargada;
		}
		throw new EntityNotExistentException(CuentaContablePrecargada.class, id.toString());
    }
    
    public PagedResponse<CuentaContablePrecargada> getCuentaContablePrecargada(CuentaContablePrecargada cuentaContablePrecargada,   Paging paging) {
        return cuentaContablePrecargadaManager.getCuentaContablePrecargada(cuentaContablePrecargada, paging);
    }
    
    public List<CuentaContablePrecargada> findAll() {
        return cuentaContablePrecargadaManager.findAll();
    }
    
    public CuentaContablePrecargada save(CuentaContablePrecargada entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return cuentaContablePrecargadaManager.save(entity);
    }
    
    public CuentaContablePrecargada update(Integer id,CuentaContablePrecargada entity) throws EntityNotExistentException{
    	CuentaContablePrecargada cuentaContablePrecargada = getById(id);
    	
        if (cuentaContablePrecargada != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, cuentaContablePrecargada, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	cuentaContablePrecargada.setIdUsuarioMod(usuario.getId());
			return cuentaContablePrecargadaManager.update(cuentaContablePrecargada);
        } else {
            throw new EntityNotExistentException(ConfigMoneda.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	CuentaContablePrecargada cuentaContablePrecargada = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	cuentaContablePrecargada.setIdUsuarioMod(usuario.getId());
    	cuentaContablePrecargada.setBorrado(Boolean.TRUE);
    	cuentaContablePrecargadaManager.delete(cuentaContablePrecargada);
        
    }  
    
    
    
}
