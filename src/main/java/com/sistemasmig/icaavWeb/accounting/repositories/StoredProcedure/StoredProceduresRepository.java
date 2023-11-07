/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure;

import com.sistemasmig.icaavWeb.accounting.models.dto.StoreProcedures;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julio
 */
@Repository
public interface StoredProceduresRepository extends JpaRepository<StoreProcedures, Integer>{
     
      @Procedure(name = "sp_con_asignacion_ctas_especiales_i")
     Map<String, ?> listaprocedure(@Param("pr_id_cta_especial") Integer id_cta_especial,
                        @Param("pr_id_empresa") Integer id_empresa,
                        @Param("pr_id_cuenta_contable") Integer id_cuenta_contable);
}
