package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Invoice extends BaseEntity {
    public enum InvoiceState {
        NOT_YET_PAID, PAID
    }

    public static final double PAST_VAT_RATE = 0.18;
    public static final double CURRENT_VAT_RATE = 0.21;

    // natural attributes
    private Long number;
    private LocalDate date;
    private double amount;
    private double vat;
    private InvoiceState state = InvoiceState.NOT_YET_PAID;

    // accidental attributes
    private Set<WorkOrder> workOrders = new HashSet<>();
    private Set<Charge> charges = new HashSet<>();

    Invoice() {

    }

    public Invoice(Long number) {
        this(number, LocalDate.now(), new ArrayList<WorkOrder>());
    }

    public Invoice(Long number, LocalDate date) {
        this(number, date, new ArrayList<WorkOrder>());
    }

    public Invoice(Long number, List<WorkOrder> workOrders) {
        this(number, LocalDate.now(), workOrders);
    }

    // full constructor
    public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
        ArgumentChecks.isNotNull(number);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isNotNull(workOrders);
        this.date = date;
        this.number = number;
        for (WorkOrder wo : workOrders) {
            addWorkOrder(wo);
        }
    }

    /**
     * Computes amount and vat (vat depends on the date)
     */
    private void computeAmount() {
        double workOrdersAmount = calculateWorkOrdersAmount();
        double vatRate = calculateVatRate();
        this.vat = workOrdersAmount * vatRate;
        this.amount = workOrdersAmount + this.vat;

    }

    private double calculateVatRate() {
        if (this.date.isBefore(LocalDate.of(2012, 07, 01))) {
            return PAST_VAT_RATE;
        } else {
            return CURRENT_VAT_RATE;
        }
    }

    private double calculateWorkOrdersAmount() {
        double amount = 0.0;
        for (WorkOrder w : workOrders) {
            amount += w.getAmount();
        }
        return amount;
    }

    /**
     * Adds (double links) the workOrder to the invoice and updates the amount
     * and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     * @throws IllegalStateException if the workorder status is not FINISHED
     */
    public void addWorkOrder(WorkOrder workOrder) {
        ArgumentChecks.isNotNull(workOrder);
        StateChecks.isTrue(this.state.equals(InvoiceState.NOT_YET_PAID),
                "The invoice status" + "is not NOT_YET_PAID");
        StateChecks.isTrue(workOrder.isFinished(),
                "The work order status" + "is not FINISHED");
        Associations.Bills.link(this, workOrder);
        workOrder.markAsInvoiced();
        workOrders.add(workOrder);
        computeAmount();

    }

    /**
     * Removes a work order from the invoice, updates the workorder state and
     * recomputes amount and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException    if the invoice status is not
     *                                  NOT_YET_PAID
     * @throws IllegalArgumentException if the invoice does not contain the
     *                                  workorder
     */
    public void removeWorkOrder(WorkOrder workOrder) {
        ArgumentChecks.isNotNull(workOrder);
        StateChecks.isTrue(this.state.equals(InvoiceState.NOT_YET_PAID),
                "The invoice status" + "is not NOT_YET_PAID");
        ArgumentChecks.isTrue(workOrders.contains(workOrder),
                "The invoice does not" + "contain the work order");
        workOrders.remove(workOrder);
        workOrder.markBackToFinished();
        computeAmount();
    }

    /**
     * Marks the invoice as PAID, but
     * 
     * @throws IllegalStateException if - Is already settled - Or the amounts
     *                               paid with charges to payment means do not
     *                               cover the total of the invoice
     */
    public void settle() {
        StateChecks.isTrue(this.state.equals(InvoiceState.NOT_YET_PAID),
                "The invoice " + "is already settledS");
        double totalCharges = calculateTotalCharges();
        StateChecks.isTrue(totalCharges >= this.amount);
        this.state = InvoiceState.PAID;
    }

    private double calculateTotalCharges() {
        double total = 0.0;
        for (Charge c : charges) {
            total += c.getAmount();
        }
        return total;
    }

    public Set<WorkOrder> getWorkOrders() {
        return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
        return workOrders;
    }

    public Set<Charge> getCharges() {
        return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
        return charges;
    }

    public Long getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public double getVat() {
        return vat;
    }

    public InvoiceState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Invoice [number=" + number + ", date=" + date + ", amount="
                + amount + ", vat=" + vat + ", state=" + state + "]";
    }

    public boolean isNotSettled() {
        return state.equals(InvoiceState.NOT_YET_PAID);
    }

}
