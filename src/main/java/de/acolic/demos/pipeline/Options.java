package de.acolic.demos.pipeline;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

public interface Options extends DataflowPipelineOptions {
    @Description("Source path. Format: gs://<path-to-folder/file>")
    @Validation.Required
    String getSourcePath();

    @Description("BQ Table. Format: project:dataset.table")
    @Validation.Required
    String getOutputTable();

    void setSourcePath(String sourcePath);

    void setOutputTable(String value);

}
