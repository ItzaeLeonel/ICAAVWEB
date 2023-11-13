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

import com.sistemasmig.icaavWeb.accounting.enums.EstatusAlertasEnum;

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
@Table(name = "ST_ADM_TR_ALERTAS_USUARIOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertasUsuarios extends DatosAuditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA_USUARIOS", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ID_ALERTA", nullable = true)
    private Alerta alerta;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    
    @Column(name = "LEIDO", columnDefinition = "CHAR(1)")
    private Integer leido;
}

