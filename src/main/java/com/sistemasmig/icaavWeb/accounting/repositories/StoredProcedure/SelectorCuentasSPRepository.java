/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;


import com.sistemasmig.icaavWeb.accounting.models.dto.ListSelectorCuentas;
import com.sistemasmig.icaavWeb.accounting.models.dto.SelectorCuentasSP;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface SelectorCuentasSPRepository extends JpaRepository<SelectorCuentasSP,Integer>{
     //Query para el procedure 
    
    //CALL `sp_con_ctas_contables_precargadas_c`(:pr_id_modelo_contable, @p1, @p2); SELECT @p2 AS `pr_message`;
/* @Query(value="call suite_mig_demo.ic_con_tr_selector_cuentas_contables_c(:pr_id_modelo_contable,@p1, @p2)",nativeQuery=true)
       List<ListSelectorCuentas> getSelectorCuentaSP(@Param("pr_id_modelo_contable") Integer  pr_id_modelo_contable);*/
     
    @Procedure(name = "ic_con_tr_selector_cuentas_contables_c")
     List<ListSelectorCuentas> listaprocedure(@Param("pr_id_modelo_contable") Integer pr_id_modelo_contable);
              
    
    
}
