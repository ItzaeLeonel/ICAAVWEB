/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Submodulo;
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
import com.sistemasmig.icaavWeb.accounting.repositories.SubmoduloRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class SubmoduloManager {
    
    @Autowired
    private SubmoduloRepository submoduloRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Submodulo getById(Integer id) throws EntityNotExistentException {
        Submodulo submodulo = submoduloRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (submodulo!=null) {
            return submodulo;
        }
        throw new EntityNotExistentException(Submodulo.class,id.toString());
    }
    
    public PagedResponse<Submodulo> getSubmodulo(Submodulo filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Submodulo> cq = cb.createQuery(Submodulo.class);
        Root<Submodulo> root = cq.from(Submodulo.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getClaveSubmodulo()!=null){
            predicates.add(cb.like(cb.lower(root.get("claveSubmodulo")), "%" + filter.getClaveSubmodulo().toLowerCase()+ "%"));
            
        }
        
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getModulo()!=null){
            if(filter.getModulo().getIdSistema()!=null){
                predicates.add(cb.equal(root.get("modulo").get("idSistema"), filter.getModulo().getIdSistema()));
            }
            
            if(filter.getModulo().getNombreModulo()!=null){
                predicates.add(cb.like(cb.lower(root.get("modulo").get("nombreModulo")), "%" + filter.getModulo().getNombreModulo().toLowerCase()+ "%"));
            }
        }
        if(filter.getTipoPaquete()!=null && filter.getTipoPaquete().getId()!=null){
            predicates.add(cb.equal(root.get("tipoPaquete").get("id"), filter.getTipoPaquete().getId()));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Submodulo> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Submodulo> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Submodulo> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Submodulo>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Submodulo createSubmodulo(Submodulo submodulo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateSubmodulo(submodulo);
        //validateUnique(submodulo);
        submodulo.setBorrado(Boolean.FALSE);
        return submoduloRepository.save(submodulo);
    }

    private void validateSubmodulo(Submodulo submodulo) throws BusinessLogicException, EntityNotExistentException {
        if (submodulo.getClaveSubmodulo()==null) {
            throw new BusinessLogicException("El campo ClaveSubmodulo es requerido para el objeto Submodulo");
        } else if (submodulo.getModulo()==null) {
            throw new BusinessLogicException("El campo Modulo es requerido para el objeto Submodulo");
        } else if (submodulo.getNombreSubmodulo()==null) {
            throw new BusinessLogicException("El campo NombreSubmodulo es requerido para el objeto Submodulo");
        }
    }
    
    /*private void validateUnique(Submodulo submodulo) throws ExistentEntityException {
        List<Submodulo> submoduloes = submoduloRepository.findByNombre(submodulo.getNombre());
        if (submoduloes!=null && !submoduloes.isEmpty()) {
            throw new ExistentEntityException(Submodulo.class,"nombre="+submodulo.getNombre());
        } 
    }
*/
    public Submodulo updateSubmodulo(Integer submoduloId, Submodulo submodulo) throws EntityNotExistentException, BusinessLogicException {
        
        Submodulo persistedSubmodulo = getById(submoduloId);
        if (persistedSubmodulo != null) {
            if(submodulo.getClaveSubmodulo()!=null){
                persistedSubmodulo.setClaveSubmodulo(submodulo.getClaveSubmodulo());
            }
            if(submodulo.getModulo()!=null){
                persistedSubmodulo.setModulo(submodulo.getModulo());
            }
            if(submodulo.getNombreSubmodulo()!=null){
                persistedSubmodulo.setNombreSubmodulo(submodulo.getNombreSubmodulo());
            }
            if(submodulo.getRoleAmex()!=null){
                persistedSubmodulo.setRoleAmex(submodulo.getRoleAmex());
            }
            if(submodulo.getTipoPaquete()!=null){
                persistedSubmodulo.setTipoPaquete(submodulo.getTipoPaquete());
            }
            return submoduloRepository.save(persistedSubmodulo);
        } else {
            throw new EntityNotExistentException(Submodulo.class,submoduloId.toString());
        }
    }

    public void deleteSubmodulo(Integer submoduloId) throws EntityNotExistentException {
        Submodulo submodulo = getById(submoduloId);
        submodulo.setBorrado(Boolean.TRUE);
        submoduloRepository.save(submodulo);
    }

    public List<Submodulo> findAll(){
        return submoduloRepository.findAll();
    }
}
