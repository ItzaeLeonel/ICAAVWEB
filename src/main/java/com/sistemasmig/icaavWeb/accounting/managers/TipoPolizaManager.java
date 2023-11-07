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
import com.sistemasmig.icaavWeb.accounting.models.TipoPoliza;
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
import com.sistemasmig.icaavWeb.accounting.repositories.TipoPolizaRepository;

/**
 *
 * @author Julio
 */
@Component
public class TipoPolizaManager {
    
    @Autowired
    private TipoPolizaRepository tipoPolizaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    
    public TipoPoliza getById(Integer id) throws EntityNotExistentException {
        TipoPoliza tipoPoliza = tipoPolizaRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (tipoPoliza!=null) {
            return tipoPoliza;
        }
        throw new EntityNotExistentException(TipoPoliza.class,id.toString());
    }
    
    
    public PagedResponse<TipoPoliza> getTipoPoliza(TipoPoliza filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<TipoPoliza> cq = cb.createQuery(TipoPoliza.class);
        Root<TipoPoliza> root = cq.from(TipoPoliza.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getClave()!=null){
            predicates.add(cb.like(cb.lower(root.get("clave")), "%" + filter.getClave().toLowerCase()+ "%"));
        }
        if(filter.getDesc_tipo_pol()!=null){
            predicates.add(cb.like(cb.lower(root.get("desc_tipo_pol")), "%" + filter.getDesc_tipo_pol().toLowerCase()+ "%"));
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
        
        TypedQuery<TipoPoliza> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<TipoPoliza> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<TipoPoliza> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<TipoPoliza>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }
    
    public TipoPoliza createTipoPoliza(TipoPoliza tipoPoliza) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateTipoPoliza(tipoPoliza);
        //validateUnique(tipoPoliza);
        tipoPoliza.setBorrado(Boolean.FALSE);
        return tipoPolizaRepository.save(tipoPoliza);
    }

    private void validateTipoPoliza(TipoPoliza tipoPoliza) throws BusinessLogicException, EntityNotExistentException {
        if (tipoPoliza.getClave()==null) {
            throw new BusinessLogicException("El campo clave es requerido para el objeto tipo poliza");
        } else if(tipoPoliza.getDesc_tipo_pol()==null){
            throw new BusinessLogicException("El campo desc_tipo_pol es requerido para el objeto tipo poliza");
        }
            
    }
    
    public TipoPoliza updateTipoPoliza(Integer tipoPolizaId, TipoPoliza tipoPoliza) throws EntityNotExistentException, BusinessLogicException {
        
        TipoPoliza persistedTipoPoliza = getById(tipoPolizaId);
        if (persistedTipoPoliza != null) {
            if(tipoPoliza.getClave()!=null){
                persistedTipoPoliza.setClave(tipoPoliza.getClave());
            }
            if(tipoPoliza.getDesc_tipo_pol()!=null){
                persistedTipoPoliza.setDesc_tipo_pol(tipoPoliza.getDesc_tipo_pol());
            }
            if(tipoPoliza.getDesc_tipo_pol()!=null){
                persistedTipoPoliza.setDesc_tipo_pol(tipoPoliza.getDesc_tipo_pol());
            }
            if(tipoPoliza.getTipoPolizaEstatusEnum()!=null){
                persistedTipoPoliza.setTipoPolizaEstatusEnum(tipoPoliza.getTipoPolizaEstatusEnum());
            }
            return tipoPolizaRepository.save(persistedTipoPoliza);
        } else {
            throw new EntityNotExistentException(TipoPoliza.class,tipoPolizaId.toString());
        }
    }

    public void deleteTipoPoliza(Integer tipoPolizaId) throws EntityNotExistentException {
        TipoPoliza tipoPoliza = getById(tipoPolizaId);
        tipoPoliza.setBorrado(Boolean.TRUE);
        tipoPolizaRepository.save(tipoPoliza);
    }
    public List<TipoPoliza> findAll(){
        return tipoPolizaRepository.findAll();
    }
    
}
