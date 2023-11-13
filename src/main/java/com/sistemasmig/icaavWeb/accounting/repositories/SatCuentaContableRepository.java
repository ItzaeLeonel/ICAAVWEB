/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemasmig.icaavWeb.accounting.entity.SatCuentaContable;

/**
 *
 * @author Waldir.Valle
 */
public interface SatCuentaContableRepository extends JpaRepository<SatCuentaContable, Integer>{
    SatCuentaContable getByIdAndBorrado(Integer id, Boolean borrado);
    SatCuentaContable getByCuentaSatAndBorrado(String cuentaSat, Boolean borrado);
    List<SatCuentaContable> findByCuentaSatAndBorrado(String cuentaSat, Boolean borrado);
    List<SatCuentaContable> findByNombreCuentaAndBorrado(String nombreCuenta, Boolean borrado);
    List<SatCuentaContable> findByCuentaSatIgnoreCaseContainingAndBorrado(String cuentaSat, Boolean borrado);
    Page<SatCuentaContable> findByCuentaSatIgnoreCaseContainingAndBorrado(String cuentaSat, Boolean borrado, Pageable pageRequest);
}
