package uo.ri.cws.application.service.provider.impl;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;

public class DtoAssembler {
    public static ProviderRecord toOrderedProviderRecord(ProviderDto dto) {
	ProviderRecord record = new ProviderRecord();
	record.id = dto.id;
	record.nif = dto.nif;
	record.name = dto.name;
	record.email = dto.email;
	record.phone = dto.phone;
	record.version = dto.version;
	return record;
    }

    public static ProviderDto toOrderedProviderDto(ProviderRecord record) {
	ProviderDto dto = new ProviderDto();
	dto.id = record.id;
	dto.nif = record.nif;
	dto.name = record.name;
	dto.email = record.email;
	dto.phone = record.phone;
	dto.version = record.version;
	return dto;
    }

    public static List<ProviderDto> toOrderedProviderDtoList(
	    List<ProviderRecord> recordList) {
	List<ProviderDto> lista = new ArrayList<ProviderDto>();
	for (ProviderRecord record : recordList) {
	    ProviderDto dto = new ProviderDto();
	    dto.id = record.id;
	    dto.nif = record.nif;
	    dto.name = record.name;
	    dto.email = record.email;
	    dto.phone = record.phone;
	    dto.version = record.version;
	    lista.add(dto);
	}
	return lista;
    }
}
