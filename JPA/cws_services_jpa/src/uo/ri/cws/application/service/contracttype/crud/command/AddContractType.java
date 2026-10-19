package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContractType implements Command<ContractTypeDto> {

    private ContractTypeDto dto;
    private ContractTypeRepository ctr = Factories.repository.forContractType();

    public AddContractType(ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto, "The argument is null");
        ArgumentChecks.isNotNull(dto.name,
                "The name of the contract type is null");
        ArgumentChecks.isNotEmpty(dto.name,
                "The name of the contract type is empty");
        ArgumentChecks.isNotBlank(dto.name,
                "The name of the contract type is blank");
        ArgumentChecks.isTrue(dto.compensationDays >= 0,
                "The number of compensation days is negative");
        this.dto = dto;
    }

    @Override
    public ContractTypeDto execute() throws BusinessException {
        checkContractTypeDoesNotExits(dto.name);
        ContractType ct = new ContractType(dto.name, dto.compensationDays);
        ctr.add(ct);
        dto.id = ct.getId();
        dto.version = ct.getVersion();
        return dto;
    }

    private void checkContractTypeDoesNotExits(String name)
            throws BusinessException {
        Optional<ContractType> o = ctr.findByName(name);
        BusinessChecks.doesNotExist(o,
                "There is another contract type with that name");
    }

}
