package uo.ri.cws.application.service.sparepart.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.sparepart.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCodeSparePart implements Command<Optional<SparePartDto>> {
	private String code;
	private SparePartGateway spg = Factories.persistence.forSparePart();

	public FindByCodeSparePart(String code) {
		ArgumentChecks.isNotBlank(code);
		this.code = code;
	}

	@Override
	public Optional<SparePartDto> execute() throws BusinessException {
		Optional<SparePartRecord> existingSparePart = spg.findByCode(code);
		if (!existingSparePart.isPresent()) {
			return Optional.empty();
		}
		Optional<SparePartDto> optionalDto = Optional.of(DtoAssembler.toSparePartRecord(existingSparePart.get()));
		return optionalDto;
	}

}
