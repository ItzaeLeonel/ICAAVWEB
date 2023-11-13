/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionTipoPolizas;
import com.sistemasmig.icaavWeb.accounting.entity.ClasificacionCtas;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;

import com.sistemasmig.icaavWeb.accounting.managers.ClasificacionesCtasManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author Julio
 */
@Component
public class ClasificacionesCtasService {
    private final static Logger logger = LoggerFactory.getLogger(ClasificacionesCtasService.class);

    @Autowired
    private ClasificacionesCtasManager clasificacionesCtasManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public ClasificacionCtas getById(Integer id) throws EntityNotExistentException {
    	ClasificacionCtas clasificacionCtas = clasificacionesCtasManager.getById(id);
        if (clasificacionCtas!=null && !clasificacionCtas.getBorrado()) {
            return clasificacionCtas;
        }
        throw new EntityNotExistentException(ClasificacionCtas.class,id.toString());
    }
    
    public PagedResponse<ClasificacionCtas> getClasificacionCtas(ClasificacionCtas entity,   Paging paging) {
        return clasificacionesCtasManager.getClasificacionCtas(entity, paging);
    }
    
    public List<ClasificacionCtas> findAll() {
        return clasificacionesCtasManager.findAll();
    }
    
    public ClasificacionCtas save(ClasificacionCtas entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return clasificacionesCtasManager.save(entity);
    }
    
    public ClasificacionCtas update(Integer id,ClasificacionCtas entity) throws EntityNotExistentException{
    	ClasificacionCtas clasificacionCtas = getById(id);
    	
        if (clasificacionCtas != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, clasificacionCtas, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	clasificacionCtas.setIdUsuarioMod(usuario.getId());
			return clasificacionesCtasManager.update(clasificacionCtas);
        } else {
            throw new EntityNotExistentException(ClasificacionCtas.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	ClasificacionCtas clasificacionCtas = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	clasificacionCtas.setIdUsuarioMod(usuario.getId());
    	clasificacionCtas.setBorrado(Boolean.TRUE);
    	clasificacionesCtasManager.delete(clasificacionCtas);
        
    }  
    
}
