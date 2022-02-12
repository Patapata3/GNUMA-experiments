package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.UUID;

public class DatasetDTO {

    DataDTO data;

    public DatasetDTO(DataDTO data) {
        this.data = data;
    }

    public DataDTO getData() {
        return data;
    }

    public class DataDTO {
        List<String> test;
        List<FoldDTO> folds;

        public DataDTO(List<FoldDTO> folds, List<String> test) {
            this.test = test;
            this.folds = folds;
        }

        public List<FoldDTO> getFolds() {
            return folds;
        }

        public List<String> getTest() {
            return test;
        }
    }
}
