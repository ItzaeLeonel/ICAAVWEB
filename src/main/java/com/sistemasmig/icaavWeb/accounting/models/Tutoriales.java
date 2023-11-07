/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.models;

import com.sistemasmig.icaavWeb.accounting.models.enums.EstatusTutorialesEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TC_TUTORIALES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tutoriales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TUTORIAL", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "NOMBRE_VIDEO", columnDefinition = "VARCHAR(255)")
    private String nombreVideo;
    @Column(name = "ENLACE_VIDEO", columnDefinition = "TEXT")
    private String enlaceVideo;
    @Column(name = "MODULO", columnDefinition = "VARCHAR(60)")
    private String modulo;
    @Column(name = "FECHA_PUBLICACION", columnDefinition = "TIMESTAMP")
    private Date fechaPublicacion;
    @Transient
    private Date fechaPublicacion2;
    @Column(name = "DESCRIPCION", columnDefinition = "LONGTEXT")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGO_FOLIOS", columnDefinition = "ENUM('CREDITO', 'PREPAGO')")
    private EstatusTutorialesEnum estatusTutorialesEnum;
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

