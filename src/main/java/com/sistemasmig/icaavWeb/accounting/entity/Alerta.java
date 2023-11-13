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
import jakarta.persistence.Table;
import com.sistemasmig.icaavWeb.accounting.enums.EstatusAlertasEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TR_ALERTAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Alerta extends DatosAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALERTAS", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
	private GrupoEmpresa grupoEmpresa;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = true)
	private Usuario usuario;

	@Column(name = "USUARIOS", columnDefinition = "TEXT")
	private String usuarios;

	@Column(name = "NOTIFICACION", columnDefinition = "VARCHAR(255)")
	private String notificacion;

	@Column(name = "HIPERVINCULO", columnDefinition = "INT(1)")
	private Integer hipervinculo;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
	private EstatusAlertasEnum estatusAlertasEnum;
}
