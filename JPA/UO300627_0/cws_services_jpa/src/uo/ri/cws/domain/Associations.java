package uo.ri.cws.domain;

public class Associations {

    public static class Owns {

        public static void link(Client client, Vehicle vehicle) {
            vehicle._setClient(client);
            client._getVehicles()
                  .add(vehicle);
        }

        public static void unlink(Client client, Vehicle vehicle) {
            client._getVehicles()
                  .remove(vehicle);
            vehicle._setClient(null);
        }

    }

    public static class Classifies {

        public static void link(VehicleType vehicleType, Vehicle vehicle) {
            vehicle._setVehicleType(vehicleType);
            vehicleType._getVehicles()
                       .add(vehicle);
        }

        public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
            vehicleType._getVehicles()
                       .remove(vehicle);
            vehicle._setVehicleType(null);
        }
    }

    public static class Holds {

        public static void link(PaymentMean mean, Client client) {
            mean._setClient(client);
            client._getPaymentMeans()
                  .add(mean);
        }

        public static void unlink(Client client, PaymentMean mean) {
            client._getPaymentMeans()
                  .remove(mean);
            mean._setClient(null);
        }
    }

    public static class Fixes {

        public static void link(Vehicle vehicle, WorkOrder workOrder) {
            workOrder._setVehicle(vehicle);
            vehicle._getWorkorders()
                   .add(workOrder);
        }

        public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
            vehicle._getWorkorders()
                   .remove(workOrder);
            workOrder._setVehicle(null);

        }
    }

    public static class Bills {

        public static void link(Invoice invoice, WorkOrder workOrder) {
            workOrder._setInvoice(invoice);
            invoice._getWorkOrders()
                   .add(workOrder);
        }

        public static void unlink(Invoice invoice, WorkOrder workOrder) {
            invoice._getWorkOrders()
                   .remove(workOrder);
            workOrder._setInvoice(null);
        }
    }

    public static class Settles {

        public static void link(Invoice invoice, Charge cargo, PaymentMean mp) {
            cargo._setInvoice(invoice);
            cargo._setPaymentMean(mp);

            invoice._getCharges()
                   .add(cargo);
            mp._getCharges()
              .add(cargo);
        }

        public static void unlink(Charge cargo) {
            Invoice invoice = cargo.getInvoice();
            PaymentMean paymentMean = cargo.getPaymentMean();

            invoice._getCharges()
                   .remove(cargo);
            paymentMean._getCharges()
                       .remove(cargo);

            cargo._setInvoice(null);
            cargo._setPaymentMean(null);
        }
    }

    public static class Assigns {

        public static void link(Mechanic mechanic, WorkOrder workOrder) {
            workOrder._setMechanic(mechanic);
            mechanic._getAssigned()
                    .add(workOrder);
        }

        public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
            mechanic._getAssigned()
                    .remove(workOrder);
            workOrder._setMechanic(null);
        }
    }

    public static class Intervenes {

        public static void link(WorkOrder workOrder, Intervention intervention,
                Mechanic mechanic) {
            intervention._setMechanic(mechanic);
            intervention._setWorkOrder(workOrder);

            workOrder._getInterventions()
                     .add(intervention);
            mechanic._getInterventions()
                    .add(intervention);
        }

        public static void unlink(Intervention intervention) {
            Mechanic mechanic = intervention.getMechanic();
            WorkOrder workOrder = intervention.getWorkOrder();

            workOrder._getInterventions()
                     .remove(intervention);
            mechanic._getInterventions()
                    .remove(intervention);

            intervention._setMechanic(null);
            intervention._setWorkOrder(null);
        }
    }

    public static class Substitutes {

        static void link(SparePart sparePart, Substitution substitution,
                Intervention intervention) {
            substitution._setIntervention(intervention);
            substitution._setSparePart(sparePart);

            sparePart._getSubstitutions()
                     .add(substitution);
            intervention._getSubstitutions()
                        .add(substitution);
        }

        public static void unlink(Substitution substitution) {
            SparePart sparePart = substitution.getSparePart();
            Intervention intervention = substitution.getIntervention();

            sparePart._getSubstitutions()
                     .remove(substitution);
            intervention._getSubstitutions()
                        .remove(substitution);

            substitution._setIntervention(null);
            substitution._setSparePart(null);

        }
    }

    public static class Binds {
        public static void link(Mechanic mechanic, Contract contract) {
            contract._setMechanic(mechanic);
            mechanic._getContracts()
                    .add(contract);
        }

        public static void unlink(Mechanic mechanic, Contract contract) {
            mechanic._getContracts()
                    .remove(contract);
            contract._setMechanic(null);
        }
    }

    public static class Generates {
        public static void link(Contract contract, Payroll payroll) {
            payroll._setContract(contract);
            contract._getPayrolls()
                    .add(payroll);
        }

        public static void unlink(Contract contract, Payroll payroll) {
            contract._getPayrolls()
                    .remove(payroll);
            payroll._setContract(null);
        }
    }

    public static class Categorizes {
        public static void link(ProfessionalGroup professionalGroup,
                Contract contract) {
            contract._setProfessionalGroup(professionalGroup);
            professionalGroup._getContracts()
                             .add(contract);
        }

        public static void unlink(ProfessionalGroup professionalGroup,
                Contract contract) {
            professionalGroup._getContracts()
                             .remove(contract);
            contract._setProfessionalGroup(professionalGroup);
        }
    }

    public static class Defines {
        public static void link(ContractType contractType, Contract contract) {
            contract._setContractType(contractType);
            contractType._getContracts()
                        .add(contract);
        }

        public static void unlink(ContractType contractType,
                Contract contract) {
            contractType._getContracts()
                        .remove(contract);
            contract._setContractType(null);
        }
    }
}
