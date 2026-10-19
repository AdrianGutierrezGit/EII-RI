package uo.ri.cws.application.persistence.provider.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public class RecordAssembler {
    public static Optional<ProviderRecord> resultSetToProviderRecord(
	    ResultSet rs) throws SQLException {

	Optional<ProviderRecord> optional = Optional.empty();
	if (rs.next()) {
	    ProviderRecord record = new ProviderRecord();
	    record.id = rs.getString("id");
	    record.email = rs.getString("email");
	    record.name = rs.getString("name");
	    record.nif = rs.getString("nif");
	    record.phone = rs.getString("phone");
	    record.version = rs.getLong("version");
	    optional = Optional.of(record);
	}
	return optional;
    }

    public static List<ProviderRecord> resultSetToProviderRecordList(
	    ResultSet rs) throws SQLException {

	List<ProviderRecord> lista = new ArrayList<ProviderRecord>();
	while (rs.next()) {
	    ProviderRecord record = new ProviderRecord();
	    record.id = rs.getString("id");
	    record.email = rs.getString("email");
	    record.name = rs.getString("name");
	    record.nif = rs.getString("nif");
	    record.phone = rs.getString("phone");
	    record.version = rs.getLong("version");
	    lista.add(record);
	}
	return lista;
    }
}
