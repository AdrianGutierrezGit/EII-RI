package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindMechanicByNif implements Command<Optional<MechanicDto>> {

    private String nif;

    public FindMechanicByNif(String nif) {
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() throws BusinessException {
	MechanicGateway mg = Factories.persistence.forMechanic();

	Optional<MechanicRecord> o = mg.findByNif(nif);
	if (o.isEmpty())
	    return Optional.empty();

	return Optional.of(DtoAssembler.toDto(o.get()));
    }

}
