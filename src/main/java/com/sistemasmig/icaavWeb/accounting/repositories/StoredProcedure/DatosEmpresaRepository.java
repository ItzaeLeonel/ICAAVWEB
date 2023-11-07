/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import com.sistemasmig.icaavWeb.accounting.models.dto.DatosEmpresaSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListDatosEmpresa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface DatosEmpresaRepository extends JpaRepository<DatosEmpresaSP,Integer>{

     
    @Procedure(name = "sp_adm_empresa_datos_generales")
     List<ListDatosEmpresa> proceduredatos(@Param("pr_id_grupo_empresa") Integer pr_id_modelo_contable);
              
}
