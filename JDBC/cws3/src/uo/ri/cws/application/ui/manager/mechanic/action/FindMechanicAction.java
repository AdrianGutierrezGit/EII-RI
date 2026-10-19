package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindMechanicAction implements Action {
    @Override
    public void execute() throws BusinessException {

	Console.println("\n Searching mechanic \n");
	String id = Console.readString("Type mechahic id");
	Optional<MechanicDto> result = Factories.service.forMechanicService()
		.findMechanicById(id);
	if (result.isEmpty()) {
	    Console.println("\nMechanic searched: not found \n");
	} else
	    Printer.printMechanic(result.get());
    }
}
