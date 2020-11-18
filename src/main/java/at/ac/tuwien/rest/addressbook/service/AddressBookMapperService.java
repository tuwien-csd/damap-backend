package at.ac.tuwien.rest.addressbook.service;

import at.ac.tuwien.rest.addressbook.dto.DmpPerson;

public interface AddressBookMapperService {

    DmpPerson getPersonById(String id);
}
