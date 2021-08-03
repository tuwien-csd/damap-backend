package at.ac.tuwien.rest.addressbook.service;

import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.rest.addressbook.dto.PersonDTO;
import at.ac.tuwien.rest.addressbook.mapper.PersonDTOMapper;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@JBossLog
public class AddressBookService {

    @Inject
    @RestClient
    AddressBookRestService addressBookRestService;

    public PersonDO getPersonById(String id) {
        PersonDTO personDTO = addressBookRestService.getPersonDetailsById(id);
        return PersonDTOMapper.mapAtoB(personDTO, new PersonDO());
    }
}
