/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.ZonaHoraria;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;

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
import com.sistemasmig.icaavWeb.accounting.repositories.ZonaHorariaRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class ZonaHorariaManager {
    
    @Autowired
    private ZonaHorariaRepository zonaHorariaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public ZonaHoraria getById(Integer id) throws EntityNotExistentException {
        ZonaHoraria zonaHoraria = zonaHorariaRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (zonaHoraria!=null) {
            return zonaHoraria;
        }
        throw new EntityNotExistentException(ZonaHoraria.class,id.toString());
    }
    
    public PagedResponse<ZonaHoraria> getZonaHoraria(ZonaHoraria filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<ZonaHoraria> cq = cb.createQuery(ZonaHoraria.class);
        Root<ZonaHoraria> root = cq.from(ZonaHoraria.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getUtc()!=null){
            predicates.add(cb.like(cb.lower(root.get("utc")), "%" + filter.getUtc().toLowerCase()+ "%"));
        }
        if(filter.getZonaHoraria()!=null){
            predicates.add(cb.like(cb.lower(root.get("zonaHoraria")), "%" + filter.getZonaHoraria().toLowerCase()+ "%"));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<ZonaHoraria> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<ZonaHoraria> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<ZonaHoraria> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<ZonaHoraria>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public ZonaHoraria createZonaHoraria(ZonaHoraria zonaHoraria) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateZonaHoraria(zonaHoraria);
        //validateUnique(zonaHoraria);
        zonaHoraria.setBorrado(Boolean.FALSE);
        return zonaHorariaRepository.save(zonaHoraria);
    }

    private void validateZonaHoraria(ZonaHoraria zonaHoraria) throws BusinessLogicException, EntityNotExistentException {
        if (zonaHoraria.getUtc()==null) {
            throw new BusinessLogicException("El campo Utc es requerido para el objeto ZonaHoraria");
        } else if (zonaHoraria.getZonaHoraria()==null) {
            throw new BusinessLogicException("El campo ZonaHoraria es requerido para el objeto ZonaHoraria");
        }
    }
    
    /*private void validateUnique(ZonaHoraria zonaHoraria) throws ExistentEntityException {
        List<ZonaHoraria> zonaHorariaes = zonaHorariaRepository.findByNombre(zonaHoraria.getNombre());
        if (zonaHorariaes!=null && !zonaHorariaes.isEmpty()) {
            throw new ExistentEntityException(ZonaHoraria.class,"nombre="+zonaHoraria.getNombre());
        } 
    }
*/
    public ZonaHoraria updateZonaHoraria(Integer zonaHorariaId, ZonaHoraria zonaHoraria) throws EntityNotExistentException, BusinessLogicException {
        
        ZonaHoraria persistedZonaHoraria = getById(zonaHorariaId);
        if (persistedZonaHoraria != null) {
            if(zonaHoraria.getUtc()!=null){
                persistedZonaHoraria.setUtc(zonaHoraria.getUtc());
            }
            if(zonaHoraria.getZonaHoraria()!=null){
                persistedZonaHoraria.setZonaHoraria(zonaHoraria.getZonaHoraria());
            }
            
            return zonaHorariaRepository.save(persistedZonaHoraria);
        } else {
            throw new EntityNotExistentException(ZonaHoraria.class,zonaHorariaId.toString());
        }
    }

    public void deleteZonaHoraria(Integer zonaHorariaId) throws EntityNotExistentException {
        ZonaHoraria zonaHoraria = getById(zonaHorariaId);
        zonaHoraria.setBorrado(Boolean.TRUE);
        zonaHorariaRepository.save(zonaHoraria);
    }

    public List<ZonaHoraria> findAll(){
        return zonaHorariaRepository.findAll();
    }
}
