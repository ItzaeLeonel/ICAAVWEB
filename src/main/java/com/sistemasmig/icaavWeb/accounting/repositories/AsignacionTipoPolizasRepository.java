/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.Alerta;
import com.sistemasmig.icaavWeb.accounting.models.AsignacionTipoPolizas;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Julio
 */
public interface AsignacionTipoPolizasRepository  extends JpaRepository<AsignacionTipoPolizas, Integer> {
     AsignacionTipoPolizas getByIdAndBorrado(Integer id, Boolean borrado);
    
}
