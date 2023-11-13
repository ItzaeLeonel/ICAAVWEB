/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sistemasmig.icaavWeb.accounting.entity.CuentaContable;
import com.sistemasmig.icaavWeb.accounting.enums.CuentaContableEstatusEnum;
import com.sistemasmig.icaavWeb.accounting.model.ListCuentasContables;

/**
 *
 * @author Waldir.Valle
 */

public interface CuentasContableRepository extends JpaRepository<CuentaContable, Integer> {

	@Procedure(name = "sp_con_cuentas_contables_i")
	Map<String, Object> spConCuentasContablesI(@Param("pr_id_grupo_empresa") Integer prIdGrupoEmpresa,
			@Param("pr_id_cuenta_sat") Integer prIdCuentaSat, @Param("pr_anio") Integer prAnio,
			@Param("pr_mes") Integer prMes, @Param("pr_numero_cuenta") String prNumeroCuenta,
			@Param("pr_tipo_cuenta") String prTipoCuenta, @Param("pr_nombre") String prNombre,
			@Param("pr_saldo_inicial") Double prSaldoInicial, @Param("pr_saldo_final") Double prSaldoFinal,
			@Param("pr_cargos") Double prCargos, @Param("pr_abonos") Double prAbonos,
			@Param("pr_cargos_acumulado") Double prCargosAcumulado,
			@Param("pr_abonos_acumulados") Double prAbonosAcumulados,
			@Param("pr_cuenta_x_pagar") Integer prCuentaXPagar,
			@Param("pr_estatus") CuentaContableEstatusEnum prEstatus,
			@Param("pr_numero_complementaria") String prNumeroComplementaria,
			@Param("pr_cuenta_complementaria") Integer prCuentaComplementaria,
			@Param("pr_inserted_id") Integer prInsertedId, @Param("pr_affect_rows") Integer prAffectRows,
			@Param("pr_message") String prMessage, @Param("pr_message2") String prMessage2);

	@Procedure(name = "sp_con_cuentas_contables_u")
	Map<String, Object> spConCuentasContablesU(@Param("pr_id_grupo_empresa") Integer prIdGrupoEmpresa,
			@Param("pr_id_cuenta_contable") Integer prIdCuentaContable,
			@Param("pr_id_cuenta_sat") Integer prIdCuentaSat, @Param("pr_anio") Integer prAnio,
			@Param("pr_mes") Integer prMes, @Param("pr_numero_cuenta") String prNumeroCuenta,
			@Param("pr_tipo_cuenta") String prTipoCuenta, @Param("pr_nombre") String prNombre,
			@Param("pr_saldo_inicial") Double prSaldoInicial, @Param("pr_saldo_final") Double prSaldoFinal,
			@Param("pr_cargos") Double prCargos, @Param("pr_abonos") Double prAbonos,
			@Param("pr_cargos_acumulado") Double prCargosAcumulado,
			@Param("pr_abonos_acumulados") Double prAbonosAcumulados,
			@Param("pr_cuenta_x_pagar") Integer prCuentaXPagar,
			@Param("pr_estatus") CuentaContableEstatusEnum prEstatus,
			@Param("pr_numero_complementaria") String prNumeroComplementaria,
			@Param("pr_affect_rows") Integer prAffectRows, @Param("pr_message") String prMessage);
	
	@Procedure(name = "sp_con_ctas_contables_c")
	Map<String, Object> spConCuentasContablesC(@Param("pr_id_grupo_empresa") Integer prIdGrupoEmpresa,
		    @Param("pr_rows_tot_table") Integer prAffectRows,
		    @Param("pr_message") String prMessage);
}
