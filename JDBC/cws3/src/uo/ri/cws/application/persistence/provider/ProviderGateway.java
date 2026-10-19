package uo.ri.cws.application.persistence.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public interface ProviderGateway extends Gateway<ProviderRecord> {

    public Optional<ProviderRecord> findProviderByNif(String nif);

    public List<ProviderRecord> findProviderByEmailPhoneName(
	    ProviderRecord record);

    public List<ProviderRecord> findByName(String name);

    public List<ProviderRecord> fndEmailPhoneNameDifNif(ProviderRecord dto);

    public List<ProviderRecord> findBySparePartCode(String code);

    public static class ProviderRecord {
	public String id;
	public String nif;
	public String name;
	public String email;
	public String phone;
	public long version;
    }

}
