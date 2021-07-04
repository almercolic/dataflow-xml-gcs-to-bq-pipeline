package de.acolic.demos;

import de.acolic.demos.pipeline.Options;
import de.acolic.demos.pipeline.PipelineDefinition;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

import static org.apache.beam.sdk.Pipeline.create;

public class App {

    public static void main(String[] args) {

        PipelineOptionsFactory.register(Options.class);
        Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        var pipeline = create(options);

        new PipelineDefinition()
                .applyTransforms(pipeline)
                .run(options)
                .waitUntilFinish();

    }

}
