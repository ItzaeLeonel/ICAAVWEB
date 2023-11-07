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
import com.sistemasmig.icaavWeb.accounting.models.AsignacionTipoPolizas;
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
import com.sistemasmig.icaavWeb.accounting.repositories.AsignacionTipoPolizasRepository;

/**
 *
 * @author Julio
 */
@Component
public class AsignacionTipoPolizasManager {
    
    @Autowired
    private AsignacionTipoPolizasRepository asignacionTipoPolizasRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    public AsignacionTipoPolizas getById(Integer id) throws EntityNotExistentException {
        AsignacionTipoPolizas asignacionTipoPolizas = asignacionTipoPolizasRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (asignacionTipoPolizas!=null) {
            return asignacionTipoPolizas;
        }
        throw new EntityNotExistentException(AsignacionTipoPolizas.class,id.toString());
    }
    
    public AsignacionTipoPolizas createAsignacionTipoPolizas(AsignacionTipoPolizas asignacionTipoPolizas) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateasignacionTipoPolizas(asignacionTipoPolizas);
        //validateUnique(tutoriales);
        asignacionTipoPolizas.setBorrado(Boolean.FALSE);
        return asignacionTipoPolizasRepository.save(asignacionTipoPolizas);
    }
    
    public PagedResponse<AsignacionTipoPolizas> getAsignacionTipoPolizas(AsignacionTipoPolizas filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<AsignacionTipoPolizas> cq = cb.createQuery(AsignacionTipoPolizas.class);
        Root<AsignacionTipoPolizas> root = cq.from(AsignacionTipoPolizas.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
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
        if(filter.getTipoPol()!=null){
            predicates.add(cb.equal(root.get("tipoPol"), filter.getTipoPol()));
        }
        if(filter.getTipoSatEnum()!=null){
            predicates.add(cb.equal(root.get("tipoSatEnum"), filter.getTipoSatEnum()));
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
        
        if(filter.getTipoPol()!=null){
                if(filter.getTipoPol().getClave()!=null){
                    predicates.add(cb.equal(root.get("tipoPol").get("clave"), filter.getTipoPol().getClave()));
                }
                 if(filter.getTipoPol().getDesc_tipo_pol()!=null){
                    predicates.add(cb.equal(root.get("tipoPol").get("descTipoPol"), filter.getTipoPol().getDesc_tipo_pol()));
                }
                 if(filter.getTipoPol().getTipoPolizaEstatusEnum()!=null){
                    predicates.add(cb.equal(root.get("tipoPol").get("tipoPolizaEstatusEnum"), filter.getTipoPol().getTipoPolizaEstatusEnum()));
                }
                  if(filter.getTipoPol().getId()!=null){
                    predicates.add(cb.equal(root.get("tipoPol").get("id"), filter.getTipoPol().getId()));
                }
                if(filter.getTipoPol().getFechaModificacion()!=null && filter.getTipoPol().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("tipoPol").get("fechaModificacion"), filter.getTipoPol().getFechaModificacion(),filter.getTipoPol().getFechaModificacion2()));
                 cq.orderBy(cb.desc(root.get("tipoPol").get("fechaModificacion")));
        }
        }
        
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<AsignacionTipoPolizas> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<AsignacionTipoPolizas> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<AsignacionTipoPolizas> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<AsignacionTipoPolizas>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }
    
    public AsignacionTipoPolizas updateAsignacionTipoPolizas(Integer asignacionTipoPolizasId, AsignacionTipoPolizas asignacionTipoPolizas) throws EntityNotExistentException, BusinessLogicException {
        
        AsignacionTipoPolizas persistedAsignacionTipoPolizas = getById(asignacionTipoPolizasId);
        if (persistedAsignacionTipoPolizas != null) {
            if(asignacionTipoPolizas.getGrupoEmpresa()!=null){
                persistedAsignacionTipoPolizas.setGrupoEmpresa(asignacionTipoPolizas.getGrupoEmpresa());
            }
            if(asignacionTipoPolizas.getTipoPol()!=null){
                persistedAsignacionTipoPolizas.setTipoPol(asignacionTipoPolizas.getTipoPol());
            }
            if(asignacionTipoPolizas.getTipoSatEnum()!=null){
                persistedAsignacionTipoPolizas.setTipoSatEnum(asignacionTipoPolizas.getTipoSatEnum());
            }
            if(asignacionTipoPolizas.getUsuario()!=null){
                persistedAsignacionTipoPolizas.setUsuario(asignacionTipoPolizas.getUsuario());
            }
            return asignacionTipoPolizasRepository.save(persistedAsignacionTipoPolizas);
        } else {
            throw new EntityNotExistentException(AsignacionTipoPolizas.class,asignacionTipoPolizasId.toString());
        }
    }
    

    private void validateasignacionTipoPolizas(AsignacionTipoPolizas asignacionTipoPolizas) throws BusinessLogicException, EntityNotExistentException {
        if (asignacionTipoPolizas.getGrupoEmpresa()==null) {
            throw new BusinessLogicException("El campo Grupo Empresa es requerido para el objeto Asignacion Tipo Poliza");
        } else if (asignacionTipoPolizas.getTipoPol()==null) {
            throw new BusinessLogicException("El campo Tipo Poliza es requerido para el objeto Asignacion Tipo Poliza");
        } 
        
    }
    public void deleteasignacionTipoPolizas(Integer alertaId) throws EntityNotExistentException {
        AsignacionTipoPolizas asignacionTipoPolizas = getById(alertaId);
        asignacionTipoPolizas.setBorrado(Boolean.TRUE);
        asignacionTipoPolizasRepository.save(asignacionTipoPolizas);
    }
  public List<AsignacionTipoPolizas> findAll(){
        return asignacionTipoPolizasRepository.findAll();
    }
    
}
