/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Waldir.Valle
 */
@Entity
@Table(name = "SAT_CUENTAS_CONTABLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SatCuentaContable {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA_SAT", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "GRUPO", columnDefinition = "VARCHAR(10)")
    private String grupo;
    @Column(name = "SUBGRUPO", columnDefinition = "VARCHAR(30)")
    private String subGrupo;
    @Column(name = "NIVEL", columnDefinition = "INT(4)")
    private Integer nivel;
    @Column(name = "CUENTA_MAYOR", columnDefinition = "VARCHAR(10)")
    private String cuentaMayor;
    @Column(name = "CUENTA_SAT", columnDefinition = "VARCHAR(10)")
    private String cuentaSat;
    @Column(name = "NOMBRE_CUENTA", columnDefinition = "VARCHAR(200)")
    private String nombreCuenta;
    @Column(name = "NATURALEZA", columnDefinition = "VARCHAR(1)")
    private String naturaleza;
    @Column(name = "BORRADO")
    private Boolean borrado;
}
