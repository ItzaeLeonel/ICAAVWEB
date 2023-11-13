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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

import com.sistemasmig.icaavWeb.accounting.enums.EstatusSucursalesEnum;
import com.sistemasmig.icaavWeb.accounting.enums.SucursalesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "IC_CAT_TR_SUCURSAL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucursales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa idGrupoEmpresa;
    @ManyToOne
    @JoinColumn(name = "ID_DIRECCION",nullable = true)
    private Direccion idDireccion;
    @ManyToOne
    @JoinColumn(name = "ID_ZONA_HORARIA", nullable = true)
    private Direccion idZonaHoraria;
    @Column(name = "TIPO", columnDefinition = "ENUM('CORPORATIVO','SUCURSAL','INPLANT')")
    private SucursalesEnum estatusSucursal;
    @Column(name = "CLAVE_SUCURSAL", columnDefinition = "VARCHAR(30)")
    private String claveSucursal;
    @Column(name = "NOMBRE", columnDefinition = "VARCHAR(90)")
    private String nombre;
    @Column(name = "EMAIL", columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(name = "TELEFONO", columnDefinition = "VARCHAR(25)")
    private String telefono;
    @Column(name = "IVA_LOCAL", columnDefinition = "DOUBLE")
    private Double ivaLocal;
    @Column(name = "IATA_NACIONAL", columnDefinition = "VARCHAR(20)")
    private String iataNacional;
    @Column(name = "IATA_INTERNACIONAL", columnDefinition = "VARCHAR(20)")
    private String iataInternacional;
    @Column(name = "MATRIZ", columnDefinition = "CHAR(1)")
    private String matriz;
    @Column(name = "PERTENECE", columnDefinition = "INT(11)")
    private Integer pertenece;
    @Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO','INACTIVO')")
    private EstatusSucursalesEnum estatus;
    @Column(name = "FECHA_MOD", columnDefinition = "TIMESTAMP")
    private Date fechaModificacion;
    @Transient
    private Date fechaModificacion2;
    @Column(name = "BORRADO")
    private Boolean borrado;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario idUsuario;
    
    @PreUpdate
    public void onUpdate(){
        this.fechaModificacion = new Date();
    }
    
    
    
}
