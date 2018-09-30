package cn.ncbsp.omicsdi.solr.services.Impl;

import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.model.Entries;
import cn.ncbsp.omicsdi.solr.model.Entry;
import cn.ncbsp.omicsdi.solr.repo.SolrEntryRepo;
import cn.ncbsp.omicsdi.solr.services.ISolrEntryService;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import cn.ncbsp.omicsdi.solr.util.SolrEntryUtil;
import cn.ncbsp.omicsdi.solr.util.XmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolrEntryServiceImpl implements ISolrEntryService{
    @Autowired
    SolrEntryRepo solrEntryRepo;

    @Override
    public void saveSolrEntry(String xml, String core, Database database) {
        database = XmlHelper.xmlToObject(xml,database);
        Entries entries = database.getEntries();
        List<Entry> list =  entries.getEntry();
        List<SolrEntry> listSolrEntry = SolrEntryUtil.parseEntryToSolrEntry(list,database.getName());
        solrEntryRepo.saveEntryList(core,listSolrEntry);
    }
}
