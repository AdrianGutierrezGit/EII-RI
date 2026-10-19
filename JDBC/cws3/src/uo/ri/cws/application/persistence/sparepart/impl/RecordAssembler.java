package uo.ri.cws.application.persistence.sparepart.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;

public class RecordAssembler {

	public static Optional<SparePartRecord> resulSetToOptionalSpare(ResultSet rs) throws SQLException {
		Optional<SparePartRecord> optional = Optional.empty();
		if (rs.next()) {
			SparePartRecord r = new SparePartRecord();
			r.id = rs.getString("id");
			r.code = rs.getString("code");
			r.description = rs.getString("description");
			r.maxStock = rs.getInt("MAXSTOCK");
			r.minStock = rs.getInt("MINSTOCK");
			r.price = rs.getDouble("price");
			r.stock = rs.getInt("stock");
			r.version = rs.getLong("version");
			optional = Optional.of(r);
		}
		return optional;
	}

	public static List<SparePartRecord> resultSetToSparePartList(ResultSet rs) throws SQLException {
		List<SparePartRecord> spareParts = new ArrayList<SparePartRecord>();
		while (rs.next()) {
			SparePartRecord r = new SparePartRecord();
			r.id = rs.getString("id");
			r.code = rs.getString("code");
			r.description = rs.getString("description");
			r.maxStock = rs.getInt("MAXSTOCK");
			r.minStock = rs.getInt("MINSTOCK");
			r.price = rs.getDouble("price");
			r.stock = rs.getInt("stock");
			r.version = rs.getLong("version");

			spareParts.add(r);
		}
		return spareParts;
	}
}
