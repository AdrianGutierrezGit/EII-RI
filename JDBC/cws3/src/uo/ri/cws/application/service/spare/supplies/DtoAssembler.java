package uo.ri.cws.application.service.spare.supplies;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SuppliedSparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SupplierProviderRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto.SuppliedSparePartDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto.SupplierProviderDto;

public class DtoAssembler {

    public static SupplyRecord toSupplyRecord(SupplyDto dto) {
	SupplyRecord record = new SupplyRecord();
	record.id = dto.id;
	record.version = dto.version;
	record.provider = toSupplierProviderRecord(dto.provider);
	record.sparePart = toSuppliedSparePartRecord(dto.sparePart);
	record.price = dto.price;
	record.deliveryTerm = dto.deliveryTerm;
	return record;
    }

    public static SuppliedSparePartRecord toSuppliedSparPartRecord(
	    SparePartRecord sparePart) {
	SuppliedSparePartRecord r = new SuppliedSparePartRecord();
	r.id = sparePart.id;
	r.description = sparePart.description;
	r.code = sparePart.code;
	return r;
    }

    private static SuppliedSparePartRecord toSuppliedSparePartRecord(
	    SuppliedSparePartDto dto) {
	SuppliedSparePartRecord spare = new SuppliedSparePartRecord();
	spare.id = dto.id;
	spare.code = dto.code;
	spare.description = dto.description;

	return spare;
    }

    private static SupplierProviderRecord toSupplierProviderRecord(
	    SupplierProviderDto dto) {
	SupplierProviderRecord supplier = new SupplierProviderRecord();
	supplier.id = dto.id;
	supplier.nif = dto.nif;
	supplier.name = dto.name;
	return supplier;
    }

    public static SupplyDto toSupplyDto(SupplyRecord record) {
	SupplyDto dto = new SupplyDto();
	dto.id = record.id;
	dto.version = record.version;
	dto.provider = toSupplierProviderDto(record.provider);
	dto.sparePart = toSuppliedSparePartDto(record.sparePart);
	dto.deliveryTerm = record.deliveryTerm;
	dto.price = record.price;
	return dto;
    }

    private static SuppliedSparePartDto toSuppliedSparePartDto(
	    SuppliedSparePartRecord record) {
	SuppliedSparePartDto spare = new SuppliedSparePartDto();
	spare.id = record.id;
	spare.code = record.code;
	spare.description = record.description;
	return spare;
    }

    private static SupplierProviderDto toSupplierProviderDto(
	    SupplierProviderRecord supplier) {
	SupplierProviderDto dto = new SupplierProviderDto();
	dto.id = supplier.id;
	dto.nif = supplier.nif;
	dto.name = supplier.name;
	return dto;
    }

    public static SupplierProviderRecord toSupplierProviderRecord(
	    ProviderRecord r) {
	SupplierProviderRecord rs = new SupplierProviderRecord();
	rs.id = r.id;
	rs.name = r.name;
	rs.nif = r.nif;
	return rs;
    }

    public static List<SupplyDto> toSupplyDtoList(
	    List<SupplyRecord> listaRecord) {
	List<SupplyDto> lista = new ArrayList<SupplyDto>();
	for (SupplyRecord r : listaRecord) {
	    lista.add(toSupplyDto(r));
	}
	return lista;

    }

}
