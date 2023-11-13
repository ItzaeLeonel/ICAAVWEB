/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sistemasmig.icaavWeb.accounting.entity.TipoPoliza;

/**
 *
 * @author Julio
 */
public interface TipoPolizaRepository extends JpaRepository<TipoPoliza, Integer>{
     TipoPoliza getByIdAndBorrado(Integer id, Boolean borrado);
    
}
