package uo.ri.cws.application.service.spare.supplies.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSupply implements Command<Void> {

    private String nif;
    private String code;

    private SupplyGateway sg = Factories.persistence.forSuppply();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SparePartGateway spg = Factories.persistence.forSparePart();

    public DeleteSupply(String nif, String code) {
	ArgumentChecks.isNotBlank(nif);
	ArgumentChecks.isNotBlank(code);
	this.nif = nif;
	this.code = code;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProviderRecord> optionalProvider = pg.findProviderByNif(nif);
	BusinessChecks.exists(optionalProvider);
	ProviderRecord provider = optionalProvider.get();

	Optional<SparePartRecord> optionalSparePart = spg.findByCode(code);
	BusinessChecks.exists(optionalProvider);
	SparePartRecord sparePart = optionalSparePart.get();

	Optional<SupplyRecord> optionalSupply = sg
		.findByProviderIdSparePartId(provider.id, sparePart.id);
	BusinessChecks.exists(optionalSupply);
	SupplyRecord supply = optionalSupply.get();
	sg.remove(supply.id);
	return null;
    }

}
