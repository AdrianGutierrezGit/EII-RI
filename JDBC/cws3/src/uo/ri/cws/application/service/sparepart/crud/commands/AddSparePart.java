package uo.ri.cws.application.service.sparepart.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.sparepart.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSparePart implements Command<SparePartDto>{
	
	 private SparePartGateway spg = Factories.persistence.forSparePart();
	 private SparePartDto newDto;
	 
	
	public AddSparePart(SparePartDto dto) throws BusinessException
	{
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.code);
		ArgumentChecks.isNotBlank(dto.description);
		
		
		BusinessChecks.isTrue(dto.maxStock>=0);
		BusinessChecks.isTrue(dto.minStock>=0);
		BusinessChecks.isTrue(dto.stock>=0);
		BusinessChecks.isTrue(dto.price>=0);
		newDto = dto;
		
	}
	
	@Override
	public SparePartDto execute() throws BusinessException {
		
		Optional<SparePartRecord> posibleDto =spg.findByCode(newDto.code);
		BusinessChecks.doesNotExist(posibleDto,"This spare part already exists");
		newDto.id = UUID.randomUUID().toString();
		newDto.version = 1L;
		
		SparePartRecord r = DtoAssembler.toSparePartRecord(newDto);
		
		spg.add(r);
		return newDto;
	}

}
