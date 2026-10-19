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

public class UpdateSparePart implements Command<Void> {

	private SparePartGateway spg = Factories.persistence.forSparePart();

	private SparePartDto dtoToUpdate;

	public UpdateSparePart(SparePartDto dto) throws BusinessException {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.description);
		ArgumentChecks.isNotBlank(dto.code);

		BusinessChecks.isTrue(dto.maxStock >= 0);
		BusinessChecks.isTrue(dto.minStock >= 0);
		BusinessChecks.isTrue(dto.stock >= 0);
		BusinessChecks.isTrue(dto.price >= 0);
		dtoToUpdate = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		// Comprobar que el sparePart existe
		Optional<SparePartRecord> existingDto = spg.findByCode(dtoToUpdate.code);
		BusinessChecks.exists(existingDto, "The sparepart doesn't exist");
		
		BusinessChecks.isTrue(existingDto.get().code == dtoToUpdate.code,"Can not change a code of a sparePart");
		BusinessChecks.hasVersion(existingDto.get().version, dtoToUpdate.version);
		SparePartRecord record = DtoAssembler.toSparePartRecord(dtoToUpdate);
		spg.update(record);

		return null;
	}

}
