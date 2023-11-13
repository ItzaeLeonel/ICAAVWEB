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
import java.util.Date;

import com.sistemasmig.icaavWeb.accounting.enums.EstatusConfigBancoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TR_CONFIG_BANCO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigBanco extends DatosAuditoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONFIG_BANCO", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
	private GrupoEmpresa grupoEmpresa;
	
	@Column(name = "ID_FORMA_PAGO", columnDefinition = "INT(11)")
	private Integer idFormaPago;
	
	@Column(name = "ID_SAT_BANCOS", columnDefinition = "INT(11)")
	private Integer idSatBancos;
	
	@Column(name = "RAZON_SOCIAL", columnDefinition = "VARCHAR(100)")
	private String razonSocial;
	
	@Column(name = "RFC", columnDefinition = "VARCHAR(20)")
	private String rfc;
	
	@Column(name = "CUENTA", columnDefinition = "VARCHAR(20)")
	private String cuenta;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTATUS", columnDefinition = "ENUM('ACTIVO', 'INACTIVO')")
	private EstatusConfigBancoEnum estatusConfigBancoEnum;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = true)
	private Usuario usuario;

}
