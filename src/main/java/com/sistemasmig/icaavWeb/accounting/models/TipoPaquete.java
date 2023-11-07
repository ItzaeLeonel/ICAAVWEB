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
@Table(name = "ST_ADM_TC_TIPO_PAQUETE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPaquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_PAQUETE", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "NOMBRE", columnDefinition = "VARCHAR(55)")
    private String nombre;
    @Column(name = "DESCRIPCION", columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "ESPACIO_ALMACENAMIENTO", columnDefinition = "DECIMAL(16,2)")
    private Double espacioAlmacenamiento;
    @Column(name = "NUMERO_FOLIOS", columnDefinition = "INT(11)")
    private Integer numeroFolios;
    @Column(name = "NUMERO_USUARIOS", columnDefinition = "INT(11)")
    private Integer numeroUsuarios;
    @Column(name = "ID_SUBSISTEMA", columnDefinition = "INT(11)")
    private Integer idSubsistema;
    @Column(name = "ESPACIO_GB", columnDefinition = "INT(11)")
    private Integer espacioGB;
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

