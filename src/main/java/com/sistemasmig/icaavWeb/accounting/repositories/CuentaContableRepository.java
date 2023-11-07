/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import com.sistemasmig.icaavWeb.accounting.models.CuentaContable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Waldir.Valle
 */

public interface CuentaContableRepository extends JpaRepository<CuentaContable, Integer>{
    CuentaContable getByIdAndBorrado(Integer id, Boolean borrado);
    CuentaContable getByNumeroCuentaAndBorrado(String numeroCuenta, Boolean borrado);
    List<CuentaContable> findByNumeroCuentaAndBorrado(String numeroCuenta, Boolean borrado);
    List<CuentaContable> findByNombreAndBorrado(String nombre, Boolean borrado);
    List<CuentaContable> findByNumeroCuentaIgnoreCaseContainingAndBorrado(String numeroCuenta, Boolean borrado);
    Page<CuentaContable> findByNumeroCuentaIgnoreCaseContainingAndBorrado(String numeroCuenta, Boolean borrado, Pageable pageRequest);
}
