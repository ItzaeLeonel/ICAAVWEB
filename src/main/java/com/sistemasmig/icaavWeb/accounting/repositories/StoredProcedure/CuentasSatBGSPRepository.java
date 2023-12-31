/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasSatBGSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasSatBG;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentasSatBGSPRepository extends JpaRepository<CuentasSatBGSP, Integer>{
    
    @Procedure(name = "sp_con_ctas_sat_bg")
     List<ListCuentasSatBG> procedureCuentasSat(@Param("pr_consulta_gral") String pr_consulta_gral); 
    
    
}
