package uo.ri.cws.application.service.sparepartreport;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.util.exception.BusinessException;

public class SparePartReportServiceImpl implements SparePartReportService {

    @Override
    public Optional<SparePartReportDto> findByCode(String code)
	    throws BusinessException {

	return null;
    }

    @Override
    public List<SparePartReportDto> findByDescription(String description)
	    throws BusinessException {

	return null;
    }

    @Override
    public List<SparePartReportDto> findUnderStock() throws BusinessException {

	return null;
    }

}
