package cn.ncbsp.omicsdi.solr.services;

import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.queryModel.QueryModel;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;

import java.util.List;

public interface ISolrEntryService<T> {
    void saveSolrEntry(String xml, String core);
    void saveSolrEntries(String folderPath,String core,String backupPath);
    List<T> getSolrEntries(String core, QueryModel queryModel,Class<T> clazz);
}
