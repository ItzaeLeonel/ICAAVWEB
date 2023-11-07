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
import com.sistemasmig.icaavWeb.accounting.models.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasContablesSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.InsertCuentaC;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasContables;
import com.sistemasmig.icaavWeb.accounting.models.dto.UpdateCCSP;
import com.sistemasmig.icaavWeb.accounting.models.enums.CuentaContableEstatusEnum;
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
import com.sistemasmig.icaavWeb.accounting.repositories.CuentaContableRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.CuentasContablesInsertRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentaContableInsertRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentaContableSPRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentaContableUpdateRepository;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.CuentacontableSelectRepository;
import java.util.Map;

/**
 *
 * @author Waldir.Valle
 */
@Component
public class CuentaContableManager {
    
    @Autowired
    private CuentaContableRepository cuentaContableRepository;
    @Autowired
    private CuentaContableUpdateRepository cuentaContableUpdateRepository;
    @Autowired
    private CuentacontableSelectRepository cuentacontableSelectRepository;
    
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private GrupoEmpresaManager grupoEmpresaManager;
    
    @Autowired
    private SatCuentaContableManager satCuentaContableManager;
    
    @Autowired
    private CuentaContableSPRepository cuentaContableSPRepository;
    
    @Autowired
    private CuentasContablesInsertRepository cuentasContablesInsertRepository;
    
    @Autowired
    private CuentaContableInsertRepository cuentaContableInsertRepository;
    

    public CuentaContable getById(Integer id) throws EntityNotExistentException {
        CuentaContable cuentaContable = cuentaContableRepository.getByIdAndBorrado(id, Boolean.FALSE);
        if (cuentaContable!=null) {
            return cuentaContable;
        }
        throw new EntityNotExistentException(CuentaContable.class,id.toString());
    }
    
