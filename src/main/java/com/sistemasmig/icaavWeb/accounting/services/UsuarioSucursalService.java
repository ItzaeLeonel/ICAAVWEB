package com.sistemasmig.icaavWeb.accounting.services;
import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.UsuarioSucursalManager;
import com.sistemasmig.icaavWeb.accounting.models.UsuarioSucursal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class UsuarioSucursalService {  
    private final static Logger logger = LoggerFactory.getLogger(UsuarioSucursalService.class);

    @Autowired
    private UsuarioSucursalManager usuarioSucursalManager;
    
    public PagedResponse<UsuarioSucursal> getUsuario(UsuarioSucursal usuarioSucursal,   Paging paging) {
        return usuarioSucursalManager.getUsuarioSucursal(usuarioSucursal, paging);
    }  
    public UsuarioSucursal getById(Integer id_usuario_sucursal) throws EntityNotExistentException {
        return usuarioSucursalManager.getById(id_usuario_sucursal);
    }
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public UsuarioSucursal createUsuarioSucursal(UsuarioSucursal usuarioSucursal) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        UsuarioSucursal usuarioSucursalPersisted = usuarioSucursalManager.createUsuarioSucursal(usuarioSucursal);
        return getById(usuarioSucursalPersisted.getId());
    }
    public UsuarioSucursal updateUsuarioSucursal(Integer empresaUsuarioId,UsuarioSucursal usuarioSucursal) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        UsuarioSucursal empresaUsuarioPersisted = usuarioSucursalManager.updateUsuarioSucursal(empresaUsuarioId, usuarioSucursal);
        return getById(empresaUsuarioPersisted.getId());
    }
    public void deleteUsuarioSucursal(Integer usuarioSucursalId) throws EntityNotExistentException, BusinessLogicException {
        usuarioSucursalManager.deleteUsuarioSucursal(usuarioSucursalId);
    }
     public List<UsuarioSucursal> findAll() {
        return usuarioSucursalManager.findAll();
    }
}
