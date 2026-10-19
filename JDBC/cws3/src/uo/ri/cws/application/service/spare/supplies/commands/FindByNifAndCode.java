package uo.ri.cws.application.service.spare.supplies.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SuppliedSparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SupplierProviderRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supplies.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNifAndCode implements Command<Optional<SupplyDto>> {

    private String nif;
    private String code;
    private SupplyGateway sg = Factories.persistence.forSuppply();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SparePartGateway spg = Factories.persistence.forSparePart();

    public FindByNifAndCode(String nif, String code) {
	ArgumentChecks.isNotBlank(nif);
	ArgumentChecks.isNotBlank(code);
	this.nif = nif;
	this.code = code;
    }

    @Override
    public Optional<SupplyDto> execute() throws BusinessException {
	Optional<ProviderRecord> optionalProvider = pg.findProviderByNif(nif);
	BusinessChecks.exists(optionalProvider);
	ProviderRecord provider = optionalProvider.get();

	Optional<SparePartRecord> optionalSparePart = spg.findByCode(code);
	BusinessChecks.exists(optionalSparePart);
	SparePartRecord sparePart = optionalSparePart.get();

	Optional<SupplyRecord> optionalSupply = sg
		.findByProviderIdSparePartId(provider.id, sparePart.id);

	if (optionalSupply.isEmpty()) {
	    return Optional.empty();
	}

	SupplyRecord supply = optionalSupply.get();

	supply.sparePart = toSuppliedSpare(sparePart);
	supply.provider = toSuppliedProvider(provider);

	SupplyDto dto = DtoAssembler.toSupplyDto(supply);
	Optional<SupplyDto> optionalDto = Optional.of(dto);
	return optionalDto;

    }

    private SuppliedSparePartRecord toSuppliedSpare(SparePartRecord spare) {
	SuppliedSparePartRecord s = new SuppliedSparePartRecord();
	s.code = spare.code;
	s.description = spare.description;
	s.id = spare.id;
	return s;
    }

    private SupplierProviderRecord toSuppliedProvider(ProviderRecord provider) {
	SupplierProviderRecord p = new SupplierProviderRecord();
	p.id = provider.id;
	p.name = provider.name;
	p.nif = provider.nif;
	return p;
    }

}
