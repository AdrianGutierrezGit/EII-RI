package uo.ri.cws.application.persistence.orderLine.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class OrderLineGatewayImpl implements OrderLineGateway {

	@Override
	public void add(OrderLineRecord t) throws PersistenceException {

	}

	@Override
	public void remove(String id) throws PersistenceException {

	}

	@Override
	public void update(OrderLineRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TORDERLINES_UPDATE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {

				pst.setDouble(1, t.price);
				pst.setInt(2, t.quantity);

				pst.setString(3, t.sparePart.id);
				pst.setString(4, t.orderId);

				pst.executeUpdate();

			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public Optional<OrderLineRecord> findById(String id) throws PersistenceException {

		return null;
	}

	@Override
	public List<OrderLineRecord> findAll() throws PersistenceException {

		return null;
	}

	@Override
	public List<OrderLineRecord> findOrdersLineByOrderId(String id) {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TORDERLINES_FINDBYORDERID");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					List<OrderLineRecord> r = RecordAssembler.resulSetToOrderLinesRecordList(rs);

					return r;
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<String> findSparePartIdByOrderCode(String code) {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TORDERLINES_FINDSPAREPARTIDBYORDERCODE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, code);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						String id = rs.getString(1);

						return Optional.of(id);
					}
					return Optional.empty();
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
