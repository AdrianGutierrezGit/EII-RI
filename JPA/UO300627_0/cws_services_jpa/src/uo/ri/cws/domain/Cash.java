package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Cash extends PaymentMean {

    Cash() {

    }

    public Cash(Client client) {
        ArgumentChecks.isNotNull(client, "Invalid null cash client");
        Associations.Holds.link(this, client);
    }

    /**
     * A cash can always pay
     */
    @Override
    public boolean canPay(Double amount) {
        return true;
    }

    @Override
    public String toString() {
        return "Cash [toString()=" + super.toString() + "]";
    }

}
