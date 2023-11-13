package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.CuentasContableManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CuentasContableService {
    private final static Logger logger = LoggerFactory.getLogger(CuentasContableService.class);

    @Autowired
    private CuentasContableManager cuentasContableManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public CuentaContable getById(Integer id) throws EntityNotExistentException {
    	CuentaContable cuentasContable = cuentasContableManager.getById(id);
		if (cuentasContable != null && !cuentasContable.getBorrado()) {
			return cuentasContable;
		}
		throw new EntityNotExistentException(CuentaContable.class, id.toString());
    }
    
    public PagedResponse<CuentaContable> getCuentaContable(CuentaContable cuentaContable,   Paging paging) {
        return cuentasContableManager.getCuentaContable(cuentaContable, paging);
    }
    
    public List<CuentaContable> findAll() {
        return cuentasContableManager.findAll();
    }
    
    public Map<String, Object> save(CuentaContable entity) {
		Map<String, Object> result = cuentasContableManager.spConCuentasContablesI(entity);
		return result;
	}

	public Map<String, Object> update(Integer id, CuentaContable entity){
		entity.setId(id);
		Map<String, Object> result = cuentasContableManager.spConCuentasContablesU(entity);
		return result;
	}

	public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
		TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
		CuentaContable cuentaContable = getById(id);
		cuentaContable.setIdUsuarioMod(usuario.getId());
		cuentaContable.setBorrado(Boolean.TRUE);
		cuentasContableManager.delete(cuentaContable);

	}
    
    public List<CuentaContable> findDigitoCuentacontable(CuentaContable entity) {
    	List<CuentaContable> cuentaContables = new ArrayList<>();
    	
    	if (entity.getNumeroCuenta() != null) {
    		cuentaContables = this.findAll().stream()
                    .filter(c -> c.getNumeroCuenta().charAt(0) == entity.getNumeroCuenta().charAt(0))
                    .collect(Collectors.toList());
    	}
    	
    	if (entity.getTipoCuenta() != null) {
    		cuentaContables = this.findAll().stream()
                    .filter(c -> c.getTipoCuenta().equals(entity.getTipoCuenta()))
                    .collect(Collectors.toList());
    	}
    	return cuentaContables;
    }

}

