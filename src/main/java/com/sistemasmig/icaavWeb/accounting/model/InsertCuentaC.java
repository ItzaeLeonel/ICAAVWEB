/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author Julio
 */


@Entity
@Table(name = "suite_mig_demo.ic_con_tr_cuentas_contables")
@Data
public class InsertCuentaC {
    @Id
    @Column(name="id_cuenta_contable")
    Integer idcuentacontable;
    
}
