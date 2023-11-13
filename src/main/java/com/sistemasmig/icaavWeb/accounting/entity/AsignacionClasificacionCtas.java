/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

import com.sistemasmig.icaavWeb.accounting.enums.EstatusAsignacionClasifCtasEnum;
import com.sistemasmig.icaavWeb.accounting.enums.TipoCuentaEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julio
 */

@Entity
@Table(name = "IC_CON_TR_ASIGNACION_CLASIFICACION_CTAS")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionClasificacionCtas extends DatosAuditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASIGNACION_CLASIFICACION", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    
    @ManyToOne
    @JoinColumn(name = "ID_CLASIFICACION", nullable = true)
    private ClasificacionCtas  clasificacion;
    
    @Column(name = "TIPO_CUENTA", columnDefinition = "ENUM('D', 'A')")
    private TipoCuentaEnum tipoCuentaEnum;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)                                                                                                                                                       
    @Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private EstatusAsignacionClasifCtasEnum estatusAsignacionClasifCtasEnum;
    
    
}
