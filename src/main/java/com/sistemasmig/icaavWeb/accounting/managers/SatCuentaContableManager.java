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
import com.sistemasmig.icaavWeb.accounting.models.SatCuentaContable;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasSatBG;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasSatCA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.sistemasmig.icaavWeb.accounting.repositories.SatCuentaContableRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentasSatBGSPRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentasSatCASPRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class SatCuentaContableManager {
    
    @Autowired
    private SatCuentaContableRepository satCuentaContableRepository;
    
    @Autowired
    private CuentasSatCASPRepository cuentasSatCASPRepository;
    
    @Autowired
    private CuentasSatBGSPRepository cuentasSatBGSPRepository;
    
     @Autowired
    private EntityManager entityManager;
    

    public SatCuentaContable getById(Integer id) throws EntityNotExistentException {
        SatCuentaContable satCuentaContable = satCuentaContableRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (satCuentaContable!=null) {
            return satCuentaContable;
        }
        throw new EntityNotExistentException(SatCuentaContable.class,id.toString());
    }
    
    public PagedResponse<SatCuentaContable> getSatCuentaContable(SatCuentaContable filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<SatCuentaContable> cq = cb.createQuery(SatCuentaContable.class);
        Root<SatCuentaContable> root = cq.from(SatCuentaContable.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getCuentaMayor()!=null){
            predicates.add(cb.like(cb.lower(root.get("cuentaMayor")), "%" + filter.getCuentaMayor().toLowerCase()+ "%"));
        }
        if(filter.getCuentaSat()!=null){
            predicates.add(cb.like(cb.lower(root.get("cuentaSat")), "%" + filter.getCuentaSat().toLowerCase()+ "%"));
        }
        if(filter.getGrupo()!=null){
            predicates.add(cb.like(cb.lower(root.get("grupo")), "%" + filter.getGrupo().toLowerCase()+ "%"));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        
        if(filter.getNaturaleza()!=null){
            predicates.add(cb.like(cb.lower(root.get("naturaleza")), "%" + filter.getNaturaleza().toLowerCase()+ "%"));
        }
        if(filter.getNivel()!=null){
            predicates.add(cb.equal(root.get("nivel"), filter.getNivel()));
        }
        if(filter.getNombreCuenta()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombreCuenta")), "%" + filter.getNombreCuenta().toLowerCase()+ "%"));
        }
        if(filter.getSubGrupo()!=null){
            predicates.add(cb.like(cb.lower(root.get("subGrupo")), "%" + filter.getSubGrupo().toLowerCase()+ "%"));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<SatCuentaContable> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<SatCuentaContable> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<SatCuentaContable> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<SatCuentaContable>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public SatCuentaContable createSatCuentaContable(SatCuentaContable satCuentaContable) throws BusinessLogicException, ExistentEntityException {
        validateSatCuentaContable(satCuentaContable);
        validateUnique(satCuentaContable);
        satCuentaContable.setBorrado(Boolean.FALSE);
        return satCuentaContableRepository.save(satCuentaContable);
    }

    private void validateSatCuentaContable(SatCuentaContable satCuentaContable) throws BusinessLogicException {
        if (StringUtils.isEmpty(satCuentaContable.getNombreCuenta())) {
            throw new BusinessLogicException("El campo NombreCuenta es requerido para el objeto SatCuentaContable");
        } 
    }
    
    private void validateUnique(SatCuentaContable satCuentaContable) throws ExistentEntityException {
        
        List<SatCuentaContable> satCuentaContablees = satCuentaContableRepository.findByNombreCuentaAndBorrado(satCuentaContable.getNombreCuenta(),Boolean.FALSE);
        if (satCuentaContablees!=null && !satCuentaContablees.isEmpty()) {
            throw new ExistentEntityException(SatCuentaContable.class,"nombre cuenta="+satCuentaContable.getNombreCuenta());
        } 
    }

    public SatCuentaContable updateSatCuentaContable(Integer satCuentaContableId, SatCuentaContable satCuentaContable) throws EntityNotExistentException, BusinessLogicException {
        
        SatCuentaContable persistedSatCuentaContable = getById(satCuentaContableId);
        if (persistedSatCuentaContable != null) {
            if(satCuentaContable.getCuentaMayor()!=null){
                persistedSatCuentaContable.setCuentaMayor(satCuentaContable.getCuentaMayor());
            }
            if(satCuentaContable.getCuentaSat()!=null){
                persistedSatCuentaContable.setCuentaSat(satCuentaContable.getCuentaSat());
            }
            if(satCuentaContable.getGrupo()!=null){
                persistedSatCuentaContable.setGrupo(satCuentaContable.getGrupo());
            }
            if(satCuentaContable.getNaturaleza()!=null){
                persistedSatCuentaContable.setNaturaleza(satCuentaContable.getNaturaleza());
            }
            if(satCuentaContable.getNivel()!=null){
                persistedSatCuentaContable.setNivel(satCuentaContable.getNivel());
            }
            if(satCuentaContable.getNombreCuenta()!=null){
                persistedSatCuentaContable.setNombreCuenta(satCuentaContable.getNombreCuenta());
            }
            if(satCuentaContable.getSubGrupo()!=null){
                persistedSatCuentaContable.setSubGrupo(satCuentaContable.getSubGrupo());
            }
            return satCuentaContableRepository.save(persistedSatCuentaContable);
        } else {
            throw new EntityNotExistentException(SatCuentaContable.class,satCuentaContableId.toString());
        }
    }

    public void deleteSatCuentaContable(Integer satCuentaContableId) throws EntityNotExistentException {
        SatCuentaContable satCuentaContable = getById(satCuentaContableId);
        satCuentaContable.setBorrado(Boolean.TRUE);
        satCuentaContableRepository.save(satCuentaContable);
    }

    public List<SatCuentaContable> findAll(){
        return satCuentaContableRepository.findAll();
    }
    
    public List<SatCuentaContable> findByNombreCuenta(String nombreCuenta,Boolean borrado){
        return satCuentaContableRepository.findByNombreCuentaAndBorrado(nombreCuenta,borrado);
    }
    
    public List <ListCuentasSatCA> getByCodAgrupadorSat(String pr_id_codigo_agrupador) throws EntityNotExistentException {
    List<ListCuentasSatCA>resultado=cuentasSatCASPRepository.procedureCuentasSat(pr_id_codigo_agrupador);
    return resultado;
    } 
     
    public List <ListCuentasSatBG> getByBusqGralSat(String pr_consulta_gral) throws EntityNotExistentException {
    List<ListCuentasSatBG>resultado=cuentasSatBGSPRepository.procedureCuentasSat(pr_consulta_gral);
    return resultado;
    }
}
