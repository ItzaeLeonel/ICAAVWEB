/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Waldir.Valle
 */

@Entity
@Table(name = "ST_ADM_TR_CONFIG_EMAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigEmails extends DatosAuditoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONFIG_EMAILS", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
	private GrupoEmpresa grupoEmpresa;

	@Column(name = "EMAIL_FACTURACION_USUARIO", columnDefinition = "VARCHAR(100)")
	private String emailFacturacionUsuario;

	@Column(name = "EMAIL_FACTURACION_HOST", columnDefinition = "VARCHAR(100)")
	private String emailFacturacionHost;

	@Column(name = "EMAIL_FACTURACION_PUERTO", columnDefinition = "VARCHAR(100)")
	private String emailFacturacionPuerto;

	@Column(name = "EMAIL_FACTURACION_PASSWORD", columnDefinition = "VARCHAR(100)")
	private String emailFacturacionPassword;

	@Column(name = "EMAIL_COBRANZA_USUARIO", columnDefinition = "VARCHAR(100)")
	private String emailCobranzaUsuario;

	@Column(name = "EMAIL_COBRANZA_HOST", columnDefinition = "VARCHAR(100)")
	private String emailCobranzaHost;

	@Column(name = "EMAIL_COBRANZA_PUERTO", columnDefinition = "VARCHAR(100)")
	private String emailCobranzaPuerto;

	@Column(name = "EMAIL_COBRANZA_PASSWORD", columnDefinition = "VARCHAR(100)")
	private String emailCobranzaPassword;

	@Column(name = "EMAIL_CXP_USUARIO", columnDefinition = "VARCHAR(100)")
	private String emailCXPUsuario;

	@Column(name = "EMAIL_CXP_HOST", columnDefinition = "VARCHAR(100)")
	private String emailCXPHost;

	@Column(name = "EMAIL_CXP_PUERTO", columnDefinition = "VARCHAR(100)")
	private String emailCXPPuerto;

	@Column(name = "EMAIL_CXP_PASSWORD", columnDefinition = "VARCHAR(100)")
	private String emailCXPPassword;

	@Column(name = "USA_CIFRADO_DEFAULT", columnDefinition = "TINYINT(1)")
	private Integer usaCifradoDefault;

	@Column(name = "AMBIENTE_PRUEBAS", columnDefinition = "VARCHAR(100)")
	private String ambientePruebas;

	@Column(name = "USA_SSL", columnDefinition = "TINYINT(1)")
	private Integer usaSSL;

	@Column(name = "DESHABILITAR_TLS", columnDefinition = "TINYINT(1)")
	private Integer deshabilitarTLS;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = true)
	private Usuario usuario;

}
