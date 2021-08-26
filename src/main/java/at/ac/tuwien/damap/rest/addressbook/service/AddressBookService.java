package at.ac.tuwien.damap.rest.addressbook.service;

import at.ac.tuwien.damap.rest.dmp.domain.PersonDO;

public interface AddressBookService {
    PersonDO getPersonById(String id);
}
