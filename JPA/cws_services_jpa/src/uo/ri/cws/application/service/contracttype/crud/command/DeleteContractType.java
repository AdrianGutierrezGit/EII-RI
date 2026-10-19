package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {

    private String name;
    private ContractTypeRepository ctr = Factories.repository.forContractType();
    private ContractRepository cr = Factories.repository.forContract();

    public DeleteContractType(String name) {
        ArgumentChecks.isNotNull(name, "The name is null");
        ArgumentChecks.isNotEmpty(name, "The name is empty");
        ArgumentChecks.isNotBlank(name, "The name is blank");
        this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
        ContractType ct = checkExits();
        checkContractsRegisteredWithThisContractType();
        ctr.remove(ct);
        return null;
    }

    private void checkContractsRegisteredWithThisContractType()
            throws BusinessException {
        BusinessChecks.isTrue(cr.findAll()
                                .stream()
                                .allMatch(c -> !c.getContractType()
                                                 .getName()
                                                 .equals(this.name)),
                "There is a contract with this contract type");
    }

    private ContractType checkExits() throws BusinessException {
        Optional<ContractType> o = ctr.findByName(name);
        BusinessChecks.exists(o, "Contract type does not exist");
        return o.get();
    }

}
