/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.Empresa;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Waldir.Valle
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
    Empresa getByIdAndBorrado(Integer id, Boolean borrado);
    Empresa getByNombreAndBorrado(String nombre, Boolean borrado);
    List<Empresa> findByNombreAndBorrado(String nombre, Boolean borrado);
    List<Empresa> findByRfcSucursalAndBorrado(String rfcSucursal, Boolean borrado);
    List<Empresa> findByClaveAndBorrado(String clave, Boolean borrado);
    List<Empresa> findByNombreIgnoreCaseContainingAndBorrado(String nombre, Boolean borrado);
    Page<Empresa> findByNombreIgnoreCaseContainingAndBorrado(String nombre, Boolean borrado, Pageable pageRequest);
}
