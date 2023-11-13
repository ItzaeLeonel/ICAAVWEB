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

import java.sql.Time;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.sistemasmig.icaavWeb.accounting.enums.EstatusUsuarioEnum;
import com.sistemasmig.icaavWeb.accounting.model.TokenDetalles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TR_USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    @ManyToOne
    @JoinColumn(name = "ID_ROLE", nullable = true)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "ID_ESTILO_EMPRESA", nullable = true)
    private EstiloEmpresa estiloEmpresa;
    @ManyToOne
    @JoinColumn(name = "ID_IDIOMA", nullable = true)
    private Idioma idioma;
    @Column(name = "USUARIO", columnDefinition = "VARCHAR(100)")
    private String usuario;
    @Column(name = "PASSWORD_USUARIO", columnDefinition = "VARCHAR(256)")
    private String passwordUsuario;
    @Column(name = "NOMBRE_USUARIO", columnDefinition = "VARCHAR(45)")
    private String nombreUsuario;
    @Column(name = "PATERNO_USUARIO", columnDefinition = "VARCHAR(45)")
    private String paternoUsuario;
    @Column(name = "MATERNO_USUARIO", columnDefinition = "VARCHAR(45)")
    private String maternoUsuario;
    @Column(name = "CORREO", columnDefinition = "CHAR(50)")
    private String correo;
    @Column(name = "INICIO_SESION", columnDefinition = "TINYINT(1)")
    private Integer inicioSesion;
    @Column(name = "PRIMER_INGRESO", columnDefinition = "INT(11)")
    private Integer primerIngreso;
    @Column(name = "INTENTOS_INGRESO", columnDefinition = "TINYINT(1)")
    private Integer intentosIngreso;
    @Column(name = "FECHA_DESBLOQUEO", columnDefinition = "DATETIME")
    private Date fechaDesbloqueo;
    @Column(name = "HORA_ACCESO_INI", columnDefinition = "TIME")
    private Time horaAccesoIni;
    @Column(name = "HORA_ACCESO_FIN", columnDefinition = "TIME")
    private Time horaAccesoFin;
    @Column(name = "ACCESO_IP", columnDefinition = "TINYINT(1)")
    private Integer accesoIP;
    @Column(name = "ACCESO_HORARIO", columnDefinition = "TINYINT(1)")
    private Integer accesoHorario;
    @Column(name = "FECHA_ULT_CONEXION", columnDefinition = "DATETIME")
    private Date fechaUltConexion;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS_USUARIO", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private EstatusUsuarioEnum estatusUsuarioEnum;
    @Column(name = "REGISTRA_USUARIO", columnDefinition = "VARCHAR(100)")
    private String registraUsuario;
    @Column(name = "TIPO_USUARIO", columnDefinition = "TINYINT(4)")
    private Integer tipoUsuario;
    @Column(name = "FECHA_REGISTRO_USUARIO", columnDefinition = "TIMESTAMP")
    private Date fechaRegistroUsuario;
    @Column(name = "VISUALIZAR_DASHBOARD", columnDefinition = "TINYINT(1)")
    private Integer visualizarDashboard;
    @Column(name = "ID_USUARIO_MOD", columnDefinition = "INT(11)")
    private Integer idUsuarioMod;
    @Column(name = "USUARIO_AMEX", columnDefinition = "INT(11)")
    private Integer usuarioAmex;
    @Column(name = "FECHA_MODIFICACION", columnDefinition = "TIMESTAMP")
    private Date fechaModificacion;
    @Transient
    private Date fechaModificacion2;
    
    @Column(name = "BORRADO")
    private Boolean borrado;
    
    @PreUpdate
    public void onUpdate(){
        this.fechaModificacion = new Date();
    }
    
    public TokenDetalles toTokenDetalles() {
        TokenDetalles tokenDetalles = new TokenDetalles();
        BeanUtils.copyProperties(this, tokenDetalles);
        return tokenDetalles;
    }
    
}

