package cn.ncbsp.omicsdi.solr.util;

import cn.ncbsp.omicsdi.solr.model.Entry;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;

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

            Set<String> setDbName = new HashSet<String>();
            List<String> listDbKey = new ArrayList<>();
            entry.getCrossReferences().getRef().forEach(reference -> {
                setDbName.add(reference.getDbname());
                listDbKey.add(reference.getDbkey());
            });
            solrEntry.setDbname(setDbName);
            solrEntry.setDbkey(listDbKey);

            Map<String,List<String>> additionalMap = new HashMap<>();
            entry.getAdditionalFields().getField().forEach(field -> {
                if(null == additionalMap.get(field.getName())) {
                    List<String> listForMap = new ArrayList<>();
                    listForMap.add(field.getValue());
                    additionalMap.put("additional_"+field.getName(),listForMap);
                }else {
                    additionalMap.get("additional_"+field.getName()).add(field.getValue());
                }
            });
            solrEntry.setAdditionalFields(additionalMap);

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
