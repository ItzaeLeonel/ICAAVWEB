/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.AsignacionCtasEspeciales;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Julio
 */
public interface AsignacionCtasEspecialesRepository extends JpaRepository<AsignacionCtasEspeciales, Integer> {
    AsignacionCtasEspeciales getByIdAndBorrado(Integer id, Boolean borrado);

}
