package uo.ri.cws.application.ui.manager.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String idMechanic = Console.readString("Type mechanic id ");

	MechanicCrudService service = Factories.service.forMechanicService();
	service.deleteMechanic(idMechanic);

	Console.println("Mechanic deleted");
    }

}
