package cn.ncbsp.omicsdi.solr.services.Impl;

import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.model.Entries;
import cn.ncbsp.omicsdi.solr.model.Entry;
import cn.ncbsp.omicsdi.solr.queryModel.QueryModel;
import cn.ncbsp.omicsdi.solr.repo.SolrEntryRepo;
import cn.ncbsp.omicsdi.solr.services.ISolrEntryService;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import cn.ncbsp.omicsdi.solr.util.SolrEntryUtil;
import cn.ncbsp.omicsdi.solr.util.XmlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SolrEntryServiceImpl implements ISolrEntryService{
    @Autowired
    SolrEntryRepo solrEntryRepo;

    @Override
    public void saveSolrEntry(String xml, String core) {
        Database database = new Database();
        database = XmlHelper.xmlToObject(xml,database);
        Entries entries = database.getEntries();
        List<Entry> list =  entries.getEntry();
        List<SolrEntry> listSolrEntry = SolrEntryUtil.parseEntryToSolrEntry(list,database.getName());
        solrEntryRepo.saveEntryList(core,listSolrEntry);
    }

    @Override
    public void saveSolrEntries(String folderPath, String core,String backupPath) {
        File folder = new File(folderPath);
        if(folder.exists()){
            File[] files = folder.listFiles();
            if(files.length > 0) {
                for(File file: files) {
                    if(Pattern.matches(".*.xml", file.getName()) || Pattern.matches(".*.XML", file.getName())){
                        /*
                        可以把所有的solrEntry给加到列表里，但是我总觉得占用内存太大，不如用一次再说下一次
                         */
                        this.saveSolrEntry(file.getAbsolutePath(),core);
                        // 应该是移动到别的文件夹下留档
                        file.renameTo(new File(backupPath+"\\"+file.getName()));
                    } else {
                        continue;
                    }

                }
            }
        }
    }

    @Override
    public List getSolrEntries(String core, QueryModel queryModel, Class clazz) {
//        solrEntryRepo.getQueryResult(core,query,clazz);
        return null;
    }
}
