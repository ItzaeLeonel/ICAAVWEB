/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Estilo;
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
import com.sistemasmig.icaavWeb.accounting.repositories.EstiloRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class EstiloManager {
    
    @Autowired
    private EstiloRepository estiloRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Estilo getById(Integer id) throws EntityNotExistentException {
        Estilo estilo = estiloRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (estilo!=null) {
            return estilo;
        }
        throw new EntityNotExistentException(Estilo.class,id.toString());
    }
    
    public PagedResponse<Estilo> getEstilo(Estilo filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Estilo> cq = cb.createQuery(Estilo.class);
        Root<Estilo> root = cq.from(Estilo.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getColor()!=null){
            predicates.add(cb.equal(root.get("color"), filter.getColor()));
        }
        if(filter.getDescripcion()!=null){
            predicates.add(cb.equal(root.get("descripcion"), filter.getDescripcion()));
        }
        if(filter.getImagen()!=null){
            predicates.add(cb.equal(root.get("imagen"), filter.getImagen()));
        }
        if(filter.getUrl()!=null){
            predicates.add(cb.equal(root.get("url"), filter.getUrl()));
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
        
        TypedQuery<Estilo> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Estilo> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Estilo> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Estilo>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Estilo createEstilo(Estilo estilo) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateEstilo(estilo);
        //validateUnique(estilo);
        estilo.setBorrado(Boolean.FALSE);
        return estiloRepository.save(estilo);
    }

    private void validateEstilo(Estilo estilo) throws BusinessLogicException, EntityNotExistentException {
        if (estilo.getEmpresa()==null) {
            throw new BusinessLogicException("El campo Empresa es requerido para el objeto Estilo");
        } else {
            if(estilo.getEmpresa().getId()==null){
                throw new BusinessLogicException("El campo id de Empresa es requerido para el objeto Estilo");
            }
        }
    }
    
    /*private void validateUnique(Estilo estilo) throws ExistentEntityException {
        List<Estilo> estiloes = estiloRepository.findByNombre(estilo.getNombre());
        if (estiloes!=null && !estiloes.isEmpty()) {
            throw new ExistentEntityException(Estilo.class,"nombre="+estilo.getNombre());
        } 
    }
*/
    public Estilo updateEstilo(Integer estiloId, Estilo estilo) throws EntityNotExistentException, BusinessLogicException {
        
        Estilo persistedEstilo = getById(estiloId);
        if (persistedEstilo != null) {
            if(estilo.getColor()!=null){
                persistedEstilo.setColor(estilo.getColor());
            }
            if(estilo.getDescripcion()!=null){
                persistedEstilo.setDescripcion(estilo.getDescripcion());
            }
            if(estilo.getEmpresa()!=null){
                persistedEstilo.setEmpresa(estilo.getEmpresa());
            }
            if(estilo.getImagen()!=null){
                persistedEstilo.setImagen(estilo.getImagen());
            }
            if(estilo.getUrl()!=null){
                persistedEstilo.setUrl(estilo.getUrl());
            }
            return estiloRepository.save(persistedEstilo);
        } else {
            throw new EntityNotExistentException(Estilo.class,estiloId.toString());
        }
    }

    public void deleteEstilo(Integer estiloId) throws EntityNotExistentException {
        Estilo estilo = getById(estiloId);
        estilo.setBorrado(Boolean.TRUE);
        estiloRepository.save(estilo);
    }

    public List<Estilo> findAll(){
        return estiloRepository.findAll();
    }
}
