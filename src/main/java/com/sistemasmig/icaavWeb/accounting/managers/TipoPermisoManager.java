/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.models.TipoPermiso;
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
import com.sistemasmig.icaavWeb.accounting.repositories.TipoPermisoRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class TipoPermisoManager {
    
    @Autowired
    private TipoPermisoRepository tipoPermisoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public TipoPermiso getById(Integer id) throws EntityNotExistentException {
        TipoPermiso tipoPermiso = tipoPermisoRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (tipoPermiso!=null) {
            return tipoPermiso;
        }
        throw new EntityNotExistentException(TipoPermiso.class,id.toString());
    }
    
    public PagedResponse<TipoPermiso> getTipoPermiso(TipoPermiso filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<TipoPermiso> cq = cb.createQuery(TipoPermiso.class);
        Root<TipoPermiso> root = cq.from(TipoPermiso.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getNombre()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase()+ "%"));
        }
        if(filter.getTraduccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("traduccion")), "%" + filter.getTraduccion().toLowerCase()+ "%"));
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
        
        TypedQuery<TipoPermiso> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<TipoPermiso> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<TipoPermiso> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<TipoPermiso>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public TipoPermiso createTipoPermiso(TipoPermiso tipoPermiso) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateTipoPermiso(tipoPermiso);
        //validateUnique(tipoPermiso);
        tipoPermiso.setBorrado(Boolean.FALSE);
        return tipoPermisoRepository.save(tipoPermiso);
    }

    private void validateTipoPermiso(TipoPermiso tipoPermiso) throws BusinessLogicException, EntityNotExistentException {
        if (tipoPermiso.getNombre()==null) {
            throw new BusinessLogicException("El campo Nombre es requerido para el objeto TipoPermiso");
        } else if (tipoPermiso.getTraduccion()==null) {
            throw new BusinessLogicException("El campo Traduccion es requerido para el objeto TipoPermiso");
        }
    }
    
    /*private void validateUnique(TipoPermiso tipoPermiso) throws ExistentEntityException {
        List<TipoPermiso> tipoPermisoes = tipoPermisoRepository.findByNombre(tipoPermiso.getNombre());
        if (tipoPermisoes!=null && !tipoPermisoes.isEmpty()) {
            throw new ExistentEntityException(TipoPermiso.class,"nombre="+tipoPermiso.getNombre());
        } 
    }
*/
    public TipoPermiso updateTipoPermiso(Integer tipoPermisoId, TipoPermiso tipoPermiso) throws EntityNotExistentException, BusinessLogicException {
        
        TipoPermiso persistedTipoPermiso = getById(tipoPermisoId);
        if (persistedTipoPermiso != null) {
            if(tipoPermiso.getNombre()!=null){
                persistedTipoPermiso.setNombre(tipoPermiso.getNombre());
            }
            if(tipoPermiso.getTraduccion()!=null){
                persistedTipoPermiso.setTraduccion(tipoPermiso.getTraduccion());
            }
            return tipoPermisoRepository.save(persistedTipoPermiso);
        } else {
            throw new EntityNotExistentException(TipoPermiso.class,tipoPermisoId.toString());
        }
    }

    public void deleteTipoPermiso(Integer tipoPermisoId) throws EntityNotExistentException {
        TipoPermiso tipoPermiso = getById(tipoPermisoId);
        tipoPermiso.setBorrado(Boolean.TRUE);
        tipoPermisoRepository.save(tipoPermiso);
    }

    public List<TipoPermiso> findAll(){
        return tipoPermisoRepository.findAll();
    }
}
