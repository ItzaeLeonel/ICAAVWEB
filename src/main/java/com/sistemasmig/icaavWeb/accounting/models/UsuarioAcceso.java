/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.models;

import com.sistemasmig.icaavWeb.accounting.models.enums.TipoUsuarioAccesoEnum;
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
@Table(name = "ST_ADM_TR_USUARIO_ACCESO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO_ACCESO", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA", nullable = true)
    private Empresa empresa;
    @Column(name = "ACCESO_POR", columnDefinition = "VARCHAR(100)")
    private String accesoPor;
    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGO_FOLIOS", columnDefinition = "ENUM('IP', 'MAC')")
    private TipoUsuarioAccesoEnum tipoUsuarioAccesoEnum;
    @Column(name = "DESCRIPCION", columnDefinition = "VARCHAR(250)")
    private String descripcion;
    @Column(name = "ID_USUARIO_MOD", columnDefinition = "INT(11)")
    private Integer idUsuarioMod;
    @Column(name = "FECHA_MOD", columnDefinition = "TIMESTAMP")
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

