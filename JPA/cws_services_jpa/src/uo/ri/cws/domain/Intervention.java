package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Intervention extends BaseEntity {
    // natural attributes
    private LocalDateTime date;
    private int minutes;

    // accidental attributes
    private WorkOrder workOrder;
    private Mechanic mechanic;
    private Set<Substitution> substitutions = new HashSet<>();

    Intervention() {

    }

    public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
        this(mechanic, workOrder, LocalDateTime.now(), minutes);
    }

    public Intervention(Mechanic mechanic, WorkOrder workOrder,
            LocalDateTime date, int minutes) {
        ArgumentChecks.isNotNull(mechanic);
        ArgumentChecks.isNotNull(workOrder);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isTrue(minutes >= 0);
        this.date = date.truncatedTo(ChronoUnit.MILLIS);
        this.minutes = minutes;
        Associations.Intervenes.link(workOrder, this, mechanic);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getMinutes() {
        return minutes;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    void _setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Set<Substitution> getSubstitutions() {
        return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
        return substitutions;
    }

    @Override
    public String toString() {
        return "Intervention [date=" + date + ", minutes=" + minutes
                + ", workOrder=" + workOrder + ", mechanic=" + mechanic + "]";
    }

    public double getAmount() {
        double substitutionsAmount = calculateSubstitutionsAmount();
        double price = this.minutes * ( workOrder.getVehicle()
                                                 .getVehicleType()
                                                 .getPricePerHour()
                / 60 );
        return substitutionsAmount + price;
    }

    private double calculateSubstitutionsAmount() {
        double amount = 0.0;
        for (Substitution s : substitutions) {
            amount += s.getAmount();
        }
        return amount;
    }

}
