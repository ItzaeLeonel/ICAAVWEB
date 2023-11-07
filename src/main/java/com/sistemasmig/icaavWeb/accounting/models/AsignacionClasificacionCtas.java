/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.models;

import com.sistemasmig.icaavWeb.accounting.models.enums.EstatusAsignacionClasifCtasEnum;
import com.sistemasmig.icaavWeb.accounting.models.enums.TipoCuentaEnum;
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
 * @author Julio
 */

@Entity
@Table(name = "IC_CON_TR_ASIGNACION_CLASIFICACION_CTAS")


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionClasificacionCtas {
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
    @Column(name = "TIPO_CUENTA", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private TipoCuentaEnum tipoCuentaEnum;
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    @Enumerated(EnumType.STRING)                                                                                                                                                       
    @Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
    private EstatusAsignacionClasifCtasEnum estatusAsignacionClasifCtasEnum;
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
