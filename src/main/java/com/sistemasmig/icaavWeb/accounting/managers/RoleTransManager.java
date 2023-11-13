/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.RoleTrans;
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
import com.sistemasmig.icaavWeb.accounting.repositories.RoleTransRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class RoleTransManager {
    
    @Autowired
    private RoleTransRepository roleTransRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public RoleTrans getById(Integer id) throws EntityNotExistentException {
        RoleTrans roleTrans = roleTransRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (roleTrans!=null) {
            return roleTrans;
        }
        throw new EntityNotExistentException(RoleTrans.class,id.toString());
    }
    
    public PagedResponse<RoleTrans> getRoleTrans(RoleTrans filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<RoleTrans> cq = cb.createQuery(RoleTrans.class);
        Root<RoleTrans> root = cq.from(RoleTrans.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getDescripcion()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getIdioma()!=null){
            if(filter.getIdioma().getCveIdioma()!=null){
                predicates.add(cb.like(cb.lower(root.get("idioma").get("cveIdioma")), "%" + filter.getIdioma().getCveIdioma().toLowerCase()+ "%"));   
            }
            if(filter.getIdioma().getId()!=null){
                predicates.add(cb.equal(root.get("idioma").get("id"), filter.getIdioma().getId()));
            }
            if(filter.getIdioma().getIdioma()!=null){
                predicates.add(cb.like(cb.lower(root.get("idioma").get("idioma")), "%" + filter.getIdioma().getIdioma().toLowerCase()+ "%"));
            }
        }
        if(filter.getNombreRole()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombreRole")), "%" + filter.getNombreRole().toLowerCase()+ "%"));
        }
        if(filter.getRoleTransAmex()!=null){
            predicates.add(cb.equal(root.get("roleTransAmex"), filter.getRoleTransAmex()));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<RoleTrans> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<RoleTrans> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<RoleTrans> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<RoleTrans>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public RoleTrans createRoleTrans(RoleTrans roleTrans) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateRoleTrans(roleTrans);
        //validateUnique(roleTrans);
        roleTrans.setBorrado(Boolean.FALSE);
        return roleTransRepository.save(roleTrans);
    }

    private void validateRoleTrans(RoleTrans roleTrans) throws BusinessLogicException, EntityNotExistentException {
        if (roleTrans.getDescripcion()==null) {
            throw new BusinessLogicException("El campo Descripcion es requerido para el objeto RoleTrans");
        } else if (roleTrans.getIdioma()==null) {
            throw new BusinessLogicException("El campo Idioma es requerido para el objeto RoleTrans");
        } else if (roleTrans.getNombreRole()==null) {
            throw new BusinessLogicException("El campo NombreRole es requerido para el objeto RoleTrans");
        }
    }
    
    /*private void validateUnique(RoleTrans roleTrans) throws ExistentEntityException {
        List<RoleTrans> roleTranses = roleTransRepository.findByNombre(roleTrans.getNombre());
        if (roleTranses!=null && !roleTranses.isEmpty()) {
            throw new ExistentEntityException(RoleTrans.class,"nombre="+roleTrans.getNombre());
        } 
    }
*/
    public RoleTrans updateRoleTrans(Integer roleTransId, RoleTrans roleTrans) throws EntityNotExistentException, BusinessLogicException {
        
        RoleTrans persistedRoleTrans = getById(roleTransId);
        if (persistedRoleTrans != null) {
            if(roleTrans.getDescripcion()!=null){
                persistedRoleTrans.setDescripcion(roleTrans.getDescripcion());
            }
            if(roleTrans.getIdioma()!=null){
                persistedRoleTrans.setIdioma(roleTrans.getIdioma());
            }
            if(roleTrans.getNombreRole()!=null){
                persistedRoleTrans.setNombreRole(roleTrans.getNombreRole());
            }
            if(roleTrans.getRoleTransAmex()!=null){
                persistedRoleTrans.setRoleTransAmex(roleTrans.getRoleTransAmex());
            }
           
            return roleTransRepository.save(persistedRoleTrans);
        } else {
            throw new EntityNotExistentException(RoleTrans.class,roleTransId.toString());
        }
    }

    public void deleteRoleTrans(Integer roleTransId) throws EntityNotExistentException {
        RoleTrans roleTrans = getById(roleTransId);
        roleTrans.setBorrado(Boolean.TRUE);
        roleTransRepository.save(roleTrans);
    }

    public List<RoleTrans> findAll(){
        return roleTransRepository.findAll();
    }
}
