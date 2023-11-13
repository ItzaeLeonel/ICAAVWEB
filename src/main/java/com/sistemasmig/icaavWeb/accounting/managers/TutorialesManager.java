/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Tutoriales;
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
import com.sistemasmig.icaavWeb.accounting.repositories.TutorialesRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class TutorialesManager {
    
    @Autowired
    private TutorialesRepository tutorialesRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Tutoriales getById(Integer id) throws EntityNotExistentException {
        Tutoriales tutoriales = tutorialesRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (tutoriales!=null) {
            return tutoriales;
        }
        throw new EntityNotExistentException(Tutoriales.class,id.toString());
    }
    
    public PagedResponse<Tutoriales> getTutoriales(Tutoriales filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Tutoriales> cq = cb.createQuery(Tutoriales.class);
        Root<Tutoriales> root = cq.from(Tutoriales.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getDescripcion()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        if(filter.getEnlaceVideo()!=null){
            predicates.add(cb.like(cb.lower(root.get("enlaceVideo")), "%" + filter.getEnlaceVideo().toLowerCase()+ "%"));
        }
        if(filter.getEstatusTutorialesEnum()!=null){
            predicates.add(cb.equal(root.get("estatusTutorialesEnum"), filter.getEstatusTutorialesEnum()));
        }
        if(filter.getFechaPublicacion()!=null && filter.getFechaPublicacion2()!=null){
            predicates.add(cb.between(root.get("fechaPublicacion"), filter.getFechaPublicacion(),filter.getFechaPublicacion2()));
            cq.orderBy(cb.desc(root.get("fechaPublicacion")));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getModulo()!=null){
            predicates.add(cb.like(cb.lower(root.get("modulo")), "%" + filter.getModulo().toLowerCase()+ "%"));
        }
        if(filter.getNombreVideo()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombreVideo")), "%" + filter.getNombreVideo().toLowerCase()+ "%"));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Tutoriales> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Tutoriales> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Tutoriales> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Tutoriales>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Tutoriales createTutoriales(Tutoriales tutoriales) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateTutoriales(tutoriales);
        //validateUnique(tutoriales);
        tutoriales.setBorrado(Boolean.FALSE);
        return tutorialesRepository.save(tutoriales);
    }

    private void validateTutoriales(Tutoriales tutoriales) throws BusinessLogicException, EntityNotExistentException {
        if (tutoriales.getDescripcion()==null) {
            throw new BusinessLogicException("El campo Descripcion es requerido para el objeto Tutoriales");
        } else if (tutoriales.getEnlaceVideo()==null) {
            throw new BusinessLogicException("El campo EnlaceVideo es requerido para el objeto Tutoriales");
        } else if (tutoriales.getModulo()==null) {
            throw new BusinessLogicException("El campo Modulo es requerido para el objeto Tutoriales");
        } else if (tutoriales.getNombreVideo()==null) {
            throw new BusinessLogicException("El campo NombreVideo es requerido para el objeto Tutoriales");
        }
    }
    
    /*private void validateUnique(Tutoriales tutoriales) throws ExistentEntityException {
        List<Tutoriales> tutorialeses = tutorialesRepository.findByNombre(tutoriales.getNombre());
        if (tutorialeses!=null && !tutorialeses.isEmpty()) {
            throw new ExistentEntityException(Tutoriales.class,"nombre="+tutoriales.getNombre());
        } 
    }
*/
    public Tutoriales updateTutoriales(Integer tutorialesId, Tutoriales tutoriales) throws EntityNotExistentException, BusinessLogicException {
        
        Tutoriales persistedTutoriales = getById(tutorialesId);
        if (persistedTutoriales != null) {
            if(tutoriales.getDescripcion()!=null){
                persistedTutoriales.setDescripcion(tutoriales.getDescripcion());
            }
            if(tutoriales.getEnlaceVideo()!=null){
                persistedTutoriales.setEnlaceVideo(tutoriales.getEnlaceVideo());
            }
            if(tutoriales.getEstatusTutorialesEnum()!=null){
                persistedTutoriales.setEstatusTutorialesEnum(tutoriales.getEstatusTutorialesEnum());
            }
            if(tutoriales.getFechaPublicacion()!=null){
                persistedTutoriales.setFechaPublicacion(tutoriales.getFechaPublicacion());
            }
            if(tutoriales.getModulo()!=null){
                persistedTutoriales.setModulo(tutoriales.getModulo());
            }
            if(tutoriales.getNombreVideo()!=null){
                persistedTutoriales.setNombreVideo(tutoriales.getNombreVideo());
            }
            return tutorialesRepository.save(persistedTutoriales);
        } else {
            throw new EntityNotExistentException(Tutoriales.class,tutorialesId.toString());
        }
    }

    public void deleteTutoriales(Integer tutorialesId) throws EntityNotExistentException {
        Tutoriales tutoriales = getById(tutorialesId);
        tutoriales.setBorrado(Boolean.TRUE);
        tutorialesRepository.save(tutoriales);
    }

    public List<Tutoriales> findAll(){
        return tutorialesRepository.findAll();
    }
}
