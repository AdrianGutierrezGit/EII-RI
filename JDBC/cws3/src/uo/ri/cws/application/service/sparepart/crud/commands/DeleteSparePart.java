package uo.ri.cws.application.service.sparepart.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSparePart implements Command<Void> {

	private SparePartGateway spg = Factories.persistence.forSparePart();
	private OrderLineGateway olg = Factories.persistence.forOrderLine();
	private SupplyGateway sg = Factories.persistence.forSuppply();

	private String code;

	public DeleteSparePart(String code) {
		ArgumentChecks.isNotBlank(code);
		this.code = code;
	}

	@Override
	public Void execute() throws BusinessException {
		// Existe la sparepart a borrar
		Optional<SparePartRecord> existSparePart = spg.findByCode(code);
		BusinessChecks.exists(existSparePart, "This Spare part does not exist");
		// La spare part no está siendo utilizada en ninguna reparacion o existe un
		// pedido que le haga referencia
		Optional<String> ol = olg.findSparePartIdByOrderCode(code);
		BusinessChecks.isTrue(ol.isEmpty());
		List<SupplyRecord> listaSupplies = sg.findBySparePartCode(code);
		BusinessChecks.isTrue(listaSupplies.size() == 0);
		spg.remove(code);
		return null;
	}

}
