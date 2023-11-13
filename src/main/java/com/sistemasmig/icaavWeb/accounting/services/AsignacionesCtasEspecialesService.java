/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionCtasEspeciales;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.managers.AsignacionesCtasEspecialesManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

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
public class AsignacionesCtasEspecialesService {

	private final static Logger logger = LoggerFactory.getLogger(CuentasEspecialesService.class);

	@Autowired
	private AsignacionesCtasEspecialesManager asignacionesCtasEspecialesManager;

	@Autowired
	private HttpServletRequest request;


	public AsignacionCtasEspeciales getById(Integer id) throws EntityNotExistentException {
		AsignacionCtasEspeciales asignacionCtasEspeciales = asignacionesCtasEspecialesManager.getById(id);
		if (asignacionCtasEspeciales != null && !asignacionCtasEspeciales.getBorrado()) {
			return asignacionCtasEspeciales;
		}
		throw new EntityNotExistentException(AsignacionCtasEspeciales.class, id.toString());
	}

	public PagedResponse<AsignacionCtasEspeciales> getAsignacionCtasEspeciales(AsignacionCtasEspeciales entity,
			Paging paging) {
		return asignacionesCtasEspecialesManager.getAsignacionCtasEspeciales(entity, paging);
	}

	public List<AsignacionCtasEspeciales> findAll() {
		return asignacionesCtasEspecialesManager.findAll();
	}

	public Map<String, Object> save(AsignacionCtasEspeciales entity) {
		Map<String, Object> result = asignacionesCtasEspecialesManager.spConAsignacionCtasEspecialesI(entity);
		return result;
	}

	public AsignacionCtasEspeciales update(Integer asignacionClasificacionCtasId, AsignacionCtasEspeciales entity)
			throws EntityNotExistentException {

		AsignacionCtasEspeciales asignacionCtasEspeciales = getById(asignacionClasificacionCtasId);
		if (asignacionCtasEspeciales != null) {
			TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
			Utils.copyPropertiesIgnoringAndNull(entity, asignacionCtasEspeciales, Constantes.COPY_EXEP_ID);
			asignacionCtasEspeciales.setIdUsuarioMod(usuario.getId());
			return asignacionesCtasEspecialesManager.update(asignacionCtasEspeciales);
		} else {
			throw new EntityNotExistentException(AsignacionCtasEspeciales.class,
					asignacionClasificacionCtasId.toString());
		}
	}

	public void delete(Integer id) throws EntityNotExistentException, BusinessLogicException {
		TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
		AsignacionCtasEspeciales asignacionCtasEspeciales = getById(id);
		asignacionCtasEspeciales.setIdUsuarioMod(usuario.getId());
		asignacionCtasEspeciales.setBorrado(Boolean.TRUE);
		asignacionesCtasEspecialesManager.delete(asignacionCtasEspeciales);

	}

	public List<AsignacionCtasEspeciales> findTipoCuentaEspecial(String tipoCuenta) {
		List<AsignacionCtasEspeciales> cuentasEspecialesFiltradas = this.findAll().stream()
				.filter(c -> c.getCtaEspecial().getTipoCuenta() == Integer.valueOf(tipoCuenta))
				.collect(Collectors.toList());

		return cuentasEspecialesFiltradas;
	}

}
