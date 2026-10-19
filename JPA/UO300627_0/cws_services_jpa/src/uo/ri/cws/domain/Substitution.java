package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Substitution extends BaseEntity {
    // natural attributes
    private int quantity;

    // accidental attributes
    private SparePart sparePart;
    private Intervention intervention;

    Substitution() {

    }

    public Substitution(SparePart sparePart, Intervention intervention,
            int quantity) {
        this(intervention, sparePart, quantity);
    }

    public Substitution(Intervention intervention, SparePart sparePart,
            int quantity) {
        ArgumentChecks.isNotNull(intervention);
        ArgumentChecks.isNotNull(sparePart);
        ArgumentChecks.isTrue(quantity > 0);
        this.quantity = quantity;
        Associations.Substitutes.link(sparePart, this, intervention);
    }

    void _setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    void _setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    public int getQuantity() {
        return quantity;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    @Override
    public String toString() {
        return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
                + ", intervention=" + intervention + "]";
    }

    public double getAmount() {
        return this.sparePart.getPrice() * this.quantity;
    }

}
