/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasContablesSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.ListCuentasContables;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentaContableSPRepository extends JpaRepository<CuentasContablesSP, Integer>{
    
    @Procedure(name = "sp_con_ctas_contables_c")
     List<ListCuentasContables> getCuentasContablesSP(@Param("pr_id_grupo_empresa") Integer pr_id_grupo_empresa);
    
    
}
