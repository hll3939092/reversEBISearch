package cn.ncbsp.omicsdi.solr.services;

import cn.ncbsp.omicsdi.solr.solrmodel.FacetList;

import java.util.Map;

public interface ISolrFacetService {
    FacetList getFacetEntriesByDomains(String core, Map<String,String[]> paramMap);
}
