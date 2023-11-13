/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.SubmoduloPermiso;
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
import com.sistemasmig.icaavWeb.accounting.repositories.SubmoduloPermisoRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class SubmoduloPermisoManager {
    
    @Autowired
    private SubmoduloPermisoRepository submoduloPermisoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public SubmoduloPermiso getById(Integer id) throws EntityNotExistentException {
        SubmoduloPermiso submoduloPermiso = submoduloPermisoRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (submoduloPermiso!=null) {
            return submoduloPermiso;
        }
        throw new EntityNotExistentException(SubmoduloPermiso.class,id.toString());
    }
    
    public PagedResponse<SubmoduloPermiso> getSubmoduloPermiso(SubmoduloPermiso filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<SubmoduloPermiso> cq = cb.createQuery(SubmoduloPermiso.class);
        Root<SubmoduloPermiso> root = cq.from(SubmoduloPermiso.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getSubmodulo()!=null){
            if(filter.getSubmodulo().getClaveSubmodulo()!=null){
                predicates.add(cb.like(cb.lower(root.get("submodulo").get("claveSubmodulo")), "%" + filter.getSubmodulo().getClaveSubmodulo().toLowerCase()+ "%"));
            }
            if(filter.getSubmodulo().getFechaModificacion()!=null && filter.getSubmodulo().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("submodulo").get("fechaModificacion"), filter.getSubmodulo().getFechaModificacion(),filter.getSubmodulo().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("submodulo").get("fechaModificacion")));
            }
            if(filter.getSubmodulo().getNombreSubmodulo()!=null){
                predicates.add(cb.like(cb.lower(root.get("submodulo").get("nombreSubmodulo")), "%" + filter.getSubmodulo().getNombreSubmodulo().toLowerCase()+ "%"));
            }
            if(filter.getSubmodulo().getRoleAmex()!=null){
                predicates.add(cb.equal(root.get("submodulo").get("id"), filter.getSubmodulo().getRoleAmex()));
            }
            if(filter.getSubmodulo().getTipoPaquete()!=null && filter.getSubmodulo().getTipoPaquete().getId()!=null){
                predicates.add(cb.equal(root.get("submodulo").get("tipoPaquete").get("id"), filter.getSubmodulo().getTipoPaquete().getId()));
            }
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
        
        TypedQuery<SubmoduloPermiso> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<SubmoduloPermiso> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<SubmoduloPermiso> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<SubmoduloPermiso>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public SubmoduloPermiso createSubmoduloPermiso(SubmoduloPermiso submoduloPermiso) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateSubmoduloPermiso(submoduloPermiso);
        //validateUnique(submoduloPermiso);
        submoduloPermiso.setBorrado(Boolean.FALSE);
        return submoduloPermisoRepository.save(submoduloPermiso);
    }

    private void validateSubmoduloPermiso(SubmoduloPermiso submoduloPermiso) throws BusinessLogicException, EntityNotExistentException {
        if (submoduloPermiso.getSubmodulo()==null) {
            throw new BusinessLogicException("El campo Submodulo es requerido para el objeto SubmoduloPermiso");
        } else if (submoduloPermiso.getTipoPermiso()==null) {
            throw new BusinessLogicException("El campo TipoPermiso es requerido para el objeto SubmoduloPermiso");
        }
    }
    
    /*private void validateUnique(SubmoduloPermiso submoduloPermiso) throws ExistentEntityException {
        List<SubmoduloPermiso> submoduloPermisoes = submoduloPermisoRepository.findByNombre(submoduloPermiso.getNombre());
        if (submoduloPermisoes!=null && !submoduloPermisoes.isEmpty()) {
            throw new ExistentEntityException(SubmoduloPermiso.class,"nombre="+submoduloPermiso.getNombre());
        } 
    }
*/
    public SubmoduloPermiso updateSubmoduloPermiso(Integer submoduloPermisoId, SubmoduloPermiso submoduloPermiso) throws EntityNotExistentException, BusinessLogicException {
        
        SubmoduloPermiso persistedSubmoduloPermiso = getById(submoduloPermisoId);
        if (persistedSubmoduloPermiso != null) {
            if(submoduloPermiso.getSubmodulo()!=null){
                persistedSubmoduloPermiso.setSubmodulo(submoduloPermiso.getSubmodulo());
            }
            if(submoduloPermiso.getTipoPermiso()!=null){
                persistedSubmoduloPermiso.setTipoPermiso(submoduloPermiso.getTipoPermiso());
            }
            return submoduloPermisoRepository.save(persistedSubmoduloPermiso);
        } else {
            throw new EntityNotExistentException(SubmoduloPermiso.class,submoduloPermisoId.toString());
        }
    }

    public void deleteSubmoduloPermiso(Integer submoduloPermisoId) throws EntityNotExistentException {
        SubmoduloPermiso submoduloPermiso = getById(submoduloPermisoId);
        submoduloPermiso.setBorrado(Boolean.TRUE);
        submoduloPermisoRepository.save(submoduloPermiso);
    }

    public List<SubmoduloPermiso> findAll(){
        return submoduloPermisoRepository.findAll();
    }
}
