/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;


import com.sistemasmig.icaavWeb.accounting.models.AsignacionCuentasEspDemo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface AsignacionCtasEspDemoRepository extends CrudRepository<AsignacionCuentasEspDemo, Integer> {
    
    
    @Query(value="select id_asignacion  from suite_mig_demo.ic_con_tr_asignacion_ctas_especiales where id_asignacion = :id",nativeQuery = true)
    public AsignacionCuentasEspDemo getById(@Param("id") Integer integer);
    
        
    

}
