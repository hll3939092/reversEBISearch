package cn.ncbsp.omicsdi.solr.util;

import cn.ncbsp.omicsdi.solr.model.Entry;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SolrEntryUtil {
    public static List<SolrEntry> parseEntryToSolrEntry(List<Entry> list,String database) {
        List<SolrEntry> solrEntries = new ArrayList<SolrEntry>();
        list.forEach(entry -> {
            SolrEntry solrEntry = new SolrEntry();
            solrEntry.setId(entry.getId());
            solrEntry.setAcc(entry.getAcc());
            solrEntry.setName(entry.getName().getValue());// ???
            solrEntry.setDescription(entry.getDescription());
            entry.getDates().getDate().forEach(date -> {
                switch (date.getType()) {
                    case "publication" :
                        solrEntry.setDatePublication(date.getValue());
                    case "submission ":
                        solrEntry.setDateSubmission(date.getValue());
                    case "updated" :
                        solrEntry.setDateUpdated(date.getValue());
                    case "creation" :
                        solrEntry.setDateOthers(date.getValue());
                    default:
                        // todo error
                }
            });
            solrEntry.setDatabase(database);

//            Set<String> setDbName = new HashSet<String>();
//            List<String> listDbKey = new ArrayList<>();
//            entry.getCrossReferences().getRef().forEach(reference -> {
//                setDbName.add(reference.getDbname());
//                listDbKey.add(reference.getDbkey());
//            });
//            solrEntry.setDbname(setDbName);
//            solrEntry.setDbkey(listDbKey);

            Map<String,List<String>> additionalMap = new HashMap<>();

            List<String> taxonomy = new ArrayList<>();
            entry.getCrossReferences().getRef().forEach(reference -> {
                if(reference.getDbname().equals("TAXONOMY")) {
                    taxonomy.add(reference.getDbkey());
                } else {
                        if(null == additionalMap.get("additional_"+reference.getDbname())){
                        List<String> listForMap = new ArrayList<>();
                        listForMap.add(reference.getDbkey());
                        additionalMap.put("additional_"+reference.getDbname(),listForMap);
                    }else {
                        List<String> listField = additionalMap.get("additional_"+reference.getDbname());
                        listField.add(reference.getDbkey());
                        additionalMap.put("additional_"+reference.getDbname(),listField);
                    }
                }
            });

            solrEntry.setTaxonomy(taxonomy);

            List<String> tissues = new ArrayList<>();
            List<String> diseases = new ArrayList<>();
            List<String> omicsTypes = new ArrayList<>();

            entry.getAdditionalFields().getField().forEach(field -> {
                if(field.getName().equals("pubmed_abstract")) {
                    solrEntry.setPubmedAbstract(field.getValue());
                } else if (field.getName().equals("view_count")) {
                    solrEntry.setViewCount(field.getValue());
                } else if (field.getName().equals("citation_count")) {
                    solrEntry.setCitationCount(field.getValue());
                } else if (field.getName().equals("search_count")) {
                    solrEntry.setSearchCount(field.getValue());
                } else if (field.getName().equals("tissue")) {
                    tissues.add(field.getValue());
                } else if (field.getName().equals("disease")) {
                    diseases.add(field.getValue());
                } else if (field.getName().equals("omics_type")) {
                    omicsTypes.add(field.getValue());
                } else {
                    if(null == additionalMap.get("additional_"+field.getName())) {
                        List<String> listForMap = new ArrayList<>();
                        listForMap.add(field.getValue());
                        additionalMap.put("additional_"+field.getName(),listForMap);
                    }else {
                        List<String> listField = additionalMap.get("additional_"+field.getName());
                        listField.add(field.getValue());
                        additionalMap.put("additional_"+field.getName(),listField);
                    }
                }
            });

            solrEntry.setTissue(tissues);
            solrEntry.setDisease(diseases);
            solrEntry.setOmicsType(omicsTypes);
//            entry.getCrossReferences().getRef().forEach(reference -> {
//                if(null == additionalMap.get("additional_"+reference.getDbname())){
//                    List<String> listForMap = new ArrayList<>();
//                    listForMap.add(reference.getDbkey());
//                    additionalMap.put("additional_"+reference.getDbname(),listForMap);
//                }else {
//                    List<String> listField = additionalMap.get("additional_"+reference.getDbname());
//                    listField.add(reference.getDbkey());
//                    additionalMap.put("additional_"+reference.getDbname(),listField);
//                }
//            });
//
//
//            entry.getAdditionalFields().getField().forEach(field -> {
//
//                // todo 这个文章简介是否只有一个？
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("pubmed_abstract")) {
//                    solrEntry.setPubmedAbstract(field.getValue());
//                }
//
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("view_count")) {
//                    solrEntry.setViewCount(field.getValue());
//                }
//
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("citation_count")) {
//                    solrEntry.setCitationCount(field.getValue());
//                }
//
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("search_count")) {
//                    solrEntry.setSearchCount(field.getValue());
//                }
//
//
//                // ???
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("search_count")) {
//                    solrEntry.setSearchCount(field.getValue());
//                }
//
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("tissue")) {
//                    solrEntry.setTissue(field.getValue());
//                }
//
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("disease")) {
//                    solrEntry.setDisease(field.getValue());
//                }
//
//                if(!StringUtils.isBlank(field.getName()) && field.getName().equals("omics_type")) {
//                    solrEntry.setOmicsType(field.getValue());
//                }
//
//
//                if(!StringUtils.isBlank(field.getName()) && !field.getName().equals("pubmed_abstract") && !field.getName().equals("search_count") && !field.getName().equals("citation_count") && !field.getName().equals("view_count")
//                && !field.getName().equals("omics_type") && !field.getName().equals("disease") && !field.getName().equals("tissue")
//                ){
//                    if(null == additionalMap.get("additional_"+field.getName())) {
//                        List<String> listForMap = new ArrayList<>();
//                        listForMap.add(field.getValue());
//                        additionalMap.put("additional_"+field.getName(),listForMap);
//                    }else {
//                        List<String> listField = additionalMap.get("additional_"+field.getName());
//                        listField.add(field.getValue());
//                        additionalMap.put("additional_"+field.getName(),listField);
//                    }
//                }
//
//            });
            solrEntry.setAdditionalFields(additionalMap);
            if(StringUtils.isBlank(solrEntry.getPubmedAbstract())) {
                solrEntry.setPubmedAbstract("Not Availiable");
            }

//            List<String> listOmicsType = new ArrayList<>();
//            List<String> listDataProtocol = new ArrayList<>();
//            List<String> listSampleProtocol = new ArrayList<>();
//            List<String> listRepository = new ArrayList<>();
//            List<String> listSpecies = new ArrayList<>();
//            List<String> listDisease = new ArrayList<>();
//            List<String> listTissue = new ArrayList<>();
//            List<String> listCellType = new ArrayList<>();
//            List<String> listFullDatasetLink = new ArrayList<>();
//            List<String> listSubmitter = new ArrayList<>();
//            List<String> listSubmitterMail = new ArrayList<>();
//            List<String> listSubmitterAffiliation = new ArrayList<>();
//            List<String> listInstrumentPlatform = new ArrayList<>();
//            List<String> listTechnologyType = new ArrayList<>();
//            List<String> listModification = new ArrayList<>();
//            List<String> listSubmitterKeywords = new ArrayList<>();
//            List<String> listQuantificationMethod = new ArrayList<>();
//            List<String> listSubmissionType = new ArrayList<>();
//            List<String> listSoftware = new ArrayList<>();
//            List<String> listPublication = new ArrayList<>();
//            List<String> listDatasetFile = new ArrayList<>();
//
//            entry.getAdditionalFields().getField().forEach(field -> {
//                switch (field.getName()){
//                    case "omics_type" :
//                        listOmicsType.add(field.getValue());
//                    case "data_protocol" :
//                        listDataProtocol.add(field.getValue());
//                    case "sample_protocol" :
//                        listSampleProtocol.add(field.getValue());
//                    case "repository" :
//                        listRepository.add(field.getValue());
//                    case "species" :
//                        listSpecies.add(field.getValue());
//                    case "disease" :
//                        listDisease.add(field.getValue());
//                    case "tissue" :
//                        listTissue.add(field.getValue());
//                    case "cell_type" :
//                        listCellType.add(field.getValue());
//                    case "full_dataset_link" :
//                        listFullDatasetLink.add(field.getValue());
//                    case "submitter" :
//                        listSubmitter.add(field.getValue());
//                    case "submitter_mail" :
//                        listSubmitterMail.add(field.getValue());
//                    case "submitter_affiliation" :
//                        listSubmitterAffiliation.add(field.getValue());
//                    case "instrument_platform" :
//                        listInstrumentPlatform.add(field.getValue());
//                    case "technology_type" :
//                        listTechnologyType.add(field.getValue());
//                    case "modification" :
//                        listModification.add(field.getValue());
//                    case "submitter_keywords" :
//                        listSubmitterKeywords.add(field.getValue());
//                    case "quantification_method" :
//                        listQuantificationMethod.add(field.getValue());
//                    case "submission_type" :
//                        listSubmissionType.add(field.getValue());
//                    case "software" :
//                        listSoftware.add(field.getValue());
//                    case "publication" :
//                        listPublication.add(field.getValue());
//                    case "dataset_file" :
//                        listDatasetFile.add(field.getValue());
//                }
//            });
//            solrEntry.setOmicsType(listOmicsType);
//            solrEntry.setDataProtocol(listDataProtocol);
//            solrEntry.setSampleProtocol(listSampleProtocol);
//            solrEntry.setRepository(listRepository);
//            solrEntry.setSpecies(listSpecies);
//            solrEntry.setDisease(listDisease);
//            solrEntry.setTissue(listTissue);
//            solrEntry.setCellType(listCellType);
//            solrEntry.setFullDatasetLink(listFullDatasetLink);
//            solrEntry.setSubmitter(listSubmitter);
//            solrEntry.setSubmitterMail(listSubmitterMail);
//            solrEntry.setSubmitterAffiliation(listSubmitterAffiliation);
//            solrEntry.setInstrumentPlatform(listInstrumentPlatform);
//            solrEntry.setTechnologyType(listTechnologyType);
//            solrEntry.setModification(listModification);
//            solrEntry.setSubmitterKeywords(listSubmitterKeywords);
//            solrEntry.setQuantificationMethod(listQuantificationMethod);
//            solrEntry.setSubmissionType(listSubmissionType);
//            solrEntry.setSoftware(listSoftware);
//            solrEntry.setPublication(listPublication);
//            solrEntry.setDatasetFile(listDatasetFile);

            solrEntries.add(solrEntry);
        });

        return solrEntries;
    }
}
