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
        name = "sp_adm_empresa_datos_generales",
        procedureName = "suite_mig_demo.sp_adm_empresa_datos_generales",
        parameters = {
          @StoredProcedureParameter(mode=ParameterMode.IN, name="pr_id_grupo_empresa", type=Integer.class),
          @StoredProcedureParameter(mode=ParameterMode.OUT, name="pr_message", type=String.class)
        })

public class DatosEmpresaSP {
    
     
     String RazonSocial;
     String NombreComercial;
     String RFC;
     String Telefono;  
     String Email; 
     @Id  
     Integer Codigoempresa; 
     String Pais;  
     String CodigoPostal;  
     String Colonia; 
     String Ciudad;
     String AlcaldiaOMunicipio;  
     String Estado;  
     String Calle;  
     String NoExterior;  
     String NoInterior;  
     Integer Usuarios;  
     Integer Sucursales;
     Integer PlandeNegocios;

}
