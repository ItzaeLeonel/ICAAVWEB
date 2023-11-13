/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sistemasmig.icaavWeb.accounting.entity.AsignacionCtasEspeciales;


/**
 *
 * @author Julio
 */
public interface AsignacionesCtasEspecialesRepository extends JpaRepository<AsignacionCtasEspeciales, Integer> {
	@Procedure(name = "sp_con_asignacion_ctas_especiales_i")
    Map<String, Object> spConAsignacionCtasEspecialesI(
        @Param("pr_id_grupo_empresa") Integer prIdGrupoEmpresa,
        @Param("pr_id_sucursal") Integer prIdSucursal,
        @Param("pr_id_cta_especial") Integer prIdCtaEspecial,
        @Param("pr_id_cuenta_contable") Integer prIdCuentaContable,
        @Param("pr_inserted_id") Integer prInsertedId,
        @Param("pr_affect_rows") Integer prAffectRows,
        @Param("pr_message") String prMessage
    );
	
	@Procedure(name = "sp_con_asignacion_ctas_especiales_d")
    Map<String, Object> spConAsignacionCtasEspecialesD(
        @Param("pr_id_asignacion") Integer prIdAsignacion,
        @Param("pr_affect_rows") Integer prAffectRows,
        @Param("pr_message") String prMessage
    );
}
