/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AlertasUsuarios;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sistemasmig.icaavWeb.accounting.repositories.AlertasUsuariosRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class AlertasUsuariosManager {

	@Autowired
	private AlertasUsuariosRepository alertasUsuariosRepository;

	@Autowired
	private EntityManager entityManager;


	public AlertasUsuarios getById(Integer id) throws EntityNotExistentException {
		return alertasUsuariosRepository.findById(id).orElse(null);
	}

	public PagedResponse<AlertasUsuarios> getAlertasUsuarios(AlertasUsuarios filter, Paging paging) {

		Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<AlertasUsuarios> cq = cb.createQuery(AlertasUsuarios.class);
		Root<AlertasUsuarios> root = cq.from(AlertasUsuarios.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
		cq.orderBy(cb.desc(root.get("id")));

		if (filter.getFechaModificacion() != null) {
			predicates.add(cb.equal(root.get("fechaModificacion"), filter.getFechaModificacion()));
			cq.orderBy(cb.desc(root.get("fechaModificacion")));
		}
		if (filter.getId() != null) {
			predicates.add(cb.equal(root.get("id"), filter.getId()));
		}
		if (filter.getLeido() != null) {
			predicates.add(cb.equal(root.get("leido"), filter.getLeido()));
		}
		if (filter.getUsuario() != null) {
			predicates.add(cb.equal(root.get("usuario"), filter.getUsuario()));
		}
		if (filter.getAlerta() != null) {
			if (filter.getAlerta().getEstatusAlertasEnum() != null) {
				predicates.add(cb.equal(root.get("alerta").get("estatusAlertasEnum"),
						filter.getAlerta().getEstatusAlertasEnum()));
			}

			if (filter.getAlerta().getFechaAlta() != null) {
				predicates.add(cb.equal(root.get("fechaAlta"), filter.getAlerta().getFechaAlta()));
				cq.orderBy(cb.desc(root.get("fechaAlta")));
			}
			if (filter.getAlerta().getFechaModificacion() != null) {
				predicates.add(cb.equal(root.get("fechaModificacion"), filter.getAlerta().getFechaModificacion()));
				cq.orderBy(cb.desc(root.get("fechaModificacion")));
			}
			if (filter.getAlerta().getGrupoEmpresa() != null) {
				if (filter.getAlerta().getGrupoEmpresa().getAmex() != null) {
					predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("amex"),
							filter.getAlerta().getGrupoEmpresa().getAmex()));
				}
				if (filter.getAlerta().getGrupoEmpresa().getEstatusUsuarioEmpresa() != null) {
					predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("estatusUsuarioEmpresa"),
							filter.getAlerta().getGrupoEmpresa().getEstatusUsuarioEmpresa()));
				}
				if (filter.getAlerta().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa() != null
						&& filter.getAlerta().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2() != null) {
					predicates.add(
							cb.between(root.get("alerta").get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"),
									filter.getAlerta().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),
									filter.getAlerta().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
					cq.orderBy(cb.desc(root.get("alerta").get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa")));
				}
				if (filter.getAlerta().getGrupoEmpresa().getGrupoId() != null) {
					predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("grupoId"),
							filter.getAlerta().getGrupoEmpresa().getGrupoId()));
				}
				if (filter.getAlerta().getGrupoEmpresa().getId() != null) {
					predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("id"),
							filter.getAlerta().getGrupoEmpresa().getId()));
				}
				if (filter.getAlerta().getGrupoEmpresa().getEmpresa() != null) {
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getAmex() != null) {
						predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("amex"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getAmex()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getBaseDatosId() != null) {
						predicates
								.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("baseDatosId"),
										filter.getAlerta().getGrupoEmpresa().getEmpresa().getBaseDatosId()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getClave() != null) {
						predicates.add(
								cb.like(cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("clave")),
										"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getClave().toLowerCase()
												+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getClavePais() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("clavePais")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getClavePais().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getCodigoAgencia() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("codigoAgencia")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getCodigoAgencia().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getCodigoOficina() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("codigoOficina")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getCodigoOficina().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getDireccion() != null
							&& filter.getAlerta().getGrupoEmpresa().getEmpresa().getDireccion().getId() != null) {
						predicates.add(cb.equal(
								root.get("alerta").get("grupoEmpresa").get("empresa").get("direccion").get("id"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getDireccion().getId()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getEmail() != null) {
						predicates.add(
								cb.like(cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("email")),
										"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getEmail().toLowerCase()
												+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getEstatus() != null) {
						predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("estatus"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getEstatus()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getFechaModificacion() != null
							&& filter.getAlerta().getGrupoEmpresa().getEmpresa().getFechaModificacion2() != null) {
						predicates.add(cb.between(
								root.get("alerta").get("grupoEmpresa").get("empresa").get("fechaModificacion"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getFechaModificacion(),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getFechaModificacion2()));
						cq.orderBy(cb
								.desc(root.get("alerta").get("grupoEmpresa").get("empresa").get("fechaModificacion")));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getFormaPagoFolios() != null) {
						predicates.add(
								cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("formaPagoFolios"),
										filter.getAlerta().getGrupoEmpresa().getEmpresa().getFormaPagoFolios()));
					}

					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getId() != null) {
						predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("id"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getId()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getIdioma() != null
							&& filter.getAlerta().getGrupoEmpresa().getEmpresa().getIdioma().getId() != null) {
						predicates.add(
								cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("idioma").get("id"),
										filter.getAlerta().getGrupoEmpresa().getEmpresa().getIdioma().getId()));
					}

					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getNombre() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("nombre")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getNombre().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getNombreComercial() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("nombreComercial")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getNombreComercial()
										.toLowerCase() + "%"));
					}

					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getRazonSocial() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("razonSocial")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getRazonSocial().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getRfcSucursal() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("rfcSucursal")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getRfcSucursal().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getSucursales() != null) {
						predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("sucursales"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getSucursales()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getTelefono() != null) {
						predicates.add(cb.like(
								cb.lower(root.get("alerta").get("grupoEmpresa").get("empresa").get("telefono")),
								"%" + filter.getAlerta().getGrupoEmpresa().getEmpresa().getTelefono().toLowerCase()
										+ "%"));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getTipoPaquete() != null
							&& filter.getAlerta().getGrupoEmpresa().getEmpresa().getTipoPaquete().getId() != null) {
						predicates.add(cb.equal(
								root.get("alerta").get("grupoEmpresa").get("empresa").get("tipoPaquete").get("id"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getTipoPaquete().getId()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getUsuarios() != null) {
						predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("usuarios"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getUsuarios()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getVersionCFDI() != null) {
						predicates
								.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("empresa").get("versionCFDI"),
										filter.getAlerta().getGrupoEmpresa().getEmpresa().getVersionCFDI()));
					}
					if (filter.getAlerta().getGrupoEmpresa().getEmpresa().getZonaHoraria() != null
							&& filter.getAlerta().getGrupoEmpresa().getEmpresa().getZonaHoraria().getId() != null) {
						predicates.add(cb.equal(
								root.get("alerta").get("grupoEmpresa").get("empresa").get("zonaHoraria").get("id"),
								filter.getAlerta().getGrupoEmpresa().getEmpresa().getZonaHoraria().getId()));
					}
				}
			}
			if (filter.getAlerta().getGrupoEmpresa().getId() != null) {
				predicates.add(cb.equal(root.get("alerta").get("grupoEmpresa").get("id"),
						filter.getAlerta().getGrupoEmpresa().getId()));
			}
		}
		cq.select(root);
		if (predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<AlertasUsuarios> query = entityManager.createQuery(cq);

		int iTotal = query.getResultList().size();

		List<AlertasUsuarios> result = query.setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();

		Page<AlertasUsuarios> page = new PageImpl<>(result, pageable, iTotal);

		return new PagedResponse<AlertasUsuarios>((int) page.getTotalElements(), page.getTotalPages(), paging.getPage(),
				paging.getPageSize(), page.getContent());
	}

	@Transactional(rollbackFor = { BusinessLogicException.class, Exception.class })
	public AlertasUsuarios save(AlertasUsuarios entity) {
		return alertasUsuariosRepository.save(entity);
	}

	@Transactional
	public AlertasUsuarios update(AlertasUsuarios entity) {
		return alertasUsuariosRepository.save(entity);
	}

	@Transactional
	public void delete(AlertasUsuarios entity) {
		alertasUsuariosRepository.save(entity);
	}

	public List<AlertasUsuarios> findAll() {
		return alertasUsuariosRepository.findAll();
	}
}
