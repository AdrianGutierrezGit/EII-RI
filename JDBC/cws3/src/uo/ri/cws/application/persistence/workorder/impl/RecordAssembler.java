package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class RecordAssembler {

    public static WorkOrderRecord toWorkOrderRecord(ResultSet rs)
	    throws SQLException {
	WorkOrderRecord result = new WorkOrderRecord();

	result.id = rs.getString("id");
	result.version = rs.getLong("version");

	result.vehicleId = rs.getString("vehicle_Id");
	result.description = rs.getString("description");
	result.date = rs.getTimestamp("date").toLocalDateTime();
	result.amount = rs.getDouble("amount");
	result.state = rs.getString("state");

	result.mechanicId = rs.getString("mechanic_Id");
	result.invoiceId = rs.getString("invoice_Id");

	return result;

    }

    public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs)
	    throws SQLException {
	List<WorkOrderRecord> res = new ArrayList<>();
	while (rs.next()) {
	    res.add(toWorkOrderRecord(rs));
	}
	return res;
    }

}
