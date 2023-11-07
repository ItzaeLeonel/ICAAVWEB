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
import com.sistemasmig.icaavWeb.accounting.models.EmpresaUsuario;
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
import com.sistemasmig.icaavWeb.accounting.repositories.EmpresaUsuarioRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class EmpresaUsuarioManager {
    
    @Autowired
    private EmpresaUsuarioRepository empresaUsuarioRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public EmpresaUsuario getById(Integer id) throws EntityNotExistentException {
        EmpresaUsuario empresaUsuario = empresaUsuarioRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (empresaUsuario!=null) {
            return empresaUsuario;
        }
        throw new EntityNotExistentException(EmpresaUsuario.class,id.toString());
    }
    
    public PagedResponse<EmpresaUsuario> getEmpresaUsuario(EmpresaUsuario filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<EmpresaUsuario> cq = cb.createQuery(EmpresaUsuario.class);
        Root<EmpresaUsuario> root = cq.from(EmpresaUsuario.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getIdUsuario()!=null){
            predicates.add(cb.equal(root.get("idUsuario"), filter.getIdUsuario()));
        }
        
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
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
            if(filter.getEmpresa().getDireccion()!=null && filter.getEmpresa().getDireccion().getId()!=null){
                predicates.add(cb.equal(root.get("empresa").get("direccion").get("id"), filter.getEmpresa().getDireccion().getId()));
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

            if(filter.getEmpresa().getId()!=null){
                predicates.add(cb.equal(root.get("empresa").get("id"), filter.getEmpresa().getId()));
            }
            if(filter.getEmpresa().getIdioma()!=null && filter.getEmpresa().getIdioma().getId()!=null){
                predicates.add(cb.equal(root.get("empresa").get("idioma").get("id"), filter.getEmpresa().getIdioma().getId()));
            }

            if(filter.getEmpresa().getNombre()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("nombre")), "%" + filter.getEmpresa().getNombre().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getNombreComercial()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("nombreComercial")), "%" + filter.getEmpresa().getNombreComercial().toLowerCase()+ "%"));
            }

            if(filter.getEmpresa().getRazonSocial()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("razonSocial")), "%" + filter.getEmpresa().getRazonSocial().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getRfcSucursal()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("rfcSucursal")), "%" + filter.getEmpresa().getRfcSucursal().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getSucursales()!=null){
                predicates.add(cb.equal(root.get("empresa").get("sucursales"), filter.getEmpresa().getSucursales()));
            }
            if(filter.getEmpresa().getTelefono()!=null){
                predicates.add(cb.like(cb.lower(root.get("empresa").get("telefono")), "%" + filter.getEmpresa().getTelefono().toLowerCase()+ "%"));
            }
            if(filter.getEmpresa().getTipoPaquete()!=null && filter.getEmpresa().getTipoPaquete().getId()!=null){
                predicates.add(cb.equal(root.get("empresa").get("tipoPaquete").get("id"), filter.getEmpresa().getTipoPaquete().getId()));
            }
            if(filter.getEmpresa().getUsuarios()!=null){
                predicates.add(cb.equal(root.get("empresa").get("usuarios"), filter.getEmpresa().getUsuarios()));
            }
            if(filter.getEmpresa().getVersionCFDI()!=null){
                predicates.add(cb.equal(root.get("empresa").get("versionCFDI"), filter.getEmpresa().getVersionCFDI()));
            }
            if(filter.getEmpresa().getZonaHoraria()!=null && filter.getEmpresa().getZonaHoraria().getId()!=null){
                predicates.add(cb.equal(root.get("empresa").get("zonaHoraria").get("id"), filter.getEmpresa().getZonaHoraria().getId()));
            }
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<EmpresaUsuario> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<EmpresaUsuario> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<EmpresaUsuario> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<EmpresaUsuario>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public EmpresaUsuario createEmpresaUsuario(EmpresaUsuario empresaUsuario) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateEmpresaUsuario(empresaUsuario);
        //validateUnique(empresaUsuario);
        empresaUsuario.setBorrado(Boolean.FALSE);
        return empresaUsuarioRepository.save(empresaUsuario);
    }

    private void validateEmpresaUsuario(EmpresaUsuario empresaUsuario) throws BusinessLogicException, EntityNotExistentException {
        if (empresaUsuario==null) {
            throw new BusinessLogicException("El campo GrupoEmpresa es requerido para el objeto EmpresaUsuario");
        } else {
            if(empresaUsuario.getId()==null){
                throw new BusinessLogicException("El campo id de GrupoEmpresa es requerido para el objeto EmpresaUsuario");
            }
            empresaManager.getById(empresaUsuario.getId());
        }
    }
    
    /*private void validateUnique(EmpresaUsuario empresaUsuario) throws ExistentEntityException {
        List<EmpresaUsuario> empresaUsuarioes = empresaUsuarioRepository.findByNombre(empresaUsuario.getNombre());
        if (empresaUsuarioes!=null && !empresaUsuarioes.isEmpty()) {
            throw new ExistentEntityException(EmpresaUsuario.class,"nombre="+empresaUsuario.getNombre());
        } 
    }
*/
    public EmpresaUsuario updateEmpresaUsuario(Integer empresaUsuarioId, EmpresaUsuario empresaUsuario) throws EntityNotExistentException, BusinessLogicException {
        
        EmpresaUsuario persistedEmpresaUsuario = getById(empresaUsuarioId);
        if (persistedEmpresaUsuario != null) {
            if(empresaUsuario.getEmpresa()!=null){
                persistedEmpresaUsuario.setEmpresa(empresaUsuario.getEmpresa());
            }
            if(empresaUsuario.getIdUsuario()!=null){
                persistedEmpresaUsuario.setIdUsuario(empresaUsuario.getIdUsuario());
            }
            
            return empresaUsuarioRepository.save(persistedEmpresaUsuario);
        } else {
            throw new EntityNotExistentException(EmpresaUsuario.class,empresaUsuarioId.toString());
        }
    }

    public void deleteEmpresaUsuario(Integer empresaUsuarioId) throws EntityNotExistentException {
        EmpresaUsuario empresaUsuario = getById(empresaUsuarioId);
        empresaUsuario.setBorrado(Boolean.TRUE);
        empresaUsuarioRepository.save(empresaUsuario);
    }

    public List<EmpresaUsuario> findAll(){
        return empresaUsuarioRepository.findAll();
    }
}
