/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author Julio
 */

@Entity
@Table(name = "IC_CON_TR_ASIGNACION_CUENTAS_ESPECIALES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionCtasEspeciales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    @Column(name = "ID_ASIGNACION", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    @ManyToOne
    @JoinColumn(name = "ID_CTA_CONTABLE", nullable = true)
    private CuentaContable cuentaContable;
    @ManyToOne
    @JoinColumn(name = "ID_CTA_ESPECIAL", nullable = true)
    private CuentasEspeciales ctaEspecial;
    @ManyToOne
    @JoinColumn(name = "ID_SUCURSAL", nullable = true)
    private Sucursales sucursal;
     
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


