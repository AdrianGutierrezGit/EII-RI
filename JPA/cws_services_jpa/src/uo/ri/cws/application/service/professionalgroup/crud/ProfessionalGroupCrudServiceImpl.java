package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.util.exception.BusinessException;

public class ProfessionalGroupCrudServiceImpl
        implements ProfessionalGroupCrudService {

    @Override
    public ProfessionalGroupDto create(ProfessionalGroupDto dto)
            throws BusinessException {
        return null;
    }

    @Override
    public void delete(String name) throws BusinessException {

    }

    @Override
    public void update(ProfessionalGroupDto dto) throws BusinessException {

    }

    @Override
    public Optional<ProfessionalGroupDto> findByName(String id)
            throws BusinessException {
        return Optional.empty();
    }

    @Override
    public List<ProfessionalGroupDto> findAll() throws BusinessException {
        return null;
    }

}
