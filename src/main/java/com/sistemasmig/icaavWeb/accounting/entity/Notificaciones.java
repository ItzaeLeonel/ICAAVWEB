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
@Table(name = "ST_ADM_TC_NOTIFICACIONES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICACIONES", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "CLAVE", columnDefinition = "VARCHAR(45)")
    private String clave;
    @Column(name = "ID_TIPO_NOTIFICACION", columnDefinition = "INT(11)")
    private Integer idTipoNotificacion;
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

