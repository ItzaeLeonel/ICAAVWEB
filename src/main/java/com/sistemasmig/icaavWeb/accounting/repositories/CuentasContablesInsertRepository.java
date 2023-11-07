/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.AsignacionCuentasEspDemo;
import com.sistemasmig.icaavWeb.accounting.models.dto.InsertCuentaC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentasContablesInsertRepository extends JpaRepository<InsertCuentaC, Integer>{
    @Query(value="select id_asignacion  from suite_mig_demo.ic_con_tr_cuentas_contables where id_cuenta_contable = :id",nativeQuery = true)
    public InsertCuentaC getById(@Param("id") Integer id);
    
}
