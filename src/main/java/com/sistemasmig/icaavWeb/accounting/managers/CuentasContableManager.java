/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContable;
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

import com.sistemasmig.icaavWeb.accounting.repositories.CuentasContableRepository;
import java.util.Map;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class CuentasContableManager {

	@Autowired
	private CuentasContableRepository cuentasContableRepository;

	@Autowired
	private EntityManager entityManager;

	

	public CuentaContable getById(Integer id) throws EntityNotExistentException {
		return cuentasContableRepository.findById(id).orElse(null);
	}

	public PagedResponse<CuentaContable> getCuentaContable(CuentaContable filter, Paging paging) {

		Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<CuentaContable> cq = cb.createQuery(CuentaContable.class);
		Root<CuentaContable> root = cq.from(CuentaContable.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
		cq.orderBy(cb.desc(root.get("id")));

		if (filter.getAbonos() != null) {
			predicates.add(cb.equal(root.get("abonos"), filter.getAbonos()));
		}
		if (filter.getAbonosAcumulados() != null) {
			predicates.add(cb.equal(root.get("abonosAcumulados"), filter.getAbonosAcumulados()));
		}
		if (filter.getAnio() != null) {
			predicates.add(cb.equal(root.get("anio"), filter.getAnio()));
		}
		if (filter.getCargos() != null) {
			predicates.add(cb.equal(root.get("cargos"), filter.getCargos()));
		}
		if (filter.getCargosAcumulados() != null) {
			predicates.add(cb.equal(root.get("cargosAcumulados"), filter.getCargosAcumulados()));
		}
		if (filter.getCuentaContableEstatusEnum() != null) {
			predicates.add(cb.equal(root.get("cuentaContableEstatusEnum"), filter.getCuentaContableEstatusEnum()));
		}
		if (filter.getCuentaXPagar() != null) {
			predicates.add(cb.equal(root.get("cuentaXPagar"), filter.getCuentaXPagar()));
		}
		if (filter.getEsCtaComp() != null) {
			predicates.add(cb.equal(root.get("esCtaComp"), filter.getEsCtaComp()));
		}
		if (filter.getFechaModificacion() != null) {
			predicates.add(cb.equal(root.get("fechaModificacion"), filter.getFechaModificacion()));
			cq.orderBy(cb.desc(root.get("fechaModificacion")));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getAmex() != null) {
			predicates.add(cb.equal(root.get("grupoEmpresa").get("amex"), filter.getGrupoEmpresa().getAmex()));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getEmpresa() != null
				&& filter.getGrupoEmpresa().getEmpresa().getId() != null) {
			predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("id"),
					filter.getGrupoEmpresa().getEmpresa().getId()));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getEmpresa() != null
				&& filter.getGrupoEmpresa().getEmpresa().getNombre() != null) {
			predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("nombre")),
					"%" + filter.getGrupoEmpresa().getEmpresa().getNombre().toLowerCase() + "%"));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getEstatusUsuarioEmpresa() != null) {
			predicates.add(cb.equal(root.get("grupoEmpresa").get("estatusUsuarioEmpresa"),
					filter.getGrupoEmpresa().getEstatusUsuarioEmpresa()));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa() != null
				&& filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2() != null) {
			predicates.add(cb.between(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"),
					filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),
					filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
			cq.orderBy(cb.desc(root.get("grupoEmpresa").get("fechaModificacion")));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getGrupoId() != null) {
			predicates.add(cb.equal(root.get("grupoEmpresa").get("grupoId"), filter.getGrupoEmpresa().getGrupoId()));
		}
		if (filter.getGrupoEmpresa() != null && filter.getGrupoEmpresa().getId() != null) {
			predicates.add(cb.equal(root.get("grupoEmpresa").get("id"), filter.getGrupoEmpresa().getId()));
		}
		if (filter.getId() != null) {
			predicates.add(cb.equal(root.get("id"), filter.getId()));
		}
		if (filter.getMes() != null) {
			predicates.add(cb.equal(root.get("mes"), filter.getMes()));
		}
		if (filter.getNaturaleza() != null) {
			predicates.add(cb.like(cb.lower(root.get("naturaleza")), "%" + filter.getNaturaleza().toLowerCase() + "%"));
		}
		if (filter.getNivel() != null) {
			predicates.add(cb.equal(root.get("nivel"), filter.getNivel()));
		}
		if (filter.getNombre() != null) {
			predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
		}
		if (filter.getNombreCuentaSat() != null) {
			predicates.add(cb.like(cb.lower(root.get("nombreCuentaSat")),
					"%" + filter.getNombreCuentaSat().toLowerCase() + "%"));
		}
		if (filter.getNumeroComplementaria() != null) {
			predicates.add(cb.like(cb.lower(root.get("numeroComplementaria")),
					"%" + filter.getNumeroComplementaria().toLowerCase() + "%"));
		}
		if (filter.getNumeroCuenta() != null) {
			predicates.add(
					cb.like(cb.lower(root.get("numeroCuenta")), "%" + filter.getNumeroCuenta().toLowerCase() + "%"));
		}
		if (filter.getNumeroCuentaMayor() != null) {
			predicates.add(cb.like(cb.lower(root.get("numeroCuentaMayor")),
					"%" + filter.getNumeroCuentaMayor().toLowerCase() + "%"));
		}
		if (filter.getSaldoFinal() != null) {
			predicates.add(cb.equal(root.get("saldoFinal"), filter.getSaldoFinal()));
		}
		if (filter.getSaldoIncial() != null) {
			predicates.add(cb.equal(root.get("saldoIncial"), filter.getSaldoIncial()));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getCuentaMayor() != null) {
			predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("cuentaMayor")),
					"%" + filter.getSatCuentaContable().getCuentaMayor().toLowerCase() + "%"));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getCuentaSat() != null) {
			predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("cuentaSat")),
					"%" + filter.getSatCuentaContable().getCuentaSat().toLowerCase() + "%"));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getGrupo() != null) {
			predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("grupo")),
					"%" + filter.getSatCuentaContable().getGrupo().toLowerCase() + "%"));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getId() != null) {
			predicates.add(cb.equal(root.get("satCuentaContable").get("id"), filter.getSatCuentaContable().getId()));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getNaturaleza() != null) {
			predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("naturaleza")),
					"%" + filter.getSatCuentaContable().getNaturaleza().toLowerCase() + "%"));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getNivel() != null) {
			predicates.add(
					cb.equal(root.get("satCuentaContable").get("nivel"), filter.getSatCuentaContable().getNivel()));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getNombreCuenta() != null) {
			predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("nombreCuenta")),
					"%" + filter.getSatCuentaContable().getNombreCuenta().toLowerCase() + "%"));
		}
		if (filter.getSatCuentaContable() != null && filter.getSatCuentaContable().getSubGrupo() != null) {
			predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("subGrupo")),
					"%" + filter.getSatCuentaContable().getSubGrupo().toLowerCase() + "%"));
		}
		if (filter.getTipoCuenta() != null) {
			predicates.add(cb.like(cb.lower(root.get("tipoCuenta")), "%" + filter.getTipoCuenta().toLowerCase() + "%"));
		}
		if (filter.getIdUsuario() != null) {
			predicates.add(cb.equal(root.get("idUsuario"), filter.getSaldoFinal()));
		}

		cq.select(root);
		if (predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<CuentaContable> query = entityManager.createQuery(cq);

		int iTotal = query.getResultList().size();

		List<CuentaContable> result = query.setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();

		Page<CuentaContable> page = new PageImpl<>(result, pageable, iTotal);

		return new PagedResponse<CuentaContable>((int) page.getTotalElements(), page.getTotalPages(), paging.getPage(),
				paging.getPageSize(), page.getContent());
	}

	@Transactional(rollbackFor = { BusinessLogicException.class, Exception.class })
	public Map<String, Object> spConCuentasContablesI(CuentaContable entity) {
		return cuentasContableRepository.spConCuentasContablesI(entity.getGrupoEmpresa().getId(),
				entity.getSatCuentaContable().getId(), entity.getAnio(), entity.getMes(), entity.getNumeroCuenta(),
				entity.getTipoCuenta(), entity.getNombre(), entity.getSaldoIncial(), entity.getSaldoFinal(),
				entity.getCargos(), entity.getAbonos(), entity.getCargosAcumulados(), entity.getAbonosAcumulados(),
				entity.getCuentaXPagar(), entity.getCuentaContableEstatusEnum(), entity.getNumeroComplementaria(), null,
				null, null, null, null);
	}

	@Transactional
	public Map<String, Object> spConCuentasContablesU(CuentaContable entity) {
		return cuentasContableRepository.spConCuentasContablesU(entity.getId(), entity.getGrupoEmpresa().getId(),
				entity.getSatCuentaContable().getId(), entity.getAnio(), entity.getMes(), entity.getNumeroCuenta(),
				entity.getTipoCuenta(), entity.getNombre(), entity.getSaldoIncial(), entity.getSaldoFinal(),
				entity.getCargos(), entity.getAbonos(), entity.getCargosAcumulados(), entity.getAbonosAcumulados(),
				entity.getCuentaXPagar(), entity.getCuentaContableEstatusEnum(), entity.getNumeroComplementaria(), null,
				null);
	}

	@Transactional
	public void delete(CuentaContable entity) {
		cuentasContableRepository.save(entity);
	}

	public List<CuentaContable> findAll() {
		return cuentasContableRepository.findAll();
	}

	public Map<String, Object> getCuentasContablesSP(Integer pr_id_grupo_empresa)
			throws EntityNotExistentException {
		Map<String, Object> resultado = cuentasContableRepository.spConCuentasContablesC(pr_id_grupo_empresa,null,null);
		return resultado;
	}
}
