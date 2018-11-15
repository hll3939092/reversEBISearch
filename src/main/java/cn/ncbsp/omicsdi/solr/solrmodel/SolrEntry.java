package cn.ncbsp.omicsdi.solr.solrmodel;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SolrDocument(collection = "omicsdi")
public class SolrEntry {
    @Id
    @Field
    String id;

    @Field
    String acc;

    @Field
    String name;

    @Field
    String description;

    @Field
    String database;

    @Field(value = "date_publication")
    String datePublication;

    @Field(value = "date_submission")
    String dateSubmission;

    @Field(value = "date_updated")
    String dateUpdated;

    @Field(value = "date_creation")
    String dateCreation ;

    @Field(value = "date_others")
    String dateOthers;

    @Field(value = "pubmed_abstract")
    String pubmedAbstract;

    @Field(value = "view_count")
    String viewCount;

    @Field(value = "citation_count")
    String citationCount;

    @Field(value = "search_count")
    String searchCount;

    @Field(value = "TAXONOMY")
    List<String> taxonomy;

    @Field(value = "tissue")
    List<String> tissue;

    @Field(value = "omics_type")
    List<String> omicsType;

    @Field(value = "disease")
    List<String> disease;



    @Field(value = "additional_*")
    Map<String,List<String>> additionalFields;

//    @Field(value = "omics_type")
//    List<String> omicsType;

    public Map<String, List<String>> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, List<String>> additionalFields) {
        this.additionalFields = additionalFields;
    }

