package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public UpdateMechanic(MechanicDto m) {
	ArgumentChecks.isNotNull(m);
	ArgumentChecks.isNotBlank(m.id);
	ArgumentChecks.isNotBlank(m.name);
	ArgumentChecks.isNotBlank(m.surname);

	this.dto = m;

    }

    public Void execute() throws BusinessException {

	Optional<MechanicRecord> om = mg.findById(dto.id);
	BusinessChecks.exists(om);
	MechanicRecord mr = om.get();
	BusinessChecks.isTrue(dto.version >= 0);

	mr.name = dto.name;
	mr.surname = dto.surname;

	mg.update(mr);
	return null;
    }

}
