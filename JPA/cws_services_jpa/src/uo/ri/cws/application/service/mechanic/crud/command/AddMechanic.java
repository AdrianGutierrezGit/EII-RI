package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;
    private MechanicRepository mr = Factories.repository.forMechanic();

    public AddMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto, "Null dto cannot be added");
        ArgumentChecks.isNotNull(dto.nif, "Invalid null nif");
        ArgumentChecks.isNotNull(dto.name, "Invalid null name");
        ArgumentChecks.isNotNull(dto.surname, "Invalid null surname");
        ArgumentChecks.isNotBlank(dto.nif, "Invalid blank nif");
        ArgumentChecks.isNotBlank(dto.name, "Invalid blank name");
        ArgumentChecks.isNotBlank(dto.surname, "Invalid blank surname");
        this.dto = dto;
    }

    @Override
    public MechanicDto execute() throws BusinessException {
        checkMechanicDoesNotExits(dto.nif);
        Mechanic m = new Mechanic(dto.nif, dto.surname, dto.name);

        mr.add(m);

        dto.id = m.getId();
        dto.version = m.getVersion();
        return dto;

    }

    private void checkMechanicDoesNotExits(String nif)
            throws BusinessException {

        Optional<Mechanic> o = mr.findByNif(nif);

        BusinessChecks.doesNotExist(o,
                "There is another mechanic with that nif");

    }

}
