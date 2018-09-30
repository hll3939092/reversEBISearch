package cn.ncbsp.omicsdi.solr.services;

import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;

public interface ISolrEntryService {
    void saveSolrEntry(String xml, String core, Database database);
}
