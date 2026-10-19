package uo.ri.cws.application.persistence.sparepart.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class SparePartGatewayImpl implements SparePartGateway {

	@Override
	public void add(SparePartRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSPAREPARTS_ADD");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, t.id);
				pst.setString(2, t.code);
				pst.setString(3, t.description);
				pst.setDouble(4, t.price);
				pst.setInt(5, t.stock);
				pst.setInt(6, t.maxStock);
				pst.setInt(7, t.minStock);
				pst.setLong(8, t.version);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void remove(String code) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TSPAREPARTS_REMOVE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, code);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(SparePartRecord t) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TSPAREPARTS_UPDATE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, t.description);
				pst.setInt(2, t.maxStock);
				pst.setInt(3, t.minStock);
				pst.setDouble(4, t.price);
				pst.setInt(5, t.stock);
//				pst.setString(6, t.id); pone que el string es demasiado largo
				pst.setString(6, t.code);
				pst.executeUpdate();

			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public Optional<SparePartRecord> findById(String id) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		String sql = Queries.get("TSPAREPARTS_FINDBYID");
		try (PreparedStatement pst = c.prepareStatement(sql)) {
			pst.setString(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				return RecordAssembler.resulSetToOptionalSpare(rs);
			}
		} catch (

		SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<SparePartRecord> findAll() throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		String sql = Queries.get("TSPAREPARTS_ALL");
		try (PreparedStatement pst = c.prepareStatement(sql)) {
			try (ResultSet rs = pst.executeQuery()) {
				return RecordAssembler.resultSetToSparePartList(rs);
			}
		} catch (

		SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<SparePartRecord> findByCode(String code) {
		Connection c = Jdbc.getCurrentConnection();
		String sql = Queries.get("TSPAREPARTS_FINDBYCODE");
		try (PreparedStatement pst = c.prepareStatement(sql)) {
			pst.setString(1, code);
			try (ResultSet rs = pst.executeQuery()) {
				return RecordAssembler.resulSetToOptionalSpare(rs);
			}
		} catch (

		SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
