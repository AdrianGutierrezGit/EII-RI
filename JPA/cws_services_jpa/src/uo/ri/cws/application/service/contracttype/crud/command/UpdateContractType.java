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

public class UpdateContractType implements Command<Void> {

    private ContractTypeDto dto;
    private ContractTypeRepository ctr = Factories.repository.forContractType();

    public UpdateContractType(ContractTypeDto dto) {
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
    public Void execute() throws BusinessException {
        ContractType ct = checkExits();
        BusinessChecks.hasVersion(dto.version, ct.getVersion());
        ct.setcompensationDaysPerYear(dto.compensationDays);
        ct.updatedNow();
        return null;
    }

    private ContractType checkExits() throws BusinessException {
        Optional<ContractType> o = ctr.findByName(dto.name);
        BusinessChecks.exists(o, "Contract type does not exist");
        return o.get();
    }
}
