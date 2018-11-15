package cn.ncbsp.omicsdi.solr.scheduler;

import cn.ncbsp.omicsdi.solr.controller.DatasetController;
import cn.ncbsp.omicsdi.solr.services.ISolrEntryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

public class ImportJob {

    private static final Logger logger = LoggerFactory.getLogger(ImportJob.class);

    @Autowired
    ISolrEntryService iSolrEntryService;

    private String folderPath;
    private String solrCore;
    private String backupPath;

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getSolrCore() {
        return solrCore;
    }

    public void setSolrCore(String solrCore) {
        this.solrCore = solrCore;
    }

    public ImportJob() {

    }

    public void importData() {
        logger.debug("start");
        iSolrEntryService.saveSolrEntries(folderPath, solrCore, backupPath);
        logger.debug("end");
    }

}
