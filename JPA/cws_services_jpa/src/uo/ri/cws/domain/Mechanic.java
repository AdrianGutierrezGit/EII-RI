package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic extends BaseEntity {
    // natural attributes
    private String nif;
    private String surname;
    private String name;

    // accidental attributes
    private Set<WorkOrder> assigned = new HashSet<>();
    private Set<Intervention> interventions = new HashSet<>();
    private Set<Contract> contracts = new HashSet<>();

    Mechanic() {

    }

    public Mechanic(String nif) {
        this(nif, "no-surname", "no-name");
    }

    public Mechanic(String nif, String surname, String name) {
        ArgumentChecks.isNotBlank(nif);
        ArgumentChecks.isNotBlank(surname);
        ArgumentChecks.isNotBlank(name);
        this.nif = nif;
        this.surname = surname;
        this.name = name;
    }

    public Set<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

    Set<Contract> _getContracts() {
        return contracts;
    }

    public Set<WorkOrder> getAssigned() {
        return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
        return assigned;
    }

    public Set<Intervention> getInterventions() {
        return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
        return interventions;
    }

    public String getNif() {
        return nif;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Mechanic [nif=" + nif + ", surname=" + surname + ", name="
                + name + "]";
    }

    public Optional<Contract> getContractInForce() {
        for (Contract c : contracts) {
            if (c.getState()
                 .equals(ContractState.IN_FORCE)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public void setName(String name) {
        ArgumentChecks.isNotBlank(name, "Invalid blank argument");
        this.name = name;

    }

    public void setSurname(String surname) {
        ArgumentChecks.isNotBlank(surname, "Invalid blank argument");
        this.surname = surname;

    }

}
