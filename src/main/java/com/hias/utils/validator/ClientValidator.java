package com.hias.utils.validator;

import com.hias.entity.Client;
import com.hias.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class ClientValidator {

    private final ClientRepository clientRepository;

    public boolean isCorporateIDExistance(String corporateID) {
        List<Client> clients = clientRepository.findByCorporateIDIgnoreCaseAndIsDeletedIsFalse(corporateID);
        return CollectionUtils.isNotEmpty(clients);
    }

}
