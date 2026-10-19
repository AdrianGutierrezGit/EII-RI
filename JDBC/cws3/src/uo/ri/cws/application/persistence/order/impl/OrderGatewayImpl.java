package uo.ri.cws.application.persistence.order.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class OrderGatewayImpl implements OrderGateway {

    @Override
    public void add(OrderRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(OrderRecord t) throws PersistenceException {

    }

    @Override
    public Optional<OrderRecord> findById(String id)
	    throws PersistenceException {
	return null;
    }

    @Override
    public List<OrderRecord> findAll() throws PersistenceException {
	return null;
    }

    public Optional<OrderRecord> findByCode(String code)
	    throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TORDERS_FINDBYCODE");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, code);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.toOrderRecord(rs);

		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public List<OrderRecord> findByProviderId(String nif)
	    throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TORDERS_FINDBYID");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToOrderRecordList(rs);

		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<OrderRecord> findByProviderNif(String nif)
	    throws PersistenceException {

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TORDERS_FINDBYNIF");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.resultSetToOrderRecordList(rs);

		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public Optional<OrderRecord> findReceiveByCode(String code)
	    throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TORDERS_FINDRECEIVECODE");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, code);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.toOrderRecord(rs);

		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }
}