    public PagedResponse<CuentaContable> getCuentaContable(CuentaContable filter, Paging paging){
        
        Pageable pageable = PageRequest.of(paging.getPage(), paging.getPageSize());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<CuentaContable> cq = cb.createQuery(CuentaContable.class);
        Root<CuentaContable> root = cq.from(CuentaContable.class);
        
        

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("borrado"), Boolean.FALSE));
        cq.orderBy(cb.desc(root.get("id")));
        
        if(filter.getAbonos()!=null){
            predicates.add(cb.equal(root.get("abonos"), filter.getAbonos()));
        }
        if(filter.getAbonosAcumulados()!=null){
            predicates.add(cb.equal(root.get("abonosAcumulados"), filter.getAbonosAcumulados()));
        }
        if(filter.getAnio()!=null){
            predicates.add(cb.equal(root.get("anio"), filter.getAnio()));
        }
        if(filter.getCargos()!=null){
            predicates.add(cb.equal(root.get("cargos"), filter.getCargos()));
        }
        if(filter.getCargosAcumulados()!=null){
            predicates.add(cb.equal(root.get("cargosAcumulados"), filter.getCargosAcumulados()));
        }
        if(filter.getCuentaContableEstatusEnum()!=null){
            predicates.add(cb.equal(root.get("cuentaContableEstatusEnum"), filter.getCuentaContableEstatusEnum()));
        }
        if(filter.getCuentaXPagar()!=null){
            predicates.add(cb.equal(root.get("cuentaXPagar"), filter.getCuentaXPagar()));
        }
        if(filter.getEsCtaComp()!=null){
            predicates.add(cb.equal(root.get("esCtaComp"), filter.getEsCtaComp()));
        }
        if(filter.getFechaModificacion()!=null && filter.getFechaModificacion2()!=null){
            predicates.add(cb.between(root.get("fechaModificacion"), filter.getFechaModificacion(),filter.getFechaModificacion2()));
            cq.orderBy(cb.desc(root.get("fechaModificacion")));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getAmex()!=null){
            predicates.add(cb.equal(root.get("grupoEmpresa").get("amex"), filter.getGrupoEmpresa().getAmex()));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getEmpresa()!=null && filter.getGrupoEmpresa().getEmpresa().getId()!=null){
            predicates.add(cb.equal(root.get("grupoEmpresa").get("empresa").get("id"), filter.getGrupoEmpresa().getEmpresa().getId()));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getEmpresa()!=null && filter.getGrupoEmpresa().getEmpresa().getNombre()!=null){
            predicates.add(cb.like(cb.lower(root.get("grupoEmpresa").get("empresa").get("nombre")), "%" + filter.getGrupoEmpresa().getEmpresa().getNombre().toLowerCase()+ "%"));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getEstatusUsuarioEmpresa()!=null){
            predicates.add(cb.equal(root.get("grupoEmpresa").get("estatusUsuarioEmpresa"), filter.getGrupoEmpresa().getEstatusUsuarioEmpresa()));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa()!=null && filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()!=null){
             predicates.add(cb.between(root.get("grupoEmpresa").get("fechaModificacionUsuarioEmpresa"), filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa(),filter.getGrupoEmpresa().getFechaModificacionUsuarioEmpresa2()));
            cq.orderBy(cb.desc(root.get("grupoEmpresa").get("fechaModificacion")));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getGrupoId()!=null){
            predicates.add(cb.equal(root.get("grupoEmpresa").get("grupoId"), filter.getGrupoEmpresa().getGrupoId()));
        }
        if(filter.getGrupoEmpresa()!=null && filter.getGrupoEmpresa().getId()!=null){
            predicates.add(cb.equal(root.get("grupoEmpresa").get("id"), filter.getGrupoEmpresa().getId()));
        }
        if(filter.getId()!=null){
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if(filter.getMes()!=null){
            predicates.add(cb.equal(root.get("mes"), filter.getMes()));
        }
        if(filter.getNaturaleza()!=null){
            predicates.add(cb.like(cb.lower(root.get("naturaleza")), "%" + filter.getNaturaleza().toLowerCase()+ "%"));
        }
        if(filter.getNivel()!=null){
            predicates.add(cb.equal(root.get("nivel"), filter.getNivel()));
        }
        if(filter.getNombre()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase()+ "%"));
        }
        if(filter.getNombreCuentaSat()!=null){
            predicates.add(cb.like(cb.lower(root.get("nombreCuentaSat")), "%" + filter.getNombreCuentaSat().toLowerCase()+ "%"));
        }
        if(filter.getNumeroComplementaria()!=null){
            predicates.add(cb.like(cb.lower(root.get("numeroComplementaria")), "%" + filter.getNumeroComplementaria().toLowerCase()+ "%"));
        }
        if(filter.getNumeroCuenta()!=null){
            predicates.add(cb.like(cb.lower(root.get("numeroCuenta")), "%" + filter.getNumeroCuenta().toLowerCase()+ "%"));
        }
        if(filter.getNumeroCuentaMayor()!=null){
            predicates.add(cb.like(cb.lower(root.get("numeroCuentaMayor")), "%" + filter.getNumeroCuentaMayor().toLowerCase()+ "%"));
        }
        if(filter.getSaldoFinal()!=null){
            predicates.add(cb.equal(root.get("saldoFinal"), filter.getSaldoFinal()));
        }
        if(filter.getSaldoIncial()!=null){
            predicates.add(cb.equal(root.get("saldoIncial"), filter.getSaldoIncial()));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getCuentaMayor()!=null){
            predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("cuentaMayor")), "%" + filter.getSatCuentaContable().getCuentaMayor().toLowerCase()+ "%"));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getCuentaSat()!=null){
            predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("cuentaSat")), "%" + filter.getSatCuentaContable().getCuentaSat().toLowerCase()+ "%"));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getGrupo()!=null){
            predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("grupo")), "%" + filter.getSatCuentaContable().getGrupo().toLowerCase()+ "%"));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getId()!=null){
            predicates.add(cb.equal(root.get("satCuentaContable").get("id"), filter.getSatCuentaContable().getId()));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getNaturaleza()!=null){
            predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("naturaleza")), "%" + filter.getSatCuentaContable().getNaturaleza().toLowerCase()+ "%"));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getNivel()!=null){
            predicates.add(cb.equal(root.get("satCuentaContable").get("nivel"), filter.getSatCuentaContable().getNivel()));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getNombreCuenta()!=null){
            predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("nombreCuenta")), "%" + filter.getSatCuentaContable().getNombreCuenta().toLowerCase()+ "%"));
        }
        if(filter.getSatCuentaContable()!=null && filter.getSatCuentaContable().getSubGrupo()!=null){
            predicates.add(cb.like(cb.lower(root.get("satCuentaContable").get("subGrupo")), "%" + filter.getSatCuentaContable().getSubGrupo().toLowerCase()+ "%"));
        }
        if(filter.getTipoCuenta()!=null){
            predicates.add(cb.like(cb.lower(root.get("tipoCuenta")), "%" + filter.getTipoCuenta().toLowerCase()+ "%"));
        }
        if(filter.getUsuarioId()!=null){
            predicates.add(cb.equal(root.get("usuarioId"), filter.getSaldoFinal()));
        }
        
        cq.select(root);
        if(predicates.size()>0){
            cq.where(predicates.toArray(new Predicate[0]));
        }
        
        TypedQuery<CuentaContable> query = entityManager.createQuery(cq);
        
        int iTotal = query.getResultList().size();

        
        
        List<CuentaContable> result = query.setFirstResult((int) pageable.getOffset())
                                    .setMaxResults(pageable.getPageSize())
                                    .getResultList();
        
        
        Page<CuentaContable> page = new PageImpl<>(result, pageable, iTotal);
        
        return new PagedResponse<CuentaContable>((int) page.getTotalElements(),page.getTotalPages(), paging.getPage(), paging.getPageSize(), page.getContent());   
    }

    public CuentaContable createCuentaContable(CuentaContable cuentaContable) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        validateCuentaContable(cuentaContable);
        validateUnique(cuentaContable);
        cuentaContable.setBorrado(Boolean.FALSE);
        return cuentaContableRepository.save(cuentaContable);
    }

    private void validateCuentaContable(CuentaContable cuentaContable) throws BusinessLogicException, EntityNotExistentException {
        if (StringUtils.isEmpty(cuentaContable.getNombre())) {
            throw new BusinessLogicException("El campo Nombre es requerido para el objeto CuentaContable");
        } else if (StringUtils.isEmpty(cuentaContable.getNumeroCuenta())) {
            throw new BusinessLogicException("El campo NumeroCuenta es requerido para el objeto CuentaContable");
        } 
        if(cuentaContable.getGrupoEmpresa()!=null){
            if(cuentaContable.getGrupoEmpresa().getId()==null){
                throw new BusinessLogicException("El campo id de GrupoEmpresa es requerido para el objeto CuentaContable");
            }
            grupoEmpresaManager.getById(cuentaContable.getGrupoEmpresa().getId());
        } 
        if(cuentaContable.getSatCuentaContable()!=null){
            if(cuentaContable.getSatCuentaContable().getId()==null){
                throw new BusinessLogicException("El campo id de SatCuentaContable es requerido para el objeto CuentaContable");
            }
            satCuentaContableManager.getById(cuentaContable.getSatCuentaContable().getId());
        }
    }
    
    private void validateUnique(CuentaContable cuentaContable) throws ExistentEntityException {
        List<CuentaContable> cuentaContablees = cuentaContableRepository.findByNumeroCuentaAndBorrado(cuentaContable.getNumeroCuenta(), Boolean.FALSE);
        if (cuentaContablees!=null && !cuentaContablees.isEmpty()) {
            throw new ExistentEntityException(CuentaContable.class,"número cuenta="+cuentaContable.getNombre());
        } 
        cuentaContablees = cuentaContableRepository.findByNombreAndBorrado(cuentaContable.getNombre(), Boolean.FALSE);
        if (cuentaContablees!=null && !cuentaContablees.isEmpty()) {
            throw new ExistentEntityException(CuentaContable.class,"nombre="+cuentaContable.getNombre());
        } 
    }

    public CuentaContable updateCuentaContable(Integer cuentaContableId, CuentaContable cuentaContable) throws EntityNotExistentException, BusinessLogicException {
        
        CuentaContable persistedCuentaContable = getById(cuentaContableId);
        if (persistedCuentaContable != null) {
            if(cuentaContable.getAbonos()!=null){
                persistedCuentaContable.setAbonos(cuentaContable.getAbonos());
            }
            if(cuentaContable.getAbonosAcumulados()!=null){
                persistedCuentaContable.setAbonosAcumulados(cuentaContable.getAbonosAcumulados());
            }
            if(cuentaContable.getAnio()!=null){
                persistedCuentaContable.setAnio(cuentaContable.getAnio());
            }
            if(cuentaContable.getCargos()!=null){
                persistedCuentaContable.setCargos(cuentaContable.getCargos());
            }
            if(cuentaContable.getCargosAcumulados()!=null){
                persistedCuentaContable.setCargosAcumulados(cuentaContable.getCargosAcumulados());
            }
            if(cuentaContable.getCuentaContableEstatusEnum()!=null){
                persistedCuentaContable.setCuentaContableEstatusEnum(cuentaContable.getCuentaContableEstatusEnum());
            }
            if(cuentaContable.getCuentaXPagar()!=null){
                persistedCuentaContable.setCuentaXPagar(cuentaContable.getCuentaXPagar());
            }
            if(cuentaContable.getEsCtaComp()!=null){
                persistedCuentaContable.setEsCtaComp(cuentaContable.getEsCtaComp());
            }
            if(cuentaContable.getGrupoEmpresa()!=null){
                persistedCuentaContable.setGrupoEmpresa(cuentaContable.getGrupoEmpresa());
            }
            if(cuentaContable.getMes()!=null){
                persistedCuentaContable.setMes(cuentaContable.getMes());
            }
            if(cuentaContable.getNaturaleza()!=null){
                persistedCuentaContable.setNaturaleza(cuentaContable.getNaturaleza());
            }
            if(cuentaContable.getNivel()!=null){
                persistedCuentaContable.setNivel(cuentaContable.getNivel());
            }
            if(cuentaContable.getNombre()!=null){
                persistedCuentaContable.setNombre(cuentaContable.getNombre());
            }
            if(cuentaContable.getNombreCuentaSat()!=null){
                persistedCuentaContable.setNombreCuentaSat(cuentaContable.getNombreCuentaSat());
            }
            if(cuentaContable.getNumeroComplementaria()!=null){
                persistedCuentaContable.setNumeroComplementaria(cuentaContable.getNumeroComplementaria());
            }
            if(cuentaContable.getNumeroCuenta()!=null){
                persistedCuentaContable.setNumeroCuenta(cuentaContable.getNumeroCuenta());
            }
            if(cuentaContable.getNumeroCuentaMayor()!=null){
                persistedCuentaContable.setNumeroCuentaMayor(cuentaContable.getNumeroCuentaMayor());
            }
            if(cuentaContable.getSaldoFinal()!=null){
                persistedCuentaContable.setSaldoFinal(cuentaContable.getSaldoFinal());
            }
            if(cuentaContable.getSaldoIncial()!=null){
                persistedCuentaContable.setSaldoIncial(cuentaContable.getSaldoIncial());
            }
            if(cuentaContable.getSatCuentaContable()!=null){
                persistedCuentaContable.setSatCuentaContable(cuentaContable.getSatCuentaContable());
            }
            if(cuentaContable.getTipoCuenta()!=null){
                persistedCuentaContable.setTipoCuenta(cuentaContable.getTipoCuenta());
            }
            if(cuentaContable.getUsuarioId()!=null){
                persistedCuentaContable.setUsuarioId(cuentaContable.getUsuarioId());
            }
            if(cuentaContable.getNombre()!=null){
                persistedCuentaContable.setNombre(cuentaContable.getNombre());
            }
            
            return cuentaContableRepository.save(persistedCuentaContable);
        } else {
            throw new EntityNotExistentException(CuentaContable.class,cuentaContableId.toString());
        }
    }

    public void deleteCuentaContable(Integer cuentaContableId) throws EntityNotExistentException {
        CuentaContable cuentaContable = getById(cuentaContableId);
        cuentaContable.setBorrado(Boolean.TRUE);
        cuentaContableRepository.save(cuentaContable);
    }

    public List<CuentaContable> findAll(){
        return cuentaContableRepository.findAll();
    }
    
    public CuentaContable getByNumeroCuentaAndBorrado (String numeroCuenta,Boolean borrado){
        return cuentaContableRepository.getByNumeroCuentaAndBorrado(numeroCuenta, borrado);
    }
    
    public List<CuentaContable> findByNumeroCuentaAndBorrado(String numeroCuenta,Boolean borrado){
        return cuentaContableRepository.findByNumeroCuentaAndBorrado(numeroCuenta,borrado);
    }
    
    public List <ListCuentasContables> getCuentasContablesSP(Integer pr_id_grupo_empresa) throws EntityNotExistentException {
    List<ListCuentasContables>resultado=cuentaContableSPRepository.getCuentasContablesSP(pr_id_grupo_empresa);
    return resultado;
    }
    public InsertCuentaC createByCuentaContableSP(Integer pr_id_empresa, Integer pr_id_cuenta_sat,Integer pr_anio, Integer pr_mes, String pr_numero_cuenta,String pr_tipo_cuenta,String pr_nombre, Double pr_saldo_inicial, Double pr_saldo_final, Double pr_cargos, Double pr_abonos, Double pr_cargos_acumulado, Double pr_abonos_acumulados,Integer pr_cuenta_x_pagar, CuentaContableEstatusEnum pr_estatus,String pr_numero_complementaria, Integer pr_cuenta_complementaria) throws EntityNotExistentException {
    Map<String, ?>resultado=cuentaContableInsertRepository.Insertprocedurecuentascont(pr_id_empresa, pr_id_cuenta_sat, pr_anio, pr_mes, pr_numero_cuenta, pr_tipo_cuenta, pr_nombre, pr_saldo_inicial, pr_saldo_final, pr_cargos, pr_abonos, pr_cargos_acumulado, pr_abonos_acumulados, pr_cuenta_x_pagar, pr_estatus, pr_numero_complementaria, pr_cuenta_complementaria);
     resultado.get("pr_inserted_id");

    if (resultado.get("pr_message") != null && resultado.get("pr_message").equals("SUCCESS")) {
        // Obtén la asignación de cuentas especiales actualizada
        InsertCuentaC insertCuentaC = cuentasContablesInsertRepository.getById((Integer) resultado.get("pr_inserted_id"));
        if (insertCuentaC == null) {
            throw new EntityNotExistentException("No es posible insertar datos.");
        }  
        return insertCuentaC;
              
    } else {
        throw new EntityNotExistentException("No se pudo realizar la consulta");
    }
    
}
       public UpdateCCSP updateByCuentaContableSP(Integer pr_id_empresa, Integer pr_id_cuenta_sat,Integer pr_anio, Integer pr_mes, String pr_numero_cuenta,String pr_tipo_cuenta,String pr_nombre, Double pr_saldo_inicial, Double pr_saldo_final, Double pr_cargos, Double pr_abonos, Double pr_cargos_acumulado, Double pr_abonos_acumulados,Integer pr_cuenta_x_pagar, String pr_estatus,String pr_numero_complementaria) throws EntityNotExistentException {

     Map<String, ?>resultado=cuentaContableUpdateRepository.Updateprocedurecuentascont(pr_id_empresa, pr_id_cuenta_sat, pr_id_cuenta_sat, pr_anio, pr_mes, pr_numero_cuenta, pr_tipo_cuenta, pr_nombre, pr_saldo_inicial, pr_saldo_final, pr_cargos, pr_abonos, pr_cargos_acumulado, pr_abonos_acumulados, pr_cuenta_x_pagar, CuentaContableEstatusEnum.ACTIVO, pr_numero_complementaria);
   

    if (resultado.get("pr_message") != null && resultado.get("pr_message").equals("SUCCESS")) {
      
    
       

    
    
       }
        return null;
   }
          }
