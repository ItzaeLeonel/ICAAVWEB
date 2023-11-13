package com.sistemasmig.icaavWeb.accounting.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class DatosAuditoria {
	
	@JsonIgnore
	@Column(name = "ID_USUARIO_ALT", columnDefinition = "INT(11)")
    private Integer idUsuarioAlt;
	
	@JsonIgnore
	@CreationTimestamp
    @Column(name = "FECHA_ALTA", columnDefinition = "TIMESTAMP")
    private Date fechaAlta;
	
	@JsonIgnore
	@Column(name = "ID_USUARIO_MOD", columnDefinition = "INT(11)")
    private Integer idUsuarioMod;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(name = "FECHA_MOD", columnDefinition = "TIMESTAMP")
    private Date fechaModificacion;
	
	@JsonIgnore
	@Column(name = "BORRADO")
    private Boolean borrado;

}
