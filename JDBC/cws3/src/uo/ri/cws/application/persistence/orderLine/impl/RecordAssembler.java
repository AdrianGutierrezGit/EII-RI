package uo.ri.cws.application.persistence.orderLine.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.order.OrderGateway.OrderedSpareRecord;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;

public class RecordAssembler {

    public static List<OrderLineRecord> resulSetToOrderLinesRecordList(
	    ResultSet rs) throws SQLException {
	List<OrderLineRecord> lista = new ArrayList<OrderLineRecord>();
	while (rs.next()) {
	    OrderLineRecord r = new OrderLineRecord();
	    r.price = rs.getDouble("price");
	    r.quantity = rs.getInt("quantity");
	    OrderedSpareRecord spare = new OrderedSpareRecord();
	    spare.id = rs.getString("sparepart_id");
	    r.sparePart = spare;
	    r.orderId = rs.getString("Order_id");
	    lista.add(r);
	}
	return lista;
    }

}
