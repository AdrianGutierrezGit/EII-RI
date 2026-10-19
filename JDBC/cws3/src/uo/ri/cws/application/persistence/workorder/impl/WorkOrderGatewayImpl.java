package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();

	    String sql = Queries.get("TWORKORDERS_UPDATESTATEANDLINK");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.state);
		pst.setString(2, t.invoiceId);
		pst.setString(3, t.id);
		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public Optional<WorkOrderRecord> findById(String id)
	    throws PersistenceException {

	return Optional.empty();
    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {

	return null;
    }

    @Override
    public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries
		    .get("TWORKORDERS_FIND_NOT_INVOICED_BY_CLIENT_NIF");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.toWorkOrderRecordList(rs);
		}

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<WorkOrderRecord> findByIds(List<String> workOrderIds) {
	List<WorkOrderRecord> lista = new ArrayList<WorkOrderRecord>();
	try {
	    Connection connection = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TWORKORDERS_FINDBYID");
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
		for (String id : workOrderIds) {
		    pst.setString(1, id);
		    try (ResultSet rs = pst.executeQuery()) {
			if (!rs.next())
			    return null;
			else
			    lista.add(RecordAssembler.toWorkOrderRecord(rs));
		    }

		}
	    }
	    return lista;
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<WorkOrderRecord> findByVehicleId(String id) {

	return null;
    }

    @Override
    public List<WorkOrderRecord> findByMechanicId(String id) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TWORKORDERS_FINDBYMECHANICID");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.toWorkOrderRecordList(rs);
		}

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

    @Override
    public List<WorkOrderRecord> findByState(String state) {

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TWORKORDER_FINDBYSTATE");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, state);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.toWorkOrderRecordList(rs);
		}

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

}
