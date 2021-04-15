package at.ac.tuwien.rest.addressbook.service;

import at.ac.tuwien.damap.rest.domain.PersonDO;
import at.ac.tuwien.rest.addressbook.dto.PersonDTO;
import at.ac.tuwien.rest.addressbook.mapper.PersonDTOMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AddressBookService {

    private static final Logger log = LoggerFactory.getLogger(AddressBookService.class);

    @Inject
    @RestClient
    private AddressBookRestService addressBookRestService;

    public PersonDO getPersonById(String id) {
        PersonDTO personDTO = addressBookRestService.getPersonDetailsById(id);
        PersonDO personDO = new PersonDO();
        PersonDTOMapper.mapAtoB(personDTO, personDO);

        return personDO;
    }
}
