package com.sistemasmig.icaavWeb.accounting.services;


import com.sistemasmig.icaavWeb.accounting.containers.PagedResponse;
import com.sistemasmig.icaavWeb.accounting.containers.Paging;
import com.sistemasmig.icaavWeb.accounting.exceptions.BusinessLogicException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotFoundException;
import com.sistemasmig.icaavWeb.accounting.exceptions.ExistentEntityException;
import com.sistemasmig.icaavWeb.accounting.managers.TutorialesManager;
import com.sistemasmig.icaavWeb.accounting.models.Tutoriales;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TutorialesService {
    private final static Logger logger = LoggerFactory.getLogger(TutorialesService.class);

    @Autowired
    private TutorialesManager tutorialesManager;
    
    public Tutoriales getById(Integer tutorialesId) throws EntityNotExistentException {
        return tutorialesManager.getById(tutorialesId);
    }
    
    public PagedResponse<Tutoriales> getTutoriales(Tutoriales tutoriales,   Paging paging) {
        return tutorialesManager.getTutoriales(tutoriales, paging);
    }
    
    public List<Tutoriales> findAll() {
        return tutorialesManager.findAll();
    }
    
    @Transactional(rollbackFor = {BusinessLogicException.class,Exception.class})
    public Tutoriales createTutoriales(Tutoriales tutoriales) throws BusinessLogicException, ExistentEntityException, EntityNotExistentException {
        Tutoriales tutorialesPersisted = tutorialesManager.createTutoriales(tutoriales);
        return getById(tutorialesPersisted.getId());
    }
    
    public Tutoriales updateTutoriales(Integer tutorialesId,Tutoriales tutoriales) throws BusinessLogicException, EntityNotExistentException, ExistentEntityException {
        Tutoriales tutorialesPersisted = tutorialesManager.updateTutoriales(tutorialesId, tutoriales);
        return getById(tutorialesPersisted.getId());
    }
    
    public void deleteTutoriales(Integer tutorialesId) throws EntityNotExistentException, BusinessLogicException {
        tutorialesManager.deleteTutoriales(tutorialesId);
        
    }  
    
    public Boolean initialize() {
        try{
            createTutorialess();
        } catch (BusinessLogicException | EntityNotFoundException | ExistentEntityException | EntityNotExistentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void createTutorialess() throws EntityNotFoundException, BusinessLogicException, ExistentEntityException, EntityNotExistentException {
       
    }
}


