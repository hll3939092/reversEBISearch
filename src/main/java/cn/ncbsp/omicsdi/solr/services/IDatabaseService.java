package cn.ncbsp.omicsdi.solr.services;

import cn.ncbsp.omicsdi.solr.model.Database;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public interface IDatabaseService {
    public String indexSolrData(String collection, SolrInputDocument solrInputDocument);
    void clearSolrTemplate();
//    void deleteDocument(String collection,String param);
}
