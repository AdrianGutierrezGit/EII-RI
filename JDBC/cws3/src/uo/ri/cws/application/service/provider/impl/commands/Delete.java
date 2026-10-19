package uo.ri.cws.application.service.provider.impl.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class Delete implements Command<Void> {

    private String nif;
    private ProviderGateway pg = Factories.persistence.forProvider();
    private OrderGateway og = Factories.persistence.forOrder();
    private SupplyGateway sp = Factories.persistence.forSuppply();

    public Delete(String nif) {
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProviderRecord> optionalRecord = pg.findProviderByNif(nif);
	BusinessChecks.exists(optionalRecord);
	if (optionalRecord.isEmpty())
	    return null;
	ProviderRecord record = optionalRecord.get();

	List<OrderRecord> orders = og.findByProviderId(record.id);
	BusinessChecks.isTrue(orders.size() == 0);
	List<SupplyRecord> suppl = sp.findByProviderId(record.id);

	BusinessChecks.isTrue(suppl.size() == 0);
	pg.remove(record.id);
	return null;
    }

}
