/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.models.ClasificacionCtas;
import com.sistemasmig.icaavWeb.accounting.repositories.ClasificacionCtasRepository;
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

/**
 *
 * @author Julio
 */
@Component
public class ClasificacionCtasManager {
    
    
    @Autowired
    private ClasificacionCtasRepository clasificacionCtasRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public ClasificacionCtas getById(Integer id) throws EntityNotExistentException {
        ClasificacionCtas clasificacionCtas = clasificacionCtasRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (clasificacionCtas!=null) {
            return clasificacionCtas;
        }
        throw new EntityNotExistentException(ClasificacionCtas.class,id.toString());
    }
    
    public PagedResponse<ClasificacionCtas> getClasificacionCtas(ClasificacionCtas filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<ClasificacionCtas> cq = cb.createQuery(ClasificacionCtas.class);
        Root<ClasificacionCtas> root = cq.from(ClasificacionCtas.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getIniciaCon()!=null){
            predicates.add(cb.equal(root.get("iniciaCon"), filter.getIniciaCon()));
        }
        if(filter.getClasificacion()!=null){
            predicates.add(cb.equal(root.get("clasificacion"), filter.getClasificacion()));
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
        
        TypedQuery<ClasificacionCtas> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<ClasificacionCtas> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<ClasificacionCtas> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<ClasificacionCtas>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public ClasificacionCtas createClasificacionCtas(ClasificacionCtas clasificacionCtas) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateClasificacionCtas(clasificacionCtas);
        //validateUnique(clasificacionCtas);
        clasificacionCtas.setBorrado(Boolean.FALSE);
        return clasificacionCtasRepository.save(clasificacionCtas);
    }

    private void validateClasificacionCtas(ClasificacionCtas clasificacionCtas) throws BusinessLogicException, EntityNotExistentException {
        if (clasificacionCtas.getIniciaCon()==null) {
            throw new BusinessLogicException("El campo IniciaCon es requerido para el objeto Clasificacion Cuentas");
        } else if (clasificacionCtas.getClasificacion()==null) {
            throw new BusinessLogicException("El campo Clasificacion es requerido para el objeto Clasificacion Cuentas");
        }
    }
    
    /*private void validateUnique(ClasificacionCtas clasificacionCtas) throws ExistentEntityException {
        List<ClasificacionCtas> clasificacionCtases = clasificacionCtasRepository.findByNombre(clasificacionCtas.getNombre());
        if (clasificacionCtases!=null && !clasificacionCtases.isEmpty()) {
            throw new ExistentEntityException(ClasificacionCtas.class,"nombre="+clasificacionCtas.getNombre());
        } 
    }
*/
    public ClasificacionCtas updateClasificacionCtas(Integer clasificacionCtasId, ClasificacionCtas clasificacionCtas) throws EntityNotExistentException, BusinessLogicException {
        
        ClasificacionCtas persistedClasificacionCtas = getById(clasificacionCtasId);
        if (persistedClasificacionCtas != null) {
            if(clasificacionCtas.getIniciaCon()!=null){
                persistedClasificacionCtas.setIniciaCon(clasificacionCtas.getIniciaCon());
            }
            if(clasificacionCtas.getClasificacion()!=null){
                persistedClasificacionCtas.setClasificacion(clasificacionCtas.getClasificacion());
            }
            return clasificacionCtasRepository.save(persistedClasificacionCtas);
        } else {
            throw new EntityNotExistentException(ClasificacionCtas.class,clasificacionCtasId.toString());
        }
    }

    public void deleteClasificacionCtas(Integer clasificacionCtasId) throws EntityNotExistentException {
        ClasificacionCtas clasificacionCtas = getById(clasificacionCtasId);
        clasificacionCtas.setBorrado(Boolean.TRUE);
        clasificacionCtasRepository.save(clasificacionCtas);
    }

    public List<ClasificacionCtas> findAll(){
        return clasificacionCtasRepository.findAll();
    }
}
