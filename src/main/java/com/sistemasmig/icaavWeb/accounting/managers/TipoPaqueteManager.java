/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.TipoPaquete;
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
import com.sistemasmig.icaavWeb.accounting.repositories.TipoPaqueteRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class TipoPaqueteManager {
    
    @Autowired
    private TipoPaqueteRepository tipoPaqueteRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public TipoPaquete getById(Integer id) throws EntityNotExistentException {
        TipoPaquete tipoPaquete = tipoPaqueteRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (tipoPaquete!=null) {
            return tipoPaquete;
        }
        throw new EntityNotExistentException(TipoPaquete.class,id.toString());
    }
    
    public PagedResponse<TipoPaquete> getTipoPaquete(TipoPaquete filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<TipoPaquete> cq = cb.createQuery(TipoPaquete.class);
        Root<TipoPaquete> root = cq.from(TipoPaquete.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getDescripcion()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        if(filter.getEspacioAlmacenamiento()!=null){
            predicates.add(cb.equal(root.get("espacioAlmacenamiento"), filter.getEspacioAlmacenamiento()));
        }
        if(filter.getEspacioGB()!=null){
            predicates.add(cb.equal(root.get("espacioGB"), filter.getEspacioGB()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getIdSubsistema()!=null){
            predicates.add(cb.equal(root.get("idSubsistema"), filter.getIdSubsistema()));
        }
        if(filter.getNombre()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        if(filter.getNumeroFolios()!=null){
            predicates.add(cb.equal(root.get("numeroFolios"), filter.getNombre()));
        }
        if(filter.getNumeroUsuarios()!=null){
            predicates.add(cb.equal(root.get("numeroUsuarios"), filter.getNombre()));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<TipoPaquete> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<TipoPaquete> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<TipoPaquete> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<TipoPaquete>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public TipoPaquete createTipoPaquete(TipoPaquete tipoPaquete) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateTipoPaquete(tipoPaquete);
        //validateUnique(tipoPaquete);
        tipoPaquete.setBorrado(Boolean.FALSE);
        return tipoPaqueteRepository.save(tipoPaquete);
    }

    private void validateTipoPaquete(TipoPaquete tipoPaquete) throws BusinessLogicException, EntityNotExistentException {
        if (tipoPaquete.getNombre()==null) {
            throw new BusinessLogicException("El campo Nombre es requerido para el objeto TipoPaquete");
        } 
    }
    
    /*private void validateUnique(TipoPaquete tipoPaquete) throws ExistentEntityException {
        List<TipoPaquete> tipoPaquetees = tipoPaqueteRepository.findByNombre(tipoPaquete.getNombre());
        if (tipoPaquetees!=null && !tipoPaquetees.isEmpty()) {
            throw new ExistentEntityException(TipoPaquete.class,"nombre="+tipoPaquete.getNombre());
        } 
    }
*/
    public TipoPaquete updateTipoPaquete(Integer tipoPaqueteId, TipoPaquete tipoPaquete) throws EntityNotExistentException, BusinessLogicException {
        
        TipoPaquete persistedTipoPaquete = getById(tipoPaqueteId);
        if (persistedTipoPaquete != null) {
            if(tipoPaquete.getDescripcion()!=null){
                persistedTipoPaquete.setDescripcion(tipoPaquete.getDescripcion());
            }
            if(tipoPaquete.getEspacioAlmacenamiento()!=null){
                persistedTipoPaquete.setEspacioAlmacenamiento(tipoPaquete.getEspacioAlmacenamiento());
            }
            if(tipoPaquete.getEspacioGB()!=null){
                persistedTipoPaquete.setEspacioGB(tipoPaquete.getEspacioGB());
            }
            if(tipoPaquete.getIdSubsistema()!=null){
                persistedTipoPaquete.setIdSubsistema(tipoPaquete.getIdSubsistema());
            }
            if(tipoPaquete.getNombre()!=null){
                persistedTipoPaquete.setNombre(tipoPaquete.getNombre());
            }
            if(tipoPaquete.getNumeroFolios()!=null){
                persistedTipoPaquete.setNumeroFolios(tipoPaquete.getNumeroFolios());
            }
            if(tipoPaquete.getNumeroUsuarios()!=null){
                persistedTipoPaquete.setNumeroUsuarios(tipoPaquete.getNumeroUsuarios());
            }
            return tipoPaqueteRepository.save(persistedTipoPaquete);
        } else {
            throw new EntityNotExistentException(TipoPaquete.class,tipoPaqueteId.toString());
        }
    }

    public void deleteTipoPaquete(Integer tipoPaqueteId) throws EntityNotExistentException {
        TipoPaquete tipoPaquete = getById(tipoPaqueteId);
        tipoPaquete.setBorrado(Boolean.TRUE);
        tipoPaqueteRepository.save(tipoPaquete);
    }

    public List<TipoPaquete> findAll(){
        return tipoPaqueteRepository.findAll();
    }
}
