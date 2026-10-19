package uo.ri.cws.application.persistence.supply.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SuppliedSparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SupplierProviderRecord;

public class RecordAssembler {
    public static Optional<SupplyRecord> toSupplyRecord(ResultSet rs)
	    throws SQLException {
	Optional<SupplyRecord> optional = Optional.empty();
	if (rs.next()) {
	    SupplyRecord value = new SupplyRecord();
	    value.id = rs.getString("id");

	    value.deliveryTerm = rs.getInt("deliveryterm");

	    value.price = rs.getDouble("price");
	    value.version = rs.getLong("version");
	    SupplierProviderRecord provider = new SupplierProviderRecord();
	    provider.id = rs.getString("provider_id");
	    SuppliedSparePartRecord sparePart = new SuppliedSparePartRecord();
	    sparePart.id = rs.getString("sparepart_id");
	    value.sparePart = sparePart;
	    optional = Optional.of(value);
	}
	return optional;
    }

    public static List<SupplyRecord> toSupplyRecordList(ResultSet rs)
	    throws SQLException {
	List<SupplyRecord> lista = new ArrayList<SupplyRecord>();
	while (rs.next()) {
	    SupplyRecord value = new SupplyRecord();
	    value.id = rs.getString("id");

	    value.deliveryTerm = rs.getInt("deliveryterm");

	    value.price = rs.getDouble("price");
	    value.version = rs.getLong("version");
	    SupplierProviderRecord provider = new SupplierProviderRecord();
	    provider.id = rs.getString("provider_id");
	    SuppliedSparePartRecord sparePart = new SuppliedSparePartRecord();
	    sparePart.id = rs.getString("sparepart_id");
	    value.sparePart = sparePart;
	    value.provider = provider;
	    lista.add(value);
	}
	return lista;
    }
}
