package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.UUID;

public class FoldDTO {
    private List<String> train;
    private List<String> valid;

    public FoldDTO(List<String> train, List<String> valid) {
        this.train = train;
        this.valid = valid;
    }

    public List<String> getTrain() {
        return train;
    }

    public List<String> getValid() {
        return valid;
    }
}
