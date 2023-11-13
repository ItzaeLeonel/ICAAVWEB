/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import com.sistemasmig.icaavWeb.accounting.enums.EstatusCuentaContablePrecargadaEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julio
 */

@Entity
@Table(name = "IC_ADM_MODELO_CTAS_CONTABLES_PREGARGADAS")
@NamedStoredProcedureQuery(
        name = "sp_con_ctas_contables_precargadas_c",
        procedureName = "suite_mig_demo.sp_con_ctas_contables_precargadas_c",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_modelo_contable", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_rows_tot_table", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaContablePrecargada extends DatosAuditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODELO_CONTABLE", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @Column(name = "NOMBRE", columnDefinition = "VARCHAR(200)")
    private String nombre;
    
    @Column(name = "DESCRIPCION", columnDefinition = "VARCHAR(200)")
    private String descripcion;
    
    @Column(name = "NIVEL1", columnDefinition = "INT(11)")
    private Integer nivel1;
    
    @Column(name = "NIVEL2", columnDefinition = "INT(11)")
    private Integer nivel2;
    
    @Column(name = "NIVEL3", columnDefinition = "INT(11)")
    private Integer nivel3;
    
    @Column(name = "NIVEL4", columnDefinition = "INT(11)")
    private Integer nivel4;
    
    @Column(name = "NIVEL5", columnDefinition = "INT(11)")
    private Integer nivel5;
    
    @Column(name = "NIVEL6", columnDefinition = "INT(11)")
    private Integer nivel6;
    
    @Column(name = "NIVEL7", columnDefinition = "INT(11)")
    private Integer nivel7;
    
    @Column(name = "NIVEL8", columnDefinition = "INT(11)")
    private Integer nivel8;
    
    @Column(name = "NIVEL9", columnDefinition = "INT(11)")
    private Integer nivel9;
    
    @Column(name = "MASCARA", columnDefinition = "VARCHAR(100)")
    private String mascara;
    
    @Column(name = "NO_NIVELES", columnDefinition = "INT(11)")
    private Integer no_niveles;
    
    @Column(name = "NO_DIGITOS", columnDefinition = "INT(11)")
    private Integer no_digitos;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
	private EstatusCuentaContablePrecargadaEnum estatusCuentaContablePrecargadaEnum;
}
