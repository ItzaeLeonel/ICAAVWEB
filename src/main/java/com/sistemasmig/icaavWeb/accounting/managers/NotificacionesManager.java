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
import com.sistemasmig.icaavWeb.accounting.models.Notificaciones;
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
import com.sistemasmig.icaavWeb.accounting.repositories.NotificacionesRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class NotificacionesManager {
    
    @Autowired
    private NotificacionesRepository notificacionesRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Notificaciones getById(Integer id) throws EntityNotExistentException {
        Notificaciones notificaciones = notificacionesRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (notificaciones!=null) {
            return notificaciones;
        }
        throw new EntityNotExistentException(Notificaciones.class,id.toString());
    }
    
    public PagedResponse<Notificaciones> getNotificaciones(Notificaciones filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Notificaciones> cq = cb.createQuery(Notificaciones.class);
        Root<Notificaciones> root = cq.from(Notificaciones.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getClave()!=null){
            predicates.add(cb.equal(root.get("clave"), filter.getClave()));
        }
        
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getIdTipoNotificacion()!=null){
            predicates.add(cb.equal(root.get("idTipoNotificacion"), filter.getIdTipoNotificacion()));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Notificaciones> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Notificaciones> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Notificaciones> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Notificaciones>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Notificaciones createNotificaciones(Notificaciones notificaciones) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateNotificaciones(notificaciones);
        //validateUnique(notificaciones);
        notificaciones.setBorrado(Boolean.FALSE);
        return notificacionesRepository.save(notificaciones);
    }

    private void validateNotificaciones(Notificaciones notificaciones) throws BusinessLogicException, EntityNotExistentException {
        if (notificaciones.getClave()==null) {
            throw new BusinessLogicException("El campo Clave es requerido para el objeto Notificaciones");
        } 
    }
    
    /*private void validateUnique(Notificaciones notificaciones) throws ExistentEntityException {
        List<Notificaciones> notificacioneses = notificacionesRepository.findByNombre(notificaciones.getNombre());
        if (notificacioneses!=null && !notificacioneses.isEmpty()) {
            throw new ExistentEntityException(Notificaciones.class,"nombre="+notificaciones.getNombre());
        } 
    }
*/
    public Notificaciones updateNotificaciones(Integer notificacionesId, Notificaciones notificaciones) throws EntityNotExistentException, BusinessLogicException {
        
        Notificaciones persistedNotificaciones = getById(notificacionesId);
        if (persistedNotificaciones != null) {
            if(notificaciones.getClave()!=null){
                persistedNotificaciones.setClave(notificaciones.getClave());
            }
            if(notificaciones.getIdTipoNotificacion()!=null){
                persistedNotificaciones.setIdTipoNotificacion(notificaciones.getIdTipoNotificacion());
            }
            
            return notificacionesRepository.save(persistedNotificaciones);
        } else {
            throw new EntityNotExistentException(Notificaciones.class,notificacionesId.toString());
        }
    }

    public void deleteNotificaciones(Integer notificacionesId) throws EntityNotExistentException {
        Notificaciones notificaciones = getById(notificacionesId);
        notificaciones.setBorrado(Boolean.TRUE);
        notificacionesRepository.save(notificaciones);
    }

    public List<Notificaciones> findAll(){
        return notificacionesRepository.findAll();
    }
}
