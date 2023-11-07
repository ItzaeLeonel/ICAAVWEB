/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.models.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;

/**
 *
 * @author Julio
 */
@Entity
@NamedStoredProcedureQuery(
        name = "sp_con_cuentas_especiales_c",
        procedureName = "suite_mig_demo.sp_con_cuentas_especiales_c",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_grupo_empresa", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_tipo_cuenta", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })

public class CuentasEspecialesSP{
    
    @Id
    private Integer no;
    

    private String nombreCuenta;
    
  
    private String numeroCuenta;
    
  
    private String funcionalidad;  
    

    private String iva;  
    
 
    private String descripcion; 
}
