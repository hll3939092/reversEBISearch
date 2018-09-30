package cn.ncbsp.omicsdi.solr.repo.Impl;

import cn.ncbsp.omicsdi.solr.repo.SolrEntryRepo;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolrEntryRepoImpl implements SolrEntryRepo {

    @Autowired
    SolrTemplate solrTemplate;

    @Override
    public void saveEntry(String core, SolrEntry entry) {
        UpdateResponse updateResponse = solrTemplate.saveBean(core,entry);
        if(updateResponse.getStatus() != 0){
            // todo
            // exception
        } else {
            solrTemplate.commit(core);
        }

    }

    @Override
    public void saveEntryList(String core, List<SolrEntry> entries) {
        UpdateResponse updateResponse = solrTemplate.saveBeans(core,entries);
        if(updateResponse.getStatus() != 0){
            // todo
            // exception
        } else {
            solrTemplate.commit(core);
        }
    }
}
