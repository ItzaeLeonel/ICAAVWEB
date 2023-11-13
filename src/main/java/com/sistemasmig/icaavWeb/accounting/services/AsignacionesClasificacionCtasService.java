/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionClasificacionCtas;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;

import com.sistemasmig.icaavWeb.accounting.managers.AsignacionesClasificacionCtasManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author Julio
 */

@Component
public class AsignacionesClasificacionCtasService {
	// private final static Logger logger =
	// LoggerFactory.getLogger(AsignacionClasificacionCtasService.class);

	@Autowired
	private AsignacionesClasificacionCtasManager asignacionesClasificacionCtasManager;
	
	@Autowired
	private HttpServletRequest request;


	public AsignacionClasificacionCtas getById(Integer id)
			throws EntityNotExistentException {
		AsignacionClasificacionCtas asignacionClasificacionCtas = asignacionesClasificacionCtasManager
				.getById(id);
		if (asignacionClasificacionCtas != null && !asignacionClasificacionCtas.getBorrado()) {
			return asignacionClasificacionCtas;
		}
		throw new EntityNotExistentException(AsignacionClasificacionCtas.class, id.toString());
	}

	public PagedResponse<AsignacionClasificacionCtas> getAsignacionClasificacionCtas(
			AsignacionClasificacionCtas entity, Paging paging) {
		return asignacionesClasificacionCtasManager.getAsignacionClasificacionCtas(entity, paging);
	}

	public List<AsignacionClasificacionCtas> findAll() {
		return asignacionesClasificacionCtasManager.findAll();
	}

	public AsignacionClasificacionCtas save(AsignacionClasificacionCtas entity) {
		TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
		entity.setIdUsuarioAlt(usuario.getId());
		entity.setBorrado(Boolean.FALSE);
		return asignacionesClasificacionCtasManager.save(entity);
	}

	public AsignacionClasificacionCtas update(Integer id,
			AsignacionClasificacionCtas entity) throws EntityNotExistentException {

		AsignacionClasificacionCtas asignacionClasificacionCtas = getById(id);
		if (asignacionClasificacionCtas != null) {
			TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
			Utils.copyPropertiesIgnoringAndNull(entity, asignacionClasificacionCtas, Constantes.COPY_EXEP_ID);
			asignacionClasificacionCtas.setIdUsuarioMod(usuario.getId());
			return asignacionesClasificacionCtasManager.update(asignacionClasificacionCtas);
		} else {
			throw new EntityNotExistentException(AsignacionClasificacionCtas.class,
					id.toString());
		}
	}

	public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
		TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
		AsignacionClasificacionCtas asignacionClasificacionCtas = getById(id);
		asignacionClasificacionCtas.setIdUsuarioMod(usuario.getId());
		asignacionClasificacionCtas.setBorrado(Boolean.TRUE);
		asignacionesClasificacionCtasManager.delete(asignacionClasificacionCtas);

	}

}
