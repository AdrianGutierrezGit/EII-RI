package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Payroll extends BaseEntity {

    private static final int PAYMENTS_IN_YEAR = 14;
    private static final int PAYROLLS_IN_YEAR = 12;
    private static final double STANDARD_NIC = 0.05;

    private LocalDate date;
    private double baseSalary;
    private double extraSalary;
    private double productivityEarning;
    private double trienniumEarning;
    private double taxDeduction;
    private double nicDeduction;

    private Contract contract;

    Payroll() {

    }

    public Payroll(Contract c, LocalDate payrollDate) {
        this(c, payrollDate, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public Payroll(Contract contract, LocalDate date, double baseSalary,
            double extraSalary, double productivityEarning,
            double trienniumEarning, double taxDeduction, double nicDeduction,
            double totalDeductions, double netSalary, double grossSalary) {
        checkArguments(contract, date, baseSalary, extraSalary,
                productivityEarning, trienniumEarning, taxDeduction,
                nicDeduction, totalDeductions, netSalary, grossSalary);

        this.contract = contract;
        this.date = date;
        this.baseSalary = contract.getAnnualBaseSalary() / PAYMENTS_IN_YEAR;
        this.extraSalary = calculateExtraSalary();
        this.productivityEarning = calculateProductivityEarning();
        this.trienniumEarning = calculateTrieniumEarning();
        this.nicDeduction = contract.getAnnualBaseSalary() / PAYROLLS_IN_YEAR
                * STANDARD_NIC;
        this.taxDeduction = ( contract.getTaxRate() )
                * ( calculateGrossSalary() );
        Associations.Generates.link(contract, this);
    }

    protected void checkArguments(Contract contract, LocalDate date,
            double baseSalary, double extraSalary, double productivityEarning,
            double trienniumEarning, double taxDeduction, double nicDeduction,
            double totalDeductions, double netSalary, double grossSalary) {
        ArgumentChecks.isNotNull(contract);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isTrue(baseSalary >= 0);
        ArgumentChecks.isTrue(extraSalary >= 0);
        ArgumentChecks.isTrue(productivityEarning >= 0);
        ArgumentChecks.isTrue(trienniumEarning >= 0);
        ArgumentChecks.isTrue(taxDeduction >= 0);
        ArgumentChecks.isTrue(nicDeduction >= 0);
        ArgumentChecks.isTrue(totalDeductions >= 0);
        ArgumentChecks.isTrue(netSalary >= 0);
        ArgumentChecks.isTrue(grossSalary >= 0);
        ArgumentChecks.isTrue(contract.getStartDate()
                                      .isBefore(date));
    }

    private double calculateNetSalary() {
        return calculateGrossSalary() - calculateTotalDeductions();
    }

    private double calculateTotalDeductions() {
        return this.taxDeduction + this.nicDeduction;
    }

    private double calculateGrossSalary() {
        return this.baseSalary + this.extraSalary + this.productivityEarning
                + this.trienniumEarning;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getMonthlyBaseSalary() {
        return baseSalary;
    }

    public double getExtraSalary() {
        return extraSalary;
    }

    public double getProductivityEarning() {
        return productivityEarning;
    }

    public double getTrienniumEarning() {
        return trienniumEarning;
    }

    public double getTaxDeduction() {
        return taxDeduction;
    }

    public double getNicDeduction() {
        return nicDeduction;
    }

    public double getTotalDeductions() {
        return calculateTotalDeductions();
    }

    public double getNetSalary() {
        return calculateNetSalary();
    }

    public double getGrossSalary() {
        return calculateGrossSalary();
    }

    public Contract getContract() {
        return contract;
    }

    void _setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "Payroll [date=" + date + ", baseSalary=" + baseSalary
                + ", extraSalary=" + extraSalary + ", productivityEarning="
                + productivityEarning + ", trienniumEarning=" + trienniumEarning
                + ", taxDeduction=" + taxDeduction + ", nicDeduction="
                + nicDeduction + ", contract=" + contract + "]";
    }

    private double calculateExtraSalary() {
        if (date.getMonth()
                .equals(Month.JUNE)
                || date.getMonth()
                       .equals(Month.DECEMBER)) {
            return this.baseSalary;
        }
        return 0.0;
    }

    private double calculateTrieniumEarning() {
        LocalDate currentDate = contract.getEndDate() != null
                ? contract.getEndDate()
                : this.date;
        int numTrieniums = Period.between(contract.getStartDate(), currentDate)
                                 .getYears()
                / 3;
        return numTrieniums * contract.getProfessionalGroup()
                                      .getTrienniumPayment();
    }

    private double calculateProductivityEarning() {
        double productivityRate = contract.getProfessionalGroup()
                                          .getProductivityRate();
        double amount = 0.0;
        for (Intervention i : contract.getMechanic()
                                      .getInterventions()) {
            amount += i.getWorkOrder()
                       .getAmount();
        }
        return productivityRate * amount;
    }

}
