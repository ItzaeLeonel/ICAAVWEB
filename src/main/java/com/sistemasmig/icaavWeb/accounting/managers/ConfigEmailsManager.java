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
import com.sistemasmig.icaavWeb.accounting.models.ConfigEmails;
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
import com.sistemasmig.icaavWeb.accounting.repositories.ConfigEmailsRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class ConfigEmailsManager {
    
    @Autowired
    private ConfigEmailsRepository configEmailsRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public ConfigEmails getById(Integer id) throws EntityNotExistentException {
        ConfigEmails configEmails = configEmailsRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (configEmails!=null) {
            return configEmails;
        }
        throw new EntityNotExistentException(ConfigEmails.class,id.toString());
    }
    
    public PagedResponse<ConfigEmails> getConfigEmails(ConfigEmails filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<ConfigEmails> cq = cb.createQuery(ConfigEmails.class);
        Root<ConfigEmails> root = cq.from(ConfigEmails.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getAmbientePruebas()!=null){
            predicates.add(cb.equal(root.get("ambientePruebas"), filter.getAmbientePruebas()));
        }
        if(filter.getDeshabilitarTLS()!=null){
            predicates.add(cb.equal(root.get("deshabilitarTLS"), filter.getDeshabilitarTLS()));
        }
        if(filter.getEmailCXPHost()!=null){
            predicates.add(cb.equal(root.get("emailCXPHost"), filter.getEmailCXPHost()));
        }
        if(filter.getEmailCXPPassword()!=null){
            predicates.add(cb.equal(root.get("emailCXPPassword"), filter.getEmailCXPPassword()));
        }
        if(filter.getEmailCXPPuerto()!=null){
            predicates.add(cb.equal(root.get("emailCXPPuerto"), filter.getEmailCXPPuerto()));
        }
        if(filter.getEmailCXPUsuario()!=null){
            predicates.add(cb.equal(root.get("emailCXPUsuario"), filter.getEmailCXPUsuario()));
        }
        if(filter.getEmailCobranzaHost()!=null){
            predicates.add(cb.equal(root.get("emailCobranzaHost"), filter.getEmailCobranzaHost()));
        }
        if(filter.getEmailCobranzaPassword()!=null){
            predicates.add(cb.equal(root.get("emailCobranzaPassword"), filter.getEmailCobranzaPassword()));
        }
        if(filter.getEmailCobranzaPuerto()!=null){
            predicates.add(cb.equal(root.get("emailCobranzaPuerto"), filter.getEmailCobranzaPuerto()));
        }
        if(filter.getEmailCobranzaUsuario()!=null){
            predicates.add(cb.equal(root.get("emailCobranzaUsuario"), filter.getEmailCobranzaUsuario()));
        }
        if(filter.getEmailFacturacionHost()!=null){
            predicates.add(cb.equal(root.get("emailFacturacionHost"), filter.getEmailFacturacionHost()));
        }
        if(filter.getEmailFacturacionPassword()!=null){
            predicates.add(cb.equal(root.get("emailFacturacionPassword"), filter.getEmailFacturacionPassword()));
        }
        if(filter.getEmailFacturacionPuerto()!=null){
            predicates.add(cb.equal(root.get("emailFacturacionPuerto"), filter.getEmailFacturacionPuerto()));
        }
        if(filter.getEmailFacturacionUsuario()!=null){
            predicates.add(cb.equal(root.get("emailFacturacionUsuario"), filter.getEmailFacturacionUsuario()));
        }
        
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getUsaCifradoDefault()!=null){
            predicates.add(cb.equal(root.get("usaCifradoDefault"), filter.getUsaCifradoDefault()));
        }
        if(filter.getUsaSSL()!=null){
            predicates.add(cb.equal(root.get("usaSSL"), filter.getUsaSSL()));
        }
        if(filter.getUsuario()!=null){
            predicates.add(cb.equal(root.get("usuario"), filter.getUsuario()));
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
        
        TypedQuery<ConfigEmails> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<ConfigEmails> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<ConfigEmails> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<ConfigEmails>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public ConfigEmails createConfigEmails(ConfigEmails configEmails) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateConfigEmails(configEmails);
        //validateUnique(configEmails);
        configEmails.setBorrado(Boolean.FALSE);
        return configEmailsRepository.save(configEmails);
    }

    private void validateConfigEmails(ConfigEmails configEmails) throws BusinessLogicException, EntityNotExistentException {
        if (configEmails.getGrupoEmpresa()==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto ConfigEmails");
        } else {
            if(configEmails.getGrupoEmpresa().getId()==null){
                throw new BusinessLogicException("El campo id de GrupoEmpresa es requerido para el objeto ConfigEmails");
            }
            empresaManager.getById(configEmails.getGrupoEmpresa().getId());
        }
    }
    
    /*private void validateUnique(ConfigEmails configEmails) throws ExistentEntityException {
        List<ConfigEmails> configEmailses = configEmailsRepository.findByNombre(configEmails.getNombre());
        if (configEmailses!=null && !configEmailses.isEmpty()) {
            throw new ExistentEntityException(ConfigEmails.class,"nombre="+configEmails.getNombre());
        } 
    }
*/
    public ConfigEmails updateConfigEmails(Integer configEmailsId, ConfigEmails configEmails) throws EntityNotExistentException, BusinessLogicException {
        
        ConfigEmails persistedConfigEmails = getById(configEmailsId);
        if (persistedConfigEmails != null) {
            if(configEmails.getAmbientePruebas()!=null){
                persistedConfigEmails.setAmbientePruebas(configEmails.getAmbientePruebas());
            }
            if(configEmails.getDeshabilitarTLS()!=null){
                persistedConfigEmails.setDeshabilitarTLS(configEmails.getDeshabilitarTLS());
            }
            if(configEmails.getEmailCXPHost()!=null){
                persistedConfigEmails.setEmailCXPHost(configEmails.getEmailCXPHost());
            }
            if(configEmails.getEmailCXPPassword()!=null){
                persistedConfigEmails.setEmailCXPPassword(configEmails.getEmailCXPPassword());
            }
            if(configEmails.getEmailCXPPuerto()!=null){
                persistedConfigEmails.setEmailCXPPuerto(configEmails.getEmailCXPPuerto());
            }
            if(configEmails.getEmailCXPUsuario()!=null){
                persistedConfigEmails.setEmailCXPUsuario(configEmails.getEmailCXPUsuario());
            }
            if(configEmails.getEmailCobranzaHost()!=null){
                persistedConfigEmails.setEmailCobranzaHost(configEmails.getEmailCobranzaHost());
            }
            if(configEmails.getEmailCobranzaPassword()!=null){
                persistedConfigEmails.setEmailCobranzaPassword(configEmails.getEmailCobranzaPassword());
            }
            if(configEmails.getEmailCobranzaPuerto()!=null){
                persistedConfigEmails.setEmailCobranzaPuerto(configEmails.getEmailCobranzaPuerto());
            }
            if(configEmails.getEmailCobranzaUsuario()!=null){
                persistedConfigEmails.setEmailCobranzaUsuario(configEmails.getEmailCobranzaUsuario());
            }
            if(configEmails.getEmailFacturacionHost()!=null){
                persistedConfigEmails.setEmailFacturacionHost(configEmails.getEmailFacturacionHost());
            }
            if(configEmails.getEmailFacturacionPassword()!=null){
                persistedConfigEmails.setEmailFacturacionPassword(configEmails.getEmailFacturacionPassword());
            }
            if(configEmails.getEmailFacturacionPuerto()!=null){
                persistedConfigEmails.setEmailFacturacionPuerto(configEmails.getEmailFacturacionPuerto());
            }
            if(configEmails.getEmailFacturacionUsuario()!=null){
                persistedConfigEmails.setEmailFacturacionUsuario(configEmails.getEmailFacturacionUsuario());
            }
            if(configEmails.getGrupoEmpresa()!=null){
                persistedConfigEmails.setGrupoEmpresa(configEmails.getGrupoEmpresa());
            }
            if(configEmails.getUsaCifradoDefault()!=null){
                persistedConfigEmails.setUsaCifradoDefault(configEmails.getUsaCifradoDefault());
            }
            if(configEmails.getUsaSSL()!=null){
                persistedConfigEmails.setUsaSSL(configEmails.getUsaSSL());
            }
            if(configEmails.getUsuario()!=null){
                persistedConfigEmails.setUsuario(configEmails.getUsuario());
            }
            
            return configEmailsRepository.save(persistedConfigEmails);
        } else {
            throw new EntityNotExistentException(ConfigEmails.class,configEmailsId.toString());
        }
    }

    public void deleteConfigEmails(Integer configEmailsId) throws EntityNotExistentException {
        ConfigEmails configEmails = getById(configEmailsId);
        configEmails.setBorrado(Boolean.TRUE);
        configEmailsRepository.save(configEmails);
    }

    public List<ConfigEmails> findAll(){
        return configEmailsRepository.findAll();
    }
}
