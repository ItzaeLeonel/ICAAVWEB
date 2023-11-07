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
import com.sistemasmig.icaavWeb.accounting.models.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.sistemasmig.icaavWeb.accounting.repositories.EmpresaRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class EmpresaManager {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
     @Autowired
    private EntityManager entityManager;
    

    public Empresa getById(Integer id) throws EntityNotExistentException {
        Empresa empresa = empresaRepository.getByIdAndBorrado(id,Boolean.FALSE);
        if (empresa!=null) {
            return empresa;
        }
        throw new EntityNotExistentException(Empresa.class,id.toString());
    }
    
    public PagedResponse<Empresa> getEmpresa(Empresa filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Empresa> cq = cb.createQuery(Empresa.class);
        Root<Empresa> root = cq.from(Empresa.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getAmex()!=null){
            predicates.add(cb.equal(root.get("amex"), filter.getAmex()));
        }
        if(filter.getBaseDatosId()!=null){
            predicates.add(cb.equal(root.get("baseDatosId"), filter.getBaseDatosId()));
        }
        if(filter.getClave()!=null){
            predicates.add(cb.like(cb.lower(root.get("clave")), "%" + filter.getClave().toLowerCase()+ "%"));
        }
        if(filter.getClavePais()!=null){
            predicates.add(cb.like(cb.lower(root.get("clavePais")), "%" + filter.getClavePais().toLowerCase()+ "%"));
        }
        if(filter.getCodigoAgencia()!=null){
            predicates.add(cb.like(cb.lower(root.get("codigoAgencia")), "%" + filter.getCodigoAgencia().toLowerCase()+ "%"));
        }
        if(filter.getCodigoOficina()!=null){
            predicates.add(cb.like(cb.lower(root.get("codigoOficina")), "%" + filter.getCodigoOficina().toLowerCase()+ "%"));
        }
        if(filter.getDireccion()!=null && filter.getDireccion().getId()!=null){
            predicates.add(cb.equal(root.get("direccion").get("id"), filter.getDireccion().getId()));
        }
        if(filter.getEmail()!=null){
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase()+ "%"));
        }
        if(filter.getEstatus()!=null){
            predicates.add(cb.equal(root.get("estatus"), filter.getEstatus()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getFormaPagoFolios()!=null){
            predicates.add(cb.equal(root.get("formaPagoFolios"), filter.getFormaPagoFolios()));
        }
        
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getIdioma()!=null && filter.getIdioma().getId()!=null){
            predicates.add(cb.equal(root.get("idioma").get("id"), filter.getIdioma().getId()));
        }
        
        if(filter.getNombre()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase()+ "%"));
        }
        if(filter.getNombreComercial()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombreComercial")), "%" + filter.getNombreComercial().toLowerCase()+ "%"));
        }
        
        if(filter.getRazonSocial()!=null){
            predicates.add(cb.like(cb.lower(root.get("razonSocial")), "%" + filter.getRazonSocial().toLowerCase()+ "%"));
        }
        if(filter.getRfcSucursal()!=null){
            predicates.add(cb.like(cb.lower(root.get("rfcSucursal")), "%" + filter.getRfcSucursal().toLowerCase()+ "%"));
        }
        if(filter.getSucursales()!=null){
            predicates.add(cb.equal(root.get("sucursales"), filter.getSucursales()));
        }
        if(filter.getTelefono()!=null){
            predicates.add(cb.like(cb.lower(root.get("telefono")), "%" + filter.getTelefono().toLowerCase()+ "%"));
        }
        if(filter.getTipoPaquete()!=null && filter.getTipoPaquete().getId()!=null){
            predicates.add(cb.equal(root.get("tipoPaquete").get("id"), filter.getTipoPaquete().getId()));
        }
        if(filter.getUsuarios()!=null){
            predicates.add(cb.equal(root.get("usuarios"), filter.getUsuarios()));
        }
        if(filter.getVersionCFDI()!=null){
            predicates.add(cb.equal(root.get("versionCFDI"), filter.getVersionCFDI()));
        }
        if(filter.getZonaHoraria()!=null && filter.getZonaHoraria().getId()!=null){
            predicates.add(cb.equal(root.get("zonaHoraria").get("id"), filter.getZonaHoraria().getId()));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<Empresa> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Empresa> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Empresa> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Empresa>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Empresa createEmpresa(Empresa empresa) throws BusinessLogicException, ExistentEntityException {
        validateEmpresa(empresa);
        validateUnique(empresa);
        empresa.setBorrado(Boolean.FALSE);
        return empresaRepository.save(empresa);
    }

    private void validateEmpresa(Empresa empresa) throws BusinessLogicException {
        if (StringUtils.isEmpty(empresa.getNombre())) {
            throw new BusinessLogicException("El campo Nombre es requerido para el objeto Empresa");
        } else if (StringUtils.isEmpty(empresa.getRfcSucursal())) {
            throw new BusinessLogicException("El campo RfcSucursal es requerido para el objeto Empresa");
        } else if (StringUtils.isEmpty(empresa.getClave())) {
            throw new BusinessLogicException("El campo Clave es requerido para el objeto Empresa");
        } else if (StringUtils.isEmpty(empresa.getNombreComercial())) {
            throw new BusinessLogicException("El campo NombreComercial es requerido para el objeto Empresa");
        } else if (StringUtils.isEmpty(empresa.getRazonSocial())) {
            throw new BusinessLogicException("El campo RazonSocial es requerido para el objeto Empresa");
        } else if (StringUtils.isEmpty(empresa.getClavePais())) {
            throw new BusinessLogicException("El campo ClavePais es requerido para el objeto Empresa");
        } else if (empresa.getIdioma()==null) {
            throw new BusinessLogicException("El campo Idioma es requerido para el objeto Empresa");
        } else if (empresa.getDireccion()==null) {
            throw new BusinessLogicException("El campo Direccion es requerido para el objeto Empresa");
        } else if (empresa.getZonaHoraria()==null) {
            throw new BusinessLogicException("El campo ZonaHoraria es requerido para el objeto Empresa");
        } else if (empresa.getUsuarios()==null) {
            throw new BusinessLogicException("El campo Usuarios es requerido para el objeto Empresa");
        } else if (empresa.getSucursales()==null) {
            throw new BusinessLogicException("El campo Sucursales es requerido para el objeto Empresa");
        } else if (empresa.getEstatus()==null) {
            throw new BusinessLogicException("El campo Estatus es requerido para el objeto Empresa");
        } else if (empresa.getVersionCFDI()==null) {
            throw new BusinessLogicException("El campo VersionCFDI es requerido para el objeto Empresa");
        } 
    }
    
    private void validateUnique(Empresa empresa) throws ExistentEntityException {
        List<Empresa> empresaes = empresaRepository.findByNombreAndBorrado(empresa.getNombre(),Boolean.FALSE);
        if (empresaes!=null && !empresaes.isEmpty()) {
            throw new ExistentEntityException(Empresa.class,"nombre="+empresa.getNombre());
        } 
        empresaes = empresaRepository.findByRfcSucursalAndBorrado(empresa.getRfcSucursal(),Boolean.FALSE);
        if (empresaes!=null && !empresaes.isEmpty()) {
            throw new ExistentEntityException(Empresa.class,"rfcSucursal="+empresa.getRfcSucursal());
        }
        empresaes = empresaRepository.findByClaveAndBorrado(empresa.getClave(),Boolean.FALSE);
        if (empresaes!=null && !empresaes.isEmpty()) {
            throw new ExistentEntityException(Empresa.class,"clave="+empresa.getClave());
        }
        //agregar validación de rfc
        //agregar validacion clave de empresa
        //
    }

    public Empresa updateEmpresa(Integer empresaId, Empresa empresa) throws EntityNotExistentException, BusinessLogicException {
        
        Empresa persistedEmpresa = getById(empresaId);
        if (persistedEmpresa != null) {
            if(empresa.getAmex()!=null){
                persistedEmpresa.setAmex(empresa.getAmex());
            }
            if(empresa.getBaseDatosId()!=null){
                persistedEmpresa.setBaseDatosId(empresa.getBaseDatosId());
            }
            if(empresa.getClave()!=null){
                persistedEmpresa.setClave(empresa.getClave());
            }
            if(empresa.getClavePais()!=null){
                persistedEmpresa.setClavePais(empresa.getClavePais());
            }
            if(empresa.getCodigoAgencia()!=null){
                persistedEmpresa.setCodigoAgencia(empresa.getCodigoAgencia());
            }
            if(empresa.getCodigoOficina()!=null){
                persistedEmpresa.setCodigoOficina(empresa.getCodigoOficina());
            }
            if(empresa.getDireccion()!=null){
                persistedEmpresa.setDireccion(empresa.getDireccion());
            }
            if(empresa.getEmail()!=null){
                persistedEmpresa.setEmail(empresa.getEmail());
            }
            if(empresa.getEstatus()!=null){
                persistedEmpresa.setEstatus(empresa.getEstatus());
            }
            if(empresa.getFormaPagoFolios()!=null){
                persistedEmpresa.setFormaPagoFolios(empresa.getFormaPagoFolios());
            }
            if(empresa.getIdioma()!=null){
                persistedEmpresa.setIdioma(empresa.getIdioma());
            }
            
            if(empresa.getNombre()!=null){
                persistedEmpresa.setNombre(empresa.getNombre());
            }
            if(empresa.getNombreComercial()!=null){
                persistedEmpresa.setNombreComercial(empresa.getNombreComercial());
            }
            
            if(empresa.getRazonSocial()!=null){
                persistedEmpresa.setRazonSocial(empresa.getRazonSocial());
            }
            if(empresa.getRfcSucursal()!=null){
                persistedEmpresa.setRfcSucursal(empresa.getRfcSucursal());
            }
            if(empresa.getSucursales()!=null){
                persistedEmpresa.setSucursales(empresa.getSucursales());
            }
            if(empresa.getTelefono()!=null){
                persistedEmpresa.setTelefono(empresa.getTelefono());
            }
            if(empresa.getTipoPaquete()!=null){
                persistedEmpresa.setTipoPaquete(empresa.getTipoPaquete());
            }
            if(empresa.getUsuarios()!=null){
                persistedEmpresa.setUsuarios(empresa.getUsuarios());
            }
            if(empresa.getVersionCFDI()!=null){
                persistedEmpresa.setVersionCFDI(empresa.getVersionCFDI());
            }
            if(empresa.getZonaHoraria()!=null){
                persistedEmpresa.setZonaHoraria(empresa.getZonaHoraria());
            }
            
            return empresaRepository.save(persistedEmpresa);
        } else {
            throw new EntityNotExistentException(Empresa.class,empresaId.toString());
        }
    }

    public void deleteEmpresa(Integer empresaId) throws EntityNotExistentException {
        Empresa empresa = getById(empresaId);
        empresa.setBorrado(Boolean.TRUE);
        empresaRepository.save(empresa);
    }

    public List<Empresa> findAll(){
        return empresaRepository.findAll();
    }
    
    public Empresa getByNombreAndBorrado(String name, Boolean borrado){
        return empresaRepository.getByNombreAndBorrado(name,borrado);
    }
    
    public List<Empresa> findByNombreIgnoreCaseContainingAndBorrado(String name, Boolean borrado){
        return empresaRepository.findByNombreIgnoreCaseContainingAndBorrado(name,borrado);
    }
}
