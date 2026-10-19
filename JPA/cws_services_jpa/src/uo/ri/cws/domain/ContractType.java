package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ContractType extends BaseEntity {

    private String name;
    private double compensationDaysPerYear;

    private Set<Contract> contracts = new HashSet<>();

    ContractType() {

    }

    public ContractType(String name, double compensationDaysPerYear) {
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isTrue(compensationDaysPerYear >= 0);
        this.name = name;
        this.compensationDaysPerYear = compensationDaysPerYear;
    }

    public String getName() {
        return name;
    }

    public double getCompensationDaysPerYear() {
        return compensationDaysPerYear;
    }

    public void setcompensationDaysPerYear(double compensationDaysPerYear) {
        this.compensationDaysPerYear = compensationDaysPerYear;
    }

    public Set<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
        return contracts;
    }

    @Override
    public String toString() {
        return "ContractType [name=" + name + ", compensationDaysPerYear="
                + compensationDaysPerYear + "]";
    }

}
