package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup extends BaseEntity {
    private String name;
    private double trienniumPayment;
    private double productivityRate;

    private Set<Contract> contracts = new HashSet<>();

    ProfessionalGroup() {

    }

    public ProfessionalGroup(String name, double trienniumPayment,
            double productivityRate) {
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isTrue(trienniumPayment >= 0);
        ArgumentChecks.isTrue(productivityRate >= 0);
        this.name = name;
        this.trienniumPayment = trienniumPayment;
        this.productivityRate = productivityRate;
    }

    public String getName() {
        return name;
    }

    public double getTrienniumPayment() {
        return trienniumPayment;
    }

    public double getProductivityRate() {
        return productivityRate;
    }

    public Set<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
        return contracts;
    }

    @Override
    public String toString() {
        return "ProfessionalGroup [name=" + name + ", trieniumPayment="
                + trienniumPayment + ", productivityRate=" + productivityRate
                + "]";
    }

}
