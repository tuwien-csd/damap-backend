package at.ac.tuwien.rest.addressbook.service;

import at.ac.tuwien.rest.addressbook.dto.DmpPerson;
import at.ac.tuwien.rest.addressbook.dto.DmpPersonId;
import at.ac.tuwien.rest.addressbook.dto.PersonDetails;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AddressBookMapperServiceImpl implements AddressBookMapperService {

    @Inject
    @RestClient
    private AddressBookService addressBookService;

    @Override
    public DmpPerson getPersonById(String id) {
        PersonDetails personDetails = addressBookService.getPersonDetailsById(id);
        DmpPerson person = new DmpPerson();
        DmpPersonId personId = new DmpPersonId();

        person.setId(personDetails.getId());
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setMbox(personDetails.getMainEmail());
        if(personDetails.getOrcid() != null) {
            personId.setIdentifier(personDetails.getOrcid());
            personId.setType(DmpPersonId.Type.ORCID);
            person.setPersonId(personId);
        }

        return person;
    }
}
