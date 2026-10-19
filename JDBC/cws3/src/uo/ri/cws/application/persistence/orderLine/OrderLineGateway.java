package uo.ri.cws.application.persistence.orderLine;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderedSpareRecord;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {

    public List<OrderLineRecord> findOrdersLineByOrderId(String id);
    public Optional<String> findSparePartIdByOrderCode(String code);
    
    public static class OrderLineRecord {
	public OrderedSpareRecord sparePart;
	public double price;
	public int quantity;
	public String orderId;
    }
}
