/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemasmig.icaavWeb.accounting.entity.Sucursales;

/**
 *
 * @author Julio
 */
public interface SucursalRepository extends JpaRepository<Sucursales, Integer> {
    Sucursales getByIdAndBorrado(Integer id, Boolean borrado);
    
}
