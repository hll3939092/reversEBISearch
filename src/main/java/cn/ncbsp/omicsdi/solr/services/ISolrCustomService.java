package cn.ncbsp.omicsdi.solr.services;

import cn.ncbsp.omicsdi.solr.model.Suggestions;
import cn.ncbsp.omicsdi.solr.solrmodel.SimilarResult;
import cn.ncbsp.omicsdi.solr.solrmodel.TermResult;

import java.util.Map;

public interface ISolrCustomService {

    Map<String, Suggestions> getSuggestResult(String core, Map<String,String[]> paramMap);
    Suggestions getSuggestResultWithSuggester(String core, String suggester, Map<String,String[]> paramMap);
    SimilarResult getSimilarResult(String core, Map<String,String[]> paramMap);
    TermResult getFrequentlyTerms(String core,Map<String,String[]> paramMap);
}
