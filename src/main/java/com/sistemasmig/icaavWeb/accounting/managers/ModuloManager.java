/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Modulo;
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
import com.sistemasmig.icaavWeb.accounting.repositories.ModuloRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class ModuloManager {
    
    @Autowired
    private ModuloRepository moduloRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Modulo getById(Integer id) throws EntityNotExistentException {
        Modulo modulo = moduloRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (modulo!=null) {
            return modulo;
        }
        throw new EntityNotExistentException(Modulo.class,id.toString());
    }
    
    public PagedResponse<Modulo> getModulo(Modulo filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Modulo> cq = cb.createQuery(Modulo.class);
        Root<Modulo> root = cq.from(Modulo.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getIdSistema()!=null){
            predicates.add(cb.equal(root.get("idSistema"), filter.getIdSistema()));
        }
        if(filter.getNombreModulo()!=null){
            predicates.add(cb.equal(root.get("nombreModulo"), filter.getNombreModulo()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getTipoPaquete()!=null){
            if(filter.getTipoPaquete().getDescripcion()!=null){
                predicates.add(cb.like(cb.lower(root.get("tipoPaquete").get("descripcion")), "%" + filter.getTipoPaquete().getDescripcion().toLowerCase()+ "%"));
            }
            if(filter.getTipoPaquete().getEspacioAlmacenamiento()!=null){
                predicates.add(cb.equal(root.get("tipoPaquete").get("espacioAlmacenamiento"), filter.getTipoPaquete().getEspacioAlmacenamiento()));
            }
            if(filter.getTipoPaquete().getEspacioGB()!=null){
                predicates.add(cb.equal(root.get("tipoPaquete").get("espacioGB"), filter.getTipoPaquete().getEspacioGB()));
            }
            if(filter.getTipoPaquete().getId()!=null){
                predicates.add(cb.equal(root.get("tipoPaquete").get("id"), filter.getTipoPaquete().getId()));
            }
            if(filter.getTipoPaquete().getIdSubsistema()!=null){
                predicates.add(cb.equal(root.get("tipoPaquete").get("idSubsistema"), filter.getTipoPaquete().getIdSubsistema()));
            }
            if(filter.getTipoPaquete().getNombre()!=null){
                predicates.add(cb.like(cb.lower(root.get("tipoPaquete").get("nombre")), "%" + filter.getTipoPaquete().getNombre().toLowerCase()+ "%"));
            }
            if(filter.getTipoPaquete().getNumeroFolios()!=null){
                predicates.add(cb.equal(root.get("tipoPaquete").get("numeroFolios"), filter.getTipoPaquete().getNumeroFolios()));
            }
            if(filter.getTipoPaquete().getNumeroUsuarios()!=null){
                predicates.add(cb.equal(root.get("tipoPaquete").get("numeroUsuarios"), filter.getTipoPaquete().getNumeroUsuarios()));
            }
            
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Modulo> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Modulo> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Modulo> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Modulo>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Modulo createModulo(Modulo modulo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateModulo(modulo);
        //validateUnique(modulo);
        modulo.setBorrado(Boolean.FALSE);
        return moduloRepository.save(modulo);
    }

    private void validateModulo(Modulo modulo) throws BusinessLogicException, EntityNotExistentException {
        if (modulo.getNombreModulo()==null) {
            throw new BusinessLogicException("El campo NombreModulo es requerido para el objeto Modulo");
        } 
    }
    
    /*private void validateUnique(Modulo modulo) throws ExistentEntityException {
        List<Modulo> moduloes = moduloRepository.findByNombre(modulo.getNombre());
        if (moduloes!=null && !moduloes.isEmpty()) {
            throw new ExistentEntityException(Modulo.class,"nombre="+modulo.getNombre());
        } 
    }
*/
    public Modulo updateModulo(Integer moduloId, Modulo modulo) throws EntityNotExistentException, BusinessLogicException {
        
        Modulo persistedModulo = getById(moduloId);
        if (persistedModulo != null) {
            if(modulo.getIdSistema()!=null){
                persistedModulo.setIdSistema(modulo.getIdSistema());
            }
            if(modulo.getNombreModulo()!=null){
                persistedModulo.setNombreModulo(modulo.getNombreModulo());
            }
            if(modulo.getTipoPaquete()!=null){
                persistedModulo.setTipoPaquete(modulo.getTipoPaquete());
            }
            
            return moduloRepository.save(persistedModulo);
        } else {
            throw new EntityNotExistentException(Modulo.class,moduloId.toString());
        }
    }

    public void deleteModulo(Integer moduloId) throws EntityNotExistentException {
        Modulo modulo = getById(moduloId);
        modulo.setBorrado(Boolean.TRUE);
        moduloRepository.save(modulo);
    }

    public List<Modulo> findAll(){
        return moduloRepository.findAll();
    }
}
