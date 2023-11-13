/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.AsignacionTipoPolizas;
import com.sistemasmig.icaavWeb.accounting.entity.ClasificacionCtas;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.repositories.ClasificacionesCtasRepository;
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

/**
 *
 * @author Julio
 */
@Component
public class ClasificacionesCtasManager {
    
    
    @Autowired
    private ClasificacionesCtasRepository clasificacionesCtasRepository;
    
    @Autowired
    private EntityManager entityManager;

    

    public ClasificacionCtas getById(Integer id) throws EntityNotExistentException {
    	return clasificacionesCtasRepository.findById(id).orElse(null);
    }
    
    public PagedResponse<ClasificacionCtas> getClasificacionCtas(ClasificacionCtas filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<ClasificacionCtas> cq = cb.createQuery(ClasificacionCtas.class);
        Root<ClasificacionCtas> root = cq.from(ClasificacionCtas.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getIniciaCon()!=null){
            predicates.add(cb.equal(root.get("iniciaCon"), filter.getIniciaCon()));
        }
        if(filter.getClasificacion()!=null){
            predicates.add(cb.equal(root.get("clasificacion"), filter.getClasificacion()));
        }
        if(filter.getFechaModificacion()!=null ){
            predicates.add(cb.equal(root.get("fechaModificacion"), filter.getFechaModificacion()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<ClasificacionCtas> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<ClasificacionCtas> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<ClasificacionCtas> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<ClasificacionCtas>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    @Transactional(rollbackFor = { BusinessLogicException.class, Exception.class })
	public ClasificacionCtas save(ClasificacionCtas entity) {
		return clasificacionesCtasRepository.save(entity);
	}

	@Transactional
	public ClasificacionCtas update(ClasificacionCtas entity) {
		return clasificacionesCtasRepository.save(entity);
	}

	@Transactional
	public void delete(ClasificacionCtas entity) {
		clasificacionesCtasRepository.save(entity);
	}

	public List<ClasificacionCtas> findAll() {
		return clasificacionesCtasRepository.findAll();
	}
}
