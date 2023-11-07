/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "suite_mig_demo.IC_CON_TR_ASIGNACION_CTAS_ESPECIALES")
@Data

public class AsignacionCuentasEspDemo {
    @Id
    @Column(name="id_asignacion")
    Integer idasignacion;
}