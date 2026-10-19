package uo.ri.cws.application.ui.manager.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
	MechanicDto m = new MechanicDto();

	m.nif = Console.readString("nif");
	m.name = Console.readString("Name");
	m.surname = Console.readString("Surname");

	Factories.service.forMechanicService().addMechanic(m);

	// Print result
	Console.println("Mechanic added");
    }

}
