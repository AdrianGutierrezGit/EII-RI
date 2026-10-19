package uo.ri.cws.application.persistence.provider.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class ProviderGatewayImpl implements ProviderGateway {

    @Override
    public void add(ProviderRecord t) throws PersistenceException {

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_ADD");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.id);
		pst.setString(2, t.email);
		pst.setString(3, t.name);
		pst.setString(4, t.nif);
		pst.setString(5, t.phone);
		pst.setLong(6, t.version);
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
	    String sql = Queries.get("TPROVIDER_REMOVE");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public void update(ProviderRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();

	    String sql = Queries.get("TPROVIDER_UPDATE");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {

		pst.setString(1, t.email);
		pst.setString(2, t.name);
		pst.setString(3, t.phone);
		pst.setString(4, t.nif);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public Optional<ProviderRecord> findById(String id)
	    throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_FINDBYID");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToProviderRecord(rs);

		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public List<ProviderRecord> findAll() throws PersistenceException {

	return null;
    }

    @Override
    public Optional<ProviderRecord> findProviderByNif(String nif) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_FINDBYNIF");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToProviderRecord(rs);
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<ProviderRecord> findProviderByEmailPhoneName(
	    ProviderRecord record) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_EMAILPHONENAME");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, record.email);
		pst.setString(2, record.phone);
		pst.setString(3, record.name);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToProviderRecordList(rs);
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<ProviderRecord> findByName(String name) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_FINDBYNAME");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, name);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToProviderRecordList(rs);
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<ProviderRecord> fndEmailPhoneNameDifNif(ProviderRecord record) {

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_FINDBYNAMEEMAILPHONEANDDIFNIF");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, record.email);
		pst.setString(1, record.name);
		pst.setString(1, record.phone);
		pst.setString(1, record.nif);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToProviderRecordList(rs);
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<ProviderRecord> findBySparePartCode(String code) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TPROVIDER_FINDBYSPAREPARTCODE");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, code);

		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToProviderRecordList(rs);
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

}
