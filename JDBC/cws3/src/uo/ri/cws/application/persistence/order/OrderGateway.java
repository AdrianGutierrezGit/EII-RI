package uo.ri.cws.application.persistence.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public interface OrderGateway extends Gateway<OrderRecord> {

    Optional<OrderRecord> findByCode(String code) throws PersistenceException;

    List<OrderRecord> findByProviderId(String id) throws PersistenceException;

    List<OrderRecord> findByProviderNif(String nif) throws PersistenceException;

    Optional<OrderRecord> findReceiveByCode(String code)
	    throws PersistenceException;

    public static class OrderRecord {
	public String id;
	public long version;

	public String code;
	public LocalDate orderedDate;
	public LocalDate receptionDate;
	public double amount;
	public String state;
	public String provider_id;

    }

    public static class OrderedSpareRecord {
	public String id;
	public String code;
	public String description;
    }

    public static class OrderedProviderRecord {
	public String id;
	public String nif;
	public String name;
	public String email;
	public String phone;
	public long version;
    }
}
