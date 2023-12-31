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
import com.sistemasmig.icaavWeb.accounting.models.AsignacionClasificacionCtas;
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
import com.sistemasmig.icaavWeb.accounting.repositories.AsignacionClasificacionCtasRepository;
/**
 *
 * @author Julio
 */
@Component
public class AsignacionClasificacionCtasManager {
    
    @Autowired
    private AsignacionClasificacionCtasRepository asignacionClasificacionCtasRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    
    public AsignacionClasificacionCtas getById(Integer id) throws EntityNotExistentException {
        AsignacionClasificacionCtas asignacionClasificacionCtas = asignacionClasificacionCtasRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (asignacionClasificacionCtas!=null) {
            return asignacionClasificacionCtas;
        }
        throw new EntityNotExistentException(AsignacionClasificacionCtas.class,id.toString());
    }
    
    public PagedResponse<AsignacionClasificacionCtas> getAsignacionClasificacionCtas(AsignacionClasificacionCtas filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<AsignacionClasificacionCtas> cq = cb.createQuery(AsignacionClasificacionCtas.class);
        Root<AsignacionClasificacionCtas> root = cq.from(AsignacionClasificacionCtas.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getEstatusAsignacionClasifCtasEnum()!=null){
             predicates.add(cb.equal(root.get("estatusAsignacionClasifCtasEnum"), filter.getEstatusAsignacionClasifCtasEnum()));

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
        if(filter.getClasificacion()!=null){
                if(filter.getClasificacion().getIniciaCon()!=null){
                    predicates.add(cb.equal(root.get("clasificacion").get("iniciaCon"), filter.getClasificacion().getIniciaCon()));
                }
                 if(filter.getClasificacion().getClasificacion()!=null){
                    predicates.add(cb.equal(root.get("clasificacion").get("clasificacion"), filter.getClasificacion().getClasificacion()));
                }
                  if(filter.getClasificacion().getId()!=null){
                    predicates.add(cb.equal(root.get("clasificacion").get("id"), filter.getClasificacion().getId()));
                }
                if(filter.getClasificacion().getFechaModificacion()!=null && filter.getClasificacion().getFechaModificacion2()!=null){
                predicates.add(cb.between(root.get("clasificacion").get("fechaModificacion"), filter.getClasificacion().getFechaModificacion(),filter.getClasificacion().getFechaModificacion2()));
                cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        }
        if(filter.getTipoCuentaEnum()!=null){
            predicates.add(cb.equal(root.get("tipoCuentaEnum"), filter.getTipoCuentaEnum()));
        }
         if(filter.getUsuario()!=null){
            predicates.add(cb.equal(root.get("usuario"), filter.getUsuario()));
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
        
        TypedQuery<AsignacionClasificacionCtas> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<AsignacionClasificacionCtas> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<AsignacionClasificacionCtas> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<AsignacionClasificacionCtas>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

 
    
    
    public AsignacionClasificacionCtas createAsignacionClasificacionCtas(AsignacionClasificacionCtas asignacionClasificacionCtas) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateAsignacionClasificacionCtas(asignacionClasificacionCtas);
        //validateUnique(asignacionClasificacionCtas);
        asignacionClasificacionCtas.setBorrado(Boolean.FALSE);
        return asignacionClasificacionCtasRepository.save(asignacionClasificacionCtas);
    }

    private void validateAsignacionClasificacionCtas(AsignacionClasificacionCtas asignacionClasificacionCtas) throws BusinessLogicException, EntityNotExistentException {
        if (asignacionClasificacionCtas.getGrupoEmpresa()==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto AsignacionClasificacionCtas");
        } else if(asignacionClasificacionCtas.getEstatusAsignacionClasifCtasEnum()==null){
                throw new BusinessLogicException("El campo estatus es requerido para el objeto AsignacionClasificacionCtas");
            }
             
    }
    
    
    
     
    public AsignacionClasificacionCtas updateAsignacionClasificacionCtas(Integer asignacionClasificacionCtasId, AsignacionClasificacionCtas asignacionClasificacionCtas) throws EntityNotExistentException, BusinessLogicException {
        
        AsignacionClasificacionCtas persistedAsignacionClasificacionCtas = getById(asignacionClasificacionCtasId);
        if (persistedAsignacionClasificacionCtas != null) {
            if(asignacionClasificacionCtas.getClasificacion()!=null){
                persistedAsignacionClasificacionCtas.setClasificacion(asignacionClasificacionCtas.getClasificacion());
            }
            if(asignacionClasificacionCtas.getEstatusAsignacionClasifCtasEnum()!=null){
                persistedAsignacionClasificacionCtas.setEstatusAsignacionClasifCtasEnum(asignacionClasificacionCtas.getEstatusAsignacionClasifCtasEnum());
            }
            if(asignacionClasificacionCtas.getGrupoEmpresa()!=null){
            persistedAsignacionClasificacionCtas.setGrupoEmpresa(asignacionClasificacionCtas.getGrupoEmpresa());
            }
            if(asignacionClasificacionCtas.getTipoCuentaEnum()!=null){
            persistedAsignacionClasificacionCtas.setTipoCuentaEnum(asignacionClasificacionCtas.getTipoCuentaEnum());
            }
              if(asignacionClasificacionCtas.getUsuario()!=null){
            persistedAsignacionClasificacionCtas.setUsuario(asignacionClasificacionCtas.getUsuario());
            }
            return asignacionClasificacionCtasRepository.save(persistedAsignacionClasificacionCtas);
        } else {
            throw new EntityNotExistentException(AsignacionClasificacionCtas.class,asignacionClasificacionCtasId.toString());
        }
    }

    public void deleteAsignacionClasificacionCtas(Integer asignacionClasificacionCtasId) throws EntityNotExistentException {
        AsignacionClasificacionCtas asignacionClasificacionCtas = getById(asignacionClasificacionCtasId);
        asignacionClasificacionCtas.setBorrado(Boolean.TRUE);
        asignacionClasificacionCtasRepository.save(asignacionClasificacionCtas);
    }
    public List<AsignacionClasificacionCtas> findAll(){
        return asignacionClasificacionCtasRepository.findAll();
    }
    
    
    
}
