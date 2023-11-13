/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionTipoPolizas;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.AsignacionesTipoPolizasManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julio
 */
@Component
public class AsignacionesTipoPolizasService {
    private final static Logger logger = LoggerFactory.getLogger(AlertasService.class);

    @Autowired
    private AsignacionesTipoPolizasManager asignacionesTipoPolizasManager;
    
    @Autowired
	private HttpServletRequest request;
    
    public AsignacionTipoPolizas getById(Integer id) throws EntityNotExistentException {
    	AsignacionTipoPolizas asignacionTipoPolizas = asignacionesTipoPolizasManager.getById(id);
        if (asignacionTipoPolizas!=null && !asignacionTipoPolizas.getBorrado()) {
            return asignacionTipoPolizas;
        }
        throw new EntityNotExistentException(AsignacionTipoPolizas.class,id.toString());
    }
    
    public PagedResponse<AsignacionTipoPolizas> getAsignacionTipoPolizas(AsignacionTipoPolizas asignacionTipoPolizas,   Paging paging) {
        return asignacionesTipoPolizasManager.getAsignacionTipoPolizas(asignacionTipoPolizas, paging);
    }
    
    public List<AsignacionTipoPolizas> findAll() {
        return asignacionesTipoPolizasManager.findAll();
    }
    
    public AsignacionTipoPolizas save(AsignacionTipoPolizas entity){
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	entity.setIdUsuarioAlt(usuario.getId());
    	entity.setBorrado(Boolean.FALSE);
        return asignacionesTipoPolizasManager.save(entity);
    }
    
    public AsignacionTipoPolizas update(Integer id,AsignacionTipoPolizas entity) throws EntityNotExistentException{
    	AsignacionTipoPolizas asignacionTipoPolizas = getById(id);
    	
        if (asignacionTipoPolizas != null) {
        	Utils.copyPropertiesIgnoringAndNull(entity, asignacionTipoPolizas, Constantes.COPY_EXEP_ID);
        	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
        	asignacionTipoPolizas.setIdUsuarioMod(usuario.getId());
			return asignacionesTipoPolizasManager.update(asignacionTipoPolizas);
        } else {
            throw new EntityNotExistentException(AsignacionTipoPolizas.class,id.toString());
        }
    }
    
    public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
    	AsignacionTipoPolizas asignacionTipoPolizas = getById(id);
    	TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
    	asignacionTipoPolizas.setIdUsuarioMod(usuario.getId());
    	asignacionTipoPolizas.setBorrado(Boolean.TRUE);
        asignacionesTipoPolizasManager.delete(asignacionTipoPolizas);
        
    }  

    
}
