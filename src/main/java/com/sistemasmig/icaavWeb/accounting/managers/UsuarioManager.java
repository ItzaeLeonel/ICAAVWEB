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
import com.sistemasmig.icaavWeb.accounting.models.Usuario;
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
import com.sistemasmig.icaavWeb.accounting.repositories.UsuarioRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class UsuarioManager {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Usuario getById(Integer id) throws EntityNotExistentException {
        Usuario usuario = usuarioRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (usuario!=null) {
            return usuario;
        }
        throw new EntityNotExistentException(Usuario.class,id.toString());
    }
    
    public PagedResponse<Usuario> getUsuario(Usuario filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> root = cq.from(Usuario.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getAccesoHorario()!=null){
            predicates.add(cb.equal(root.get("accesoHorario"), filter.getAccesoHorario()));
        }
        if(filter.getAccesoIP()!=null){
            predicates.add(cb.equal(root.get("accesoIP"), filter.getAccesoIP()));
        }
        if(filter.getCorreo()!=null){
            predicates.add(cb.like(cb.lower(root.get("correo")), "%" + filter.getCorreo().toLowerCase()+ "%"));
        }
        if(filter.getHoraAccesoFin()!=null){
            predicates.add(cb.equal(root.get("horaAccesoFin"), filter.getHoraAccesoFin()));
        }
        if(filter.getHoraAccesoIni()!=null){
            predicates.add(cb.equal(root.get("horaAccesoIni"), filter.getHoraAccesoIni()));
        }
        if(filter.getEstiloEmpresa()!=null){
            if(filter.getEstiloEmpresa().getEstatusEstiloEmpresaEnum()!=null){
                predicates.add(cb.equal(root.get("estiloEmpresa").get("estatusEstiloEmpresaEnum"), filter.getEstiloEmpresa().getEstatusEstiloEmpresaEnum()));
            }
            if(filter.getEstiloEmpresa().getIdEstilo()!=null){
                predicates.add(cb.equal(root.get("estiloEmpresa").get("idEstilo"), filter.getEstiloEmpresa().getIdEstilo()));
            }
            if(filter.getEstiloEmpresa().getId()!=null){
                predicates.add(cb.equal(root.get("estiloEmpresa").get("id"), filter.getEstiloEmpresa().getId()));
            }
            if(filter.getEstiloEmpresa().getFechaModificacion()!=null && filter.getEstiloEmpresa().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("estiloEmpresa").get("fechaModificacion"), filter.getEstiloEmpresa().getFechaModificacion(),filter.getEstiloEmpresa().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("estiloEmpresa").get("fechaModificacion")));
            }
            
            if(filter.getEstiloEmpresa().getEmpresa()!=null){
                if(filter.getEstiloEmpresa().getEmpresa().getAmex()!=null){
                    predicates.add(cb.equal(root.get("estiloEmpresa").get("empresa").get("amex"), filter.getEstiloEmpresa().getEmpresa().getAmex()));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getBaseDatosId()!=null){
                    predicates.add(cb.equal(root.get("estiloEmpresa").get("empresa").get("baseDatosId"), filter.getEstiloEmpresa().getEmpresa().getBaseDatosId()));
                }
                
                if(filter.getEstiloEmpresa().getEmpresa().getClave()!=null){
                    predicates.add(cb.like(cb.lower(root.get("estiloEmpresa").get("empresa").get("clave")), "%" + filter.getEstiloEmpresa().getEmpresa().getClave().toLowerCase()+ "%"));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getClavePais()!=null){
                    predicates.add(cb.like(cb.lower(root.get("estiloEmpresa").get("empresa").get("clavePais")), "%" + filter.getEstiloEmpresa().getEmpresa().getClavePais().toLowerCase()+ "%"));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getCodigoAgencia()!=null){
                    predicates.add(cb.like(cb.lower(root.get("estiloEmpresa").get("empresa").get("codigoAgencia")), "%" + filter.getEstiloEmpresa().getEmpresa().getCodigoAgencia().toLowerCase()+ "%"));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getCodigoOficina()!=null){
                    predicates.add(cb.like(cb.lower(root.get("estiloEmpresa").get("empresa").get("codigoOficina")), "%" + filter.getEstiloEmpresa().getEmpresa().getCodigoOficina().toLowerCase()+ "%"));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getEmail()!=null){
                    predicates.add(cb.like(cb.lower(root.get("estiloEmpresa").get("empresa").get("email")), "%" + filter.getEstiloEmpresa().getEmpresa().getEmail().toLowerCase()+ "%"));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getEstatus()!=null){
                    predicates.add(cb.equal(root.get("estiloEmpresa").get("empresa").get("estatus"), filter.getEstiloEmpresa().getEmpresa().getEstatus()));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getFechaModificacion()!=null && filter.getEstiloEmpresa().getEmpresa().getFechaModificacion2()!=null){
                    predicates.add(cb.between(root.get("estiloEmpresa").get("empresa").get("fechaModificacion"), filter.getEstiloEmpresa().getEmpresa().getFechaModificacion(),filter.getEstiloEmpresa().getEmpresa().getFechaModificacion2()));
                    cq.orderBy(cb.desc(root.get("estiloEmpresa").get("empresa").get("fechaModificacion")));
                }
                
                if(filter.getEstiloEmpresa().getEmpresa().getFormaPagoFolios()!=null){
                    predicates.add(cb.equal(root.get("estiloEmpresa").get("empresa").get("formaPagoFolios"), filter.getEstiloEmpresa().getEmpresa().getFormaPagoFolios()));
                }
                if(filter.getEstiloEmpresa().getEmpresa().getNombre()!=null){
                    predicates.add(cb.like(cb.lower(root.get("estiloEmpresa").get("empresa").get("nombre")), "%" + filter.getEstiloEmpresa().getEmpresa().getNombre().toLowerCase()+ "%"));
                }
            }
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
        if(filter.getInicioSesion()!=null){
            predicates.add(cb.equal(root.get("inicioSesion"), filter.getInicioSesion()));
        }
        if(filter.getIntentosIngreso()!=null){
            predicates.add(cb.equal(root.get("intentosIngreso"), filter.getIntentosIngreso()));
        }
        if(filter.getMaternoUsuario()!=null){
            predicates.add(cb.like(cb.lower(root.get("maternoUsuario")), "%" + filter.getMaternoUsuario().toLowerCase()+ "%"));
        }
        if(filter.getNombreUsuario()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombreUsuario")), "%" + filter.getNombreUsuario().toLowerCase()+ "%"));
        }
        if(filter.getPasswordUsuario()!=null){
            predicates.add(cb.like(cb.lower(root.get("passwordUsuario")), "%" + filter.getPasswordUsuario().toLowerCase()+ "%"));
        }
        if(filter.getPaternoUsuario()!=null){
            predicates.add(cb.like(cb.lower(root.get("paternoUsuario")), "%" + filter.getPaternoUsuario().toLowerCase()+ "%"));
        }
        if(filter.getPrimerIngreso()!=null){
            predicates.add(cb.equal(root.get("primerIngreso"), filter.getPrimerIngreso()));
        }
        if(filter.getRegistraUsuario()!=null){
            predicates.add(cb.like(cb.lower(root.get("registraUsuario")), "%" + filter.getRegistraUsuario().toLowerCase()+ "%"));
        }
        if(filter.getUsuario()!=null){
            predicates.add(cb.like(cb.lower(root.get("usuario")), "%" + filter.getUsuario().toLowerCase()+ "%"));
        }
        if(filter.getUsuarioAmex()!=null){
            predicates.add(cb.equal(root.get("usuarioAmex"), filter.getUsuarioAmex()));
        }
        if(filter.getVisualizarDashboard()!=null){
            predicates.add(cb.equal(root.get("visualizarDashboard"), filter.getVisualizarDashboard()));
        }
        if(filter.getGrupoEmpresa()!=null){
            if(filter.getGrupoEmpresa().getAmex()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("amex"), filter.getGrupoEmpresa().getAmex()));
            }
            
            if(filter.getGrupoEmpresa().getEmpresa()!=null){
                if(filter.getGrupoEmpresa().getEmpresa().getAmex()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("amex"), filter.getGrupoEmpresa().getEmpresa().getAmex()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getBaseDatosId()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("baseDatosId"), filter.getGrupoEmpresa().getEmpresa().getBaseDatosId()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getClave()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("clave")), "%" + filter.getGrupoEmpresa().getEmpresa().getClave().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getClavePais()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("clavePais")), "%" + filter.getGrupoEmpresa().getEmpresa().getClavePais().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getCodigoAgencia()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("codigoAgencia")), "%" + filter.getGrupoEmpresa().getEmpresa().getCodigoAgencia().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getCodigoOficina()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("codigoOficina")), "%" + filter.getGrupoEmpresa().getEmpresa().getCodigoOficina().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getDireccion()!=null && filter.getGrupoEmpresa().getEmpresa().getDireccion().getId()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("direccion").get("id"), filter.getGrupoEmpresa().getEmpresa().getDireccion().getId()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getEmail()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("email")), "%" + filter.getGrupoEmpresa().getEmpresa().getEmail().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getEstatus()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("estatus"), filter.getGrupoEmpresa().getEmpresa().getEstatus()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getFechaModificacion()!=null && filter.getGrupoEmpresa().getEmpresa().getFechaModificacion2()!=null){
                    predicates.add(cb.between(root.get("grupoEmpresa").get("empresa").get("fechaModificacion"), filter.getGrupoEmpresa().getEmpresa().getFechaModificacion(),filter.getGrupoEmpresa().getEmpresa().getFechaModificacion2()));
                    cq.orderBy(cb.desc(root.get("grupoEmpresa").get("empresa").get("fechaModificacion")));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getFormaPagoFolios()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("formaPagoFolios"), filter.getGrupoEmpresa().getEmpresa().getFormaPagoFolios()));
                }

                if(filter.getGrupoEmpresa().getEmpresa().getId()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("id"), filter.getGrupoEmpresa().getEmpresa().getId()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getIdioma()!=null && filter.getGrupoEmpresa().getEmpresa().getIdioma().getId()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("idioma").get("id"), filter.getGrupoEmpresa().getEmpresa().getIdioma().getId()));
                }

                if(filter.getGrupoEmpresa().getEmpresa().getNombre()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("nombre")), "%" + filter.getGrupoEmpresa().getEmpresa().getNombre().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getNombreComercial()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("nombreComercial")), "%" + filter.getGrupoEmpresa().getEmpresa().getNombreComercial().toLowerCase()+ "%"));
                }

                if(filter.getGrupoEmpresa().getEmpresa().getRazonSocial()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("razonSocial")), "%" + filter.getGrupoEmpresa().getEmpresa().getRazonSocial().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getRfcSucursal()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("rfcSucursal")), "%" + filter.getGrupoEmpresa().getEmpresa().getRfcSucursal().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getSucursales()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("sucursales"), filter.getGrupoEmpresa().getEmpresa().getSucursales()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getTelefono()!=null){
                    predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("telefono")), "%" + filter.getGrupoEmpresa().getEmpresa().getTelefono().toLowerCase()+ "%"));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getTipoPaquete()!=null && filter.getGrupoEmpresa().getEmpresa().getTipoPaquete().getId()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("tipoPaquete").get("id"), filter.getGrupoEmpresa().getEmpresa().getTipoPaquete().getId()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getUsuarios()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("usuarios"), filter.getGrupoEmpresa().getEmpresa().getUsuarios()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getVersionCFDI()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("versionCFDI"), filter.getGrupoEmpresa().getEmpresa().getVersionCFDI()));
                }
                if(filter.getGrupoEmpresa().getEmpresa().getZonaHoraria()!=null && filter.getGrupoEmpresa().getEmpresa().getZonaHoraria().getId()!=null){
                    predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("zonaHoraria").get("id"), filter.getGrupoEmpresa().getEmpresa().getZonaHoraria().getId()));
                }
            }
            if(filter.getGrupoEmpresa().getEstatusUsuarioEmpresa()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("estatusUsuarioEmpresa"), filter.getGrupoEmpresa().getEstatusUsuarioEmpresa()));
            }
            if(filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa()!=null && filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()!=null){
                predicates.add(cb.between(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"), filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
                cq.orderBy(cb.desc(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa")));
            }
            if(filter.getGrupoEmpresa().getGrupoId()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("grupoId"), filter.getGrupoEmpresa().getGrupoId()));
            }
            if(filter.getGrupoEmpresa().getId()!=null){
                predicates.add(cb.equal(root.get("grupoEmpresa").get("id"), filter.getGrupoEmpresa().getId()));
            }
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Usuario> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Usuario> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Usuario> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Usuario>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Usuario createUsuario(Usuario usuario) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateUsuario(usuario);
        //validateUnique(usuario);
        usuario.setBorrado(Boolean.FALSE);
        return usuarioRepository.save(usuario);
    }

    private void validateUsuario(Usuario usuario) throws BusinessLogicException, EntityNotExistentException {
        if (usuario.getCorreo()==null) {
            throw new BusinessLogicException("El campo Correo es requerido para el objeto Usuario");
        } else if (usuario.getNombreUsuario()==null) {
            throw new BusinessLogicException("El campo NombreUsuario es requerido para el objeto Usuario");
        } else if (usuario.getRole()==null) {
            throw new BusinessLogicException("El campo Role es requerido para el objeto Usuario");
        } else if (usuario.getUsuario()==null) {
            throw new BusinessLogicException("El campo Usuario es requerido para el objeto Usuario");
        } else if (usuario.getPasswordUsuario()==null) {
            throw new BusinessLogicException("El campo getPasswordUsuario es requerido para el objeto Usuario");
        }
    }
    
    /*private void validateUnique(Usuario usuario) throws ExistentEntityException {
        List<Usuario> usuarioes = usuarioRepository.findByNombre(usuario.getNombre());
        if (usuarioes!=null && !usuarioes.isEmpty()) {
            throw new ExistentEntityException(Usuario.class,"nombre="+usuario.getNombre());
        } 
    }
*/
    public Usuario updateUsuario(Integer usuarioId, Usuario usuario) throws EntityNotExistentException, BusinessLogicException {
        
        Usuario persistedUsuario = getById(usuarioId);
        if (persistedUsuario != null) {
            if(usuario.getAccesoHorario()!=null){
                persistedUsuario.setAccesoHorario(usuario.getAccesoHorario());
            }
            if(usuario.getAccesoIP()!=null){
                persistedUsuario.setAccesoIP(usuario.getAccesoIP());
            }
            if(usuario.getCorreo()!=null){
                persistedUsuario.setCorreo(usuario.getCorreo());
            }
            if(usuario.getEstatusUsuarioEnum()!=null){
                persistedUsuario.setEstatusUsuarioEnum(usuario.getEstatusUsuarioEnum());
            }
            if(usuario.getEstiloEmpresa()!=null){
                persistedUsuario.setEstiloEmpresa(usuario.getEstiloEmpresa());
            }
            if(usuario.getFechaDesbloqueo()!=null){
                persistedUsuario.setFechaDesbloqueo(usuario.getFechaDesbloqueo());
            }
            if(usuario.getFechaRegistroUsuario()!=null){
                persistedUsuario.setFechaRegistroUsuario(usuario.getFechaRegistroUsuario());
            }
            if(usuario.getFechaUltConexion()!=null){
                persistedUsuario.setFechaUltConexion(usuario.getFechaUltConexion());
            }
            if(usuario.getGrupoEmpresa()!=null){
                persistedUsuario.setGrupoEmpresa(usuario.getGrupoEmpresa());
            }
            if(usuario.getHoraAccesoFin()!=null){
                persistedUsuario.setHoraAccesoFin(usuario.getHoraAccesoFin());
            }
            if(usuario.getHoraAccesoIni()!=null){
                persistedUsuario.setHoraAccesoIni(usuario.getHoraAccesoIni());
            }
            if(usuario.getIdUsuarioMod()!=null){
                persistedUsuario.setIdUsuarioMod(usuario.getIdUsuarioMod());
            }
            if(usuario.getIdioma()!=null){
                persistedUsuario.setIdioma(usuario.getIdioma());
            }
            if(usuario.getInicioSesion()!=null){
                persistedUsuario.setInicioSesion(usuario.getInicioSesion());
            }
            if(usuario.getIntentosIngreso()!=null){
                persistedUsuario.setIntentosIngreso(usuario.getIntentosIngreso());
            }
            if(usuario.getMaternoUsuario()!=null){
                persistedUsuario.setMaternoUsuario(usuario.getMaternoUsuario());
            }
            if(usuario.getNombreUsuario()!=null){
                persistedUsuario.setNombreUsuario(usuario.getNombreUsuario());
            }
            if(usuario.getPasswordUsuario()!=null){
                persistedUsuario.setPasswordUsuario(usuario.getPasswordUsuario());
            }
            if(usuario.getPaternoUsuario()!=null){
                persistedUsuario.setPaternoUsuario(usuario.getPaternoUsuario());
            }
            if(usuario.getPrimerIngreso()!=null){
                persistedUsuario.setPrimerIngreso(usuario.getPrimerIngreso());
            }
            if(usuario.getRegistraUsuario()!=null){
                persistedUsuario.setRegistraUsuario(usuario.getRegistraUsuario());
            }
            if(usuario.getRole()!=null){
                persistedUsuario.setRole(usuario.getRole());
            }
            if(usuario.getTipoUsuario()!=null){
                persistedUsuario.setTipoUsuario(usuario.getTipoUsuario());
            }
            if(usuario.getUsuario()!=null){
                persistedUsuario.setUsuario(usuario.getUsuario());
            }
            if(usuario.getUsuarioAmex()!=null){
                persistedUsuario.setUsuarioAmex(usuario.getUsuarioAmex());
            }
            if(usuario.getVisualizarDashboard()!=null){
                persistedUsuario.setVisualizarDashboard(usuario.getVisualizarDashboard());
            }
            return usuarioRepository.save(persistedUsuario);
        } else {
            throw new EntityNotExistentException(Usuario.class,usuarioId.toString());
        }
    }

    public void deleteUsuario(Integer usuarioId) throws EntityNotExistentException {
        Usuario usuario = getById(usuarioId);
        usuario.setBorrado(Boolean.TRUE);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
}
