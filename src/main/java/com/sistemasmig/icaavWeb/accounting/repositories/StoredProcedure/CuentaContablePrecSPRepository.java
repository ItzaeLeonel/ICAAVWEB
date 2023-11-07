/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;


import com.sistemasmig.icaavWeb.accounting.models.dto.CCPrecargada;
import com.sistemasmig.icaavWeb.accounting.models.dto.CCPrecargadasSP;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentaContablePrecSPRepository extends JpaRepository<CCPrecargada,Integer>{

     @Procedure(name = "sp_con_ctas_contables_precargadas_c")
     List<CCPrecargadasSP> getCCPrecargadaSP(@Param("pr_id_modelo_contable") Integer pr_id_grupo_empresa);
        
    
    
}
