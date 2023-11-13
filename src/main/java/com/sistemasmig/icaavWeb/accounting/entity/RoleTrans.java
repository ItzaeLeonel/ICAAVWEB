/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.entity;

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
@Table(name = "ST_ADM_TC_ROLE_TRANS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleTrans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROLE", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "NOMBRE_ROLE", columnDefinition = "VARCHAR(50)")
    private String nombreRole;
    @Column(name = "DESCRIPCION", columnDefinition = "VARCHAR(250)")
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "ID_IDIOMA", nullable = true)
    private Idioma idioma;
    @Column(name = "ROLE_TRANS_AMEX", columnDefinition = "INT(11)")
    private Integer roleTransAmex;
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

