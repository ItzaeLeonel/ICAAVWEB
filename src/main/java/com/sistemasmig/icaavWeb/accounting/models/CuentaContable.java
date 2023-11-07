/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.models;

import com.sistemasmig.icaavWeb.accounting.models.enums.CuentaContableEstatusEnum;
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
@Table(name = "IC_CON_TR_CUENTAS_CONTABLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaContable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA_CONTABLE", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    @ManyToOne
    @JoinColumn(name = "ID_CUENTA_SAT", nullable = true)
    private SatCuentaContable satCuentaContable;
    @Column(name = "ANIO", columnDefinition = "INT(4)")
    private Integer anio;
    @Column(name = "MES", columnDefinition = "INT(2)")
    private Integer mes;
    @Column(name = "NUMERO_CUENTA", columnDefinition = "VARCHAR(20)")
    private String numeroCuenta;
    @Column(name = "TIPO_CUENTA", columnDefinition = "VARCHAR(1)")
    private String tipoCuenta;
    @Column(name = "NOMBRE", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Column(name = "SALDO_INICIAL", columnDefinition = "DECIMAL(16,4)")
    private Double saldoIncial;
    @Column(name = "SALDO_FINAL", columnDefinition = "DECIMAL(16,4)")
    private Double saldoFinal;
    @Column(name = "CARGOS", columnDefinition = "DECIMAL(16,4)")
    private Double cargos;
    @Column(name = "ABONOS", columnDefinition = "DECIMAL(16,4)")
    private Double abonos;
    @Column(name = "CARGOS_ACUMULADOS", columnDefinition = "DECIMAL(16,4)")
    private Double cargosAcumulados;
    @Column(name = "ABONOS_ACUMULADOS", columnDefinition = "DECIMAL(16,4)")
    private Double abonosAcumulados;
    @Column(name = "NIVEL", columnDefinition = "INT(11)")
    private Integer nivel;
    @Column(name = "CUENTA_X_PAGAR", columnDefinition = "INT(11)")
    private Integer cuentaXPagar;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private CuentaContableEstatusEnum cuentaContableEstatusEnum;
    @Column(name = "FECHA_MODODIFICACION", columnDefinition = "TIMESTAMP")
    private Date fechaModificacion;
    @Transient
    private Date fechaModificacion2;
    @Column(name = "NUMERO_COMPLEMENTARIA", columnDefinition = "VARCHAR(50)")
    private String numeroComplementaria;
    @Column(name = "ES_CTA_COMP", columnDefinition = "INT(1)")
    private Integer esCtaComp;
    @Column(name = "NATURALEZA", columnDefinition = "VARCHAR(1)")
    private String naturaleza;
    @Column(name = "NUMERO_CUENTA_MAYOR", columnDefinition = "VARCHAR(50)")
    private String numeroCuentaMayor;
    @Column(name = "NOMBRE_CUENTA_SAT", columnDefinition = "VARCHAR(200)")
    private String nombreCuentaSat;
    @Column(name = "ID_USUARIO", columnDefinition = "INT(1)")
    private Integer usuarioId;
    @Column(name = "BORRADO")
    private Boolean borrado;
    
    
    @PreUpdate
    public void onUpdate(){
        this.fechaModificacion = new Date();
    }
}
