package uo.ri.cws.application.persistence.supply;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;

public interface SupplyGateway extends Gateway<SupplyRecord> {

    public List<SupplyRecord> findByProviderId(String providerId);

    public Optional<SupplyRecord> findByProviderIdSparePartId(String providerId,
	    String sparePartId);

    public List<SupplyRecord> findBySparePartId(String sparePartId);

    public List<SupplyRecord> findBySparePartCode(String sparePartId);
    
    public static class SupplyRecord {
	public String id;
	public long version;

	public SupplierProviderRecord provider = new SupplierProviderRecord();
	public SuppliedSparePartRecord sparePart = new SuppliedSparePartRecord();

	public double price;
	public int deliveryTerm;

	public static class SupplierProviderRecord {
	    public String id;
	    public String nif;
	    public String name;
	}

	public static class SuppliedSparePartRecord {
	    public String id;
	    public String code;
	    public String description;
	}
    }

	
}
