/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasContablesUpdateSP;
import com.sistemasmig.icaavWeb.accounting.models.enums.CuentaContableEstatusEnum;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentaContableUpdateRepository extends JpaRepository<CuentasContablesUpdateSP, Integer>{
    
    @Procedure(name = "sp_con_cuentas_contables_u")
     Map<String, ?> Updateprocedurecuentascont(@Param("pr_id_grupo_empresa") Integer pr_id_grupo_empresa,
                        @Param("pr_id_cuenta_contable") Integer pr_id_cuenta_contable,
                        @Param("pr_id_cuenta_sat") Integer pr_id_cuenta_sat,
                        @Param("pr_anio") Integer pr_anio,
                        @Param("pr_mes") Integer pr_mes,
                        @Param("pr_numero_cuenta") String pr_numero_cuenta,
                        @Param("pr_tipo_cuenta") String pr_tipo_cuenta,
                        @Param("pr_nombre") String pr_nombre,
                        @Param("pr_saldo_inicial") Double pr_saldo_inicial,
                        @Param("pr_saldo_final") Double pr_saldo_final,
                        @Param("pr_cargos") Double pr_cargos,
                        @Param("pr_abonos") Double pr_abonos,
                        @Param("pr_cargos_acumulado") Double pr_cargos_acumulado,
                        @Param("pr_abonos_acumulados") Double pr_abonos_acumulados,
                        @Param("pr_cuenta_x_pagar") Integer pr_cuenta_x_pagar,
                        @Param("pr_estatus") CuentaContableEstatusEnum pr_estatus,
                        @Param("pr_numero_complementaria") String pr_numero_complementaria);
    
}
