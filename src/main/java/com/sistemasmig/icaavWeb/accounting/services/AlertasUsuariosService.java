package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AlertasUsuarios;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;

import com.sistemasmig.icaavWeb.accounting.managers.AlertasUsuariosManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AlertasUsuariosService {
   // private final static Logger logger = LoggerFactory.getLogger(AlertasUsuariosService.class);

    @Autowired
    private AlertasUsuariosManager alertasUsuariosManager;
    
    @Autowired
	private HttpServletRequest request;
    
    
    public AlertasUsuarios getById(Integer id) throws EntityNotExistentException {
    	AlertasUsuarios alertasUsuarios = alertasUsuariosManager.getById(id);
        if (alertasUsuarios!=null && !alertasUsuarios.getBorrado()) {
            return alertasUsuarios;
        }
        throw new EntityNotExistentException(AlertasUsuarios.class,id.toString());
    }
    
    public PagedResponse<AlertasUsuarios> getAlertasUsuarios(AlertasUsuarios entity,   Paging paging) {
        return alertasUsuariosManager.getAlertasUsuarios(entity, paging);
    }
    
    public List<AlertasUsuarios> findAll() {
        return alertasUsuariosManager.findAll();
    }
    
    public AlertasUsuarios save(AlertasUsuarios entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return alertasUsuariosManager.save(entity);
    }
    
    public AlertasUsuarios update(Integer id,AlertasUsuarios entity) throws EntityNotExistentException{
    	AlertasUsuarios alertasUsuarios = getById(id);
    	
        if (alertasUsuarios != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, alertasUsuarios, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	alertasUsuarios.setIdUsuarioMod(usuario.getId());
			return alertasUsuariosManager.update(alertasUsuarios);
        } else {
            throw new EntityNotExistentException(AlertasUsuarios.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	AlertasUsuarios alertasUsuarios = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	alertasUsuarios.setIdUsuarioMod(usuario.getId());
        alertasUsuarios.setBorrado(Boolean.TRUE);
        alertasUsuariosManager.delete(alertasUsuarios);
        
    }  
}


