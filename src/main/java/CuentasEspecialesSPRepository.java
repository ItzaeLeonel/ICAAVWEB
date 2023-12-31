/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


import com.sistemasmig.icaavWeb.accounting.models.dto.CuentasEspecialesSP;
import com.sistemasmig.icaavWeb.accounting.models.dto.DemoLista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Julio
 */
public interface CuentasEspecialesSPRepository extends JpaRepository<CuentasEspecialesSP, Integer>{
    

      @Procedure(name = "sp_con_cuentas_especiales_c")
     List<DemoLista> getCuentasEspSP(@Param("pr_id_grupo_empresa") Integer pr_id_grupo_empresa,@Param("pr_tipo_cuenta") Integer pr_tipo_cuenta);
         
    
}
