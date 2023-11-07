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
import com.sistemasmig.icaavWeb.accounting.models.TmpMascara;
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
import com.sistemasmig.icaavWeb.accounting.repositories.TmpMascaraRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class TmpMascaraManager {
    
    @Autowired
    private TmpMascaraRepository tmpMascaraRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public TmpMascara getById(Integer id) throws EntityNotExistentException {
        TmpMascara tmpMascara = tmpMascaraRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (tmpMascara!=null) {
            return tmpMascara;
        }
        throw new EntityNotExistentException(TmpMascara.class,id.toString());
    }
    
    public PagedResponse<TmpMascara> getTmpMascara(TmpMascara filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<TmpMascara> cq = cb.createQuery(TmpMascara.class);
        Root<TmpMascara> root = cq.from(TmpMascara.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getCampo()!=null){
            predicates.add(cb.like(cb.lower(root.get("campo")), "%" + filter.getCampo().toLowerCase()+ "%"));
        }
        if(filter.getMascara()!=null){
            predicates.add(cb.like(cb.lower(root.get("mascara")), "%" + filter.getMascara().toLowerCase()+ "%"));
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
        
        TypedQuery<TmpMascara> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<TmpMascara> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<TmpMascara> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<TmpMascara>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public TmpMascara createTmpMascara(TmpMascara tmpMascara) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateTmpMascara(tmpMascara);
        //validateUnique(tmpMascara);
        tmpMascara.setBorrado(Boolean.FALSE);
        return tmpMascaraRepository.save(tmpMascara);
    }

    private void validateTmpMascara(TmpMascara tmpMascara) throws BusinessLogicException, EntityNotExistentException {
        if (tmpMascara.getCampo()==null) {
            throw new BusinessLogicException("El campo Campo es requerido para el objeto TmpMascara");
        } else if (tmpMascara.getMascara()==null) {
            throw new BusinessLogicException("El campo Mascara es requerido para el objeto TmpMascara");
        }
    }
    
    /*private void validateUnique(TmpMascara tmpMascara) throws ExistentEntityException {
        List<TmpMascara> tmpMascaraes = tmpMascaraRepository.findByNombre(tmpMascara.getNombre());
        if (tmpMascaraes!=null && !tmpMascaraes.isEmpty()) {
            throw new ExistentEntityException(TmpMascara.class,"nombre="+tmpMascara.getNombre());
        } 
    }
*/
    public TmpMascara updateTmpMascara(Integer tmpMascaraId, TmpMascara tmpMascara) throws EntityNotExistentException, BusinessLogicException {
        
        TmpMascara persistedTmpMascara = getById(tmpMascaraId);
        if (persistedTmpMascara != null) {
            if(tmpMascara.getCampo()!=null){
                persistedTmpMascara.setCampo(tmpMascara.getCampo());
            }
            if(tmpMascara.getMascara()!=null){
                persistedTmpMascara.setMascara(tmpMascara.getMascara());
            }
            return tmpMascaraRepository.save(persistedTmpMascara);
        } else {
            throw new EntityNotExistentException(TmpMascara.class,tmpMascaraId.toString());
        }
    }

    public void deleteTmpMascara(Integer tmpMascaraId) throws EntityNotExistentException {
        TmpMascara tmpMascara = getById(tmpMascaraId);
        tmpMascara.setBorrado(Boolean.TRUE);
        tmpMascaraRepository.save(tmpMascara);
    }

    public List<TmpMascara> findAll(){
        return tmpMascaraRepository.findAll();
    }
}
