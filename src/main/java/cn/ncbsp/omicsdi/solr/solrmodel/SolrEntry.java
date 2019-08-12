package cn.ncbsp.omicsdi.solr.solrmodel;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SolrDocument
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
    String dateCreation;

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

    @Field(value = "reanalysis_count")
    String reanalysisCount;

    @Field(value = "TAXONOMY")
    List<String> taxonomy;

    @Field(value = "tissue")
    List<String> tissue;

    @Field(value = "omics_type")
    List<String> omicsType;

    @Field(value = "disease")
    List<String> disease;

    @Field(value = "submitter_keywords")
    List<String> submitterKeywords;

    @Field(value = "curator_keywords")
    List<String> curatorKeywords;

    @Field(value = "publication_date")
    Date publicationDate;

    @Field(value = "ENSEMBL")
    List<String> ENSEMBL;

    @Field(value = "UNIPROT")
    List<String> UNIPROT;

    @Field(value = "CHEBI")
    List<String> CHEBI;

    @Field(value = "PUBMED")
    List<String> PUBMED;

    @Field(value = "data_protocol")
    String dataProtocol;

    @Field(value = "sample_protocol")
    String sampleProtocol;
    @Field(value = "additional_*")
    Map<String, List<String>> additionalFields;

    public SolrEntry() {
    }

    public String getDataProtocol() {
        return dataProtocol;
    }

    public void setDataProtocol(String dataProtocol) {
        this.dataProtocol = dataProtocol;
    }

    public String getSampleProtocol() {
        return sampleProtocol;
    }

    public void setSampleProtocol(String sampleProtocol) {
        this.sampleProtocol = sampleProtocol;
    }

    public List<String> getPUBMED() {
        return PUBMED;
    }

//    @Field(value = "omics_type")
//    List<String> omicsType;

    public void setPUBMED(List<String> PUBMED) {
        this.PUBMED = PUBMED;
    }

    public Map<String, List<String>> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, List<String>> additionalFields) {
        this.additionalFields = additionalFields;
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

    public String getReanalysisCount() {
        return reanalysisCount;
    }

    public void setReanalysisCount(String reanalysisCount) {
        this.reanalysisCount = reanalysisCount;
    }

    public List<String> getSubmitterKeywords() {
        return submitterKeywords;
    }

    public void setSubmitterKeywords(List<String> submitterKeywords) {
        this.submitterKeywords = submitterKeywords;
    }

    public List<String> getCuratorKeywords() {
        return curatorKeywords;
    }

    public void setCuratorKeywords(List<String> curatorKeywords) {
        this.curatorKeywords = curatorKeywords;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<String> getENSEMBL() {
        return ENSEMBL;
    }

    public void setENSEMBL(List<String> ENSEMBL) {
        this.ENSEMBL = ENSEMBL;
    }

    public List<String> getUNIPROT() {
        return UNIPROT;
    }

    public void setUNIPROT(List<String> UNIPROT) {
        this.UNIPROT = UNIPROT;
    }

    public List<String> getCHEBI() {
        return CHEBI;
    }

    public void setCHEBI(List<String> CHEBI) {
        this.CHEBI = CHEBI;
    }
}
