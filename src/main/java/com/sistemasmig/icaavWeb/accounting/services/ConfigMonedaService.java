package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ConfigMoneda;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.ConfigMonedaManager;
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
public class ConfigMonedaService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigMonedaService.class);

    @Autowired
    private ConfigMonedaManager configMonedaManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public ConfigMoneda getById(Integer id) throws EntityNotExistentException {
    	ConfigMoneda configMoneda = configMonedaManager.getById(id);
        if (configMoneda!=null && !configMoneda.getBorrado()) {
            return configMoneda;
        }
        throw new EntityNotExistentException(ConfigMoneda.class,id.toString());
    }
    
    public PagedResponse<ConfigMoneda> getConfigMoneda(ConfigMoneda configMoneda,   Paging paging) {
        return configMonedaManager.getConfigMoneda(configMoneda, paging);
    }
    
    public List<ConfigMoneda> findAll() {
        return configMonedaManager.findAll();
    }
    
    public ConfigMoneda save(ConfigMoneda entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return configMonedaManager.save(entity);
    }
    
    public ConfigMoneda update(Integer id,ConfigMoneda entity) throws EntityNotExistentException{
    	ConfigMoneda configMoneda = getById(id);
    	
        if (configMoneda != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, configMoneda, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	configMoneda.setIdUsuarioMod(usuario.getId());
			return configMonedaManager.update(configMoneda);
        } else {
            throw new EntityNotExistentException(ConfigMoneda.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	ConfigMoneda configMoneda = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	configMoneda.setIdUsuarioMod(usuario.getId());
    	configMoneda.setBorrado(Boolean.TRUE);
    	configMonedaManager.delete(configMoneda);
        
    }  
}


