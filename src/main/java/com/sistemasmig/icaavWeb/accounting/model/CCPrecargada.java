/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;

import lombok.Data;

@Entity
@Data

/**
 *
 * @author Julio
 */

@NamedStoredProcedureQuery(
        name = "sp_con_ctas_contables_precargadas_c",
        procedureName = "suite_mig_demo.sp_con_ctas_contables_precargadas_c",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_modelo_contable", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_rows_tot_table", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })
public class CCPrecargada {
    @Id
    private String cuenta;
    

    private String nombreCuenta;
    
  
    private String tipo;
    
  
    private int nivel;  
    

   
    
}
