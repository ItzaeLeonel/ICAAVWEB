package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ConfigEmails;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.ConfigEmailsManager;
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
public class ConfigEmailsService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigEmailsService.class);

    @Autowired
    private ConfigEmailsManager configEmailsManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public ConfigEmails getById(Integer id) throws EntityNotExistentException {
    	ConfigEmails configEmails = configEmailsManager.getById(id);
        if (configEmails!=null && !configEmails.getBorrado()) {
            return configEmails;
        }
        throw new EntityNotExistentException(ConfigEmails.class,id.toString());
    }
    
    public PagedResponse<ConfigEmails> getConfigEmails(ConfigEmails configEmails,   Paging paging) {
        return configEmailsManager.getConfigEmails(configEmails, paging);
    }
    
    public List<ConfigEmails> findAll() {
        return configEmailsManager.findAll();
    }
    
    public ConfigEmails save(ConfigEmails entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return configEmailsManager.save(entity);
    }
    
    public ConfigEmails update(Integer id,ConfigEmails entity) throws EntityNotExistentException{
    	ConfigEmails configEmails = getById(id);
    	
        if (configEmails != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, configEmails, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	configEmails.setIdUsuarioMod(usuario.getId());
			return configEmailsManager.update(configEmails);
        } else {
            throw new EntityNotExistentException(ConfigEmails.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	ConfigEmails configEmails = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	configEmails.setIdUsuarioMod(usuario.getId());
    	configEmails.setBorrado(Boolean.TRUE);
    	configEmailsManager.delete(configEmails);
        
    }  
}


