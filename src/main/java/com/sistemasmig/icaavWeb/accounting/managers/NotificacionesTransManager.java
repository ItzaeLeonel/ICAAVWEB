/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.NotificacionesTrans;
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
import com.sistemasmig.icaavWeb.accounting.repositories.NotificacionesTransRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class NotificacionesTransManager {
    
    @Autowired
    private NotificacionesTransRepository notificacionesTransRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public NotificacionesTrans getById(Integer id) throws EntityNotExistentException {
        NotificacionesTrans notificacionesTrans = notificacionesTransRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (notificacionesTrans!=null) {
            return notificacionesTrans;
        }
        throw new EntityNotExistentException(NotificacionesTrans.class,id.toString());
    }
    
    public PagedResponse<NotificacionesTrans> getNotificacionesTrans(NotificacionesTrans filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<NotificacionesTrans> cq = cb.createQuery(NotificacionesTrans.class);
        Root<NotificacionesTrans> root = cq.from(NotificacionesTrans.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getDescripcionAlerta()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcionAlerta")), "%" + filter.getDescripcionAlerta().toLowerCase()+ "%"));   
        }
        if(filter.getDescripcionEmail()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcionEmail")), "%" + filter.getDescripcionEmail().toLowerCase()+ "%"));
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
        if(filter.getNotificaciones()!=null){
            if(filter.getNotificaciones().getClave()!=null){
                predicates.add(cb.like(cb.lower(root.get("notificaciones").get("clave")), "%" + filter.getNotificaciones().getClave().toLowerCase()+ "%"));   
            }
            if(filter.getNotificaciones().getId()!=null){
                predicates.add(cb.equal(root.get("notificaciones").get("id"), filter.getNotificaciones().getId()));
            }
            if(filter.getNotificaciones().getIdTipoNotificacion()!=null){
                predicates.add(cb.equal(root.get("notificaciones").get("idTipoNotificacion"), filter.getNotificaciones().getIdTipoNotificacion()));
            }
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<NotificacionesTrans> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<NotificacionesTrans> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<NotificacionesTrans> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<NotificacionesTrans>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public NotificacionesTrans createNotificacionesTrans(NotificacionesTrans notificacionesTrans) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateNotificacionesTrans(notificacionesTrans);
        //validateUnique(notificacionesTrans);
        notificacionesTrans.setBorrado(Boolean.FALSE);
        return notificacionesTransRepository.save(notificacionesTrans);
    }

    private void validateNotificacionesTrans(NotificacionesTrans notificacionesTrans) throws BusinessLogicException, EntityNotExistentException {
        if (notificacionesTrans.getDescripcionAlerta()==null) {
            throw new BusinessLogicException("El campo DescripcionAlerta es requerido para el objeto NotificacionesTrans");
        } else if (notificacionesTrans.getDescripcionEmail()==null) {
            throw new BusinessLogicException("El campo DescripcionEmail es requerido para el objeto NotificacionesTrans");
        } else if (notificacionesTrans.getNotificaciones()==null) {
            throw new BusinessLogicException("El campo Notificaciones es requerido para el objeto NotificacionesTrans");
        }
        
    }
    
    /*private void validateUnique(NotificacionesTrans notificacionesTrans) throws ExistentEntityException {
        List<NotificacionesTrans> notificacionesTranses = notificacionesTransRepository.findByNombre(notificacionesTrans.getNombre());
        if (notificacionesTranses!=null && !notificacionesTranses.isEmpty()) {
            throw new ExistentEntityException(NotificacionesTrans.class,"nombre="+notificacionesTrans.getNombre());
        } 
    }
*/
    public NotificacionesTrans updateNotificacionesTrans(Integer notificacionesTransId, NotificacionesTrans notificacionesTrans) throws EntityNotExistentException, BusinessLogicException {
        
        NotificacionesTrans persistedNotificacionesTrans = getById(notificacionesTransId);
        if (persistedNotificacionesTrans != null) {
            if(notificacionesTrans.getDescripcionAlerta()!=null){
                persistedNotificacionesTrans.setDescripcionAlerta(notificacionesTrans.getDescripcionAlerta());
            }
            if(notificacionesTrans.getDescripcionEmail()!=null){
                persistedNotificacionesTrans.setDescripcionEmail(notificacionesTrans.getDescripcionEmail());
            }
            if(notificacionesTrans.getIdioma()!=null){
                persistedNotificacionesTrans.setIdioma(notificacionesTrans.getIdioma());
            }
            if(notificacionesTrans.getNotificaciones()!=null){
                persistedNotificacionesTrans.setNotificaciones(notificacionesTrans.getNotificaciones());
            }
            
            return notificacionesTransRepository.save(persistedNotificacionesTrans);
        } else {
            throw new EntityNotExistentException(NotificacionesTrans.class,notificacionesTransId.toString());
        }
    }

    public void deleteNotificacionesTrans(Integer notificacionesTransId) throws EntityNotExistentException {
        NotificacionesTrans notificacionesTrans = getById(notificacionesTransId);
        notificacionesTrans.setBorrado(Boolean.TRUE);
        notificacionesTransRepository.save(notificacionesTrans);
    }

    public List<NotificacionesTrans> findAll(){
        return notificacionesTransRepository.findAll();
    }
}
