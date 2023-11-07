/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Julio
 */

@Entity
@Table(name = "IC_ADM_MODELO_CTAS_CONTABLES_PREGARGADAS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaContablePrecargada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODELO_CONTABLE", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "NOMBRE", columnDefinition = "VARCHAR(200)")
    private String nombre;
    @Column(name = "DESCRIPCION", columnDefinition = "VARCHAR(200)")
    private String descripcion;
    @Column(name = "NIVEL1", columnDefinition = "INT(11)")
    private Integer nivel1;
    @Column(name = "NIVEL2", columnDefinition = "INT(11)")
    private Integer nivel2;
    @Column(name = "NIVEL3", columnDefinition = "INT(11)")
    private Integer nivel3;
    @Column(name = "NIVEL4", columnDefinition = "INT(11)")
    private Integer nivel4;
    @Column(name = "NIVEL5", columnDefinition = "INT(11)")
    private Integer nivel5;
    @Column(name = "NIVEL6", columnDefinition = "INT(11)")
    private Integer nivel6;
    @Column(name = "NIVEL7", columnDefinition = "INT(11)")
    private Integer nivel7;
    @Column(name = "NIVEL8", columnDefinition = "INT(11)")
    private Integer nivel8;
    @Column(name = "NIVEL9", columnDefinition = "INT(11)")
    private Integer nivel9;
    @Column(name = "MASCARA", columnDefinition = "VARCHAR(2)")
    private String mascara;
    @Column(name = "NO_NIVELES", columnDefinition = "INT(11)")
    private Integer no_niveles;
    @Column(name = "NO_DIGITOS", columnDefinition = "INT(11)")
    private Integer no_digitos;
    @Column(name = "ESTATUS", columnDefinition = "VARCHAR(2)")
    private String estatus;
    @Column(name = "FECHA_MOD", columnDefinition = "TIMESTAMP")
    private Date fechaModificacion;
    @Transient
    private Date fechaModificacion2;
    @Column(name = "BORRADO")
    private Boolean borrado;
    
    @PreUpdate
    public void onUpdate(){
        this.fechaModificacion = new Date();
    }
    
}
