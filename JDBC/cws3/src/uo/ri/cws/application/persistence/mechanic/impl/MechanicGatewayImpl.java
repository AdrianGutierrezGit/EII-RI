package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

	@Override
	public void add(MechanicRecord r) throws PersistenceException {

		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TMECHANICS_ADD");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, r.id);
				pst.setString(2, r.nif);
				pst.setString(3, r.name);
				pst.setString(4, r.surname);
				pst.setLong(5, r.version);

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
			String sql = Queries.get("TMECHANICS_REMOVE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(MechanicRecord rc) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TMECHANICS_UPDATE");
			try (PreparedStatement pst = c.prepareStatement(sql)) {

				pst.setString(1, rc.name);
				pst.setString(2, rc.surname);
				pst.setString(3, rc.id);

				pst.executeUpdate();

			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
		Optional<MechanicRecord> record = Optional.empty();
		try {
			Connection c = Jdbc.getCurrentConnection();

			String sql = Queries.get("TMECHANICS_FINDBYID");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					record = RecordAssembler.toMechanicRecord(rs);
					return record;
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public List<MechanicRecord> findAll() throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TMECHANICS_FINDALL");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				try (ResultSet rs = pst.executeQuery()) {
					return RecordAssembler.toMechanicRecordList(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	@Override
	public Optional<MechanicRecord> findByNif(String nif) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			String sql = Queries.get("TMECHANICS_FINDBYNIF");
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setString(1, nif);
				try (ResultSet rs = pst.executeQuery()) {
					return RecordAssembler.toMechanicRecord(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
