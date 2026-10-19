package uo.ri.cws.domain;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

public class Charge extends BaseEntity {
    // natural attributes
    private double amount = 0.0;

    // accidental attributes
    private Invoice invoice;
    private PaymentMean paymentMean;

    Charge() {

    }

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
        ArgumentChecks.isNotNull(invoice);
        ArgumentChecks.isNotNull(paymentMean);
        ArgumentChecks.isTrue(amount >= 0);
        this.amount = amount;
        paymentMean.pay(amount);
        Associations.Settles.link(invoice, this, paymentMean);
    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean
     * 
     * @throws IllegalStateException if the invoice is already settled
     */
    public void rewind() {
        // asserts the invoice is not in PAID status
        // decrements the payment mean accumulated ( paymentMean.pay( -amount) )
        // unlinks invoice, this and paymentMean
    }

    public double getAmount() {
        return amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    void _setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    void _setPaymentMean(PaymentMean paymentMean) {
        this.paymentMean = paymentMean;
    }

    @Override
    public String toString() {
        return "Charge [amount=" + amount + ", invoice=" + invoice
                + ", paymentMean=" + paymentMean + "]";
    }

}
