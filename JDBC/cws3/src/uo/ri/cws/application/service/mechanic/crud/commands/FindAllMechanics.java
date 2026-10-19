package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;

public class FindAllMechanics implements Command<List<MechanicDto>> {

    public List<MechanicDto> execute() {

	MechanicGateway mg = Factories.persistence.forMechanic();
	List<MechanicRecord> list = mg.findAll();

	return DtoAssembler.toDtoList(list);
    }
}