//
//
//    @Field(value = "data_protocol")
//    List<String> dataProtocol;
//
//    @Field(value = "sample_protocol")
//    List<String> sampleProtocol;
//
//    @Field(value = "repository")
//    List<String> repository;
//
//    @Field(value = "species")
//    List<String> species;
//
//    @Field(value = "disease")
//    List<String> disease;
//
//
//    @Field(value = "tissue")
//    List<String> tissue;
//
//    @Field(value = "cell_type")
//    List<String> cellType;
//
//    @Field(value = "full_dataset_link")
//    List<String> fullDatasetLink;
//
//
//    @Field(value = "submitter")
//    List<String> submitter;
//
//
//
//    @Field(value = "submitter_mail")
//    List<String> submitterMail;
//
//    @Field(value = "submitter_affiliation")
//    List<String> submitterAffiliation;
//
//    @Field(value = "instrument_platform")
//    List<String> instrumentPlatform;
//
//    @Field(value = "technology_type")
//    List<String> technologyType;
//
//
//    @Field(value = "modification")
//    List<String> modification;
//
//    @Field(value = "submitter_keywords")
//    List<String> submitterKeywords;
//
//    @Field(value = "quantification_method")
//    List<String> quantificationMethod;
//
//    @Field(value = "submission_type")
//    List<String> submissionType;
//
//
//
//    @Field(value = "software")
//    List<String> software;
//
//    @Field(value = "publication")
//    List<String> publication;
//
//    @Field(value = "dataset_file")
//    List<String> datasetFile;


    public SolrEntry() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getDateSubmission() {
        return dateSubmission;
    }

    public void setDateSubmission(String dateSubmission) {
        this.dateSubmission = dateSubmission;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateOthers() {
        return dateOthers;
    }

    public void setDateOthers(String dateOthers) {
        this.dateOthers = dateOthers;
    }

    public List<String> getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(List<String> taxonomy) {
        this.taxonomy = taxonomy;
    }

    public List<String> getTissue() {
        return tissue;
    }

    public void setTissue(List<String> tissue) {
        this.tissue = tissue;
    }

    public List<String> getOmicsType() {
        return omicsType;
    }

    public void setOmicsType(List<String> omicsType) {
        this.omicsType = omicsType;
    }

    public List<String> getDisease() {
        return disease;
    }

    public void setDisease(List<String> disease) {
        this.disease = disease;
    }

    //    public Set<String> getDbname() {
//        return dbname;
//    }
//
//    public void setDbname(Set<String> dbname) {
//        this.dbname = dbname;
//    }
//
//    public List<String> getDbkey() {
//        return dbkey;
//    }
//
//    public void setDbkey(List<String> dbkey) {
//        this.dbkey = dbkey;
//    }

//    public List<String> getOmicsType() {
//        return omicsType;
//    }
//
//    public void setOmicsType(List<String> omicsType) {
//        this.omicsType = omicsType;
//    }
//
//    public List<String> getDataProtocol() {
//        return dataProtocol;
//    }
//
//    public void setDataProtocol(List<String> dataProtocol) {
//        this.dataProtocol = dataProtocol;
//    }
//
//    public List<String> getSampleProtocol() {
//        return sampleProtocol;
//    }
//
//    public void setSampleProtocol(List<String> sampleProtocol) {
//        this.sampleProtocol = sampleProtocol;
//    }
//
//    public List<String> getRepository() {
//        return repository;
//    }
//
//    public void setRepository(List<String> repository) {
//        this.repository = repository;
//    }
//
//    public List<String> getSpecies() {
//        return species;
//    }
//
//    public void setSpecies(List<String> species) {
//        this.species = species;
//    }
//
//    public List<String> getDisease() {
//        return disease;
//    }
//
//    public void setDisease(List<String> disease) {
//        this.disease = disease;
//    }
//
//    public List<String> getTissue() {
//        return tissue;
//    }
//
//    public void setTissue(List<String> tissue) {
//        this.tissue = tissue;
//    }
//
//    public List<String> getCellType() {
//        return cellType;
//    }
//
//    public void setCellType(List<String> cellType) {
//        this.cellType = cellType;
//    }
//
//    public List<String> getFullDatasetLink() {
//        return fullDatasetLink;
//    }
//
//    public void setFullDatasetLink(List<String> fullDatasetLink) {
//        this.fullDatasetLink = fullDatasetLink;
//    }
//
//    public List<String> getSubmitter() {
//        return submitter;
//    }
//
//    public void setSubmitter(List<String> submitter) {
//        this.submitter = submitter;
//    }
//
//    public List<String> getSubmitterMail() {
//        return submitterMail;
//    }
//
//    public void setSubmitterMail(List<String> submitterMail) {
//        this.submitterMail = submitterMail;
//    }
//
//    public List<String> getSubmitterAffiliation() {
//        return submitterAffiliation;
//    }
//
//    public void setSubmitterAffiliation(List<String> submitterAffiliation) {
//        this.submitterAffiliation = submitterAffiliation;
//    }
//
//    public List<String> getInstrumentPlatform() {
//        return instrumentPlatform;
//    }
//
//    public void setInstrumentPlatform(List<String> instrumentPlatform) {
//        this.instrumentPlatform = instrumentPlatform;
//    }
//
//    public List<String> getTechnologyType() {
//        return technologyType;
//    }
//
//    public void setTechnologyType(List<String> technologyType) {
//        this.technologyType = technologyType;
//    }
//
//    public List<String> getModification() {
//        return modification;
//    }
//
//    public void setModification(List<String> modification) {
//        this.modification = modification;
//    }
//
//    public List<String> getSubmitterKeywords() {
//        return submitterKeywords;
//    }
//
//    public void setSubmitterKeywords(List<String> submitterKeywords) {
//        this.submitterKeywords = submitterKeywords;
//    }
//
//    public List<String> getQuantificationMethod() {
//        return quantificationMethod;
//    }
//
//    public void setQuantificationMethod(List<String> quantificationMethod) {
//        this.quantificationMethod = quantificationMethod;
//    }
//
//    public List<String> getSubmissionType() {
//        return submissionType;
//    }
//
//    public void setSubmissionType(List<String> submissionType) {
//        this.submissionType = submissionType;
//    }
//
//    public List<String> getSoftware() {
//        return software;
//    }
//
//    public void setSoftware(List<String> software) {
//        this.software = software;
//    }
//
//    public List<String> getPublication() {
//        return publication;
//    }
//
//    public void setPublication(List<String> publication) {
//        this.publication = publication;
//    }
//
//    public List<String> getDatasetFile() {
//        return datasetFile;
//    }
//
//    public void setDatasetFile(List<String> datasetFile) {
//        this.datasetFile = datasetFile;
//    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPubmedAbstract() {
        return pubmedAbstract;
    }

    public void setPubmedAbstract(String pubmedAbstract) {
        this.pubmedAbstract = pubmedAbstract;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(String citationCount) {
        this.citationCount = citationCount;
    }

    public String getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(String searchCount) {
        this.searchCount = searchCount;
    }
}
