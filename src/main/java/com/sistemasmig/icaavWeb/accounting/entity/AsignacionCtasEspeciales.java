/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julio
 */

@Entity
@Table(name = "IC_CON_TR_ASIGNACION_CUENTAS_ESPECIALES")
@NamedStoredProcedureQuery(
	    name = "sp_con_asignacion_ctas_especiales_i",
	    procedureName = "icaavwebaccounting.sp_con_asignacion_ctas_especiales_i",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pr_id_grupo_empresa", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pr_id_sucursal", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pr_id_cta_especial", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pr_id_cuenta_contable", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "pr_inserted_id", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "pr_affect_rows", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "pr_message", type = String.class)
	    })
@NamedStoredProcedureQuery(
	    name = "sp_con_asignacion_ctas_especiales_d",
	    procedureName = "icaavwebaccounting.sp_con_asignacion_ctas_especiales_d",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "pr_id_asignacion", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "pr_affect_rows", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "pr_message", type = String.class)
	    })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionCtasEspeciales extends DatosAuditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    @Column(name = "ID_ASIGNACION", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    
    
    @ManyToOne
    @JoinColumn(name = "ID_CTA_CONTABLE", nullable = true)
    private CuentaContable cuentaContable;
    
    @ManyToOne
    @JoinColumn(name = "ID_CTA_ESPECIAL", nullable = true)
    private CuentasEspeciales ctaEspecial;
    
    @ManyToOne
    @JoinColumn(name = "ID_SUCURSAL", nullable = true)
    private Sucursales sucursal;
     
    
}


