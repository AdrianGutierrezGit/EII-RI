package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractJpaRepository extends BaseJpaRepository<Contract>
        implements ContractRepository {

    @Override
    public List<Contract> findAll() {
        return Jpa.getManager()
                  .createNamedQuery("Contract.findAll", Contract.class)
                  .getResultList();
    }

    @Override
    public List<Contract> findAllInForce() {
        return Jpa.getManager()
                  .createNamedQuery("Contract.findAllInForce", Contract.class)
                  .getResultList();
    }

    @Override
    public List<Contract> findByMechanicId(String id) {
        return Jpa.getManager()
                  .createNamedQuery("Contract.findByMechanicId", Contract.class)
                  .setParameter(1, id)
                  .getResultList();
    }

    @Override
    public List<Contract> findByProfessionalGroupId(String id) {
        return null;
    }

    @Override
    public List<Contract> findByContractTypeId(String id) {
        return null;
    }

    @Override
    public List<Contract> findAllInForceThisMonth(LocalDate present) {
        LocalDate previusMonth = LocalDate.now()
                                          .minusMonths(1);
        LocalDate lastDay = previusMonth.withDayOfMonth(
                previusMonth.lengthOfMonth());
        LocalDate firstDay = previusMonth.withDayOfMonth(1);
        return Jpa.getManager()
                  .createNamedQuery("Contract.findAllInForceThisMonth",
                          Contract.class)
                  .setParameter(1, firstDay)
                  .setParameter(2, lastDay)
                  .getResultList();
    }

    @Override
    public List<Contract> findInforceContracts() {
        return null;
    }

}
