/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "IC_CON_TR_CLASIFICACION_CTAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClasificacionCtas extends DatosAuditoria{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLASIFICACION", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @Column(name = "INICIA_CON", columnDefinition = "INT(11)")
    private Integer iniciaCon;
    
    @Column(name = "CLASIFICACION", columnDefinition = "VARCHAR(255)")
    private String clasificacion;
    
}
