package uo.ri.cws.application.service.spare.supplies.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supplies.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSupply implements Command<SupplyDto> {

    private SupplyDto dto;

    private ProviderGateway pg = Factories.persistence.forProvider();
    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SupplyGateway sg = Factories.persistence.forSuppply();

    public AddSupply(SupplyDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isTrue(dto.sparePart != null || dto.provider != null);
	this.dto = dto;
    }

    @Override
    public SupplyDto execute() throws BusinessException {
	SupplyRecord record = DtoAssembler.toSupplyRecord(dto);
	Optional<ProviderRecord> provider = pg.findById(record.provider.id);

	Optional<SparePartRecord> sparePart = spg.findById(record.provider.id);
	BusinessChecks.exists(provider);
	BusinessChecks.exists(sparePart);
	Optional<SupplyRecord> repeated = sg.findById(record.id);
	BusinessChecks.doesNotExist(repeated);

	sg.add(record);

	return DtoAssembler.toSupplyDto(record);
    }

}
