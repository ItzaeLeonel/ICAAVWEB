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

/**
 *
 * @author Julio
 */

@Entity
@Data

@NamedStoredProcedureQuery(
        name = "sp_con_ctas_sat_cod_agru_c",
        procedureName = "suite_mig_demo.sp_con_ctas_sat_cod_agru_c",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_codigo_agrupador", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_rows_tot_table", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })
public class CuentasSatCASP {
    
     @Id
     Integer id;
     String CuentaSat;
     String NombreCuenta;
     String Nivel;
     String Naturaleza;  
     String CuentaMayor; 
     String Grupo;  
     String Subgrupo; 
    
}
