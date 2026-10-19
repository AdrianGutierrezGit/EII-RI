package uo.ri.cws.application.service.sparepart.crud;

import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;

public class DtoAssembler {
	public static SparePartRecord toSparePartRecord(SparePartDto dto) {
		SparePartRecord record = new SparePartRecord();
		record.id = dto.id;
		record.version = dto.version;
		record.code = dto.code;
		record.description = dto.description;
		record.price = dto.price;
		record.stock = dto.stock;
		record.minStock = dto.minStock;
		record.maxStock = dto.maxStock;
		return record;
	}

	public static SparePartDto toSparePartRecord(SparePartRecord record) {
		SparePartDto dto = new SparePartDto();
		dto.id = record.id;
		dto.version = record.version;
		dto.code = record.code;
		dto.description = record.description;
		dto.price = record.price;
		dto.stock = record.stock;
		dto.minStock = record.minStock;
		dto.maxStock = record.maxStock;
		return dto;
	}
}
