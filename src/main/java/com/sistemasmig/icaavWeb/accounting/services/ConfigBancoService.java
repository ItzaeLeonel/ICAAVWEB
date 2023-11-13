package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ConfigBanco;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.ConfigBancoManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ConfigBancoService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigBancoService.class);

    @Autowired
    private ConfigBancoManager configBancoManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public ConfigBanco getById(Integer id) throws EntityNotExistentException {
    	ConfigBanco configBanco = configBancoManager.getById(id);
        if (configBanco!=null && !configBanco.getBorrado()) {
            return configBanco;
        }
        throw new EntityNotExistentException(ConfigBanco.class,id.toString());
    }
    
    public PagedResponse<ConfigBanco> getConfigBanco(ConfigBanco configBanco,   Paging paging) {
        return configBancoManager.getConfigBanco(configBanco, paging);
    }
    
    public List<ConfigBanco> findAll() {
        return configBancoManager.findAll();
    }
    
    public ConfigBanco save(ConfigBanco entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return configBancoManager.save(entity);
    }
    
    public ConfigBanco update(Integer id,ConfigBanco entity) throws EntityNotExistentException{
    	ConfigBanco configBanco = getById(id);
    	
        if (configBanco != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, configBanco, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	configBanco.setIdUsuarioMod(usuario.getId());
			return configBancoManager.update(configBanco);
        } else {
            throw new EntityNotExistentException(ConfigBanco.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	ConfigBanco configBanco = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	configBanco.setIdUsuarioMod(usuario.getId());
    	configBanco.setBorrado(Boolean.TRUE);
    	configBancoManager.delete(configBanco);
        
    }  
}


