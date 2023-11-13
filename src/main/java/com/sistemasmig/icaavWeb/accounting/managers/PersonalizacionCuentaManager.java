/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.PersonalizacionCuenta;
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
import com.sistemasmig.icaavWeb.accounting.repositories.CuentasContablePrecRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.PersonalizacionCuentaRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julio
 */
@Component
public class PersonalizacionCuentaManager {
    
   
    @Autowired
    private PersonalizacionCuentaRepository personalizacionCuentaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    
        public PersonalizacionCuenta getById(Integer id) throws EntityNotExistentException {
        PersonalizacionCuenta personalizacionCuenta = personalizacionCuentaRepository.getByIdAndBorrado(id,Boolean.FALSE);
        if (personalizacionCuenta!=null) {
            return personalizacionCuenta;
        }
        throw new EntityNotExistentException(PersonalizacionCuenta.class,id.toString());
    }
        
        public PagedResponse<PersonalizacionCuenta> getPersonalizacionCuenta(PersonalizacionCuenta filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<PersonalizacionCuenta> cq = cb.createQuery(PersonalizacionCuenta.class);
        Root<PersonalizacionCuenta> root = cq.from(PersonalizacionCuenta.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
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
        if(filter.getEstatusPCEnum()!=null){
           predicates.add(cb.equal(root.get("estatusPCEnum"), filter.getEstatusPCEnum()));
        }
         if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getMascara()!=null){
            predicates.add(cb.like(cb.lower(root.get("mascara")), "%" + filter.getMascara().toLowerCase()+ "%"));
        }
        if(filter.getNivel1()!=null){
            predicates.add(cb.equal(root.get("nivel1"), filter.getNivel1()));
        }
        if(filter.getNivel2()!=null){
            predicates.add(cb.equal(root.get("nivel2"), filter.getNivel2()));
        }
        if(filter.getNivel3()!=null){
            predicates.add(cb.equal(root.get("nivel3"), filter.getNivel3()));
        }
        if(filter.getNivel4()!=null){
            predicates.add(cb.equal(root.get("nivel4"), filter.getNivel4()));
        }
        if(filter.getNivel5()!=null){
            predicates.add(cb.equal(root.get("nivel5"), filter.getNivel5()));
        }
        if(filter.getNivel6()!=null){
            predicates.add(cb.equal(root.get("nivel6"), filter.getNivel6()));
        }
        if(filter.getNivel7()!=null){
            predicates.add(cb.equal(root.get("nivel7"), filter.getNivel7()));
        }
        if(filter.getNivel8()!=null){
            predicates.add(cb.equal(root.get("nivel8"), filter.getNivel8()));
        }
        if(filter.getNivel9()!=null){
            predicates.add(cb.equal(root.get("nivel9"), filter.getNivel9()));
        }
        
        if(filter.getNo_niveles()!=null){
            predicates.add(cb.equal(root.get("no_niveles"), filter.getNo_niveles()));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<PersonalizacionCuenta> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<PersonalizacionCuenta> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<PersonalizacionCuenta> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<PersonalizacionCuenta>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }
    
    
    public PersonalizacionCuenta createPersonalizacionCuenta(PersonalizacionCuenta personalizacionCuenta) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validatepersonalizacionCuenta(personalizacionCuenta);
        //validateUnique(personalizacionCuenta);
        personalizacionCuenta.setBorrado(Boolean.FALSE);
        return personalizacionCuentaRepository.save(personalizacionCuenta);
    }

    private void validatepersonalizacionCuenta(PersonalizacionCuenta personalizacionCuenta) throws BusinessLogicException, EntityNotExistentException {
        if (personalizacionCuenta.getEstatusPCEnum()==null) {
            throw new BusinessLogicException("El campo Estatus es requerido para el objeto Personalizacion Cuenta");
        } else if(personalizacionCuenta.getMascara()==null){
            throw new BusinessLogicException("El campo Mascara es requerido para el objeto Personalizacion Cuenta");
        }else if (personalizacionCuenta.getGrupoEmpresa()==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto Personalizacion Cuenta");
            
        }
    }
    public PersonalizacionCuenta updatePersonalizacionCuenta(Integer personalizacionCuentaId, PersonalizacionCuenta personalizacionCuenta) throws EntityNotExistentException, BusinessLogicException {
        
        PersonalizacionCuenta persistedPersonalizacionCuenta = getById(personalizacionCuentaId);
        if (persistedPersonalizacionCuenta != null) {
            if(personalizacionCuenta.getEstatusPCEnum()!=null){
                persistedPersonalizacionCuenta.setEstatusPCEnum(personalizacionCuenta.getEstatusPCEnum());
            }
            if(personalizacionCuenta.getGrupoEmpresa()!=null){
                persistedPersonalizacionCuenta.setGrupoEmpresa(personalizacionCuenta.getGrupoEmpresa());
            }
            if(personalizacionCuenta.getMascara()!=null){
                persistedPersonalizacionCuenta.setMascara(personalizacionCuenta.getMascara());
            }
            if(personalizacionCuenta.getNo_niveles()!=null){
                persistedPersonalizacionCuenta.setNo_niveles(personalizacionCuenta.getNo_niveles());
            }
            if(personalizacionCuenta.getNivel1()!=null){
                persistedPersonalizacionCuenta.setNivel1(personalizacionCuenta.getNivel1());
            }
            if(personalizacionCuenta.getNivel2()!=null){
                persistedPersonalizacionCuenta.setNivel2(personalizacionCuenta.getNivel2());
            }
            if(personalizacionCuenta.getNivel3()!=null){
                persistedPersonalizacionCuenta.setNivel3(personalizacionCuenta.getNivel3());
            }
            if(personalizacionCuenta.getNivel4()!=null){
                persistedPersonalizacionCuenta.setNivel4(personalizacionCuenta.getNivel4());
            }
            if(personalizacionCuenta.getNivel5()!=null){
                persistedPersonalizacionCuenta.setNivel5(personalizacionCuenta.getNivel5());
            }
            if(personalizacionCuenta.getNivel6()!=null){
                persistedPersonalizacionCuenta.setNivel6(personalizacionCuenta.getNivel6());
            }
            if(personalizacionCuenta.getNivel7()!=null){
                persistedPersonalizacionCuenta.setNivel7(personalizacionCuenta.getNivel7());
            }
            if(personalizacionCuenta.getNivel8()!=null){
                persistedPersonalizacionCuenta.setNivel8(personalizacionCuenta.getNivel8());
            }
            if(personalizacionCuenta.getNivel9()!=null){
                persistedPersonalizacionCuenta.setNivel9(personalizacionCuenta.getNivel9());
            }
            return personalizacionCuentaRepository.save(persistedPersonalizacionCuenta);
        } else {
            throw new EntityNotExistentException(PersonalizacionCuenta.class,personalizacionCuentaId.toString());
        }
    }

    public void deletePersonalizacionCuenta(Integer personalizacionCuentaId) throws EntityNotExistentException {
        PersonalizacionCuenta personalizacionCuenta = getById(personalizacionCuentaId);
        personalizacionCuenta.setBorrado(Boolean.TRUE);
        personalizacionCuentaRepository.save(personalizacionCuenta);
    }
     public List<PersonalizacionCuenta> findAll(){
        return personalizacionCuentaRepository.findAll();
    }
    
    
}
