
package com.sistemasmig.icaavWeb.accounting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


import com.sistemasmig.icaavWeb.accounting.enums.TipoSatEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julio
 */

@Entity
@Table(name = "IC_CON_TR_ASIGNACION_TIPO_POLIZAS")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionTipoPolizas extends DatosAuditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASIGNACION_POLIZA", unique = true, columnDefinition = "INT(11)", updatable = false, nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ID_GRUPO_EMPRESA", nullable = true)
    private GrupoEmpresa grupoEmpresa;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_POL", nullable = true)
    private TipoPoliza tipoPoliza;
    
    @Column(name = "TIPO_SAT", columnDefinition = "ENUM('I', 'E', 'D', 'N')")
    private TipoSatEnum tipoSatEnum;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Usuario usuario;
    
    
    
}
