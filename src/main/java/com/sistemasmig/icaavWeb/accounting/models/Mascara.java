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
@Table(name = "ST_ADM_TR_MASCARA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mascara {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MASCARA", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA", nullable = true)
    private Empresa empresa;
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
    @Column(name = "VALOR_DEFAULT", columnDefinition = "VARCHAR(2)")
    private String valorDefault;
    @Column(name = "FECHA_MODIFICACION", columnDefinition = "TIMESTAMP")
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

