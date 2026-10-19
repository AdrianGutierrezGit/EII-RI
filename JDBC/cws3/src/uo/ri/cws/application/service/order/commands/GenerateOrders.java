package uo.ri.cws.application.service.order.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class GenerateOrders implements Command<List<OrderDto>> {
	private SparePartGateway spg = Factories.persistence.forSparePart();

	@Override
	public List<OrderDto> execute() throws BusinessException {
		List<SparePartRecord> piezasDebajoMinStock = spg.findAll();
		piezasDebajoMinStock.removeIf(sparePart -> sparePart.stock > sparePart.minStock);
		
		return null;
	}

}
