package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.entity.Usuario;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.UsuarioManager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UsuarioService {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioManager usuarioManager;
    
    public Usuario getById(Integer usuarioId) throws EntityNotExistentException {
        return usuarioManager.getById(usuarioId);
    }
    
    public PagedResponse<Usuario> getUsuario(Usuario usuario,   Paging paging) {
        return usuarioManager.getUsuario(usuario, paging);
    }
    
    public List<Usuario> findAll() {
        return usuarioManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Usuario createUsuario(Usuario usuario) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Usuario usuarioPersisted = usuarioManager.createUsuario(usuario);
        return getById(usuarioPersisted.getId());
    }
    
    public Usuario updateUsuario(Integer usuarioId,Usuario usuario) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Usuario usuarioPersisted = usuarioManager.updateUsuario(usuarioId, usuario);
        return getById(usuarioPersisted.getId());
    }
    
    public void deleteUsuario(Integer usuarioId) throws EntityNotExistentException, BusinessLogicException {
        usuarioManager.deleteUsuario(usuarioId);
        
    }  

    
}


