/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;

import com.sistemasmig.icaavWeb.accounting.enums.CuentaContableEstatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Waldir.Valle
 */
@Entity
@Table(name = "IC_CON_TR_CUENTAS_CONTABLES")
@NamedStoredProcedureQuery(
        name = "sp_con_ctas_contables_c",
        procedureName = "icaavwebaccounting.sp_con_ctas_contables_c",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_grupo_empresa", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_rows_tot_table", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })
@NamedStoredProcedureQuery(
        name = "sp_con_cuentas_contables_i",
        procedureName = "icaavwebaccounting.sp_con_cuentas_contables_i",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_grupo_empresa", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_cuenta_sat", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_anio", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_mes", type=Integer.class),
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
@NamedStoredProcedureQuery(
        name = "sp_con_cuentas_contables_u",
        procedureName = "icaavwebaccounting.sp_con_cuentas_contables_u",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_cuenta_contable", type=Integer.class),
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
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_affect_rows", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
       
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaContable extends DatosAuditoria{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CUENTA_CONTABLE", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
	private GrupoEmpresa grupoEmpresa;
	
	@ManyToOne
	@JoinColumn(name = "ID_CUENTA_SAT", nullable = true)
	private SatCuentaContable satCuentaContable;
	
	@Column(name = "ANIO", columnDefinition = "INT(4)")
	private Integer anio;
	
	@Column(name = "MES", columnDefinition = "INT(2)")
	private Integer mes;
	
	@Column(name = "NUMERO_CUENTA", columnDefinition = "VARCHAR(20)")
	private String numeroCuenta;
	
	@Column(name = "TIPO_CUENTA", columnDefinition = "VARCHAR(1)")
	private String tipoCuenta;
	
	@Column(name = "NOMBRE", columnDefinition = "VARCHAR(50)")
	private String nombre;
	
	@Column(name = "SALDO_INICIAL", columnDefinition = "DECIMAL(16,4)")
	private Double saldoIncial;
	
	@Column(name = "SALDO_FINAL", columnDefinition = "DECIMAL(16,4)")
	private Double saldoFinal;
	
	@Column(name = "CARGOS", columnDefinition = "DECIMAL(16,4)")
	private Double cargos;
	
	@Column(name = "ABONOS", columnDefinition = "DECIMAL(16,4)")
	private Double abonos;
	
	@Column(name = "CARGOS_ACUMULADOS", columnDefinition = "DECIMAL(16,4)")
	private Double cargosAcumulados;
	
	@Column(name = "ABONOS_ACUMULADOS", columnDefinition = "DECIMAL(16,4)")
	private Double abonosAcumulados;
	
	@Column(name = "NIVEL", columnDefinition = "INT(11)")
	private Integer nivel;
	
	@Column(name = "CUENTA_X_PAGAR", columnDefinition = "INT(11)")
	private Integer cuentaXPagar;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
	private CuentaContableEstatusEnum cuentaContableEstatusEnum;
	
	@Column(name = "NUMERO_COMPLEMENTARIA", columnDefinition = "VARCHAR(50)")
	private String numeroComplementaria;
	
	@Column(name = "ES_CTA_COMP", columnDefinition = "INT(1)")
	private Integer esCtaComp;
	
	@Column(name = "NATURALEZA", columnDefinition = "VARCHAR(1)")
	private String naturaleza;
	
	@Column(name = "NUMERO_CUENTA_MAYOR", columnDefinition = "VARCHAR(50)")
	private String numeroCuentaMayor;
	
	@Column(name = "NOMBRE_CUENTA_SAT", columnDefinition = "VARCHAR(200)")
	private String nombreCuentaSat;
	
	@Column(name = "ID_USUARIO", columnDefinition = "INT(1)")
	private Integer idUsuario;
	
}
