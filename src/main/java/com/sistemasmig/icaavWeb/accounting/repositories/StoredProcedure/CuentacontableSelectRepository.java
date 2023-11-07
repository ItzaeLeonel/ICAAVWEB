/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import com.sistemasmig.icaavWeb.accounting.models.dto.UpdateCCSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentacontableSelectRepository extends JpaRepository<UpdateCCSP, Integer>{
    @Query(value="select id_cuenta_contable  from suite_mig_demo.ic_con_tr_cuentas_contables where id_cuenta_contable = :id",nativeQuery = true)
   
    public UpdateCCSP getById(@Param("id") Integer id);
    
}
