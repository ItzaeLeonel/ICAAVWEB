/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.entity.CuentaContablePrecargada;
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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sistemasmig.icaavWeb.accounting.repositories.CuentasContablePrecRepository;

/**
 *
 * @author Julio
 */
@Component
public class CuentasContablePrecargadaManager {

	@Autowired
	private CuentasContablePrecRepository cuentaContablePrecRepository;
	

	@Autowired
	private EntityManager entityManager;

	public CuentaContablePrecargada getById(Integer id) throws EntityNotExistentException {
		return cuentaContablePrecRepository.findById(id).orElse(null);
	}

	public PagedResponse<CuentaContablePrecargada> getCuentaContablePrecargada(CuentaContablePrecargada filter,
			Paging paging) {

		Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<CuentaContablePrecargada> cq = cb.createQuery(CuentaContablePrecargada.class);
		Root<CuentaContablePrecargada> root = cq.from(CuentaContablePrecargada.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
		cq.orderBy(cb.desc(root.get("id")));

		if (filter.getId() != null) {
			predicates.add(cb.equal(root.get("id"), filter.getId()));
		}
		if (filter.getDescripcion() != null) {
			predicates
					.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase() + "%"));
		}
		
		if (filter.getFechaModificacion() != null ) {
			predicates.add(cb.equal(root.get("fechaModificacion"), filter.getFechaModificacion()));
			cq.orderBy(cb.desc(root.get("fechaModificacion")));
		}
		if (filter.getMascara() != null) {
			predicates.add(cb.like(cb.lower(root.get("mascara")), "%" + filter.getMascara().toLowerCase() + "%"));
		}
		if (filter.getNivel1() != null) {
			predicates.add(cb.equal(root.get("nivel1"), filter.getNivel1()));
		}
		if (filter.getNivel2() != null) {
			predicates.add(cb.equal(root.get("nivel2"), filter.getNivel2()));
		}
		if (filter.getNivel3() != null) {
			predicates.add(cb.equal(root.get("nivel3"), filter.getNivel3()));
		}
		if (filter.getNivel4() != null) {
			predicates.add(cb.equal(root.get("nivel4"), filter.getNivel4()));
		}
		if (filter.getNivel5() != null) {
			predicates.add(cb.equal(root.get("nivel5"), filter.getNivel5()));
		}
		if (filter.getNivel6() != null) {
			predicates.add(cb.equal(root.get("nivel6"), filter.getNivel6()));
		}
		if (filter.getNivel7() != null) {
			predicates.add(cb.equal(root.get("nivel7"), filter.getNivel7()));
		}
		if (filter.getNivel8() != null) {
			predicates.add(cb.equal(root.get("nivel8"), filter.getNivel8()));
		}
		if (filter.getNivel9() != null) {
			predicates.add(cb.equal(root.get("nivel9"), filter.getNivel9()));
		}
		if (filter.getNo_digitos() != null) {
			predicates.add(cb.equal(root.get("no_digitos"), filter.getNo_digitos()));
		}
		if (filter.getNo_niveles() != null) {
			predicates.add(cb.equal(root.get("no_niveles"), filter.getNo_niveles()));
		}
		if (filter.getNombre() != null) {
			predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
		}

		cq.select(root);
		if (predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<CuentaContablePrecargada> query = entityManager.createQuery(cq);

		int iTotal = query.getResultList().size();

		List<CuentaContablePrecargada> result = query.setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();

		Page<CuentaContablePrecargada> page = new PageImpl<>(result, pageable, iTotal);

		return new PagedResponse<CuentaContablePrecargada>((int) page.getTotalElements(), page.getTotalPages(),
				paging.getPage(), paging.getPageSize(), page.getContent());
	}

	@Transactional(rollbackFor = { BusinessLogicException.class, Exception.class })
	public CuentaContablePrecargada save(CuentaContablePrecargada entity) {
		return cuentaContablePrecRepository.save(entity);
	}

	@Transactional
	public CuentaContablePrecargada update(CuentaContablePrecargada entity) {
		return cuentaContablePrecRepository.save(entity);
	}

	@Transactional
	public void delete(CuentaContablePrecargada entity) {
		cuentaContablePrecRepository.save(entity);
	}

	public Map<String, Object> spCuentasContablesPregarcadasC(CuentaContablePrecargada entity) throws EntityNotExistentException {
		 Map<String, Object> resultado = cuentaContablePrecRepository.spCuentasContablesPrecargadasC(entity.getId());
		return resultado;
	}
	
	public List<CuentaContablePrecargada> findAll() {
		return cuentaContablePrecRepository.findAll();
	}

}
