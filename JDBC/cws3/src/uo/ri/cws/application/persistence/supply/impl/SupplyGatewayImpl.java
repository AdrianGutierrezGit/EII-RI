package uo.ri.cws.application.persistence.supply.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class SupplyGatewayImpl implements SupplyGateway {

	@Override
	public void add(SupplyRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TSUPPLIES_ADD");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, t.id);
				pst.setLong(2, t.deliveryTerm);
				pst.setDouble(3, t.price);
				pst.setLong(4, t.version);
				pst.setString(5, t.provider.id);
				pst.setString(6, t.sparePart.id);

				pst.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public void remove(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TSUPPLIES_REMOVE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(SupplyRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSUPPLIES_UPDATE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {

				pst.setInt(1, t.deliveryTerm);
				pst.setDouble(2, t.price);
				pst.setString(3, t.provider.id);
				pst.setString(4, t.sparePart.id);
				pst.setString(5, t.id);
				pst.executeUpdate();

			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<SupplyRecord> findById(String id) throws PersistenceException {
		Optional<SupplyRecord> record = Optional.empty();
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSUPPLIES_FINDBYID");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					record = RecordAssembler.toSupplyRecord(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return record;
	}

	@Override
	public List<SupplyRecord> findAll() throws PersistenceException {

		return null;
	}

	@Override
	public List<SupplyRecord> findByProviderId(String providerId) {

		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSUPPLIES_FINDBYPROVIDERID");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, providerId);
				try (ResultSet rs = pst.executeQuery()) {
					return RecordAssembler.toSupplyRecordList(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public Optional<SupplyRecord> findByProviderIdSparePartId(String providerId, String sparePartId) {
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSUPPLIES_FINDBYPROVIDERIDANDSPAREPARTID");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, providerId);
				pst.setString(2, sparePartId);
				try (ResultSet rs = pst.executeQuery()) {
					return RecordAssembler.toSupplyRecord(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	
	
	@Override
	public List<SupplyRecord> findBySparePartId(String sparePartId) {

		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSUPPLIES_FINDBYSPAREPARTID");
			try (PreparedStatement pst = c.prepareStatement(sql)) {

				pst.setString(1, sparePartId);
				try (ResultSet rs = pst.executeQuery()) {
					return RecordAssembler.toSupplyRecordList(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	
	@Override
	public List<SupplyRecord> findBySparePartCode(String sparePartCode) {

		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSUPPLIES_FINDBYSPAREPARTCODE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {

				pst.setString(1, sparePartCode);
				try (ResultSet rs = pst.executeQuery()) {
					return RecordAssembler.toSupplyRecordList(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}
}
