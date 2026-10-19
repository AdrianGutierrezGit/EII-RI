package uo.ri.cws.application.service.order;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderedSpareRecord;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedProviderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedSpareDto;

public class DtoAssembler {

    public static List<OrderDto> toListOrderDto(List<OrderRecord> lista) {
	List<OrderDto> nuevaLista = new ArrayList<OrderDto>();
	for (OrderRecord dto : lista) {
	    OrderDto line = toOrderDto(dto);
	    nuevaLista.add(line);
	}
	return nuevaLista;
    }

    public static OrderDto toOrderDto(OrderRecord r) {
	OrderDto dto = new OrderDto();
	dto.id = r.id;
	dto.version = r.version;
	dto.code = r.code;
	dto.orderedDate = r.orderedDate;
	dto.receptionDate = r.receptionDate;
	dto.amount = r.amount;
	dto.state = r.state;

	return dto;
    }

    public static OrderedProviderDto toOrderedProviderDto(ProviderRecord r) {
	OrderedProviderDto dto = new OrderedProviderDto();
	dto.id = r.id;
	dto.nif = r.nif;
	dto.name = r.name;
	return dto;
    }

    public static List<OrderLineDto> toListOrderLineDto(
	    List<OrderLineRecord> lista) {
	List<OrderLineDto> nuevaLista = new ArrayList<OrderLineDto>();
	for (OrderLineRecord dto : lista) {
	    OrderLineDto line = toOrderLineDto(dto);
	    nuevaLista.add(line);
	}
	return nuevaLista;
    }

    public static OrderLineDto toOrderLineDto(OrderLineRecord dto) {
	OrderLineDto record = new OrderLineDto();
	record.sparePart = toOrderedSpareDto(dto.sparePart);
	record.price = dto.price;
	record.quantity = dto.quantity;
	return record;
    }

    public static OrderedSpareDto toOrderedSpareDto(OrderedSpareRecord dto) {
	OrderedSpareDto record = new OrderedSpareDto();
	record.id = dto.id;
	record.code = dto.code;
	record.description = dto.description;
	return record;
    }

}
