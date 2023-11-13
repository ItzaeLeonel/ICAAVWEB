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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

import com.sistemasmig.icaavWeb.accounting.enums.EstatusConfigMonedaEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TR_CONFIG_MONEDA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMoneda extends DatosAuditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MONEDA_EMPRESA", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @Column(name = "ID_MONEDA", columnDefinition = "INT(11)")
    private Integer idMoneda;
    
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    
    @Column(name = "TIPO_CAMBIO", columnDefinition = "DECIMAL(16,4)")
    private Double tipoCambio;
    
    @Column(name = "MONEDA_NACIONAL", columnDefinition = "CHAR(1)")
    private String monedaNacional;
    
    @Column(name = "TIPO_CAMBIO_AUTO", columnDefinition = "CHAR(1)")
    private String tipoCambioAuto;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private EstatusConfigMonedaEnum estatusConfigMonedaEnum;
    
    @Column(name = "ID_USUARIO", columnDefinition = "INT(11)")
    private Integer idUsuario;
   
}

