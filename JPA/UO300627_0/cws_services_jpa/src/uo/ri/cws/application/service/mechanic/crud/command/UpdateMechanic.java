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

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicRepository mr = Factories.repository.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto, "Null dto cannot be added");
        ArgumentChecks.isNotNull(dto.id, "Invalid null id");
        ArgumentChecks.isNotNull(dto.nif, "Invalid null nif");
        ArgumentChecks.isNotNull(dto.name, "Invalid null name");
        ArgumentChecks.isNotNull(dto.surname, "Invalid null surname");
        ArgumentChecks.isNotBlank(dto.id, "Invalid blank id");
        ArgumentChecks.isNotBlank(dto.nif, "Invalid blank nif");
        ArgumentChecks.isNotBlank(dto.name, "Invalid blank name");
        ArgumentChecks.isNotBlank(dto.surname, "Invalid blank surname");
        this.dto = dto;
    }

    public Void execute() throws BusinessException {
        Mechanic m = checkExits(dto.id);
        BusinessChecks.hasVersion(dto.version, m.getVersion());
        m.setName(dto.name);
        m.setSurname(dto.surname);
        m.updatedNow();
        return null;
    }

    private Mechanic checkExits(String mechanicId) throws BusinessException {
        Optional<Mechanic> o = mr.findById(mechanicId);
        BusinessChecks.exists(o, "Mechanic does not exist");
        return o.get();

    }

}
