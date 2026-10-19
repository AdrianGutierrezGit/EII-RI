package uo.ri.cws.application.service.order.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.order.DtoAssembler;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ReceiveOrder implements Command<OrderDto> {

    private String code;
    private OrderGateway og = Factories.persistence.forOrder();

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private OrderLineGateway olg = Factories.persistence.forOrderLine();

    public ReceiveOrder(String code) {
	ArgumentChecks.isNotBlank(code);
	this.code = code;
    }

    @Override
    public OrderDto execute() throws BusinessException {
	Optional<OrderRecord> optionalRecord = og.findReceiveByCode(code);
	BusinessChecks.exists(optionalRecord);
	OrderRecord order = optionalRecord.get();

	order.state = "RECEIVED";
	order.receptionDate = LocalDate.now();

	List<OrderLineRecord> orderLines = olg
		.findOrdersLineByOrderId(order.id);
	changePricesOrderLines(orderLines);

	og.update(order);

	OrderDto orderDto = DtoAssembler.toOrderDto(order);

	return orderDto;
    }

    public void changePricesOrderLines(List<OrderLineRecord> lista)
	    throws BusinessException {
	for (OrderLineRecord line : lista) {

	    Optional<SparePartRecord> optionalSpare = spg
		    .findById(line.sparePart.id);
	    BusinessChecks.exists(optionalSpare);
	    SparePartRecord spareRecord = optionalSpare.get();
	    double precioViejo = spareRecord.price;
	    int cantidadVieja = spareRecord.stock;
	    double precioNuevo = line.price;
	    int cantidadNueva = line.quantity;

	    spareRecord.price = (cantidadVieja * precioViejo
		    + cantidadNueva * precioNuevo * 1.2)
		    / (cantidadVieja + cantidadNueva);
	    spareRecord.stock = cantidadNueva + cantidadVieja;
	    spg.update(spareRecord);

	}
    }

}
