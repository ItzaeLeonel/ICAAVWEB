/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.CuentasEspeciales;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.model.DemoLista;
import com.sistemasmig.icaavWeb.accounting.repositories.CuentasEspecialesRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentasEspecialesSPRepository;

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


import java.util.Map;


/**
 *
 * @author Julio
 */
@Component
public class CuentasEspecialesManager {
    
    @Autowired
    private CuentasEspecialesRepository cuentasEspecialesRepository;
    
    @Autowired
    private CuentasEspecialesSPRepository cuentasEspecialesSPRepository;
    
    @Autowired
    private EntityManager entityManager;
    

    public CuentasEspeciales getById(Integer id) throws EntityNotExistentException {
        CuentasEspeciales cuentasEspeciales = cuentasEspecialesRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (cuentasEspeciales!=null) {
            return cuentasEspeciales;
        }
        throw new EntityNotExistentException(CuentasEspeciales.class,id.toString());
    }
    
  
    
    
    public PagedResponse<CuentasEspeciales> getCuentasEspeciales(CuentasEspeciales filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<CuentasEspeciales> cq = cb.createQuery(CuentasEspeciales.class);
        Root<CuentasEspeciales> root = cq.from(CuentasEspeciales.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getDescripcion()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        if(filter.getFuncionalidad()!=null){
            predicates.add(cb.like(cb.lower(root.get("funcionalidad")), "%" + filter.getFuncionalidad().toLowerCase()+ "%"));
        }
        if(filter.getIva()!=null){
            predicates.add(cb.like(cb.lower(root.get("por_iva")), "%" + filter.getIva().toLowerCase()+ "%"));
        }
        if(filter.getTipoCuenta()!=null){
            predicates.add(cb.equal(root.get("tipo_cuenta"), filter.getTipoCuenta()));
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
        
        TypedQuery<CuentasEspeciales> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<CuentasEspeciales> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<CuentasEspeciales> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<CuentasEspeciales>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public CuentasEspeciales createCuentasEspeciales(CuentasEspeciales cuentasEspeciales) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateCuentasEspeciales(cuentasEspeciales);
        //validateUnique(alerta);
        cuentasEspeciales.setBorrado(Boolean.FALSE);
        return cuentasEspecialesRepository.save(cuentasEspeciales);
    }

    private void validateCuentasEspeciales(CuentasEspeciales cuentasEspeciales) throws BusinessLogicException, EntityNotExistentException {
        if (cuentasEspeciales.getDescripcion()==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto Alerta");
        } 
        else if(cuentasEspeciales.getFuncionalidad()==null){
                throw new BusinessLogicException("El campo id de GrupoEmpresa es requerido para el objeto Alerta");
            }
         
        
    }

   
    
    public CuentasEspeciales updateCuentasEspeciales(Integer cuentasEspecialesId, CuentasEspeciales cuentasEspeciales) throws EntityNotExistentException, BusinessLogicException {
        
        CuentasEspeciales persistedCuentasEspeciales = getById(cuentasEspecialesId);
        if (persistedCuentasEspeciales != null) {
            if(cuentasEspeciales.getDescripcion()!=null){
                persistedCuentasEspeciales.setDescripcion(cuentasEspeciales.getDescripcion());
            }
            if(cuentasEspeciales.getFuncionalidad()!=null){
                persistedCuentasEspeciales.setFuncionalidad(cuentasEspeciales.getFuncionalidad());
            }
            if(cuentasEspeciales.getIva()!=null){
            persistedCuentasEspeciales.setIva(cuentasEspeciales.getIva());
            }
            if(cuentasEspeciales.getTipoCuenta()!=null){
            persistedCuentasEspeciales.setTipoCuenta(cuentasEspeciales.getTipoCuenta());
            }
            return cuentasEspecialesRepository.save(persistedCuentasEspeciales);
        } else {
            throw new EntityNotExistentException(CuentasEspeciales.class,cuentasEspecialesId.toString());
        }
    }

    public void deleteCuentasEspeciales(Integer cuentasEspecialesId) throws EntityNotExistentException {
        CuentasEspeciales cuentasEspeciales = getById(cuentasEspecialesId);
        cuentasEspeciales.setBorrado(Boolean.TRUE);
        cuentasEspecialesRepository.save(cuentasEspeciales);
    }
    public List<CuentasEspeciales> findAll(){
        return cuentasEspecialesRepository.findAll();
    }
    
    public List <DemoLista> getByCuentaEspecialSP(Integer pr_id_grupo_empresa, Integer pr_tipo_cuenta) throws EntityNotExistentException {
    List<DemoLista>resultado=cuentasEspecialesSPRepository.getCuentasEspSP(pr_id_grupo_empresa, pr_tipo_cuenta);
    return resultado;
    }
    
     
}
