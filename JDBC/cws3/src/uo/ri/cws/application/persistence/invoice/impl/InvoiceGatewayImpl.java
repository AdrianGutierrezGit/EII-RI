	package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
	try {
	    Connection connection = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TINVOICES_ADD");
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
		pst.setString(1, t.id);
		pst.setLong(2, t.number);
		pst.setDate(3, java.sql.Date.valueOf(t.date));
		pst.setDouble(4, t.vat);
		pst.setDouble(5, t.amount);
		pst.setString(6, "NOT_YET_PAID");
		pst.setLong(7, 1L);

		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new PersistenceException();
	}

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {

    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
	    throws PersistenceException {
	return null;
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
	return null;
    }

    @Override
    public Optional<InvoiceRecord> findByNumber(Long number) {
	return null;
    }

    @Override
    public Long getNextInvoiceNumber() throws PersistenceException {
	try {
	    Connection connection = Jdbc.getCurrentConnection();
	    String sql = Queries.get("TINVOICES_GETNEXTINVOICENUMBER");
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return rs.getLong(1) + 1;
		    } else {
			return 1L;
		    }
		}

	    }
	} catch (SQLException e) {
	    throw new PersistenceException();
	}
    }

}
