package cn.ncbsp.omicsdi.solr.util;

import cn.ncbsp.omicsdi.solr.model.Entry;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolrEntryUtil {
    public static List<SolrEntry> parseEntryToSolrEntry(List<Entry> list, String database) {
        List<SolrEntry> solrEntries = new ArrayList<SolrEntry>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        list.forEach(entry -> {
            SolrEntry solrEntry = new SolrEntry();
            solrEntry.setId(entry.getId());
            solrEntry.setAcc(entry.getAcc());
            solrEntry.setName(entry.getName().getValue());// ???
            solrEntry.setDescription(entry.getDescription());
            if (entry.getDates() != null) {
                entry.getDates().getDate().forEach(date -> {
                    switch (date.getType()) {
                        case "publication":
                            solrEntry.setDatePublication(date.getValue());
                            try {
                                solrEntry.setPublicationDate(simpleDateFormat.parse(date.getValue()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        case "submission ":
                            solrEntry.setDateSubmission(date.getValue());
                        case "updated":
                            solrEntry.setDateUpdated(date.getValue());
                        case "creation":
                            solrEntry.setDateOthers(date.getValue());
                        default:
                            // todo error
                    }
                });
            }
            solrEntry.setDatabase(database);
            Map<String, List<String>> additionalMap = new HashMap<>();
            List<String> taxonomy = new ArrayList<>();
            List<String> ensembl = new ArrayList<>();
            List<String> uniport = new ArrayList<>();
            List<String> chebi = new ArrayList<>();
            List<String> pubmed = new ArrayList<>();
            entry.getCrossReferences().getRef().forEach(reference -> {
                if (reference.getDbkey().equalsIgnoreCase("TAXONOMY")) {
                    taxonomy.add(reference.getDbname());
                } else if (reference.getDbkey().equalsIgnoreCase("ensembl")) {
                    ensembl.add(reference.getDbname());
                } else if (reference.getDbkey().equalsIgnoreCase("uniprot")) {
                    uniport.add(reference.getDbname());
                } else if (reference.getDbkey().equalsIgnoreCase("chebi")) {
                    chebi.add(reference.getDbname());
                } else if (reference.getDbkey().equalsIgnoreCase("pubmed")) {
                    pubmed.add(reference.getDbname());
                } else {
                    if (null == additionalMap.get("additional_" + reference.getDbkey())) {
                        List<String> listForMap = new ArrayList<>();
                        listForMap.add(reference.getDbname());
                        additionalMap.put("additional_" + reference.getDbkey(), listForMap);
                    } else {
                        List<String> listField = additionalMap.get("additional_" + reference.getDbkey());
                        listField.add(reference.getDbname());
                        additionalMap.put("additional_" + reference.getDbkey(), listField);
                    }
                }
            });

            solrEntry.setTaxonomy(taxonomy);
            solrEntry.setENSEMBL(ensembl);
            solrEntry.setUNIPROT(uniport);
            solrEntry.setCHEBI(chebi);
            solrEntry.setPUBMED(pubmed);

            List<String> tissues = new ArrayList<>();
            List<String> diseases = new ArrayList<>();
            List<String> omicsTypes = new ArrayList<>();
            List<String> submitterKeywords = new ArrayList<>();
            List<String> curatorKeywords = new ArrayList<>();

            entry.getAdditionalFields().getField().forEach(field -> {
                if (field.getName().equals("pubmed_abstract")) {
                    solrEntry.setPubmedAbstract(field.getValue());
                } else if (field.getName().equals("view_count")) {
                    solrEntry.setViewCount(field.getValue());
                } else if (field.getName().equals("citation_count")) {
                    solrEntry.setCitationCount(field.getValue());
                } else if (field.getName().equals("search_count")) {
                    solrEntry.setSearchCount(field.getValue());
                } else if (field.getName().equals("reanalysis_count")) {
                    solrEntry.setReanalysisCount(field.getValue());
                } else if (field.getName().equals("tissue")) {
                    tissues.add(field.getValue());
                } else if (field.getName().equals("disease")) {
                    diseases.add(field.getValue());
                } else if (field.getName().equals("omics_type")) {
                    omicsTypes.add(field.getValue());
                } else if (field.getName().equals("submitter_keywords")) {
                    submitterKeywords.add(field.getValue());
                } else if (field.getName().equals("curator_keywords")) {
                    curatorKeywords.add(field.getValue());
                } else if (field.getName().equals("data_protocol")) {
                    solrEntry.setDataProtocol(field.getValue());
                } else if (field.getName().equals("sample_protocol")) {
                    solrEntry.setSampleProtocol(field.getValue());
                } else {
                    if (null == additionalMap.get("additional_" + field.getName())) {
                        List<String> listForMap = new ArrayList<>();
                        listForMap.add(field.getValue());
                        additionalMap.put("additional_" + field.getName(), listForMap);
                    } else {
                        List<String> listField = additionalMap.get("additional_" + field.getName());
                        listField.add(field.getValue());
                        additionalMap.put("additional_" + field.getName(), listField);
                    }
                }
            });

            solrEntry.setTissue(tissues);
            solrEntry.setDisease(diseases);
            solrEntry.setOmicsType(omicsTypes);
            solrEntry.setSubmitterKeywords(submitterKeywords);
            solrEntry.setCuratorKeywords(curatorKeywords);
            solrEntry.setAdditionalFields(additionalMap);
            if (StringUtils.isBlank(solrEntry.getPubmedAbstract())) {
                solrEntry.setPubmedAbstract("Not Availiable");
            }
            solrEntries.add(solrEntry);
        });

        return solrEntries;
    }
}
