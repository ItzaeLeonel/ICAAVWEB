/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

import com.sistemasmig.icaavWeb.accounting.enums.TipoPolizaEstatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Julio
 */

@Entity
@Table(name = "IC_CAT_TIPO_POLIZAS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPoliza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_POL", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "CLAVE", columnDefinition = "VARCHAR(5)")
    private String clave;
    @Column(name = "DESC_TIPO_POL", columnDefinition = "VARCHAR(25)")
    private String desc_tipo_pol;
   
    @Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private TipoPolizaEstatusEnum tipoPolizaEstatusEnum;
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
