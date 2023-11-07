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
import com.sistemasmig.icaavWeb.accounting.models.ConfigMoneda;
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
import com.sistemasmig.icaavWeb.accounting.repositories.ConfigMonedaRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class ConfigMonedaManager {
    
    @Autowired
    private ConfigMonedaRepository configMonedaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public ConfigMoneda getById(Integer id) throws EntityNotExistentException {
        ConfigMoneda configMoneda = configMonedaRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (configMoneda!=null) {
            return configMoneda;
        }
        throw new EntityNotExistentException(ConfigMoneda.class,id.toString());
    }
    
    public PagedResponse<ConfigMoneda> getConfigMoneda(ConfigMoneda filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<ConfigMoneda> cq = cb.createQuery(ConfigMoneda.class);
        Root<ConfigMoneda> root = cq.from(ConfigMoneda.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getEstatusConfigMonedaEnum()!=null){
            predicates.add(cb.equal(root.get("estatusConfigMonedaEnum"), filter.getEstatusConfigMonedaEnum()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getIdMoneda()!=null){
            predicates.add(cb.equal(root.get("idMoneda"), filter.getIdMoneda()));
        }
        if(filter.getIdUsuario()!=null){
            predicates.add(cb.equal(root.get("idUsuario"), filter.getIdUsuario()));
        }
        if(filter.getMonedaNacional()!=null){
            predicates.add(cb.equal(root.get("monedaNacional"), filter.getMonedaNacional()));
        }
        if(filter.getTipoCambio()!=null){
            predicates.add(cb.equal(root.get("tipoCambio"), filter.getTipoCambio()));
        }
        if(filter.getTipoCambioAuto()!=null){
            predicates.add(cb.equal(root.get("tipoCambioAuto"), filter.getTipoCambioAuto()));
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
        
        TypedQuery<ConfigMoneda> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<ConfigMoneda> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<ConfigMoneda> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<ConfigMoneda>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public ConfigMoneda createConfigMoneda(ConfigMoneda configMoneda) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateConfigMoneda(configMoneda);
        //validateUnique(configMoneda);
        configMoneda.setBorrado(Boolean.FALSE);
        return configMonedaRepository.save(configMoneda);
    }

    private void validateConfigMoneda(ConfigMoneda configMoneda) throws BusinessLogicException, EntityNotExistentException {
        if (configMoneda.getGrupoEmpresa()==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto ConfigMoneda");
        } else {
            if(configMoneda.getGrupoEmpresa().getId()==null){
                throw new BusinessLogicException("El campo id de GrupoEmpresa es requerido para el objeto ConfigMoneda");
            }
            empresaManager.getById(configMoneda.getGrupoEmpresa().getId());
        }
    }
    
    /*private void validateUnique(ConfigMoneda configMoneda) throws ExistentEntityException {
        List<ConfigMoneda> configMonedaes = configMonedaRepository.findByNombre(configMoneda.getNombre());
        if (configMonedaes!=null && !configMonedaes.isEmpty()) {
            throw new ExistentEntityException(ConfigMoneda.class,"nombre="+configMoneda.getNombre());
        } 
    }
*/
    public ConfigMoneda updateConfigMoneda(Integer configMonedaId, ConfigMoneda configMoneda) throws EntityNotExistentException, BusinessLogicException {
        
        ConfigMoneda persistedConfigMoneda = getById(configMonedaId);
        if (persistedConfigMoneda != null) {
            if(configMoneda.getEstatusConfigMonedaEnum()!=null){
                persistedConfigMoneda.setEstatusConfigMonedaEnum(configMoneda.getEstatusConfigMonedaEnum());
            }
            if(configMoneda.getGrupoEmpresa()!=null){
                persistedConfigMoneda.setGrupoEmpresa(configMoneda.getGrupoEmpresa());
            }
            if(configMoneda.getIdMoneda()!=null){
                persistedConfigMoneda.setIdMoneda(configMoneda.getIdMoneda());
            }
            if(configMoneda.getIdUsuario()!=null){
                persistedConfigMoneda.setIdUsuario(configMoneda.getIdUsuario());
            }
            if(configMoneda.getMonedaNacional()!=null){
                persistedConfigMoneda.setMonedaNacional(configMoneda.getMonedaNacional());
            }
            if(configMoneda.getTipoCambio()!=null){
                persistedConfigMoneda.setTipoCambio(configMoneda.getTipoCambio());
            }
            if(configMoneda.getTipoCambioAuto()!=null){
                persistedConfigMoneda.setTipoCambioAuto(configMoneda.getTipoCambioAuto());
            }
            return configMonedaRepository.save(persistedConfigMoneda);
        } else {
            throw new EntityNotExistentException(ConfigMoneda.class,configMonedaId.toString());
        }
    }

    public void deleteConfigMoneda(Integer configMonedaId) throws EntityNotExistentException {
        ConfigMoneda configMoneda = getById(configMonedaId);
        configMoneda.setBorrado(Boolean.TRUE);
        configMonedaRepository.save(configMoneda);
    }

    public List<ConfigMoneda> findAll(){
        return configMonedaRepository.findAll();
    }
}
