package uo.ri.cws.domain;

import java.time.LocalDate;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {
    private String number;
    private String type;
    private LocalDate validThru;

    CreditCard() {

    }

    public CreditCard(String number, String type, LocalDate validThru) {
        super();
        ArgumentChecks.isNotNull(number);
        ArgumentChecks.isNotNull(type);
        ArgumentChecks.isNotNull(validThru);
        this.number = number;
        this.type = type;
        this.validThru = validThru;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    /**
     * A credit card can pay if is not outdated
     */
    @Override
    public boolean canPay(Double amount) {
        return validThru.isAfter(LocalDate.now());
    }

    @Override
    public void pay(double importe) {
        StateChecks.isTrue(canPay(importe), "The credit card is outdated");
        super.pay(importe);
    }

    @Override
    public String toString() {
        return "CreditCard [number=" + number + ", type=" + type
                + ", validThru=" + validThru + super.toString() + "]";
    }

}
