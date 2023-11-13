/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sistemasmig.icaavWeb.accounting.model.CuentasSatCASP;
import com.sistemasmig.icaavWeb.accounting.model.ListCuentasSatCA;

/**
 *
 * @author Julio
 */
public interface CuentasSatCASPRepository extends JpaRepository<CuentasSatCASP, Integer>{
    @Procedure(name = "sp_con_ctas_sat_cod_agru_c")
     List<ListCuentasSatCA> procedureCuentasSat(@Param("pr_id_codigo_agrupador") String pr_id_codigo_agrupador); 
}
