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
import com.sistemasmig.icaavWeb.accounting.models.Sucursales;
import com.sistemasmig.icaavWeb.accounting.models.UsuarioSucursal;
import com.sistemasmig.icaavWeb.accounting.repositories.SucursalRepository;
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
public class SucursalManager {
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    public Sucursales getById(Integer id) throws EntityNotExistentException {
        Sucursales sucursal = sucursalRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (sucursal!=null) {
             return sucursal;
        }
        throw new EntityNotExistentException(Sucursales.class,id.toString());
    }
    
    
       public PagedResponse<Sucursales> getSucursal(Sucursales filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Sucursales> cq = cb.createQuery(Sucursales.class);
        Root<Sucursales> root = cq.from(Sucursales.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getIdUsuario()!=null){
            if(filter.getIdUsuario().getAccesoHorario()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("accesoHorario"), filter.getIdUsuario().getAccesoHorario()));
            }
            if(filter.getIdUsuario().getAccesoIP()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("accesoIP"), filter.getIdUsuario().getAccesoIP()));
            }
            if(filter.getIdUsuario().getCorreo()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("correo")), "%" + filter.getIdUsuario().getCorreo().toLowerCase()+ "%"));
            }
            if(filter.getIdUsuario().getHoraAccesoFin()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("horaAccesoFin"), filter.getIdUsuario().getHoraAccesoFin()));
            }
            if(filter.getIdUsuario().getHoraAccesoIni()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("horaAccesoIni"), filter.getIdUsuario().getHoraAccesoIni()));
            }
            
            if(filter.getIdUsuario().getFechaModificacion()!=null && filter.getIdUsuario().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("idUsuario").get("fechaModificacion"), filter.getIdUsuario().getFechaModificacion(),filter.getIdUsuario().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("idUsuario").get("fechaModificacion")));
            }
            if(filter.getIdUsuario().getId()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("id"), filter.getIdUsuario().getId()));
            }
            if(filter.getIdUsuario().getIdUsuarioMod()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("idUsuarioMod"), filter.getIdUsuario().getIdUsuarioMod()));
            }
            if(filter.getIdUsuario().getInicioSesion()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("inicioSesion"), filter.getIdUsuario().getInicioSesion()));
            }
            if(filter.getIdUsuario().getIntentosIngreso()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("intentosIngreso"), filter.getIdUsuario().getIntentosIngreso()));
            }
            if(filter.getIdUsuario().getMaternoUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("maternoUsuario")), "%" + filter.getIdUsuario().getMaternoUsuario().toLowerCase()+ "%"));
            }
            if(filter.getIdUsuario().getNombreUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("nombreUsuario")), "%" + filter.getIdUsuario().getNombreUsuario().toLowerCase()+ "%"));
            }
            if(filter.getIdUsuario().getPasswordUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("passwordUsuario")), "%" + filter.getIdUsuario().getPasswordUsuario().toLowerCase()+ "%"));
            }
            if(filter.getIdUsuario().getPaternoUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("paternoUsuario")), "%" + filter.getIdUsuario().getPaternoUsuario().toLowerCase()+ "%"));
            }
            if(filter.getIdUsuario().getPrimerIngreso()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("primerIngreso"), filter.getIdUsuario().getPrimerIngreso()));
            }
            if(filter.getIdUsuario().getRegistraUsuario()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("registraUsuario")), "%" + filter.getIdUsuario().getRegistraUsuario().toLowerCase()+ "%"));
            }
            if(filter.getIdUsuario().getId()!=null){
                predicates.add(cb.like(cb.lower(root.get("idUsuario").get("usuario")), "%" + filter.getIdUsuario().getId()));
            }
            if(filter.getIdUsuario().getUsuarioAmex()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("usuarioAmex"), filter.getIdUsuario().getUsuarioAmex()));
            }
            if(filter.getIdUsuario().getVisualizarDashboard()!=null){
                predicates.add(cb.equal(root.get("idUsuario").get("visualizarDashboard"), filter.getIdUsuario().getVisualizarDashboard()));
            }
        }
          if(filter.getIdGrupoEmpresa()!=null){
            if(filter.getIdGrupoEmpresa().getAmex()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("amex"), filter.getIdGrupoEmpresa().getAmex()));
            }           
            if(filter.getIdGrupoEmpresa().getEstatusUsuarioEmpresa()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("estatusUsuarioEmpresa"), filter.getIdGrupoEmpresa().getEstatusUsuarioEmpresa()));
            }
            if(filter.getIdGrupoEmpresa().getFechaModificacionUsuarioEmpresa()!=null && filter.getIdGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()!=null){
                predicates.add(cb.between(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"), filter.getIdGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),filter.getIdGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
                cq.orderBy(cb.desc(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa")));
            }
            if(filter.getIdGrupoEmpresa().getGrupoId()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("grupoId"), filter.getIdGrupoEmpresa().getGrupoId()));
            }
            if(filter.getIdGrupoEmpresa().getId()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("id"), filter.getIdGrupoEmpresa().getId()));
            }
        }
          
        if(filter.getIdDireccion()!=null && filter.getIdDireccion().getId()!=null){
            predicates.add(cb.equal(root.get("direccion").get("id"), filter.getIdDireccion().getId()));
        }
          if(filter.getIdZonaHoraria()!=null && filter.getIdZonaHoraria().getId()!=null){
            predicates.add(cb.equal(root.get("zonaHoraria").get("id"), filter.getIdZonaHoraria().getId()));
        }
          
        if(filter.getEstatusSucursal()!=null){
                predicates.add(cb.equal(root.get("EstatusSucursal"), filter.getEstatusSucursal()));
        }
        if(filter.getClaveSucursal()!=null){
                predicates.add(cb.equal(root.get("claveSucursal"), filter.getClaveSucursal()));
        }
        if(filter.getNombre()!=null){
                predicates.add(cb.equal(root.get("nombre"), filter.getNombre()));
        }
        if(filter.getEmail()!=null){
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));
        }
        if(filter.getTelefono()!=null){
                predicates.add(cb.equal(root.get("telefono"), filter.getTelefono()));
        }
         if(filter.getIvaLocal()!=null){
                predicates.add(cb.equal(root.get("ivaLocal"), filter.getIvaLocal()));
        }
           if(filter.getIataNacional()!=null){
                predicates.add(cb.equal(root.get("iataNacional"), filter.getIataNacional()));
        }
             if(filter.getIataInternacional()!=null){
                predicates.add(cb.equal(root.get("iataInternacional"), filter.getIataInternacional()));
        }
               if(filter.getMatriz()!=null){
                predicates.add(cb.equal(root.get("matriz"), filter.getMatriz()));
        }
              if(filter.getPertenece()!=null){
                predicates.add(cb.equal(root.get("pertenece"), filter.getPertenece()));
        }
               if(filter.getEstatus()!=null){
                predicates.add(cb.equal(root.get("estatus"), filter.getEstatus()));
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
        
        TypedQuery<Sucursales> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Sucursales> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Sucursales> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Sucursales>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }
    
    
    
    
    
    
    
    public Sucursales createSucursal(Sucursales sucursales) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateSucursal(sucursales);
        //validateUnique(sucursal);
        sucursales.setBorrado(Boolean.FALSE);
        return sucursalRepository.save(sucursales);
    }

    private void validateSucursal(Sucursales sucursales) throws BusinessLogicException, EntityNotExistentException {
        if (sucursales.getIdDireccion()==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto Sucursal");
        } 
        
           
        }
    
    
        public Sucursales updateSucursales(Integer sucursalId, Sucursales sucursales) throws EntityNotExistentException, BusinessLogicException {
        
         
        Sucursales persistedSucursales = getById(sucursalId);
        
        if (persistedSucursales != null) {
            
            if(sucursales.getNombre()!=null){
                persistedSucursales.setNombre(sucursales.getNombre());
            }
            if(sucursales.getEmail()!=null){
                persistedSucursales.setEmail(sucursales.getEmail());
            }
            if(sucursales.getClaveSucursal()!=null){
                persistedSucursales.setClaveSucursal(sucursales.getClaveSucursal());
            }
            if(sucursales.getEstatus()!=null){
                persistedSucursales.setEstatus(sucursales.getEstatus());
            }
            if(sucursales.getEstatusSucursal()!=null){
                persistedSucursales.setEstatusSucursal(sucursales.getEstatusSucursal());
            }
            if(sucursales.getIataInternacional()!=null){
                persistedSucursales.setIataInternacional(sucursales.getIataInternacional());
            }
            if(sucursales.getIataNacional()!=null){
                persistedSucursales.setIataNacional(sucursales.getIataNacional());
            }
            if(sucursales.getIvaLocal()!=null){
                persistedSucursales.setIvaLocal(sucursales.getIvaLocal());
            }
            if(sucursales.getMatriz()!=null){
                persistedSucursales.setMatriz(sucursales.getMatriz());
            }
            if(sucursales.getPertenece()!=null){
                persistedSucursales.setPertenece(sucursales.getPertenece());
            }
            if(sucursales.getTelefono()!=null){
                persistedSucursales.setTelefono(sucursales.getTelefono());
            }
            if(sucursales.getFechaModificacion()!=null){
                persistedSucursales.setFechaModificacion(sucursales.getFechaModificacion());
            }
            if(sucursales.getFechaModificacion2()!=null){
                persistedSucursales.setFechaModificacion2(sucursales.getFechaModificacion2());
            }
            
      
            return sucursalRepository.save(persistedSucursales);
        } else {
            throw new EntityNotExistentException(Sucursales.class,sucursalId.toString());
        }
    }
     
      public void deleteSucursales(Integer sucursalId) throws EntityNotExistentException {
        Sucursales sucursales = getById(sucursalId);
        sucursales.setBorrado(Boolean.TRUE);
        sucursalRepository.save(sucursales);
    }
    
     public List<Sucursales> findAll(){
        return sucursalRepository.findAll();
    }
    
}
