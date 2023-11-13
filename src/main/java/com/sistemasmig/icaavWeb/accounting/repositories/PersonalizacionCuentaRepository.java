/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemasmig.icaavWeb.accounting.entity.PersonalizacionCuenta;

/**
 *
 * @author Julio
 */
public interface PersonalizacionCuentaRepository extends JpaRepository<PersonalizacionCuenta, Integer>{
    PersonalizacionCuenta getByIdAndBorrado(Integer id, Boolean borrado);
    
}
