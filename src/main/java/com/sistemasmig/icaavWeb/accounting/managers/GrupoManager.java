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
import com.sistemasmig.icaavWeb.accounting.models.Grupo;
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
import com.sistemasmig.icaavWeb.accounting.repositories.GrupoRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class GrupoManager {
    
    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Grupo getById(Integer id) throws EntityNotExistentException {
        Grupo grupo = grupoRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (grupo!=null) {
            return grupo;
        }
        throw new EntityNotExistentException(Grupo.class,id.toString());
    }
    
    public PagedResponse<Grupo> getGrupo(Grupo filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Grupo> cq = cb.createQuery(Grupo.class);
        Root<Grupo> root = cq.from(Grupo.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getEstatusGrupo()!=null){
            predicates.add(cb.equal(root.get("estatusGrupo"), filter.getEstatusGrupo()));
        }
        if(filter.getNoLicencias()!=null){
            predicates.add(cb.equal(root.get("noLicencias"), filter.getNoLicencias()));
        }
        if(filter.getNomGrupo()!=null){
            predicates.add(cb.equal(root.get("nomGrupo"), filter.getNomGrupo()));
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
        
        TypedQuery<Grupo> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Grupo> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Grupo> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Grupo>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Grupo createGrupo(Grupo grupo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateGrupo(grupo);
        //validateUnique(grupo);
        grupo.setBorrado(Boolean.FALSE);
        return grupoRepository.save(grupo);
    }

    private void validateGrupo(Grupo grupo) throws BusinessLogicException, EntityNotExistentException {
        if (grupo.getNomGrupo()==null) {
            throw new BusinessLogicException("El campo NomGrupo es requerido para el objeto Grupo");
        } 
    }
    
    /*private void validateUnique(Grupo grupo) throws ExistentEntityException {
        List<Grupo> grupoes = grupoRepository.findByNombre(grupo.getNombre());
        if (grupoes!=null && !grupoes.isEmpty()) {
            throw new ExistentEntityException(Grupo.class,"nombre="+grupo.getNombre());
        } 
    }
*/
    public Grupo updateGrupo(Integer grupoId, Grupo grupo) throws EntityNotExistentException, BusinessLogicException {
        
        Grupo persistedGrupo = getById(grupoId);
        if (persistedGrupo != null) {
            if(grupo.getEstatusGrupo()!=null){
                persistedGrupo.setEstatusGrupo(grupo.getEstatusGrupo());
            }
            if(grupo.getNoLicencias()!=null){
                persistedGrupo.setNoLicencias(grupo.getNoLicencias());
            }
            if(grupo.getNomGrupo()!=null){
                persistedGrupo.setNomGrupo(grupo.getNomGrupo());
            }
            return grupoRepository.save(persistedGrupo);
        } else {
            throw new EntityNotExistentException(Grupo.class,grupoId.toString());
        }
    }

    public void deleteGrupo(Integer grupoId) throws EntityNotExistentException {
        Grupo grupo = getById(grupoId);
        grupo.setBorrado(Boolean.TRUE);
        grupoRepository.save(grupo);
    }

    public List<Grupo> findAll(){
        return grupoRepository.findAll();
    }
}
