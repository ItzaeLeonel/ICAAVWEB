/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.managers;

import com.sistemasmig.icaavWeb.accounting.models.dto.ListDatosEmpresa;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.DatosEmpresaRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julio
 */
@Component
public class DatosEmpresaSPManager {
    @Autowired
    private DatosEmpresaRepository datosEmpresaRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    public List <ListDatosEmpresa> getByDatosEmpresa(Integer pr_id_grupo_empresa) throws EntityNotExistentException {
    List<ListDatosEmpresa>resultado=datosEmpresaRepository.proceduredatos(pr_id_grupo_empresa);
    return resultado;
    } 
    
    
}
