/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.ZonaHoraria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Waldir.Valle
 */
public interface ZonaHorariaRepository extends JpaRepository<ZonaHoraria, Integer>{
    ZonaHoraria getByIdAndBorrado(Integer id, Boolean borrado);
   
}
