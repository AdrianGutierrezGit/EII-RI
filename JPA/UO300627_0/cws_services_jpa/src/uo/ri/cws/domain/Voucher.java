package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Voucher extends PaymentMean {
    private String code;
    private double available = 0.0;
    private String description;

    Voucher() {

    }

    public Voucher(String code, String description, double available) {
        super();
        ArgumentChecks.isNotNull(code);
        ArgumentChecks.isTrue(available >= 0);
        ArgumentChecks.isNotNull(description);
        this.code = code;
        this.description = description;
        this.available = available;
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
        StateChecks.isTrue(canPay(amount), "Not enough available to pay");
        super.pay(amount);
        available -= amount;
    }

    /**
     * A voucher can pay if it has enough available to pay the amount
     */
    @Override
    public boolean canPay(Double amount) {
        return this.available >= amount;
    }

    public String getCode() {
        return code;
    }

    public double getAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Voucher [code=" + code + ", available=" + available
                + ", description=" + description + super.toString() + "]";
    }

}
