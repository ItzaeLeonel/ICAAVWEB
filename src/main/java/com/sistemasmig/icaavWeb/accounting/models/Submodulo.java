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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TC_SUBMODULO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submodulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUBMODULO", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_MODULO", nullable = true)
    private Modulo modulo;
    @Column(name = "CLAVE_SUBMODULO", columnDefinition = "CHAR(20)")
    private String claveSubmodulo;
    @Column(name = "NOMBRE_SUBMODULO", columnDefinition = "VARCHAR(60)")
    private String nombreSubmodulo;
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_PAQUETE", nullable = true)
    private TipoPaquete tipoPaquete;
    @Column(name = "ROLE_AMEX", columnDefinition = "INT(11)")
    private Integer roleAmex;
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

