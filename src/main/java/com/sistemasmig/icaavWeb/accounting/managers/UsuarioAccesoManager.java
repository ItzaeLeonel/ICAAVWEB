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
import com.sistemasmig.icaavWeb.accounting.models.UsuarioAcceso;
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
import com.sistemasmig.icaavWeb.accounting.repositories.UsuarioAccesoRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class UsuarioAccesoManager {
    
    @Autowired
    private UsuarioAccesoRepository usuarioAccesoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public UsuarioAcceso getById(Integer id) throws EntityNotExistentException {
        UsuarioAcceso usuarioAcceso = usuarioAccesoRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (usuarioAcceso!=null) {
            return usuarioAcceso;
        }
        throw new EntityNotExistentException(UsuarioAcceso.class,id.toString());
    }
    
    public PagedResponse<UsuarioAcceso> getUsuarioAcceso(UsuarioAcceso filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<UsuarioAcceso> cq = cb.createQuery(UsuarioAcceso.class);
        Root<UsuarioAcceso> root = cq.from(UsuarioAcceso.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getAccesoPor()!=null){
           predicates.add(cb.like(cb.lower(root.get("accesoPor")), "%" + filter.getAccesoPor().toLowerCase()+ "%"));
        }
        if(filter.getDescripcion()!=null){
           predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase()+ "%"));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getIdUsuarioMod()!=null){
            predicates.add(cb.equal(root.get("idUsuarioMod"), filter.getIdUsuarioMod()));
        }
        if(filter.getTipoUsuarioAccesoEnum()!=null){
            predicates.add(cb.equal(root.get("tipoUsuarioAccesoEnum"), filter.getTipoUsuarioAccesoEnum()));
        }
        if(filter.getEmpresa()!=null){
            if(filter.getEmpresa().getAmex()!=null){
                predicates.add(cb.equal(root.get("empresa").get("amex"), filter.getEmpresa().getAmex()));
            }
            if(filter.getEmpresa().getBaseDatosId()!=null){
                predicates.add(cb.equal(root.get("empresa").get("baseDatosId"), filter.getEmpresa().getBaseDatosId()));
            }

            if(filter.getEmpresa().getClave()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("clave")), "%" + filter.getEmpresa().getClave().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getClavePais()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("clavePais")), "%" + filter.getEmpresa().getClavePais().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getCodigoAgencia()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("codigoAgencia")), "%" + filter.getEmpresa().getCodigoAgencia().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getCodigoOficina()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("codigoOficina")), "%" + filter.getEmpresa().getCodigoOficina().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getEmail()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("email")), "%" + filter.getEmpresa().getEmail().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getEstatus()!=null){
                predicates.add(cb.equal(root.get("empresa").get("estatus"), filter.getEmpresa().getEstatus()));
            }
            if(filter.getEmpresa().getFechaModificacion()!=null && filter.getEmpresa().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("empresa").get("fechaModificacion"), filter.getEmpresa().getFechaModificacion(),filter.getEmpresa().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("empresa").get("fechaModificacion")));
            }
            if(filter.getEmpresa().getFormaPagoFolios()!=null){
                predicates.add(cb.equal(root.get("empresa").get("formaPagoFolios"), filter.getEmpresa().getFormaPagoFolios()));
            }
            if(filter.getEmpresa().getNombre()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("nombre")), "%" + filter.getEmpresa().getNombre().toLowerCase()+ "%"));
            }
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
        
        TypedQuery<UsuarioAcceso> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<UsuarioAcceso> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<UsuarioAcceso> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<UsuarioAcceso>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public UsuarioAcceso createUsuarioAcceso(UsuarioAcceso usuarioAcceso) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateUsuarioAcceso(usuarioAcceso);
        //validateUnique(usuarioAcceso);
        usuarioAcceso.setBorrado(Boolean.FALSE);
        return usuarioAccesoRepository.save(usuarioAcceso);
    }

    private void validateUsuarioAcceso(UsuarioAcceso usuarioAcceso) throws BusinessLogicException, EntityNotExistentException {
        if (usuarioAcceso.getUsuario()==null) {
            throw new BusinessLogicException("El campo Usuario es requerido para el objeto UsuarioAcceso");
        } 
    }
    
    /*private void validateUnique(UsuarioAcceso usuarioAcceso) throws ExistentEntityException {
        List<UsuarioAcceso> usuarioAccesoes = usuarioAccesoRepository.findByNombre(usuarioAcceso.getNombre());
        if (usuarioAccesoes!=null && !usuarioAccesoes.isEmpty()) {
            throw new ExistentEntityException(UsuarioAcceso.class,"nombre="+usuarioAcceso.getNombre());
        } 
    }
*/
    public UsuarioAcceso updateUsuarioAcceso(Integer usuarioAccesoId, UsuarioAcceso usuarioAcceso) throws EntityNotExistentException, BusinessLogicException {
        
        UsuarioAcceso persistedUsuarioAcceso = getById(usuarioAccesoId);
        if (persistedUsuarioAcceso != null) {
            if(usuarioAcceso.getAccesoPor()!=null){
                persistedUsuarioAcceso.setAccesoPor(usuarioAcceso.getAccesoPor());
            }
            if(usuarioAcceso.getDescripcion()!=null){
                persistedUsuarioAcceso.setDescripcion(usuarioAcceso.getDescripcion());
            }
            if(usuarioAcceso.getEmpresa()!=null){
                persistedUsuarioAcceso.setEmpresa(usuarioAcceso.getEmpresa());
            }
            if(usuarioAcceso.getIdUsuarioMod()!=null){
                persistedUsuarioAcceso.setIdUsuarioMod(usuarioAcceso.getIdUsuarioMod());
            }
            if(usuarioAcceso.getTipoUsuarioAccesoEnum()!=null){
                persistedUsuarioAcceso.setTipoUsuarioAccesoEnum(usuarioAcceso.getTipoUsuarioAccesoEnum());
            }
            if(usuarioAcceso.getUsuario()!=null){
                persistedUsuarioAcceso.setUsuario(usuarioAcceso.getUsuario());
            }
            
            return usuarioAccesoRepository.save(persistedUsuarioAcceso);
        } else {
            throw new EntityNotExistentException(UsuarioAcceso.class,usuarioAccesoId.toString());
        }
    }

    public void deleteUsuarioAcceso(Integer usuarioAccesoId) throws EntityNotExistentException {
        UsuarioAcceso usuarioAcceso = getById(usuarioAccesoId);
        usuarioAcceso.setBorrado(Boolean.TRUE);
        usuarioAccesoRepository.save(usuarioAcceso);
    }

    public List<UsuarioAcceso> findAll(){
        return usuarioAccesoRepository.findAll();
    }
}
