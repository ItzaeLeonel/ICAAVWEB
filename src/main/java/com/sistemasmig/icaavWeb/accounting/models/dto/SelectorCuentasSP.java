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

import lombok.Data;

@Entity
@Data

@NamedStoredProcedureQuery(
        name = "ic_con_tr_selector_cuentas_contables_c",
        procedureName = "suite_mig_demo.ic_con_tr_selector_cuentas_contables_c",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_modelo_contable", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_rows_tot_table", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })





/**
 *
 * @author Julio
 */
public class SelectorCuentasSP {
  @Id
   Integer id;
    private String cuenta;
    

    private String nombreCuenta;
    
  
    private String tipo;
    
  
    private int nivel;  
}
