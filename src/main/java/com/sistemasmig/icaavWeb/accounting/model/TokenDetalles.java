package com.sistemasmig.icaavWeb.accounting.model;

import java.sql.Time;
import java.util.Date;

import com.sistemasmig.icaavWeb.accounting.entity.EstiloEmpresa;
import com.sistemasmig.icaavWeb.accounting.entity.GrupoEmpresa;
import com.sistemasmig.icaavWeb.accounting.entity.Idioma;
import com.sistemasmig.icaavWeb.accounting.entity.Role;
import com.sistemasmig.icaavWeb.accounting.enums.EstatusUsuarioEnum;

import lombok.Data;

@Data
public class TokenDetalles {
	
    private Integer id;
    
    private GrupoEmpresa grupoEmpresa;
    
    private Role role;
    
    private EstiloEmpresa estiloEmpresa;
    
    private Idioma idioma;
    
    private String usuario;
    
    private String passwordUsuario;
    
    private String nombreUsuario;
    
    private String paternoUsuario;
    
    private String maternoUsuario;
    
    private String correo;
    
    private Integer inicioSesion;
    
    private Integer primerIngreso;
    
    private Integer intentosIngreso;
    
    private Date fechaDesbloqueo;
    
    private Time horaAccesoIni;
    
    private Time horaAccesoFin;
    
    private Integer accesoIP;
    
    private Integer accesoHorario;
   
    private Date fechaUltConexion;
    
    private EstatusUsuarioEnum estatusUsuarioEnum;
    
    private String registraUsuario;
    
    private Integer tipoUsuario;
    
    private Integer usuarioAmex;
    
    
}
