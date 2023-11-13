/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemasmig.icaavWeb.accounting.entity.Notificaciones;

/**
 *
 * @author Waldir.Valle
 */
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Integer>{
    Notificaciones getByIdAndBorrado(Integer id, Boolean borrado);
   
}
