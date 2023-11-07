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

@Data
@Entity
//----------------Stored Procedure para insertar en la tabla de ic_con_tr_asignacion_ctas_especiales //
@NamedStoredProcedureQuery(
        name = "sp_con_asignacion_ctas_especiales_i",
        procedureName = "suite_mig_demo.sp_con_asignacion_ctas_especiales_i",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_cta_especial", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_empresa", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_cuenta_contable", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_inserted_id", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_affect_rows", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })


public class StoreProcedures {
    @Id
    Integer id;
 
}
