/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;



import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.UsuarioSucursal;
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
import com.sistemasmig.icaavWeb.accounting.repositories.UsuarioSucursalRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class UsuarioSucursalManager {
 
    @Autowired
    private UsuarioSucursalRepository usuarioSucursalRepository;
    
    @Autowired
    private EntityManager entityManager;
    
     @Autowired
    private EmpresaManager empresaManager;

    public UsuarioSucursal getById(Integer id) throws EntityNotExistentException {
        UsuarioSucursal usuarioSucursal = usuarioSucursalRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (usuarioSucursal!=null) {
            return usuarioSucursal;
        }
        throw new EntityNotExistentException(UsuarioSucursal.class,id.toString());
    }
     
    public UsuarioSucursal createUsuarioSucursal(UsuarioSucursal usuarioSucursal) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateUsuarioSucursal(usuarioSucursal);
        //validateUnique(usuario);
        usuarioSucursal.setBorrado(Boolean.FALSE);
        return usuarioSucursalRepository.save(usuarioSucursal);
    }
    
    public PagedResponse<UsuarioSucursal> getUsuarioSucursal(UsuarioSucursal filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<UsuarioSucursal> cq = cb.createQuery(UsuarioSucursal.class);
        Root<UsuarioSucursal> root = cq.from(UsuarioSucursal.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getUsuario()!=null){
            if(filter.getUsuario().getAccesoHorario()!=null){
                predicates.add(cb.equal(root.get("usuario").get("accesoHorario"), filter.getUsuario().getAccesoHorario()));
            }
            if(filter.getUsuario().getAccesoIP()!=null){
                predicates.add(cb.equal(root.get("usuario").get("accesoIP"), filter.getUsuario().getAccesoIP()));
            }
            if(filter.getUsuario().getCorreo()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("correo")), "%" + filter.getUsuario().getCorreo().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getHoraAccesoFin()!=null){
                predicates.add(cb.equal(root.get("usuario").get("horaAccesoFin"), filter.getUsuario().getHoraAccesoFin()));
            }
            if(filter.getUsuario().getHoraAccesoIni()!=null){
                predicates.add(cb.equal(root.get("usuario").get("horaAccesoIni"), filter.getUsuario().getHoraAccesoIni()));
            }
            
            if(filter.getUsuario().getFechaModificacion()!=null && filter.getUsuario().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("usuario").get("fechaModificacion"), filter.getUsuario().getFechaModificacion(),filter.getUsuario().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("usuario").get("fechaModificacion")));
            }
            if(filter.getUsuario().getId()!=null){
                predicates.add(cb.equal(root.get("usuario").get("id"), filter.getUsuario().getId()));
            }
            if(filter.getUsuario().getIdUsuarioMod()!=null){
                predicates.add(cb.equal(root.get("usuario").get("idUsuarioMod"), filter.getUsuario().getIdUsuarioMod()));
            }
            if(filter.getUsuario().getInicioSesion()!=null){
                predicates.add(cb.equal(root.get("usuario").get("inicioSesion"), filter.getUsuario().getInicioSesion()));
            }
            if(filter.getUsuario().getIntentosIngreso()!=null){
                predicates.add(cb.equal(root.get("usuario").get("intentosIngreso"), filter.getUsuario().getIntentosIngreso()));
            }
            if(filter.getUsuario().getMaternoUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("maternoUsuario")), "%" + filter.getUsuario().getMaternoUsuario().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getNombreUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("nombreUsuario")), "%" + filter.getUsuario().getNombreUsuario().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getPasswordUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("passwordUsuario")), "%" + filter.getUsuario().getPasswordUsuario().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getPaternoUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("paternoUsuario")), "%" + filter.getUsuario().getPaternoUsuario().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getPrimerIngreso()!=null){
                predicates.add(cb.equal(root.get("usuario").get("primerIngreso"), filter.getUsuario().getPrimerIngreso()));
            }
            if(filter.getUsuario().getRegistraUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("registraUsuario")), "%" + filter.getUsuario().getRegistraUsuario().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("usuario").get("usuario")), "%" + filter.getUsuario().getUsuario().toLowerCase()+ "%"));
            }
            if(filter.getUsuario().getUsuarioAmex()!=null){
                predicates.add(cb.equal(root.get("usuario").get("usuarioAmex"), filter.getUsuario().getUsuarioAmex()));
            }
            if(filter.getUsuario().getVisualizarDashboard()!=null){
                predicates.add(cb.equal(root.get("usuario").get("visualizarDashboard"), filter.getUsuario().getVisualizarDashboard()));
            }
        }
        if(filter.getIdSucursal()!=null){
            predicates.add(cb.equal(root.get("idSucursal"), filter.getIdSucursal()));
        } 
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getIdUsuarioMod()!=null){
            predicates.add(cb.equal(root.get("idUsuarioMod"), filter.getIdUsuarioMod()));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<UsuarioSucursal> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<UsuarioSucursal> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<UsuarioSucursal> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<UsuarioSucursal>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    private void validateUsuarioSucursal(UsuarioSucursal usuarioSucursal) throws BusinessLogicException, EntityNotExistentException {
         if (usuarioSucursal.getIdSucursal()==null) {
            throw new BusinessLogicException("El campo sucursal es requerido para el objeto Usuario Sucursal");
        } else if (usuarioSucursal.getIdUsuarioMod()==null) {
            throw new BusinessLogicException("El campo usuario es requerido para el objeto Usuario Sucursal");
        }
    }
    
     public UsuarioSucursal updateUsuarioSucursal(Integer usuarioSucursalId, UsuarioSucursal usuarioSucursal) throws EntityNotExistentException, BusinessLogicException {
        
         if (usuarioSucursal.getIdUsuarioMod()==null) {
            throw new BusinessLogicException("El campo IdUsuarioMod es requerido para el objeto Usuario Sucursal");
            }
        UsuarioSucursal persistedUsuarioSucursal = getById(usuarioSucursalId);
        
        if (persistedUsuarioSucursal != null) {
            
            if(usuarioSucursal.getIdSucursal()!=null){
                persistedUsuarioSucursal.setIdSucursal(usuarioSucursal.getIdSucursal());
            }
            if(usuarioSucursal.getIdUsuarioMod()!=null){
                persistedUsuarioSucursal.setIdUsuarioMod(usuarioSucursal.getIdUsuarioMod());
            }
            if(usuarioSucursal.getFechaModificacion()!=null){
                persistedUsuarioSucursal.setFechaModificacion(usuarioSucursal.getFechaModificacion());
            }
            if(usuarioSucursal.getFechaModificacion2()!=null){
                persistedUsuarioSucursal.setFechaModificacion2(usuarioSucursal.getFechaModificacion2());
            }
            
      
            return usuarioSucursalRepository.save(persistedUsuarioSucursal);
        } else {
            throw new EntityNotExistentException(UsuarioSucursal.class,usuarioSucursalId.toString());
        }
    }
     
      public void deleteUsuarioSucursal(Integer usuarioSucursalId) throws EntityNotExistentException {
        UsuarioSucursal usuarioSucursal = getById(usuarioSucursalId);
        usuarioSucursal.setBorrado(Boolean.TRUE);
        usuarioSucursalRepository.save(usuarioSucursal);
    }
    
     public List<UsuarioSucursal> findAll(){
        return usuarioSucursalRepository.findAll();
    }
    
}
