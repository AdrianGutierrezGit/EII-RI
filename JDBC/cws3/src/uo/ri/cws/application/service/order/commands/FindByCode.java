package uo.ri.cws.application.service.order.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderedSpareRecord;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.order.DtoAssembler;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedProviderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCode implements Command<Optional<OrderDto>> {
    private OrderGateway og = Factories.persistence.forOrder();
    private OrderLineGateway olg = Factories.persistence.forOrderLine();
    private SparePartGateway sog = Factories.persistence.forSparePart();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private String code;

    public FindByCode(String code) {
	ArgumentChecks.isNotNull(code);
	this.code = code;
    }

    @Override
    public Optional<OrderDto> execute() throws BusinessException {
	Optional<OrderRecord> optionalOrderRecord = og.findByCode(code);

	if (optionalOrderRecord.isEmpty())
	    return Optional.empty();

	OrderRecord orderRecord = optionalOrderRecord.get();
	List<OrderLineRecord> lista = new ArrayList<OrderLineRecord>();
	List<OrderLineRecord> nueva = olg
		.findOrdersLineByOrderId(orderRecord.id);
	if (nueva != null)
	    lista.addAll(nueva);
	for (OrderLineRecord line : lista) {
	    Optional<SparePartRecord> spare = sog.findById(line.sparePart.id);

	    if (!spare.isEmpty()) {
		line.sparePart = new OrderedSpareRecord();
		line.sparePart.id = spare.get().id;
		line.sparePart.code = spare.get().code;
		line.sparePart.description = spare.get().description;
	    }
	}
	OrderDto dto = DtoAssembler.toOrderDto(orderRecord);
	dto.lines = DtoAssembler.toListOrderLineDto(lista);

	Optional<ProviderRecord> provider = pg
		.findById(orderRecord.provider_id);
	dto.provider = new OrderedProviderDto();
	if (!provider.isEmpty())
	    dto.provider = DtoAssembler.toOrderedProviderDto(provider.get());
	return Optional.of(dto);
    }

}
