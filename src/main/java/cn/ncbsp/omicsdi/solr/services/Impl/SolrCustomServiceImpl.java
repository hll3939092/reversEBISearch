package cn.ncbsp.omicsdi.solr.services.Impl;

import cn.ncbsp.omicsdi.solr.model.Suggestions;
import cn.ncbsp.omicsdi.solr.services.ISolrCustomService;
import cn.ncbsp.omicsdi.solr.solrmodel.Entry;
import cn.ncbsp.omicsdi.solr.solrmodel.SimilarResult;
import cn.ncbsp.omicsdi.solr.solrmodel.TermResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.Suggestion;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.params.TermsParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static cn.ncbsp.omicsdi.solr.constant.Constants.REQUEST_HANDLER_SUGGEST;


/**
 * Created by Xpon on 2018/11/08
 */

@Service
public class SolrCustomServiceImpl implements ISolrCustomService {


    @Autowired
    SolrClient solrClient;


    @Override
    public Map<String, Suggestions> getSuggestResult(String core, Map<String,String[]> paramMap) {
        if(paramMap.isEmpty()){
            // todo exception
            return null;
        }

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler(REQUEST_HANDLER_SUGGEST);
        Set<String> set = paramMap.keySet();
        for(String key : set) {
            solrQuery.setParam(key,paramMap.get(key));
        }

        QueryResponse queryResponse = null;
        Map<String, List<Suggestion>> suggestionMap = null;


        try {
            queryResponse = solrClient.query(core, solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        suggestionMap = queryResponse.getSuggesterResponse().getSuggestions();

        if(suggestionMap.isEmpty()){
            // todo exception
            return null;
        }

        Set<String> suggestionKeySet = suggestionMap.keySet();
        Map<String, Suggestions> suggestionsMap = new HashMap<>();
        for(String key : suggestionKeySet) {
            Suggestions suggestions = new Suggestions();
            cn.ncbsp.omicsdi.solr.model.Suggestion[] suggestionCollections = suggestionMap.get(key).stream().map(x -> {
                cn.ncbsp.omicsdi.solr.model.Suggestion suggestion = new cn.ncbsp.omicsdi.solr.model.Suggestion();
                suggestion.setSuggestion(x.getTerm());
                return suggestion;
            }).toArray(cn.ncbsp.omicsdi.solr.model.Suggestion[]::new);
            suggestions.setEntries(suggestionCollections);
            suggestionsMap.put(key, suggestions);
        }

        if(suggestionsMap.isEmpty()){
            // todo exception
            return null;
        }


        return suggestionsMap;

    }

    @Override
    public Suggestions getSuggestResultWithSuggester(String core, String suggester, Map<String, String[]> paramMap) {
        Suggestions suggestions = getSuggestResult(core, paramMap).get(suggester);
        return suggestions;
    }


    @Override
    public SimilarResult getSimilarResult(String core, Map<String,String[]> paramMap) {
        if(paramMap.isEmpty()){
            // todo exception
            return null;
        }

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/" + MoreLikeThisParams.MLT);
        Set<String> set = paramMap.keySet();
        for(String key : set) {
            solrQuery.setParam(key,paramMap.get(key));
        };

        QueryResponse queryResponse = null;
        Map<String, Map<String, Float>> namedList = null;
        SolrDocumentList solrDocumentList = null;

        try {
            queryResponse = solrClient.query(core, solrQuery);
            solrDocumentList = queryResponse.getResults();
            namedList = queryResponse.getResponse().asMap(queryResponse.getResponse().size());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimilarResult similarResult = new SimilarResult();
        Map<String, Float> map = namedList.get("interestingTerms");
        Float score = 0.0F;
        for(String key : map.keySet()) {
            score += map.get(key);
        }

        List<Entry> entryList = new ArrayList<>();

        for(SolrDocument solrDocument : solrDocumentList) {
            Entry entry = new Entry();
            entry.setId((String) solrDocument.get("id"));
            entry.setScore(score.toString());
            entryList.add(entry);
        }

        Entry[] entries = entryList.stream().toArray(Entry[] :: new);

        similarResult.setEntries(entries);
        return similarResult;
    }

    @Override
    public TermResult getFrequentlyTerms(String core, Map<String, String[]> paramMap) {
        if(paramMap.isEmpty()){
            // todo exception
            return null;
        }


        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/" + TermsParams.TERMS);

        Set<String> set = paramMap.keySet();
        for(String key : set) {
            solrQuery.setParam(key,paramMap.get(key));
        };

        QueryResponse queryResponse = null;
        TermsResponse termsResponse = null;
        Map<String, Map<String, Integer>> namedList = null;
        SolrDocumentList solrDocumentList = null;

        try {
            queryResponse = solrClient.query(core, solrQuery);
            termsResponse = queryResponse.getTermsResponse();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<TermsResponse.Term>> termMap = termsResponse.getTermMap();

        return null;
    }
}
