/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemasmig.icaavWeb.accounting.entity.UsuarioRecuperacion;

/**
 *
 * @author Waldir.Valle
 */
public interface UsuarioRecuperacionRepository extends JpaRepository<UsuarioRecuperacion, Integer>{
    UsuarioRecuperacion getByIdAndBorrado(Integer id, Boolean borrado);
   
}
