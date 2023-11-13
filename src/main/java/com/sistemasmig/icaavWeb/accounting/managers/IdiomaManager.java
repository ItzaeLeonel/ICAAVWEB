/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Idioma;
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
import com.sistemasmig.icaavWeb.accounting.repositories.IdiomaRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class IdiomaManager {
    
    @Autowired
    private IdiomaRepository idiomaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Idioma getById(Integer id) throws EntityNotExistentException {
        Idioma idioma = idiomaRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (idioma!=null) {
            return idioma;
        }
        throw new EntityNotExistentException(Idioma.class,id.toString());
    }
    
    
    public Idioma getByIdioma(String lenguajeIn) throws EntityNotExistentException {
        Idioma idioma = idiomaRepository.listaprocedure(lenguajeIn.toString());
        if (idioma!=null) {
            return idioma;
        }
        throw new EntityNotExistentException(Idioma.class,lenguajeIn.toString());
    }
    
    /*
    
    public Idioma getIdioma2(String lenguaje) {
        String errCodigo = "";
        String errMensaje = "";

        idiomaRepository.getIdioma2(lenguaje, errCodigo, errMensaje);
            System.out.println(errCodigo);
            System.out.println(errMensaje);
        if (errCodigo.equals("000001")) {
            
            return null;
            
        } else {
            try {
                // Realiza otras operaciones si es necesario
                return this.getByIdioma(lenguaje);
            } catch (EntityNotExistentException ex) {
                Logger.getLogger(IdiomaManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    
    */
    
    
    
    public PagedResponse<Idioma> getIdioma(Idioma filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Idioma> cq = cb.createQuery(Idioma.class);
        Root<Idioma> root = cq.from(Idioma.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getCveIdioma()!=null){
            predicates.add(cb.like(cb.lower(root.get("cveIdioma")), "%" + filter.getCveIdioma().toLowerCase()+ "%"));
        }
        if(filter.getIdioma()!=null){
            predicates.add(cb.like(cb.lower(root.get("idioma")), "%" + filter.getIdioma().toLowerCase()+ "%"));
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
        
        TypedQuery<Idioma> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Idioma> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Idioma> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Idioma>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Idioma createIdioma(Idioma idioma) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateIdioma(idioma);
        //validateUnique(idioma);
        idioma.setBorrado(Boolean.FALSE);
        return idiomaRepository.save(idioma);
    }

    private void validateIdioma(Idioma idioma) throws BusinessLogicException, EntityNotExistentException {
        if (idioma.getIdioma()==null) {
            throw new BusinessLogicException("El campo Idioma es requerido para el objeto Idioma");
        } else if(idioma.getCveIdioma()==null){
            throw new BusinessLogicException("El campo CveIdioma es requerido para el objeto Idioma");
        }
            
    }
    
    /*private void validateUnique(Idioma idioma) throws ExistentEntityException {
        List<Idioma> idiomaes = idiomaRepository.findByNombre(idioma.getNombre());
        if (idiomaes!=null && !idiomaes.isEmpty()) {
            throw new ExistentEntityException(Idioma.class,"nombre="+idioma.getNombre());
        } 
    }
*/
    public Idioma updateIdioma(Integer idiomaId, Idioma idioma) throws EntityNotExistentException, BusinessLogicException {
        
        Idioma persistedIdioma = getById(idiomaId);
        if (persistedIdioma != null) {
            if(idioma.getCveIdioma()!=null){
                persistedIdioma.setCveIdioma(idioma.getCveIdioma());
            }
            if(idioma.getIdioma()!=null){
                persistedIdioma.setIdioma(idioma.getIdioma());
            }
            return idiomaRepository.save(persistedIdioma);
        } else {
            throw new EntityNotExistentException(Idioma.class,idiomaId.toString());
        }
    }

    public void deleteIdioma(Integer idiomaId) throws EntityNotExistentException {
        Idioma idioma = getById(idiomaId);
        idioma.setBorrado(Boolean.TRUE);
        idiomaRepository.save(idioma);
    }

    public List<Idioma> findAll(){
        return idiomaRepository.findAll();
    }
}
