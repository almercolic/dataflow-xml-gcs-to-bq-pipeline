resource "google_bigquery_dataset" "dataset" {
  project       = google_project.project.project_id
  dataset_id    = "company_dataset"
  friendly_name = "Companies"
  location      = "EU"
  depends_on = [
  google_project_service.services["dataflow.googleapis.com"]]
}

resource "google_bigquery_table" "table" {
  project             = google_project.project.project_id
  dataset_id          = google_bigquery_dataset.dataset.dataset_id
  table_id            = "companies"
  friendly_name       = "Companies"
  deletion_protection = false
}

resource "google_storage_bucket" "bucket" {
  project       = google_project.project.project_id
  name          = "xml-pipeline-storage-${random_integer.project_id_suffix.result}"
  location      = "EU"
  force_destroy = true
  depends_on = [
  google_project_service.services["storage.googleapis.com"]]
}

resource "google_storage_bucket_object" "files" {
  for_each = fileset(path.module, "files/*")
  name     = each.value
  source   = "./${each.value}"
  bucket   = google_storage_bucket.bucket.name
}

resource "google_storage_bucket" "dataflow_temp_bucket" {
  project       = google_project.project.project_id
  name          = "xml-pipeline-dataflow-${random_integer.project_id_suffix.result}"
  location      = "EU"
  force_destroy = true
  depends_on = [
  google_project_service.services["storage.googleapis.com"]]
}