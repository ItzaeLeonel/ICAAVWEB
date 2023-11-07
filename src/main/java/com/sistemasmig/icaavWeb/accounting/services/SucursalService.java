/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasmig.icaavWeb.accounting.services;

import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.SucursalManager;
import com.sistemasmig.icaavWeb.accounting.models.Sucursales;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Julio
 */
@Component
public class SucursalService {
    
    @Autowired
    private SucursalManager sucursalManager;
    
    public PagedResponse<Sucursales> getSucursal(Sucursales sucursal,   Paging paging) {
        return sucursalManager.getSucursal(sucursal, paging);
    } 
    
 
    public Sucursales getById(Integer id_sucursal) throws EntityNotExistentException {
        return sucursalManager.getById(id_sucursal);
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Sucursales createSucursal(Sucursales sucursal) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Sucursales sucursalPersisted = sucursalManager.createSucursal(sucursal);
        return getById(sucursalPersisted.getId());
    }
    
    public Sucursales updateSucursales(Integer sucursalId,Sucursales sucursales) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Sucursales sucursalesPersisted = sucursalManager.updateSucursales(sucursalId, sucursales);
        return getById(sucursalesPersisted.getId());
    }
    public void deleteSucursales(Integer sucursalId) throws EntityNotExistentException, BusinessLogicException {
        sucursalManager.deleteSucursales(sucursalId);
    }
     public List<Sucursales> findAll() {
        return sucursalManager.findAll();
    }
    
}
