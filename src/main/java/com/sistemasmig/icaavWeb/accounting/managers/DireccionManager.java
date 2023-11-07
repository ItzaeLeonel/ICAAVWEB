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
import com.sistemasmig.icaavWeb.accounting.models.Direccion;
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
import com.sistemasmig.icaavWeb.accounting.repositories.DireccionRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class DireccionManager {
    
    @Autowired
    private DireccionRepository direccionRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Direccion getById(Integer id) throws EntityNotExistentException {
        Direccion direccion = direccionRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (direccion!=null) {
            return direccion;
        }
        throw new EntityNotExistentException(Direccion.class,id.toString());
    }
    
    public PagedResponse<Direccion> getDireccion(Direccion filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Direccion> cq = cb.createQuery(Direccion.class);
        Root<Direccion> root = cq.from(Direccion.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getCalleDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("calleDireccion")), "%" + filter.getCalleDireccion().toLowerCase()+ "%"));
        }
        if(filter.getCiudadDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("ciudadDireccion")), "%" + filter.getCiudadDireccion().toLowerCase()+ "%"));
        }
        if(filter.getCodigoPostalDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("codigoPostalDireccion")), "%" + filter.getCodigoPostalDireccion().toLowerCase()+ "%"));
        }
        if(filter.getColoniaDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("coloniaDireccion")), "%" + filter.getColoniaDireccion().toLowerCase()+ "%"));
        }
        if(filter.getCvePais()!=null){
            predicates.add(cb.like(cb.lower(root.get("cvePais")), "%" + filter.getCvePais().toLowerCase()+ "%"));
        }
        if(filter.getEstadoDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("estadoDireccion")), "%" + filter.getEstadoDireccion().toLowerCase()+ "%"));
        }
        if(filter.getEstatusDireccionEnum()!=null){
            predicates.add(cb.equal(root.get("estatusDireccionEnum"), filter.getEstatusDireccionEnum()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getMunicipioDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("municipioDireccion")), "%" + filter.getMunicipioDireccion().toLowerCase()+ "%"));
        }
        if(filter.getNumExteriorDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("numExteriorDireccion")), "%" + filter.getNumExteriorDireccion().toLowerCase()+ "%"));
        }
        if(filter.getNumInteriorDireccion()!=null){
            predicates.add(cb.like(cb.lower(root.get("numInteriorDireccion")), "%" + filter.getNumInteriorDireccion().toLowerCase()+ "%"));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Direccion> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Direccion> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Direccion> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Direccion>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Direccion createDireccion(Direccion direccion) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateDireccion(direccion);
        //validateUnique(direccion);
        direccion.setBorrado(Boolean.FALSE);
        return direccionRepository.save(direccion);
    }

    private void validateDireccion(Direccion direccion) throws BusinessLogicException, EntityNotExistentException {
        if (direccion.getCalleDireccion()==null) {
            throw new BusinessLogicException("El campo CalleDireccion es requerido para el objeto Direccion");
        } else if (direccion.getCiudadDireccion()==null) {
            throw new BusinessLogicException("El campo CiudadDireccion es requerido para el objeto Direccion");
        } else if (direccion.getCodigoPostalDireccion()==null) {
            throw new BusinessLogicException("El campo CodigoPostalDireccion es requerido para el objeto Direccion");
        } else if (direccion.getNumExteriorDireccion()==null) {
            throw new BusinessLogicException("El campo NumExteriorDireccion es requerido para el objeto Direccion");
        }
    }
    
    /*private void validateUnique(Direccion direccion) throws ExistentEntityException {
        List<Direccion> direcciones = direccionRepository.findByNombre(direccion.getNombre());
        if (direcciones!=null && !direcciones.isEmpty()) {
            throw new ExistentEntityException(Direccion.class,"nombre="+direccion.getNombre());
        } 
    }
*/
    public Direccion updateDireccion(Integer direccionId, Direccion direccion) throws EntityNotExistentException, BusinessLogicException {
        
        Direccion persistedDireccion = getById(direccionId);
        if (persistedDireccion != null) {
            if(direccion.getCalleDireccion()!=null){
                persistedDireccion.setCalleDireccion(direccion.getCalleDireccion());
            }
            if(direccion.getCiudadDireccion()!=null){
                persistedDireccion.setCiudadDireccion(direccion.getCiudadDireccion());
            }
            if(direccion.getCodigoPostalDireccion()!=null){
                persistedDireccion.setCodigoPostalDireccion(direccion.getCodigoPostalDireccion());
            }
            if(direccion.getColoniaDireccion()!=null){
                persistedDireccion.setColoniaDireccion(direccion.getColoniaDireccion());
            }
            if(direccion.getCvePais()!=null){
                persistedDireccion.setCvePais(direccion.getCvePais());
            }
            if(direccion.getEstadoDireccion()!=null){
                persistedDireccion.setEstadoDireccion(direccion.getEstadoDireccion());
            }
            if(direccion.getEstatusDireccionEnum()!=null){
                persistedDireccion.setEstatusDireccionEnum(direccion.getEstatusDireccionEnum());
            }
            if(direccion.getMunicipioDireccion()!=null){
                persistedDireccion.setMunicipioDireccion(direccion.getMunicipioDireccion());
            }
            if(direccion.getNumExteriorDireccion()!=null){
                persistedDireccion.setNumExteriorDireccion(direccion.getNumExteriorDireccion());
            }
            if(direccion.getNumInteriorDireccion()!=null){
                persistedDireccion.setNumInteriorDireccion(direccion.getNumInteriorDireccion());
            }
            return direccionRepository.save(persistedDireccion);
        } else {
            throw new EntityNotExistentException(Direccion.class,direccionId.toString());
        }
    }

    public void deleteDireccion(Integer direccionId) throws EntityNotExistentException {
        Direccion direccion = getById(direccionId);
        direccion.setBorrado(Boolean.TRUE);
        direccionRepository.save(direccion);
    }

    public List<Direccion> findAll(){
        return direccionRepository.findAll();
    }
}
