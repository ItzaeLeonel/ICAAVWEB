

package com.sistemasmig.icaavWeb.accounting.managers;


import com.sistemasmig.icaavWeb.accounting.models.dto.ListSelectorCuentas;
import com.sistemasmig.icaavWeb.accounting.exceptions.EntityNotExistentException;
import java.util.List;
import com.sistemasmig.icaavWeb.accounting.repositories.StoredProcedure.SelectorCuentasSPRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julio
 */

@Component
public class SelectorCuentaContableManager {
    
     @Autowired
    private SelectorCuentasSPRepository selectorCuentasSPRepository;
    
    @Autowired
    private EntityManager entityManager;
    
      public List <ListSelectorCuentas> getBySelectorCuentaSP(Integer pr_id_modelo_contable) throws EntityNotExistentException {
    List<ListSelectorCuentas>resultado=selectorCuentasSPRepository.listaprocedure(pr_id_modelo_contable);
    return resultado;
    } 
    
 
    
}
