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

public class UpdateSupply implements Command<Void> {

    private SupplyDto dto;
    private SparePartGateway spg = Factories.persistence.forSparePart();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SupplyGateway sg = Factories.persistence.forSuppply();

    public UpdateSupply(SupplyDto dto) throws BusinessException {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotNull(dto.sparePart);
	ArgumentChecks.isNotNull(dto.provider);
	BusinessChecks.isTrue(dto.price >= 0);
	BusinessChecks.isTrue(dto.deliveryTerm >= 0);
	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {

	Optional<SparePartRecord> optionalSparePart = spg
		.findById(dto.sparePart.id);
	BusinessChecks.exists(optionalSparePart);

	Optional<ProviderRecord> optionalProvider = pg
		.findById(dto.provider.id);
	BusinessChecks.exists(optionalProvider);
	BusinessChecks.hasVersion(0, 0);
	Optional<SupplyRecord> record = sg.findById(dto.id);
	BusinessChecks.exists(record);
	BusinessChecks.hasVersion(dto.version, record.get().version);
	sg.update(DtoAssembler.toSupplyRecord(dto));
	return null;
    }

}
