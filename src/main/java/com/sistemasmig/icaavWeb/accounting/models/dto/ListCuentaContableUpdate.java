/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.models.dto;

/**
 *
 * @author Julio
 */
public interface ListCuentaContableUpdate {
    
    Integer getIdCuentaContable();
    Integer getIdGrupoEmpresa();
    Integer getIdCuentaSat();
    Integer getAnio();
    Integer getMes();
    String getNumeroCuenta();
    String getTipoCuenta();
    String getNombre();
    Double getSaldoInicial();
    Double getSaldoFinal();
    Double getCargos();
    Double getAbonos();
    Double getCargosAcumulados();
    Double getAbonosAcumulados();
    Integer getCuentaXPagar();
    Enum getEstatus();
    String getNumeroComplementaria();

}
