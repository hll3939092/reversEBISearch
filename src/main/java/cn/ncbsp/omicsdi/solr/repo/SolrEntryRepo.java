package cn.ncbsp.omicsdi.solr.repo;

import cn.ncbsp.omicsdi.solr.model.Entry;
import cn.ncbsp.omicsdi.solr.model.Money;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface SolrEntryRepo {
    void saveEntry(String core, SolrEntry entry);
    void saveEntryList(String core, List<SolrEntry> entries);
}
