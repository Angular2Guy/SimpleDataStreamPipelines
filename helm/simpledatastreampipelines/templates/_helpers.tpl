{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "helm-chart.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "helm-chart.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "helm-chart.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create sourceSinkApp values
*/}}
{{- define "helpers.list-env-source-sink-variables"}}
{{- $secretName := .Values.secret.nameSourceSink -}}
{{- range $key, $val := .Values.envSourceSink.secret }}
- name: {{ $key }}
  valueFrom:
    secretKeyRef:
      name: {{ $secretName }}
      key: {{ $key }}
{{- end}}
{{- range $key, $val := .Values.envSourceSink.normal }}
- name: {{ $key }}
  value: {{ $val | quote }}
{{- end}}
{{- end }}

{{/*
Create envDb values
*/}}
{{- define "helpers.list-envDb-variables"}}
{{- $secretName := .Values.secret.nameDb -}}
{{- range $key, $val := .Values.envDb.secret }}
- name: {{ $key }}
  valueFrom:
    secretKeyRef:
      name: {{ $secretName }}
      key: {{ $key }}
{{- end}}
{{- range $key, $val := .Values.envDb.normal }}
- name: {{ $key }}
  value: {{ $val | quote }}
{{- end}}
{{- end }}

{{/*
Create envKafka values
*/}}
{{- define "helpers.list-envKafka-variables"}}
{{- $secretName := .Values.secret.nameKafka -}}
{{- range $key, $val := .Values.envKafka.secret }}
- name: {{ $key }}
  valueFrom:
    secretKeyRef:
      name: {{ $secretName }}
      key: {{ $key }}
{{- end}}
{{- range $key, $val := .Values.envKafka.normal }}
- name: {{ $key }}
  value: {{ $val | quote }}
{{- end}}
{{- end }}

{{/*
Create soapToDbApp values
*/}}
{{- define "helpers.list-env-soap-to-db-variables"}}
{{- $secretName := .Values.secret.nameSoapToDb -}}
{{- range $key, $val := .Values.envSoapToDb.secret }}
- name: {{ $key }}
  valueFrom:
    secretKeyRef:
      name: {{ $secretName }}
      key: {{ $key }}
{{- end}}
{{- range $key, $val := .Values.envSoapToDb.normal }}
- name: {{ $key }}
  value: {{ $val | quote }}
{{- end}}
{{- end }}

{{/*
Create databaseToRestApp values
*/}}
{{- define "helpers.list-env-database-to-rest-variables"}}
{{- $secretName := .Values.secret.nameDatabaseToRest -}}
{{- range $key, $val := .Values.envDatabaseToRest.secret }}
- name: {{ $key }}
  valueFrom:
    secretKeyRef:
      name: {{ $secretName }}
      key: {{ $key }}
{{- end}}
{{- range $key, $val := .Values.envDatabaseToRest.normal }}
- name: {{ $key }}
  value: {{ $val | quote }}
{{- end}}
{{- end }}

{{/*
Create eventToFileApp values
*/}}
{{- define "helpers.list-env-event-to-file-variables"}}
{{- $secretName := .Values.secret.nameEventToFile -}}
{{- range $key, $val := .Values.envEventToFile.secret }}
- name: {{ $key }}
  valueFrom:
    secretKeyRef:
      name: {{ $secretName }}
      key: {{ $key }}
{{- end}}
{{- range $key, $val := .Values.envEventToFile.normal }}
- name: {{ $key }}
  value: {{ $val | quote }}
{{- end}}
{{- end }}