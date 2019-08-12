package cn.ncbsp.omicsdi.solr.services;

import cn.ncbsp.omicsdi.solr.model.DomainList;
import cn.ncbsp.omicsdi.solr.queryModel.IQModel;
import cn.ncbsp.omicsdi.solr.solrmodel.QueryResult;

public interface IStatisticsService {
    DomainList getQueryResult();
}
