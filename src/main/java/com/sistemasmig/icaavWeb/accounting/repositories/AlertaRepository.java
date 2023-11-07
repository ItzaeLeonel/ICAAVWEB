/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.Alerta;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Waldir.Valle
 */
public interface AlertaRepository extends JpaRepository<Alerta, Integer>{
    Alerta getByIdAndBorrado(Integer id, Boolean borrado);
    List<Alerta> findByUsuariosAndBorrado(String usuarios, Boolean borrado);
    List<Alerta> findByUsuariosIgnoreCaseContainingAndBorrado(String usuarios, Boolean borrado);
    Page<Alerta> findByUsuariosIgnoreCaseContainingAndBorrado(String usuarios, Boolean borrado, Pageable pageRequest);
}
