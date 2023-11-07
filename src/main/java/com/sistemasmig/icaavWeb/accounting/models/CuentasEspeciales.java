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
@Table(name = "IC_CON_TR_CUENTAS_ESPECIALES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentasEspeciales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CTA_ESPECIAL", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "DESCRIPCION", columnDefinition = "VARCHAR(500)")
    private String descripcion;
     @Column(name = "FUNCIONALIDAD", columnDefinition = "VARCHAR(500)")
    private String funcionalidad;
     @Column(name = "POR_IVA", columnDefinition = "VARCHAR(500)")
    private String iva;
    @Column(name = "TIPO_CUENTA", columnDefinition = "INT(1)")
    private Integer tipoCuenta;
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
