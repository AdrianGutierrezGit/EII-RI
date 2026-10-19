package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;

    public AddMechanic(MechanicDto m) {
	ArgumentChecks.isNotNull(m);
	ArgumentChecks.isNotBlank(m.nif);
	ArgumentChecks.isNotBlank(m.name);
	ArgumentChecks.isNotBlank(m.surname);

	this.dto = m;
    }

    public MechanicDto execute() throws BusinessException {
	MechanicGateway mg = Factories.persistence.forMechanic();

	Optional<MechanicRecord> mr = mg.findByNif(dto.nif);
	BusinessChecks.doesNotExist(mr,
		"There is a mechanic with the same nif");

	dto.id = UUID.randomUUID().toString();
	dto.version = 1L;
	MechanicRecord r = DtoAssembler.toRecord(dto);
	mg.add(r);
	return dto;
    }

}