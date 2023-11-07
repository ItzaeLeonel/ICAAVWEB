/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.AlertasUsuarios;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Waldir.Valle
 */
public interface AlertasUsuariosRepository extends JpaRepository<AlertasUsuarios, Integer>{
    AlertasUsuarios getByIdAndBorrado(Integer id, Boolean borrado);
    List<AlertasUsuarios> findByAlerta_UsuariosAndBorrado(String usuarios, Boolean borrado);
    List<AlertasUsuarios> findByAlerta_UsuariosIgnoreCaseContainingAndBorrado(String usuarios, Boolean borrado);
    Page<AlertasUsuarios> findByAlerta_UsuariosIgnoreCaseContainingAndBorrado(String usuarios, Boolean borrado, Pageable pageRequest);
}
