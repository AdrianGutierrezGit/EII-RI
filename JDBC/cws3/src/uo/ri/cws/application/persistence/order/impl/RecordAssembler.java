package uo.ri.cws.application.persistence.order.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public class RecordAssembler {

    public static Optional<OrderRecord> toOrderRecord(ResultSet rs)
	    throws SQLException {
	Optional<OrderRecord> optional = Optional.empty();
	OrderRecord r = new OrderRecord();
	if (rs.next()) {
	    r.id = rs.getString("id");
	    r.amount = rs.getDouble("amount");
	    r.code = rs.getString("code");
	    r.orderedDate = (rs.getDate("orderedDate") != null)
		    ? rs.getDate("orderedDate").toLocalDate()
		    : LocalDate.now();
	    if (rs.getDate("receptionDate") != null)
		r.receptionDate = rs.getDate("receptionDate").toLocalDate();
	    r.state = rs.getString("state");
	    r.version = rs.getLong("version");
	    r.provider_id = rs.getString("provider_id");
	    optional = Optional.of(r);
	}
	return optional;
    }

    public static List<OrderRecord> resultSetToOrderRecordList(ResultSet rs)
	    throws SQLException {
	List<OrderRecord> lista = new ArrayList<OrderRecord>();
	while (rs.next()) {
	    OrderRecord r = new OrderRecord();
	    r.id = rs.getString("id");
	    r.amount = rs.getDouble("amount");
	    r.code = rs.getString("code");
	    r.orderedDate = (rs.getDate("orderedDate") != null)
		    ? rs.getDate("orderedDate").toLocalDate()
		    : LocalDate.now();
	    if (rs.getDate("receptionDate") != null)
		r.receptionDate = rs.getDate("receptionDate").toLocalDate();
	    r.state = rs.getString("state");
	    r.version = rs.getLong("version");

	    lista.add(r);
	}
	return lista;
    }

}
