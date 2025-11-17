package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.model.request.PhoneRequest;
import co.edu.javeriana.as.personapp.model.response.PhoneResponse;

@Mapper
public class PhoneMapperRest {
	
	public PhoneResponse fromDomainToAdapterRestMaria(Phone phone) {
		return fromDomainToAdapterRest(phone, "MariaDB");
	}
	
	public PhoneResponse fromDomainToAdapterRestMongo(Phone phone) {
		return fromDomainToAdapterRest(phone, "MongoDB");
	}
	
	public PhoneResponse fromDomainToAdapterRest(Phone phone, String database) {
		Integer duenioCc = phone.getOwner() != null ? phone.getOwner().getIdentification() : null;
		return new PhoneResponse(
				phone.getNumber(), 
				phone.getCompany(), 
				duenioCc,
				database,
				"OK");
	}

	public Phone fromAdapterToDomain(PhoneRequest request) {
		Phone phone = new Phone();
		phone.setNumber(request.getNumero());
		phone.setCompany(request.getOperador());
		if (request.getDuenioCc() != null) {
			Person owner = new Person();
			owner.setIdentification(request.getDuenioCc());
			phone.setOwner(owner);
		}
		return phone;
	}
}

