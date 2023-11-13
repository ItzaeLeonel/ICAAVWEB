/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasmig.icaavWeb.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistemasmig.icaavWeb.accounting.entity.Idioma;

/**
 *
 * @author Waldir.Valle
 */
public interface IdiomaRepository extends JpaRepository<Idioma, Integer>{
    Idioma getByIdAndBorrado(Integer id, Boolean borrado);
    
    //Obeterne rdatos por valores
@Query(value="{call getIdioma(:lenguajeIn)}",nativeQuery=true)
Idioma listaprocedure(@Param("lenguajeIn") String lenguajeIn);

 /*
@Procedure(name = "getIdioma2")
    void getIdioma2(@Param("Lenguaje_In") String lenguaje,
                   @Param("Err_Codigo") String errCodigo,
                   @Param("Err_Mensaj") String errMensaje);
*/
}
