package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.EstiloEmpresaManager;
import com.sistemasmig.icaavWeb.accounting.models.EstiloEmpresa;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EstiloEmpresaService {
    private final static Logger logger = LoggerFactory.getLogger(EstiloEmpresaService.class);

    @Autowired
    private EstiloEmpresaManager estiloEmpresaManager;
    
    public EstiloEmpresa getById(Integer estiloEmpresaId) throws EntityNotExistentException {
        return estiloEmpresaManager.getById(estiloEmpresaId);
    }
    
    public PagedResponse<EstiloEmpresa> getEstiloEmpresa(EstiloEmpresa estiloEmpresa,   Paging paging) {
        return estiloEmpresaManager.getEstiloEmpresa(estiloEmpresa, paging);
    }
    
    public List<EstiloEmpresa> findAll() {
        return estiloEmpresaManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public EstiloEmpresa createEstiloEmpresa(EstiloEmpresa estiloEmpresa) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        EstiloEmpresa estiloEmpresaPersisted = estiloEmpresaManager.createEstiloEmpresa(estiloEmpresa);
        return getById(estiloEmpresaPersisted.getId());
    }
    
    public EstiloEmpresa updateEstiloEmpresa(Integer estiloEmpresaId,EstiloEmpresa estiloEmpresa) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        EstiloEmpresa estiloEmpresaPersisted = estiloEmpresaManager.updateEstiloEmpresa(estiloEmpresaId, estiloEmpresa);
        return getById(estiloEmpresaPersisted.getId());
    }
    
    public void deleteEstiloEmpresa(Integer estiloEmpresaId) throws EntityNotExistentException, BusinessLogicException {
        estiloEmpresaManager.deleteEstiloEmpresa(estiloEmpresaId);
        
    }  
    
    public Boolean initialize() {
        try{
            createEstiloEmpresas();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createEstiloEmpresas() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


