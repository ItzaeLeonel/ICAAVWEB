/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.models;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Waldir.Valle
 */
@Entity
@Table(name = "ST_ADM_TR_GRUPO_EMPRESA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO_EMPRESA", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "ID_GRUPO", columnDefinition = "INT(11)")
    private Integer grupoId;
    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA", nullable = true)
    private Empresa empresa;
    @Column(name = "ESTATUS_USUARIO_EMPRESA", columnDefinition = "TINYINT(1)")
    private Integer estatusUsuarioEmpresa;
    @Column(name = "FECHA_MOD_USUARIO_EMPRESA", columnDefinition = "TIMESTAMP")
    private Date fechaModificacionUsuarioEmpresa;
    @Transient
    private Date fechaModificacionUsuarioEmpresa2;
    @Column(name = "GRUPO_EMPRESA_AMEX", columnDefinition = "INT(11)")
    private Integer amex;
    @Column(name = "BORRADO")
    private Boolean borrado;
    
    @PreUpdate
    public void onUpdate(){
        this.fechaModificacionUsuarioEmpresa = new Date();
    }
}
