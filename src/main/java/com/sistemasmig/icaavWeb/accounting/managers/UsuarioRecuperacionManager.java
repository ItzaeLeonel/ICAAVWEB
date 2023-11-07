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
import com.sistemasmig.icaavWeb.accounting.models.UsuarioRecuperacion;
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
import com.sistemasmig.icaavWeb.accounting.repositories.UsuarioRecuperacionRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class UsuarioRecuperacionManager {
    
    @Autowired
    private UsuarioRecuperacionRepository usuarioRecuperacionRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public UsuarioRecuperacion getById(Integer id) throws EntityNotExistentException {
        UsuarioRecuperacion usuarioRecuperacion = usuarioRecuperacionRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (usuarioRecuperacion!=null) {
            return usuarioRecuperacion;
        }
        throw new EntityNotExistentException(UsuarioRecuperacion.class,id.toString());
    }
    
    public PagedResponse<UsuarioRecuperacion> getUsuarioRecuperacion(UsuarioRecuperacion filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<UsuarioRecuperacion> cq = cb.createQuery(UsuarioRecuperacion.class);
        Root<UsuarioRecuperacion> root = cq.from(UsuarioRecuperacion.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getCorreo()!=null){
            predicates.add(cb.like(cb.lower(root.get("correo")), "%" + filter.getCorreo().toLowerCase()+ "%"));
        }
        if(filter.getFechaLimite()!=null && filter.getFechaLimite2()!=null){
            predicates.add(cb.between(root.get("fechaLimite"), filter.getFechaLimite(),filter.getFechaLimite2()));
            cq.orderBy(cb.desc(root.get("fechaLimite")));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getFechaSolicitud()!=null && filter.getFechaSolicitud2()!=null){
            predicates.add(cb.between(root.get("fechaSolicitud"), filter.getFechaSolicitud(),filter.getFechaSolicitud2()));
            cq.orderBy(cb.desc(root.get("fechaSolicitud")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getToken()!=null){
            predicates.add(cb.equal(root.get("token"), filter.getToken()));
        }
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
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<UsuarioRecuperacion> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<UsuarioRecuperacion> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<UsuarioRecuperacion> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<UsuarioRecuperacion>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public UsuarioRecuperacion createUsuarioRecuperacion(UsuarioRecuperacion usuarioRecuperacion) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateUsuarioRecuperacion(usuarioRecuperacion);
        //validateUnique(usuarioRecuperacion);
        usuarioRecuperacion.setBorrado(Boolean.FALSE);
        return usuarioRecuperacionRepository.save(usuarioRecuperacion);
    }

    private void validateUsuarioRecuperacion(UsuarioRecuperacion usuarioRecuperacion) throws BusinessLogicException, EntityNotExistentException {
        if (usuarioRecuperacion.getCorreo()==null) {
            throw new BusinessLogicException("El campo Correo es requerido para el objeto UsuarioRecuperacion");
        } else if (usuarioRecuperacion.getUsuario()==null) {
            throw new BusinessLogicException("El campo Usuario es requerido para el objeto UsuarioRecuperacion");
        }
    }
    
    /*private void validateUnique(UsuarioRecuperacion usuarioRecuperacion) throws ExistentEntityException {
        List<UsuarioRecuperacion> usuarioRecuperaciones = usuarioRecuperacionRepository.findByNombre(usuarioRecuperacion.getNombre());
        if (usuarioRecuperaciones!=null && !usuarioRecuperaciones.isEmpty()) {
            throw new ExistentEntityException(UsuarioRecuperacion.class,"nombre="+usuarioRecuperacion.getNombre());
        } 
    }
*/
    public UsuarioRecuperacion updateUsuarioRecuperacion(Integer usuarioRecuperacionId, UsuarioRecuperacion usuarioRecuperacion) throws EntityNotExistentException, BusinessLogicException {
        
        UsuarioRecuperacion persistedUsuarioRecuperacion = getById(usuarioRecuperacionId);
        if (persistedUsuarioRecuperacion != null) {
            if(usuarioRecuperacion.getCorreo()!=null){
                persistedUsuarioRecuperacion.setCorreo(usuarioRecuperacion.getCorreo());
            }
            if(usuarioRecuperacion.getFechaLimite()!=null){
                persistedUsuarioRecuperacion.setFechaLimite(usuarioRecuperacion.getFechaLimite());
            }
            if(usuarioRecuperacion.getFechaSolicitud()!=null){
                persistedUsuarioRecuperacion.setFechaSolicitud(usuarioRecuperacion.getFechaSolicitud());
            }
            if(usuarioRecuperacion.getToken()!=null){
                persistedUsuarioRecuperacion.setToken(usuarioRecuperacion.getToken());
            }
            if(usuarioRecuperacion.getUsuario()!=null){
                persistedUsuarioRecuperacion.setUsuario(usuarioRecuperacion.getUsuario());
            }
            
            return usuarioRecuperacionRepository.save(persistedUsuarioRecuperacion);
        } else {
            throw new EntityNotExistentException(UsuarioRecuperacion.class,usuarioRecuperacionId.toString());
        }
    }

    public void deleteUsuarioRecuperacion(Integer usuarioRecuperacionId) throws EntityNotExistentException {
        UsuarioRecuperacion usuarioRecuperacion = getById(usuarioRecuperacionId);
        usuarioRecuperacion.setBorrado(Boolean.TRUE);
        usuarioRecuperacionRepository.save(usuarioRecuperacion);
    }

    public List<UsuarioRecuperacion> findAll(){
        return usuarioRecuperacionRepository.findAll();
    }
}
