/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.models.dto.CCPrecargadasSP;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.models.CuentaContablePrecargada;
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
import com.sistemasmig.icaavWeb.accounting.repositories.CuentaContablePrecRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentaContablePrecSPRepository;

/**
 *
 * @author Julio
 */
@Component
public class CuentaContablePrecargadaManager {
    
    @Autowired
    private CuentaContablePrecRepository cuentaContablePrecRepository;
    @Autowired
    private CuentaContablePrecSPRepository cuentaContablePrecSPRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    
        public CuentaContablePrecargada getById(Integer id) throws EntityNotExistentException {
        CuentaContablePrecargada cuentaContablePrecargada = cuentaContablePrecRepository.getByIdAndBorrado(id,Boolean.FALSE);
        if (cuentaContablePrecargada!=null) {
            return cuentaContablePrecargada;
        }
        throw new EntityNotExistentException(CuentaContablePrecargada.class,id.toString());
    }
        public PagedResponse<CuentaContablePrecargada> getCuentaContablePrecargada(CuentaContablePrecargada filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<CuentaContablePrecargada> cq = cb.createQuery(CuentaContablePrecargada.class);
        Root<CuentaContablePrecargada> root = cq.from(CuentaContablePrecargada.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getDescripcion()!=null){
            predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        if(filter.getEstatus()!=null){
            predicates.add(cb.like(cb.lower(root.get("estatus")), "%" + filter.getEstatus().toLowerCase()+ "%"));
        }
         if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getMascara()!=null){
            predicates.add(cb.like(cb.lower(root.get("mascara")), "%" + filter.getMascara().toLowerCase()+ "%"));
        }
        if(filter.getNivel1()!=null){
            predicates.add(cb.equal(root.get("nivel1"), filter.getNivel1()));
        }
        if(filter.getNivel2()!=null){
            predicates.add(cb.equal(root.get("nivel2"), filter.getNivel2()));
        }
        if(filter.getNivel3()!=null){
            predicates.add(cb.equal(root.get("nivel3"), filter.getNivel3()));
        }
        if(filter.getNivel4()!=null){
            predicates.add(cb.equal(root.get("nivel4"), filter.getNivel4()));
        }
        if(filter.getNivel5()!=null){
            predicates.add(cb.equal(root.get("nivel5"), filter.getNivel5()));
        }
        if(filter.getNivel6()!=null){
            predicates.add(cb.equal(root.get("nivel6"), filter.getNivel6()));
        }
        if(filter.getNivel7()!=null){
            predicates.add(cb.equal(root.get("nivel7"), filter.getNivel7()));
        }
        if(filter.getNivel8()!=null){
            predicates.add(cb.equal(root.get("nivel8"), filter.getNivel8()));
        }
        if(filter.getNivel9()!=null){
            predicates.add(cb.equal(root.get("nivel9"), filter.getNivel9()));
        }
        if(filter.getNo_digitos()!=null){
            predicates.add(cb.equal(root.get("no_digitos"), filter.getNo_digitos()));
        }
        if(filter.getNo_niveles()!=null){
            predicates.add(cb.equal(root.get("no_niveles"), filter.getNo_niveles()));
        }
        if(filter.getNombre()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase()+ "%"));
        }
        
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<CuentaContablePrecargada> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<CuentaContablePrecargada> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<CuentaContablePrecargada> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<CuentaContablePrecargada>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }
    
    
    public CuentaContablePrecargada createCuentaContablePrecargada(CuentaContablePrecargada cuentaContablePrecargada) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validatecuentaContablePrecargada(cuentaContablePrecargada);
        //validateUnique(cuentaContablePrecargada);
        cuentaContablePrecargada.setBorrado(Boolean.FALSE);
        return cuentaContablePrecRepository.save(cuentaContablePrecargada);
    }

    private void validatecuentaContablePrecargada(CuentaContablePrecargada cuentaContablePrecargada) throws BusinessLogicException, EntityNotExistentException {
        if (cuentaContablePrecargada.getDescripcion()==null) {
            throw new BusinessLogicException("El campo descripcion es requerido para el objeto cuenta contable precargada");
        } else if(cuentaContablePrecargada.getEstatus()==null){
            throw new BusinessLogicException("El campo estatus es requerido para el objeto cuenta contable precargada");
        }
            
    }
    public CuentaContablePrecargada updateCuentaContablePrecargada(Integer cuentaContablePrecargadaId, CuentaContablePrecargada cuentaContablePrecargada) throws EntityNotExistentException, BusinessLogicException {
        
        CuentaContablePrecargada persistedCuentaContablePrecargada = getById(cuentaContablePrecargadaId);
        if (persistedCuentaContablePrecargada != null) {
            if(cuentaContablePrecargada.getEstatus()!=null){
                persistedCuentaContablePrecargada.setEstatus(cuentaContablePrecargada.getEstatus());
            }
            if(cuentaContablePrecargada.getDescripcion()!=null){
                persistedCuentaContablePrecargada.setDescripcion(cuentaContablePrecargada.getDescripcion());
            }
            if(cuentaContablePrecargada.getMascara()!=null){
                persistedCuentaContablePrecargada.setMascara(cuentaContablePrecargada.getMascara());
            }
            if(cuentaContablePrecargada.getNombre()!=null){
                persistedCuentaContablePrecargada.setNombre(cuentaContablePrecargada.getNombre());
            }
            if(cuentaContablePrecargada.getNo_digitos()!=null){
                persistedCuentaContablePrecargada.setNo_digitos(cuentaContablePrecargada.getNo_digitos());
            }
            if(cuentaContablePrecargada.getNo_niveles()!=null){
                persistedCuentaContablePrecargada.setNo_niveles(cuentaContablePrecargada.getNo_niveles());
            }
            if(cuentaContablePrecargada.getNivel1()!=null){
                persistedCuentaContablePrecargada.setNivel1(cuentaContablePrecargada.getNivel1());
            }
            if(cuentaContablePrecargada.getNivel2()!=null){
                persistedCuentaContablePrecargada.setNivel2(cuentaContablePrecargada.getNivel2());
            }
            if(cuentaContablePrecargada.getNivel3()!=null){
                persistedCuentaContablePrecargada.setNivel3(cuentaContablePrecargada.getNivel3());
            }
            if(cuentaContablePrecargada.getNivel4()!=null){
                persistedCuentaContablePrecargada.setNivel4(cuentaContablePrecargada.getNivel4());
            }
            if(cuentaContablePrecargada.getNivel5()!=null){
                persistedCuentaContablePrecargada.setNivel5(cuentaContablePrecargada.getNivel5());
            }
            if(cuentaContablePrecargada.getNivel6()!=null){
                persistedCuentaContablePrecargada.setNivel6(cuentaContablePrecargada.getNivel6());
            }
            if(cuentaContablePrecargada.getNivel7()!=null){
                persistedCuentaContablePrecargada.setNivel7(cuentaContablePrecargada.getNivel7());
            }
            if(cuentaContablePrecargada.getNivel8()!=null){
                persistedCuentaContablePrecargada.setNivel8(cuentaContablePrecargada.getNivel8());
            }
            if(cuentaContablePrecargada.getNivel9()!=null){
                persistedCuentaContablePrecargada.setNivel9(cuentaContablePrecargada.getNivel9());
            }
            return cuentaContablePrecRepository.save(persistedCuentaContablePrecargada);
        } else {
            throw new EntityNotExistentException(CuentaContablePrecargada.class,cuentaContablePrecargadaId.toString());
        }
    }

    public void deleteCuentaContablePrecargada(Integer cuentaContablePrecargadaId) throws EntityNotExistentException {
        CuentaContablePrecargada cuentaContablePrecargada = getById(cuentaContablePrecargadaId);
        cuentaContablePrecargada.setBorrado(Boolean.TRUE);
        cuentaContablePrecRepository.save(cuentaContablePrecargada);
    }
     public List<CuentaContablePrecargada> findAll(){
        return cuentaContablePrecRepository.findAll();
    }
     
     
     
     
     public CuentaContablePrecargada getByCuentaC(Integer pr_id_modelo_contable) throws EntityNotExistentException {
        CuentaContablePrecargada cuentaContablePrecargada = cuentaContablePrecRepository.listaprocedure(pr_id_modelo_contable);
        if (cuentaContablePrecargada!=null) {
            return cuentaContablePrecargada;
        }
        throw new EntityNotExistentException(CuentaContablePrecargada.class,pr_id_modelo_contable.toString());
    }
     
     
    public List <CCPrecargadasSP> getByCCPrecargadasSP(Integer pr_id_modelo_contable) throws EntityNotExistentException {
    List<CCPrecargadasSP>resultado=cuentaContablePrecSPRepository.getCCPrecargadaSP(pr_id_modelo_contable);
    return resultado;
    } 
     
     
    
}
