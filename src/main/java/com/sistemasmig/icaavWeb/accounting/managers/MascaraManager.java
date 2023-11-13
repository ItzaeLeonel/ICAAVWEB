/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Mascara;
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
import com.sistemasmig.icaavWeb.accounting.repositories.MascaraRepository;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class MascaraManager {
    
    @Autowired
    private MascaraRepository mascaraRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmpresaManager empresaManager;
    

    public Mascara getById(Integer id) throws EntityNotExistentException {
        Mascara mascara = mascaraRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (mascara!=null) {
            return mascara;
        }
        throw new EntityNotExistentException(Mascara.class,id.toString());
    }
    
    public PagedResponse<Mascara> getMascara(Mascara filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Mascara> cq = cb.createQuery(Mascara.class);
        Root<Mascara> root = cq.from(Mascara.class);
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
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
        
        TypedQuery<Mascara> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<Mascara> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<Mascara> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<Mascara>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public Mascara createMascara(Mascara mascara) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateMascara(mascara);
        //validateUnique(mascara);
        mascara.setBorrado(Boolean.FALSE);
        return mascaraRepository.save(mascara);
    }

    private void validateMascara(Mascara mascara) throws BusinessLogicException, EntityNotExistentException {
        if (mascara.getEmpresa()==null) {
            throw new BusinessLogicException("El campo Empresa es requerido para el objeto Mascara");
        } else {
            if(mascara.getEmpresa().getId()==null){
                throw new BusinessLogicException("El campo id de Empresa es requerido para el objeto Mascara");
            }
        }
    }
    
    /*private void validateUnique(Mascara mascara) throws ExistentEntityException {
        List<Mascara> mascaraes = mascaraRepository.findByNombre(mascara.getNombre());
        if (mascaraes!=null && !mascaraes.isEmpty()) {
            throw new ExistentEntityException(Mascara.class,"nombre="+mascara.getNombre());
        } 
    }
*/
    public Mascara updateMascara(Integer mascaraId, Mascara mascara) throws EntityNotExistentException, BusinessLogicException {
        
        Mascara persistedMascara = getById(mascaraId);
        if (persistedMascara != null) {
            if(mascara.getEmpresa()!=null){
                persistedMascara.setEmpresa(mascara.getEmpresa());
            }
            if(mascara.getNivel1()!=null){
                persistedMascara.setNivel1(mascara.getNivel1());
            }
            if(mascara.getNivel2()!=null){
                persistedMascara.setNivel2(mascara.getNivel2());
            }
            if(mascara.getNivel3()!=null){
                persistedMascara.setNivel3(mascara.getNivel3());
            }
            if(mascara.getNivel4()!=null){
                persistedMascara.setNivel4(mascara.getNivel4());
            }
            if(mascara.getNivel5()!=null){
                persistedMascara.setNivel5(mascara.getNivel5());
            }
            if(mascara.getNivel6()!=null){
                persistedMascara.setNivel6(mascara.getNivel6());
            }
            if(mascara.getNivel7()!=null){
                persistedMascara.setNivel7(mascara.getNivel7());
            }
            if(mascara.getNivel8()!=null){
                persistedMascara.setNivel8(mascara.getNivel8());
            }
             if(mascara.getNivel9()!=null){
                persistedMascara.setNivel9(mascara.getNivel9());
            }
            return mascaraRepository.save(persistedMascara);
        } else {
            throw new EntityNotExistentException(Mascara.class,mascaraId.toString());
        }
    }

    public void deleteMascara(Integer mascaraId) throws EntityNotExistentException {
        Mascara mascara = getById(mascaraId);
        mascara.setBorrado(Boolean.TRUE);
        mascaraRepository.save(mascara);
    }

    public List<Mascara> findAll(){
        return mascaraRepository.findAll();
    }
}
