package org.unibayreuth.gnumaexperiments.queries.classifiers;

import java.util.List;

public class RetrieveAddressListClassifierQuery {
    private List<String> addresses;

    public RetrieveAddressListClassifierQuery(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return "RetrieveAddressListClassifierQuery{" +
                "addresses=" + addresses +
                '}';
    }
}
