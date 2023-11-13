package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Alerta;
import com.sistemasmig.icaavWeb.accounting.entity.Usuario;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.AlertasManager;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;
import com.sistemasmig.icaavWeb.accounting.utils.Constantes;
import com.sistemasmig.icaavWeb.accounting.utils.Utils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlertasService {
	private final static Logger logger = LoggerFactory.getLogger(AlertasService.class);

	@Autowired
	private AlertasManager alertasManager;

	@Autowired
	private HttpServletRequest request;

	public Alerta getById(Integer id) throws EntityNotExistentException {
		Alerta alerta = alertasManager.getById(id);
		if (alerta != null && !alerta.getBorrado()) {
			return alerta;
		}
		throw new EntityNotExistentException(Alerta.class, id.toString());
	}

	public PagedResponse<Alerta> getAlerta(Alerta entity, Paging paging) {
		return alertasManager.getAlerta(entity, paging);
	}

	public List<Alerta> findAll() {
		return alertasManager.findAll();
	}

	public Alerta save(Alerta alerta) {
		TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
		alerta.setIdUsuarioAlt(usuario.getId());
		alerta.setBorrado(Boolean.FALSE);
		return alertasManager.save(alerta);
	}

	public Alerta update(Integer id, Alerta entity) throws EntityNotExistentException {
		Alerta alerta = getById(id);
		if (alerta != null) {
			TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
			Utils.copyPropertiesIgnoringAndNull(entity, alerta, Constantes.COPY_EXEP_ID);
			alerta.setIdUsuarioMod(usuario.getId());
			return alertasManager.update(alerta);
		} else {
			throw new EntityNotExistentException(Alerta.class, id.toString());
		}

	}

	public void delete(Integer id) throws EntityNotExistentException {
		Alerta alerta = getById(id);
		TokenDetalles usuario = (TokenDetalles) request.getAttribute(Constantes.TOKEN_USUARIO);
		alerta.setIdUsuarioMod(usuario.getId());
		alerta.setBorrado(Boolean.TRUE);
		alertasManager.delete(alerta);

	}
}
