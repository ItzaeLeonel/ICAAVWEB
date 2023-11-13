/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionCtasEspeciales;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
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

import com.sistemasmig.icaavWeb.accounting.repositories.AsignacionesCtasEspecialesRepository;
//import com.sistemasmig.icaavWeb.accounting.repositories.GetAsignacionCuentasEspRepository;

import java.util.Map;

/**
 *
 * @author Julio
 */
@Component
public class AsignacionesCtasEspecialesManager {

	@Autowired
	private AsignacionesCtasEspecialesRepository asignacionesCtasEspecialesRepository;

	@Autowired
	private EntityManager entityManager;

	public AsignacionCtasEspeciales getById(Integer id) {
		return asignacionesCtasEspecialesRepository.findById(id).orElse(null);
	}

	public PagedResponse<AsignacionCtasEspeciales> getAsignacionCtasEspeciales(AsignacionCtasEspeciales filter,
			Paging paging) {

		Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<AsignacionCtasEspeciales> cq = cb.createQuery(AsignacionCtasEspeciales.class);
		Root<AsignacionCtasEspeciales> root = cq.from(AsignacionCtasEspeciales.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
		cq.orderBy(cb.desc(root.get("id")));
		if (filter.getId() != null) {
			predicates.add(cb.equal(root.get("id"), filter.getId()));
		}
		if (filter.getCuentaContable() != null) {
			predicates.add(cb.equal(root.get("cuentaContable"), filter.getCuentaContable()));
		}

		if (filter.getCtaEspecial() != null) {
			if (filter.getCtaEspecial().getId() != null) {
				predicates.add(cb.equal(root.get("cuentaEspecial").get("id"), filter.getCtaEspecial().getId()));
			}
			if (filter.getCtaEspecial().getDescripcion() != null) {
				predicates.add(cb.equal(root.get("cuentaEspecial").get("descripcion"),
						filter.getCtaEspecial().getDescripcion()));
			}
			if (filter.getCtaEspecial().getFuncionalidad() != null) {
				predicates.add(cb.equal(root.get("cuentaEspecial").get("funcionalidad"),
						filter.getCtaEspecial().getFuncionalidad()));
			}
			if (filter.getCtaEspecial().getIva() != null) {
				predicates.add(cb.equal(root.get("cuentaEspecial").get("iva"), filter.getCtaEspecial().getIva()));
			}
			if (filter.getCtaEspecial().getTipoCuenta() != null) {
				predicates.add(cb.equal(root.get("cuentaEspecial").get("tipoCuenta"),
						filter.getCtaEspecial().getTipoCuenta()));
			}
			if (filter.getCtaEspecial().getFechaModificacion() != null
					&& filter.getCtaEspecial().getFechaModificacion2() != null) {
				predicates.add(cb.between(root.get("cuentaEspecial").get("fechaModificacion"),
						filter.getCtaEspecial().getFechaModificacion(),
						filter.getCtaEspecial().getFechaModificacion2()));
				cq.orderBy(cb.desc(root.get("cuentaEspecial").get("fechaModificacion")));
			}
		}

		if (filter.getCuentaContable() != null) {
			if (filter.getCuentaContable().getId() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("id"), filter.getCuentaContable().getId()));
			}
			if (filter.getCuentaContable().getAbonos() != null) {
				predicates.add(
						cb.equal(root.get("cuentaContable").get("abonos"), filter.getCuentaContable().getAbonos()));
			}
			if (filter.getCuentaContable().getAbonosAcumulados() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("abonosAcumulados"),
						filter.getCuentaContable().getAbonosAcumulados()));
			}
			if (filter.getCuentaContable().getAnio() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("anio"), filter.getCuentaContable().getAnio()));
			}
			if (filter.getCuentaContable().getBorrado() != null) {
				predicates.add(
						cb.equal(root.get("cuentaContable").get("borrado"), filter.getCuentaContable().getBorrado()));
			}
			if (filter.getCuentaContable().getCargos() != null) {
				predicates.add(
						cb.equal(root.get("cuentaContable").get("borrado"), filter.getCuentaContable().getCargos()));
			}
			if (filter.getCuentaContable().getCargosAcumulados() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("cargosAcumulados"),
						filter.getCuentaContable().getCargosAcumulados()));
			}
			if (filter.getCuentaContable().getEsCtaComp() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("getEstCtaComp"),
						filter.getCuentaContable().getEsCtaComp()));
			}
			if (filter.getCuentaContable().getCuentaXPagar() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("cuentaPorPagar"),
						filter.getCuentaContable().getCuentaXPagar()));
			}
			if (filter.getCuentaContable().getCuentaContableEstatusEnum() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("CuentaContableEstatusEnum"),
						filter.getCuentaContable().getCuentaContableEstatusEnum()));
			}
			if (filter.getCuentaContable().getMes() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("mes"), filter.getCuentaContable().getMes()));
			}
			if (filter.getCuentaContable().getNaturaleza() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("naturaleza"),
						filter.getCuentaContable().getNaturaleza()));
			}
			if (filter.getCuentaContable().getNivel() != null) {
				predicates
						.add(cb.equal(root.get("cuentaContable").get("nivel"), filter.getCuentaContable().getNivel()));
			}
			if (filter.getCuentaContable().getNombre() != null) {
				predicates.add(
						cb.equal(root.get("cuentaContable").get("nombre"), filter.getCuentaContable().getNombre()));
			}
			if (filter.getCuentaContable().getNombreCuentaSat() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("nombreCuentaSat"),
						filter.getCuentaContable().getNombreCuentaSat()));
			}
			if (filter.getCuentaContable().getNumeroComplementaria() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("numeroComplementaria"),
						filter.getCuentaContable().getNumeroComplementaria()));
			}
			if (filter.getCuentaContable().getNumeroCuenta() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("numeroCuenta"),
						filter.getCuentaContable().getNumeroCuenta()));
			}
			if (filter.getCuentaContable().getNumeroCuentaMayor() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("numeroCuentaMayor"),
						filter.getCuentaContable().getNumeroCuentaMayor()));
			}
			if (filter.getCuentaContable().getSaldoIncial() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("saldoInicial"),
						filter.getCuentaContable().getSaldoIncial()));
			}
			if (filter.getCuentaContable().getSaldoFinal() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("saldoFinal"),
						filter.getCuentaContable().getSaldoFinal()));
			}
			if (filter.getCuentaContable().getTipoCuenta() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("tipoCuenta"),
						filter.getCuentaContable().getTipoCuenta()));
			}
			if (filter.getCuentaContable().getFechaModificacion() != null) {
				predicates.add(cb.equal(root.get("cuentaContable").get("fechaModificacion"),
						filter.getCuentaContable().getFechaModificacion()));
				cq.orderBy(cb.desc(root.get("cuentaContable").get("funcionalidad")));
			}
		}

		if (filter.getGrupoEmpresa() != null) {
			if (filter.getGrupoEmpresa().getAmex() != null) {
				predicates.add(cb.equal(root.get("grupoEmpresa").get("amex"), filter.getGrupoEmpresa().getAmex()));
			}

			if (filter.getGrupoEmpresa().getEmpresa() != null) {
				if (filter.getGrupoEmpresa().getEmpresa().getAmex() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("amex"),
							filter.getGrupoEmpresa().getEmpresa().getAmex()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getBaseDatosId() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("baseDatosId"),
							filter.getGrupoEmpresa().getEmpresa().getBaseDatosId()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getClave() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("clave")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getClave().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getClavePais() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("clavePais")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getClavePais().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getCodigoAgencia() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("codigoAgencia")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getCodigoAgencia().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getCodigoOficina() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("codigoOficina")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getCodigoOficina().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getDireccion() != null
						&& filter.getGrupoEmpresa().getEmpresa().getDireccion().getId() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("direccion").get("id"),
							filter.getGrupoEmpresa().getEmpresa().getDireccion().getId()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getEmail() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("email")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getEmail().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getEstatus() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("estatus"),
							filter.getGrupoEmpresa().getEmpresa().getEstatus()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getFechaModificacion() != null
						&& filter.getGrupoEmpresa().getEmpresa().getFechaModificacion2() != null) {
					predicates.add(cb.between(root.get("grupoEmpresa").get("empresa").get("fechaModificacion"),
							filter.getGrupoEmpresa().getEmpresa().getFechaModificacion(),
							filter.getGrupoEmpresa().getEmpresa().getFechaModificacion2()));
					cq.orderBy(cb.desc(root.get("grupoEmpresa").get("empresa").get("fechaModificacion")));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getFormaPagoFolios() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("formaPagoFolios"),
							filter.getGrupoEmpresa().getEmpresa().getFormaPagoFolios()));
				}

				if (filter.getGrupoEmpresa().getEmpresa().getId() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("id"),
							filter.getGrupoEmpresa().getEmpresa().getId()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getIdioma() != null
						&& filter.getGrupoEmpresa().getEmpresa().getIdioma().getId() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("idioma").get("id"),
							filter.getGrupoEmpresa().getEmpresa().getIdioma().getId()));
				}

				if (filter.getGrupoEmpresa().getEmpresa().getNombre() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("nombre")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getNombre().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getNombreComercial() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("nombreComercial")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getNombreComercial().toLowerCase() + "%"));
				}

				if (filter.getGrupoEmpresa().getEmpresa().getRazonSocial() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("razonSocial")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getRazonSocial().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getRfcSucursal() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("rfcSucursal")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getRfcSucursal().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getSucursales() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("sucursales"),
							filter.getGrupoEmpresa().getEmpresa().getSucursales()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getTelefono() != null) {
					predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("telefono")),
							"%" + filter.getGrupoEmpresa().getEmpresa().getTelefono().toLowerCase() + "%"));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getTipoPaquete() != null
						&& filter.getGrupoEmpresa().getEmpresa().getTipoPaquete().getId() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("tipoPaquete").get("id"),
							filter.getGrupoEmpresa().getEmpresa().getTipoPaquete().getId()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getUsuarios() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("usuarios"),
							filter.getGrupoEmpresa().getEmpresa().getUsuarios()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getVersionCFDI() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("versionCFDI"),
							filter.getGrupoEmpresa().getEmpresa().getVersionCFDI()));
				}
				if (filter.getGrupoEmpresa().getEmpresa().getZonaHoraria() != null
						&& filter.getGrupoEmpresa().getEmpresa().getZonaHoraria().getId() != null) {
					predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("zonaHoraria").get("id"),
							filter.getGrupoEmpresa().getEmpresa().getZonaHoraria().getId()));
				}
			}

			if (filter.getGrupoEmpresa().getEstatusUsuarioEmpresa() != null) {
				predicates.add(cb.equal(root.get("grupoEmpresa").get("estatusUsuarioEmpresa"),
						filter.getGrupoEmpresa().getEstatusUsuarioEmpresa()));
			}
			if (filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa() != null
					&& filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2() != null) {
				predicates.add(cb.between(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"),
						filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),
						filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
				cq.orderBy(cb.desc(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa")));
			}
			if (filter.getGrupoEmpresa().getGrupoId() != null) {
				predicates
						.add(cb.equal(root.get("grupoEmpresa").get("grupoId"), filter.getGrupoEmpresa().getGrupoId()));
			}
			if (filter.getGrupoEmpresa().getId() != null) {
				predicates.add(cb.equal(root.get("grupoEmpresa").get("id"), filter.getGrupoEmpresa().getId()));
			}
		}

		if (filter.getFechaModificacion() != null) {
			predicates.add(cb.equal(root.get("fechaModificacion"), filter.getFechaModificacion()));
			cq.orderBy(cb.desc(root.get("fechaModificacion")));
		}
		if (filter.getSucursal() != null) {
			predicates.add(cb.equal(root.get("usuarioSucursal"), filter.getSucursal()));
		}

		cq.select(root);
		if (predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<AsignacionCtasEspeciales> query = entityManager.createQuery(cq);

		int iTotal = query.getResultList().size();

		List<AsignacionCtasEspeciales> result = query.setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();

		Page<AsignacionCtasEspeciales> page = new PageImpl<>(result, pageable, iTotal);

		return new PagedResponse<AsignacionCtasEspeciales>((int) page.getTotalElements(), page.getTotalPages(),
				paging.getPage(), paging.getPageSize(), page.getContent());
	}

	@Transactional(rollbackFor = { BusinessLogicException.class, Exception.class })
	public Map<String, Object> spConAsignacionCtasEspecialesI(AsignacionCtasEspeciales entity) {
		return asignacionesCtasEspecialesRepository.spConAsignacionCtasEspecialesI(entity.getGrupoEmpresa().getId(),
				entity.getSucursal().getId(), entity.getCtaEspecial().getId(), entity.getCuentaContable().getId(), null,
				null, null);
	}

	@Transactional
	public AsignacionCtasEspeciales update(AsignacionCtasEspeciales entity) {
		return asignacionesCtasEspecialesRepository.save(entity);
	}

	@Transactional
	public void delete(AsignacionCtasEspeciales entity) {
		asignacionesCtasEspecialesRepository.save(entity);
	}

	public List<AsignacionCtasEspeciales> findAll() {
		return asignacionesCtasEspecialesRepository.findAll();
	}
}
