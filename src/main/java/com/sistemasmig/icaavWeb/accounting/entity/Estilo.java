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
@Table(name = "ST_ADM_TC_ESTILO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estilo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTILO", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA", nullable = true)
    private Empresa empresa;
    @Column(name = "DESCRIPCION", columnDefinition = "VARCHAR(100)")
    private String descripcion;
    @Column(name = "URL", columnDefinition = "VARCHAR(255)")
    private String url;
    @Column(name = "COLOR", columnDefinition = "VARCHAR(20)")
    private String color;
    @Column(name = "IMAGEN", columnDefinition = "VARCHAR(255)")
    private String imagen;
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

