/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.models.dto;

import com.sistemasmig.icaavWeb.accounting.models.enums.CuentaContableEstatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import lombok.Data;

/**
 *
 * @author Julio
 */

@Data
@Entity
//----------------Stored Procedure para inseratr registros en la tabla de ic_con_cuentas_contables //
@NamedStoredProcedureQuery(
        name = "sp_con_cuentas_contables_u",
        procedureName = "suite_mig_demo.sp_con_cuentas_contables_u",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_grupo_empresa", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_cuenta_sat", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_anio", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_mes", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_numero_cuenta", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_tipo_cuenta", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_nombre", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_saldo_inicial", type=Double.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_saldo_final", type=Double.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_cargos", type=Double.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_abonos", type=Double.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_cargos_acumulado", type=Double.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_abonos_acumulados", type=Double.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_cuenta_x_pagar", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_estatus", type=CuentaContableEstatusEnum.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_numero_complementaria", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_cuenta_complementaria", type=Integer.class),    
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_inserted_id", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_affect_rows", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message2", type=String.class)
        })
public class CuentasContablesInsertSP {
    @Id
    Integer id;
}