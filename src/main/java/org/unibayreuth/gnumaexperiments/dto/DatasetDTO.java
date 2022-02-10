package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.UUID;

public class DatasetDTO {
    List<String> test;
    List<FoldDTO> folds;

    public DatasetDTO(List<String> test, List<FoldDTO> folds) {
        this.test = test;
        this.folds = folds;
    }

    public List<String> getTest() {
        return test;
    }

    public List<FoldDTO> getFolds() {
        return folds;
    }
}
