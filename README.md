***XML Pipeline from GCS to BigQuery***

This demo application reads all XML files from a certain path, parses them and inserts into BQ executed in Google Dataflow (Apache Beam)

**Prerequisites**

`terraform apply` resources from the `terraform` folder. This will create a new project with a GCS bucket with two example XML files and a BigQuery table
After tthe succesful run everything can be easily removed with `terraform destroy`
Terraform needs two variables in order to create the resources: `organization` and `billing_account`

The example XML files can be found here: `terraform/files/`

**Trigger the Dataflow application from shell**

The following command triggers the pipeline:
```
mvn compile exec:java \
  -Dexec.mainClass=de.acolic.demos.App \
  -Dexec.args=" \
  --sourcePath=gs://xml-pipeline-storage-287/files/** \
  --outputTable=xml-pipeline-example-287:company_dataset.companies \
  --gcpTempLocation=gs://xml-pipeline-dataflow-287/temp \
  --project=xml-pipeline-example-287 \
  --region=europe-west4 \
  --runner=DataflowRunner"
```

**Output**

After successful run of the Dataflow pipeline the BQ table should contain the example companies. The UI should like:

![BQ](resources/bigquery_screenshot.png?raw=true "Big Query")