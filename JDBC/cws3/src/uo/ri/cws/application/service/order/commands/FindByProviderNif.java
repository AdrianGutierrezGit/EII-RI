package uo.ri.cws.application.service.order.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.service.order.DtoAssembler;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByProviderNif implements Command<List<OrderDto>> {

    private String nif;
    private OrderGateway og = Factories.persistence.forOrder();

    public FindByProviderNif(String nif) {
	ArgumentChecks.isNotNull(nif);
	this.nif = nif;
    }

    @Override
    public List<OrderDto> execute() throws BusinessException {

	List<OrderRecord> records = og.findByProviderNif(nif);
	return DtoAssembler.toListOrderDto(records);
    }

}
