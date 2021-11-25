package org.unibayreuth.gnumaexperiments.commands;

import java.util.Date;

public class CreateExperimentCommand {
    private Date date;

    public CreateExperimentCommand(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
