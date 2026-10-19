package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
        implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
        return Jpa.getManager()
                  .createNamedQuery("Payroll.findByContract", Payroll.class)
                  .setParameter(1, contractId)
                  .getResultList();
    }

    @Override
    public List<Payroll> findLastMonthPayrolls() {
        LocalDate previusMonth = LocalDate.now()
                                          .minusMonths(1);
        LocalDate lastDay = previusMonth.withDayOfMonth(
                previusMonth.lengthOfMonth());
        LocalDate firstDay = previusMonth.withDayOfMonth(1);
        return Jpa.getManager()
                  .createNamedQuery("Payroll.findLastMonthPayrolls",
                          Payroll.class)
                  .setParameter(1, firstDay)
                  .setParameter(2, lastDay)
                  .getResultList();
    }

    @Override
    public Optional<Payroll> findLastPayrollByMechanicId(String mechanicId) {
        LocalDate previusMonth = LocalDate.now()
                                          .minusMonths(1);
        LocalDate lastDay = previusMonth.withDayOfMonth(
                previusMonth.lengthOfMonth());
        LocalDate firstDay = previusMonth.withDayOfMonth(1);
        return Jpa.getManager()
                  .createNamedQuery("Payroll.findLastPayrollByMechanicId",
                          Payroll.class)
                  .setParameter(1, mechanicId)
                  .setParameter(2, firstDay)
                  .setParameter(3, lastDay)
                  .getResultStream()
                  .findFirst();
    }

    @Override
    public List<Payroll> findByProfessionalGroupName(String name) {
        return Jpa.getManager()
                  .createNamedQuery("Payroll.findByProfessionalGroupName",
                          Payroll.class)
                  .setParameter(1, name)
                  .getResultList();
    }

    @Override
    public List<Payroll> findByMechanicId(String mId) {
        return Jpa.getManager()
                  .createNamedQuery("Payroll.findByMechanicId", Payroll.class)
                  .setParameter(1, mId)
                  .getResultList();
    }

    @Override
    public Optional<Payroll> findByContractIdAndDate(String id,
            LocalDate date) {
        return null;
    }

}
