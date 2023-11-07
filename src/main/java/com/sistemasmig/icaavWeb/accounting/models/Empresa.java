/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.models;

import com.sistemasmig.icaavWeb.accounting.models.enums.TipoPagoEnum;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TR_EMPRESA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_DIRECCION", nullable = true)
    private Direccion direccion;
    @Column(name = "ID_BASE_DATOS", columnDefinition = "INT(11)")
    private Integer baseDatosId;
    @ManyToOne
    @JoinColumn(name = "ID_IDIOMA", nullable = true)
    private Idioma idioma;
    @Column(name = "CVE_PAIS", columnDefinition = "CHAR(2)")
    private String clavePais;
    @Column(name = "NOM_EMPRESA", columnDefinition = "VARCHAR(100)")
    private String nombre;
    @Column(name = "RFC_SUCURSAL", columnDefinition = "VARCHAR(45)")
    private String rfcSucursal;
    @Column(name = "CVE_EMPRESA", columnDefinition = "VARCHAR(45)")
    private String clave;
    @Column(name = "COMERCIAL_EMPRESA", columnDefinition = "VARCHAR(100)")
    private String nombreComercial;
    @Column(name = "RAZON_SOCIAL", columnDefinition = "VARCHAR(250)")
    private String razonSocial;
    @Column(name = "EMAIL_EMPRESA", columnDefinition = "VARCHAR(60)")
    private String email;
    @Column(name = "TELEFONO_EMPRESA", columnDefinition = "VARCHAR(35)")
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "ID_ZONA_HORARIA", nullable = true)
    private ZonaHoraria zonaHoraria;
    @Column(name = "USUARIOS", columnDefinition = "INT(11)")
    private Integer usuarios;
    @Column(name = "SUCURSALES", columnDefinition = "INT(11)")
    private Integer sucursales;
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_PAQUETE", nullable = true)
    private TipoPaquete tipoPaquete;
    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGO_FOLIOS", columnDefinition = "ENUM('CREDITO', 'PREPAGO')")
    private TipoPagoEnum formaPagoFolios;
    @Column(name = "VERSION_CFDI", columnDefinition = "DECIMAL(15,1)")
    private Double versionCFDI;
    @Column(name = "ESTATUS_EMPRESA", columnDefinition = "TINYINT(1)")
    private Integer estatus;
    @Column(name = "FECHA_MOD_EMPRESA", columnDefinition = "TIMESTAMP")
    private Date fechaModificacion;
    @Transient
    private Date fechaModificacion2;
    @Column(name = "EMPRESA_AMEX", columnDefinition = "INT(11)")
    private Integer amex;
    @Column(name = "CODIGO_AGENCIA", columnDefinition = "VARCHAR(45)")
    private String codigoAgencia;
    @Column(name = "CODIGO_OFICINA", columnDefinition = "VARCHAR(45)")
    private String codigoOficina;
    @Column(name = "BORRADO")
    private Boolean borrado;
    
    @PreUpdate
    public void onUpdate(){
        this.fechaModificacion = new Date();
    }
    
}

