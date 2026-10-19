package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public void add(InterventionRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {

    }

    @Override
    public Optional<InterventionRecord> findById(String id)
	    throws PersistenceException {
	return null;
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
	return null;
    }

    @Override
    public List<InterventionRecord> findByMechanicId(String id)
	    throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TINTERVENTIONS_FINDBYMECHANICID");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    return RecordAssembler.toInterventionRecordList(rs);
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
    }

}
