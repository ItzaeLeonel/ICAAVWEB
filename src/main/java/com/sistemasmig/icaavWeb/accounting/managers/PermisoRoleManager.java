/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.PermisoRole;
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
import com.sistemasmig.icaavWeb.accounting.repositories.PermisoRoleRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class PermisoRoleManager {
    
    @Autowired
    private PermisoRoleRepository permisoRoleRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public PermisoRole getById(Integer id) throws EntityNotExistentException {
        PermisoRole permisoRole = permisoRoleRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (permisoRole!=null) {
            return permisoRole;
        }
        throw new EntityNotExistentException(PermisoRole.class,id.toString());
    }
    
    public PagedResponse<PermisoRole> getPermisoRole(PermisoRole filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<PermisoRole> cq = cb.createQuery(PermisoRole.class);
        Root<PermisoRole> root = cq.from(PermisoRole.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getIdUsuario()!=null){
            predicates.add(cb.equal(root.get("idUsuario"), filter.getIdUsuario()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getRole()!=null){
            if(filter.getRole().getDescripcion()!=null){
                predicates.add(cb.like(cb.lower(root.get("role").get("descripcion")), "%" + filter.getRole().getDescripcion().toLowerCase()+ "%"));
            }
            if(filter.getRole().getFechaModificacion()!=null && filter.getRole().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("role").get("fechaModificacion"), filter.getRole().getFechaModificacion(),filter.getRole().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("role").get("fechaModificacion")));
            }
            if(filter.getRole().getGrupoEmpresa()!=null){
                if(filter.getRole().getGrupoEmpresa().getEstatusUsuarioEmpresa()!=null){
                    predicates.add(cb.equal(root.get("role").get("grupoEmpresa").get("estatusUsuarioEmpresa"), filter.getRole().getGrupoEmpresa().getEstatusUsuarioEmpresa()));
                }
                if(filter.getRole().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa()!=null && filter.getRole().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()!=null){
                    predicates.add(cb.between(root.get("role").get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"), filter.getRole().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),filter.getRole().getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
                    cq.orderBy(cb.desc(root.get("role").get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa")));
                }
                if(filter.getRole().getGrupoEmpresa().getGrupoId()!=null){
                    predicates.add(cb.equal(root.get("role").get("grupoEmpresa").get("grupoId"), filter.getRole().getGrupoEmpresa().getGrupoId()));
                }
                if(filter.getRole().getGrupoEmpresa().getId()!=null){
                    predicates.add(cb.equal(root.get("role").get("grupoEmpresa").get("id"), filter.getRole().getGrupoEmpresa().getId()));
                }
            }
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<PermisoRole> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<PermisoRole> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<PermisoRole> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<PermisoRole>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public PermisoRole createPermisoRole(PermisoRole permisoRole) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validatePermisoRole(permisoRole);
        //validateUnique(permisoRole);
        permisoRole.setBorrado(Boolean.FALSE);
        return permisoRoleRepository.save(permisoRole);
    }

    private void validatePermisoRole(PermisoRole permisoRole) throws BusinessLogicException, EntityNotExistentException {
        if (permisoRole.getRole()==null) {
            throw new BusinessLogicException("El campo Role es requerido para el objeto PermisoRole");
        } else if (permisoRole.getTipoPermiso()==null) {
            throw new BusinessLogicException("El campo TipoPermiso es requerido para el objeto PermisoRole");
        }
    }
    
    /*private void validateUnique(PermisoRole permisoRole) throws ExistentEntityException {
        List<PermisoRole> permisoRolees = permisoRoleRepository.findByNombre(permisoRole.getNombre());
        if (permisoRolees!=null && !permisoRolees.isEmpty()) {
            throw new ExistentEntityException(PermisoRole.class,"nombre="+permisoRole.getNombre());
        } 
    }
*/
    public PermisoRole updatePermisoRole(Integer permisoRoleId, PermisoRole permisoRole) throws EntityNotExistentException, BusinessLogicException {
        
        PermisoRole persistedPermisoRole = getById(permisoRoleId);
        if (persistedPermisoRole != null) {
            if(permisoRole.getIdUsuario()!=null){
                persistedPermisoRole.setIdUsuario(permisoRole.getIdUsuario());
            }
            if(permisoRole.getRole()!=null){
                persistedPermisoRole.setRole(permisoRole.getRole());
            }
            if(permisoRole.getSubmodulo()!=null){
                persistedPermisoRole.setSubmodulo(permisoRole.getSubmodulo());
            }
            if(permisoRole.getTipoPermiso()!=null){
                persistedPermisoRole.setTipoPermiso(permisoRole.getTipoPermiso());
            }
            
            return permisoRoleRepository.save(persistedPermisoRole);
        } else {
            throw new EntityNotExistentException(PermisoRole.class,permisoRoleId.toString());
        }
    }

    public void deletePermisoRole(Integer permisoRoleId) throws EntityNotExistentException {
        PermisoRole permisoRole = getById(permisoRoleId);
        permisoRole.setBorrado(Boolean.TRUE);
        permisoRoleRepository.save(permisoRole);
    }

    public List<PermisoRole> findAll(){
        return permisoRoleRepository.findAll();
    }
}
