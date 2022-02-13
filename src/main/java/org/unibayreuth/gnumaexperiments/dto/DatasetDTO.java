package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;

public class DatasetDTO {
    private DataDTO data;

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
