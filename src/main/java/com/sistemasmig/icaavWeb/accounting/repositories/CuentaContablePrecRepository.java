/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.CuentaContablePrecargada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentaContablePrecRepository extends JpaRepository<CuentaContablePrecargada, Integer>{
    CuentaContablePrecargada getByIdAndBorrado(Integer id, Boolean borrado);
    
    
    /*
    @Query(value = "CALL ic_con_tr_selector_cuentas_contables_c(:pr_id_modelo_contable, @pr_rows_tot_table, @pr_message); "
        + "SELECT @pr_rows_tot_table AS pr_rows_tot_table, @pr_message AS pr_message", nativeQuery = true)
CuentaContablePrecargada listaprocedure(@Param("pr_id_modelo_contable") Integer pr_id_modelo_contable);*/
    
       // Obeterne rdatos por valores
@Query(value="{call ic_con_tr_selector_cuentas_contables_c(:pr_id_modelo_contable)}",nativeQuery=true)
CuentaContablePrecargada listaprocedure(@Param("pr_id_modelo_contable") Integer pr_id_modelo_contable);


    

}
