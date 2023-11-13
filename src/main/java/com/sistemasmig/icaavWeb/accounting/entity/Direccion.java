/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

import com.sistemasmig.icaavWeb.accounting.enums.EstatusDireccionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TC_TIPO_DIRECCION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "CVE_PAIS", columnDefinition = "VARCHAR(3)")
    private String cvePais;
    @Column(name = "CALLE_DIRECCION", columnDefinition = "VARCHAR(255)")
    private String calleDireccion;
    @Column(name = "NUM_EXTERIOR_DIRECCION", columnDefinition = "VARCHAR(45)")
    private String numExteriorDireccion;
    @Column(name = "NUM_INTERIOR_DIRECCION", columnDefinition = "VARCHAR(45)")
    private String numInteriorDireccion;
    @Column(name = "COLONIA_DIRECCION", columnDefinition = "VARCHAR(100)")
    private String coloniaDireccion;
    @Column(name = "MUNICIPIO_DIRECCION", columnDefinition = "VARCHAR(100)")
    private String municipioDireccion;
    @Column(name = "CIUDAD_DIRECCION", columnDefinition = "VARCHAR(100)")
    private String ciudadDireccion;
    @Column(name = "ESTADO_DIRECCION", columnDefinition = "VARCHAR(100)")
    private String estadoDireccion;
    @Column(name = "CODIGO_POSTAL_DIRECCION", columnDefinition = "VARCHAR(10)")
    private String codigoPostalDireccion;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS_DIRECCION", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private EstatusDireccionEnum estatusDireccionEnum;
    
    @Column(name = "FECHA_MOD_DIRECCION", columnDefinition = "TIMESTAMP")
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

