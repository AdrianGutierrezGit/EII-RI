package uo.ri.cws.application.ui.manager.spares.supply.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ViewDetailAction implements Action {

    @Override
    public void execute() throws Exception {
	Console.println("Please, provide the following data");
	String nif = Console.readString("Provider Nif ");
	String code = Console.readString("Spare part code ");

	SuppliesCrudService service = Factories.service
		.forSuppliesCrudService();
	Optional<SupplyDto> op = service.findByNifAndCode(nif, code);

	if (op.isEmpty()) {
	    Console.println("There is no such supply.");
	    Console.println("Please mind the nif and try again");
	    return;
	}

	Printer.print(op.get());
    }

}
