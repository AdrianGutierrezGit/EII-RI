package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class WorkOrder extends BaseEntity {

    public enum WorkOrderState {
        OPEN, ASSIGNED, FINISHED, INVOICED
    }

    // natural attributes
    private LocalDateTime date;
    private String description;
    private double amount = 0.0;

    private WorkOrderState state = WorkOrderState.OPEN;

    // accidental attributes
    private Vehicle vehicle;
    private Mechanic mechanic;
    private Invoice invoice;
    private Set<Intervention> interventions = new HashSet<>();

    WorkOrder() {

    }

    public WorkOrder(Vehicle vehicle) {
        this(vehicle, "no-description");
    }

    public WorkOrder(Vehicle vehicle, String description) {
        this(vehicle, LocalDateTime.now(), description);
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date) {
        this(vehicle, date, "no-description");
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
        ArgumentChecks.isNotNull(vehicle);
        ArgumentChecks.isNotBlank(description);

        this.date = date.truncatedTo(ChronoUnit.MILLIS);
        this.description = description;
        Associations.Fixes.link(vehicle, this);

    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public WorkOrderState getState() {
        return state;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public String toString() {
        return "WorkOrder [date=" + date + ", description=" + description
                + ", amount=" + amount + ", state=" + state + ", vehicle="
                + vehicle + "]";
    }

    /**
     * Changes it to INVOICED state given the right conditions This method is
     * called from Invoice.addWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not FINISHED, or -
     *                               The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
        StateChecks.isTrue(isFinished(),
                "The work order is not in FINISHED state");
        StateChecks.isTrue(this.invoice != null,
                "The work order is not" + " linked with the invoice");
        this.state = WorkOrderState.INVOICED;
    }

    /**
     * Given the right conditions unlinks the workorder and the mechanic,
     * changes the state to FINISHED and computes the amount
     *
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state, or
     */
    public void markAsFinished() {
        StateChecks.isTrue(isAssigned(),
                "The work order is not in ASSIGNED state");
        mechanic._getAssigned()
                .remove(this);
        this.mechanic = null;
        this.amount = calculateInterventionsAmount();
        this.state = WorkOrderState.FINISHED;
    }

    private double calculateInterventionsAmount() {
        double amount = 0.0;
        for (Intervention i : interventions) {
            amount += i.getAmount();
        }
        return amount;
    }

    /**
     * Changes it back to FINISHED state given the right conditions This method
     * is called from Invoice.removeWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not INVOICED, or
     */
    public void markBackToFinished() {
        StateChecks.isTrue(isInvoiced(),
                "The work order is not in INVOICED state");
        this.invoice = null;
        this.state = WorkOrderState.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its state
     * to ASSIGNED
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in OPEN state,
     *                               or
     */
    public void assignTo(Mechanic mechanic) {
        ArgumentChecks.isNotNull(mechanic);
        StateChecks.isTrue(isOpen(), "The work order is not in OPEN state");
        this.mechanic = mechanic;
        this.mechanic._getAssigned()
                     .add(this);
        this.state = WorkOrderState.ASSIGNED;
    }

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes its
     * state back to OPEN
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state
     */
    public void unassign() {
        StateChecks.isTrue(isAssigned(),
                "The work order is not in ASSIGNED state");
        this.mechanic._getAssigned()
                     .remove(this);
        this.mechanic = null;
        this.state = WorkOrderState.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic it first have to be
     * moved back to OPEN state.
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in FINISHED
     *                               state
     */
    public void reopen() {
        StateChecks.isTrue(isFinished(),
                "The work order is not in finished state");
        this.state = WorkOrderState.OPEN;
    }

    public Set<Intervention> getInterventions() {
        return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
        return interventions;
    }

    void _setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    void _setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public boolean isFinished() {
        return state.equals(WorkOrderState.FINISHED);
    }

    public boolean isAssigned() {
        return state.equals(WorkOrderState.ASSIGNED);
    }

    public boolean isOpen() {
        return state.equals(WorkOrderState.OPEN);
    }

    public boolean isInvoiced() {
        return state.equals(WorkOrderState.INVOICED);
    }

}
