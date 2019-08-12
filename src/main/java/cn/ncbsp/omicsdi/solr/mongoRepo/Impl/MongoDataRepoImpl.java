package cn.ncbsp.omicsdi.solr.mongoRepo.Impl;

import cn.ncbsp.omicsdi.solr.model.AdditionalFields;
import cn.ncbsp.omicsdi.solr.model.CrossReferences;
import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.model.Reference;
import cn.ncbsp.omicsdi.solr.mongoRepo.IMongoDataRepo;
import cn.ncbsp.omicsdi.solr.mongoRepo.mongoModel.DatabaseDetail;
import cn.ncbsp.omicsdi.solr.mongoRepo.mongoModel.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MongoDataRepoImpl implements IMongoDataRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Database getAllDatasets(Query query, String databaseName) {

        List<Dataset> list = mongoTemplate.find(query, Dataset.class);
        System.out.println("xx");
        List<cn.ncbsp.omicsdi.solr.model.Entry> entryList = list.stream().map(x -> {
            cn.ncbsp.omicsdi.solr.model.Entry entry = new cn.ncbsp.omicsdi.solr.model.Entry();
            Map<String, Set<String>> crossReferencesMap = x.getCrossReferences();
            entry.setId(x.getAccession());
            entry.setAcc(x.getAccession());
            entry.setName(x.getName());
            entry.setDescription(x.getDescription());
            List<Reference> references = new ArrayList<>();
            for (String key : crossReferencesMap.keySet()) {
                for (String value : crossReferencesMap.get(key)) {
                    Reference reference = new Reference();
                    reference.setDbkey(key);
                    reference.setDbname(value);
                    references.add(reference);
                }
            }
            Map<String, Set<String>> additionalMap = x.getAdditional();
            List<cn.ncbsp.omicsdi.solr.model.Field> additionalFields = new ArrayList<>();
            for (String key : additionalMap.keySet()) {
                for (String value : additionalMap.get(key)) {
                    cn.ncbsp.omicsdi.solr.model.Field field = new cn.ncbsp.omicsdi.solr.model.Field();
                    field.setName(key);
                    field.setValue(value);
                    additionalFields.add(field);
                }
            }
            CrossReferences crossReferences = new CrossReferences();
            crossReferences.setRef(references);
            entry.setCrossReferences(crossReferences);
            AdditionalFields additionalFields1 = new AdditionalFields();
            additionalFields1.setField(additionalFields);
            entry.setAdditionalFields(additionalFields1);
            return entry;
        }).collect(Collectors.toList());

        Database database = new Database();
        database.setEntries(entryList);
        database.setName(databaseName);
        database.setEntryCount(entryList.size());
        database.setReleaseDate(new Date().toString());
        return database;
    }

    @Override
    public Map<String, Integer> getAllDatabase() {

        List<DatabaseDetail> databaseDetailList = mongoTemplate.findAll(DatabaseDetail.class);
        List<String> databases = databaseDetailList.stream().map(x -> {
            return x.getRepository();
        }).collect(Collectors.toList());

        Map<String, Integer> map = new ConcurrentHashMap<>();
        for (String database : databases) {
            long l = mongoTemplate.count(new Query(Criteria.where("database").is(database)), "datasets.dataset");
            map.put(database, (int) l);
        }
        return map;
    }
}
