package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.UUID;

public class FoldDTO {
    private List<UUID> train;
    private List<UUID> valid;

    public FoldDTO(List<UUID> train, List<UUID> valid) {
        this.train = train;
        this.valid = valid;
    }

    public List<UUID> getTrain() {
        return train;
    }

    public List<UUID> getValid() {
        return valid;
    }
}
