package org.unibayreuth.gnumaexperiments.queries.classifiers;

public class RetrieveAddressClassifiersQuery {
    private String address;

    public RetrieveAddressClassifiersQuery(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "RetrieveAddressClassifiersQuery{" +
                "address='" + address + '\'' +
                '}';
    }
}
