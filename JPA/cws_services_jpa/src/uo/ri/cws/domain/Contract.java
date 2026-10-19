package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Contract extends BaseEntity {
    public enum ContractState {
        IN_FORCE, TERMINATED
    }

    private LocalDate startDate;
    private LocalDate endDate;
    private double annualBaseSalary;
    private double taxRate;
    private double settlement;
    private ContractState state;

    private ProfessionalGroup professionalGroup;
    private ContractType contractType;
    private Mechanic mechanic;
    private Set<Payroll> payrolls = new HashSet<>();

    Contract() {

    }

    public Contract(Mechanic mechanic, ContractType contractType,
            ProfessionalGroup professionalGroup, LocalDate signingDate,
            double annualSalary) {
        this(mechanic, contractType, professionalGroup, signingDate, null,
                annualSalary, 0.0, 0.0, ContractState.IN_FORCE);
    }

    public Contract(Mechanic mechanic, ContractType contractType,
            ProfessionalGroup professionalGroup, LocalDate signingDate,
            LocalDate endDate, double annualSalary) {

        this(mechanic, contractType, professionalGroup, signingDate,
                calculateEndDate(contractType, endDate), annualSalary, 0.0, 0.0,
                ContractState.IN_FORCE);
    }

    public Contract(Mechanic mechanic, ContractType contractType,
            ProfessionalGroup professionalGroup, LocalDate startDate,
            LocalDate endDate, double annualBaseSalary, double taxRate,
            double settlement, ContractState state) {
        checkArguments(mechanic, contractType, professionalGroup, startDate,
                annualBaseSalary, taxRate, settlement, state);

        terminateActiveContractsOfMechanic(mechanic);
        this.mechanic = mechanic;
        this.contractType = contractType;

        checkAndInitializeEndDate(startDate, endDate);

        this.professionalGroup = professionalGroup;
        this.startDate = startDate.withDayOfMonth(1);
        this.annualBaseSalary = annualBaseSalary;
        this.taxRate = calculateTaxRate();
        this.settlement = settlement;
        this.state = state;

        Associations.Binds.link(mechanic, this);
        Associations.Categorizes.link(professionalGroup, this);
        Associations.Defines.link(contractType, this);
    }

    protected void checkAndInitializeEndDate(LocalDate startDate,
            LocalDate endDate) {
        if (isFixedTerm()) {
            ArgumentChecks.isNotNull(endDate);
        }
        if (endDate != null) {
            ArgumentChecks.isNotNull(endDate);
            ArgumentChecks.isTrue(startDate.isBefore(endDate));
            this.endDate = endDate.withDayOfMonth(endDate.lengthOfMonth());
        }
    }

    protected void checkArguments(Mechanic mechanic, ContractType contractType,
            ProfessionalGroup professionalGroup, LocalDate startDate,
            double annualBaseSalary, double taxRate, double settlement,
            ContractState state) {
        ArgumentChecks.isNotNull(mechanic);
        ArgumentChecks.isNotNull(contractType);
        ArgumentChecks.isNotNull(professionalGroup);
        ArgumentChecks.isNotNull(startDate);
        ArgumentChecks.isTrue(annualBaseSalary >= 0);
        ArgumentChecks.isTrue(taxRate >= 0);
        ArgumentChecks.isTrue(settlement >= 0);
        ArgumentChecks.isNotNull(state);
    }

    private static LocalDate calculateEndDate(ContractType contractType,
            LocalDate endDate) {
        if (contractType != null && endDate != null) {
            if (contractType.getName()
                            .equals("INDEFINITE")) {
                return null;
            }
            return endDate;
        }
        return null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getAnnualBaseSalary() {
        return annualBaseSalary;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getSettlement() {
        return settlement;
    }

    public ContractState getState() {
        return state;
    }

    public ProfessionalGroup getProfessionalGroup() {
        return professionalGroup;
    }

    void _setProfessionalGroup(ProfessionalGroup professionalGroup) {
        this.professionalGroup = professionalGroup;
    }

    public ContractType getContractType() {
        return contractType;
    }

    void _setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Set<Payroll> getPayrolls() {
        return new HashSet<>(payrolls);
    }

    Set<Payroll> _getPayrolls() {
        return payrolls;
    }

    @Override
    public String toString() {
        return "Contract [startDate=" + startDate + ", endDate=" + endDate
                + ", annualBaseSalary=" + annualBaseSalary + ", taxRate="
                + taxRate + ", settlement=" + settlement + ", state=" + state
                + ", professionalGroup=" + professionalGroup + ", contractType="
                + contractType + ", mechanic=" + mechanic + "]";
    }

    public boolean isInForce() {
        return this.state.equals(ContractState.IN_FORCE);
    }

    public void terminate(LocalDate endDate) {
        ArgumentChecks.isNotNull(endDate);
        ArgumentChecks.isTrue(this.startDate.isBefore(endDate));
        StateChecks.isTrue(isInForce(), "The contract is already terminated");
        this.endDate = endDate.withDayOfMonth(endDate.lengthOfMonth());
        this.state = ContractState.TERMINATED;
        int years = Period.between(startDate, endDate)
                          .getYears();
        if (years == 0) {
            years = 1;
        }
        this.settlement = years * contractType.getCompensationDaysPerYear()
                * calculateDailyGrossSalary();
    }

    private double calculateDailyGrossSalary() {
        double grossSalary = 0.0;
        int count = 0;
        for (Payroll p : payrolls) {
            if (count < 12) {
                grossSalary += p.getGrossSalary();
            }
            count++;
        }
        return grossSalary / 365;

    }

    public boolean isTerminated() {
        return this.state.equals(ContractState.TERMINATED);
    }

    public boolean isFixedTerm() {
        return contractType.getName()
                           .equals("FIXED_TERM");
    }

    private double calculateTaxRate() {
        double salary = this.annualBaseSalary;

        if (salary <= 12450) {
            return 0.19;
        } else if (salary <= 20200) {
            return 0.24;
        } else if (salary <= 35200) {
            return 0.30;
        } else if (salary <= 60000) {
            return 0.37;
        } else if (salary <= 300000) {
            return 0.45;
        } else {
            return 0.47;
        }
    }

    private void terminateActiveContractsOfMechanic(Mechanic mechanic) {
        for (Contract c : mechanic._getContracts()) {
            if (c.state.equals(ContractState.IN_FORCE)) {
                c.state = ContractState.TERMINATED;
            }
        }

    }

}
