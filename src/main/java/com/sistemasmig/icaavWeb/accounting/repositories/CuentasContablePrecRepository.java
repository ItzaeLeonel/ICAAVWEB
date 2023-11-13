/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;


import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sistemasmig.icaavWeb.accounting.entity.CuentaContablePrecargada;


/**
 *
 * @author Julio
 */
public interface CuentasContablePrecRepository extends JpaRepository<CuentaContablePrecargada, Integer>{
   
	@Procedure(name = "sp_con_ctas_contables_precargadas_c")
	Map<String, Object> spCuentasContablesPrecargadasC(@Param("pr_id_modelo_contable") Integer prIdModelContable);

}
