package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.EmpresaUsuarioManager;
import com.sistemasmig.icaavWeb.accounting.models.EmpresaUsuario;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EmpresaUsuarioService {
    private final static Logger logger = LoggerFactory.getLogger(EmpresaUsuarioService.class);

    @Autowired
    private EmpresaUsuarioManager empresaUsuarioManager;
    
    public EmpresaUsuario getById(Integer empresaUsuarioId) throws EntityNotExistentException {
        return empresaUsuarioManager.getById(empresaUsuarioId);
    }
    
    public PagedResponse<EmpresaUsuario> getEmpresaUsuario(EmpresaUsuario empresaUsuario,   Paging paging) {
        return empresaUsuarioManager.getEmpresaUsuario(empresaUsuario, paging);
    }
    
    public List<EmpresaUsuario> findAll() {
        return empresaUsuarioManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public EmpresaUsuario createEmpresaUsuario(EmpresaUsuario empresaUsuario) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        EmpresaUsuario empresaUsuarioPersisted = empresaUsuarioManager.createEmpresaUsuario(empresaUsuario);
        return getById(empresaUsuarioPersisted.getId());
    }
    
    public EmpresaUsuario updateEmpresaUsuario(Integer empresaUsuarioId,EmpresaUsuario empresaUsuario) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        EmpresaUsuario empresaUsuarioPersisted = empresaUsuarioManager.updateEmpresaUsuario(empresaUsuarioId, empresaUsuario);
        return getById(empresaUsuarioPersisted.getId());
    }
    
    public void deleteEmpresaUsuario(Integer empresaUsuarioId) throws EntityNotExistentException, BusinessLogicException {
        empresaUsuarioManager.deleteEmpresaUsuario(empresaUsuarioId);
        
    }  
    
    public Boolean initialize() {
        try{
            createEmpresaUsuarios();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createEmpresaUsuarios() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


