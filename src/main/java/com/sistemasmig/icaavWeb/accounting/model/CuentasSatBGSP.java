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

/**
 *
 * @author Julio
 */

@Entity
@Data

@NamedStoredProcedureQuery(
        name = "sp_con_ctas_sat_bg",
        procedureName = "suite_mig_demo.sp_con_ctas_sat_bg",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_consulta_gral", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_rows_tot_table", type=String.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })
public class CuentasSatBGSP {
    
     @Id
     Integer id;
     String CuentaSat;
     String CodigoAgrupador;
     String Nivel;
     String Naturaleza;  
     String CuentaMayor; 
     String Grupo;  
     String Subgrupo; 
    
}
