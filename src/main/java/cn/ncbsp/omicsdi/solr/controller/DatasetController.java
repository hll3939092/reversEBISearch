package cn.ncbsp.omicsdi.solr.controller;

import cn.ncbsp.omicsdi.solr.constant.Constants;
import cn.ncbsp.omicsdi.solr.constant.FieldConstants;
import cn.ncbsp.omicsdi.solr.model.Domain;
import cn.ncbsp.omicsdi.solr.model.DomainList;
import cn.ncbsp.omicsdi.solr.model.Money;
//import cn.ncbsp.omicsdi.solr.services.IMoneyService;
import cn.ncbsp.omicsdi.solr.model.Suggestions;
import cn.ncbsp.omicsdi.solr.queryModel.QueryModel;
import cn.ncbsp.omicsdi.solr.services.IAutocompleteService;
import cn.ncbsp.omicsdi.solr.services.IDomainSearchService;
import cn.ncbsp.omicsdi.solr.services.ISolrCustomService;
import cn.ncbsp.omicsdi.solr.services.ISolrFacetService;
import cn.ncbsp.omicsdi.solr.services.Impl.AutocompleteServiceImpl;
import cn.ncbsp.omicsdi.solr.solrmodel.*;
import cn.ncbsp.omicsdi.solr.util.ParamStringUtil;
import cn.ncbsp.omicsdi.solr.util.SolrClientBuilderUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.Suggestion;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.params.TermsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/")
public class DatasetController {

    private static final Logger logger = LoggerFactory.getLogger(DatasetController.class);

    @Autowired
    IDomainSearchService domainSearchService;

    @Autowired
    IAutocompleteService autocompleteService;

    @Autowired
    ISolrCustomService solrCustomService;

    @Autowired
    ISolrFacetService solrFacetService;


//    @Autowired
//    IMoneyService moneyService;

    // EBI Search RESTful Web Services R.
    // Top terms

    // @return TermResult
    // 可以使用 对应DatasetWSClient getFrequentlyTerms 方法
    @ApiOperation(value="DatasetWSClient getFrequentlyTerms")
    @RequestMapping(value = "/{domain}/topterms/{fieldid}")
    public TermResult getFrequentlyTerms (
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "fieldid") String fieldid
    ) {
        Map<String,String[]> paramMap = new HashMap<>();

        // term fields
        String[] fields = fieldid.split(",");
        paramMap.put(TermsParams.TERMS_FIELD, fields);


        TermResult termResult = solrCustomService.getFrequentlyTerms(domain, paramMap);

        return termResult;
    }



    // All EBI search
//    @RequestMapping(value = "/")
//    public String getAllEBISearch(
//            @RequestParam(value = "query", required = false, defaultValue = "20") int query,
//            @RequestParam(value = "format",required = false, defaultValue = "") String format,
//            @RequestParam(value = "excludezero", required = false, defaultValue = "") String excludezero
//    ) {
//        return "ok";
//    }

    // Domain search

    // 对应DatasetWsClient getDatasets
    // sortfield+order 形成排序
    @ApiOperation(value="DatasetWSClient getDatasets")
    @RequestMapping(value = "/{domain}",method = RequestMethod.GET)
    public QueryResult getDomains(
            @PathVariable(value = "domain") String domain,
            @RequestParam(value = "query",required = false, defaultValue = "") String query,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "start",required = false, defaultValue = "0") int start,
            @RequestParam(value = "size",required = false, defaultValue = "10") int size,
            @RequestParam(value = "facetcount",required = false, defaultValue = "20") int facetcount,
            @RequestParam(value = "sortfield",required = false, defaultValue = "") String sortfield,
            @RequestParam(value = "order",required = false, defaultValue = "") String order,
            @RequestParam(value = "format",required = false, defaultValue = "JSON") String format,
            @RequestParam(value = "sort",required = false, defaultValue = "") String sort
//            @RequestParam(value = "domain_source",required = false, defaultValue = "") String domainSource,
//            @RequestParam(value = "facetfields",required = false, defaultValue = "") String facetfields

    ) {
//        if(StringUtils.isEmpty(facetfields)) {
            QueryModel queryModel = new QueryModel();
            queryModel.setDomain(domain);
            queryModel.setQuery(query);
            queryModel.setFields(ParamStringUtil.splitString(fields));
            queryModel.setFacets(ParamStringUtil.splitString(fields));
            queryModel.setStart(start);
            queryModel.setSize(size);
            queryModel.setFacetcount(facetcount);

            queryModel.setSortfield(sortfield);
            queryModel.setOrder(order);
            queryModel.setFormat(format);
            queryModel.setSort(sort);

            QueryResult queryResult = domainSearchService.getQueryResult(queryModel);


//        List<Money> lmoney = moneyService.getMoneyByName(domain);
//        String money = lmoney.get(0).toString();
            return queryResult;
//        } else {
//
//            Map<String, String[]> paramMap = new HashMap<>();
//            String[] databases = query.substring(15,query.length()-1).split(" OR ");
//            paramMap.put("databases", databases);
//
//            String[] facetsfield = facetfields.split(",");
//            paramMap.put("facetfields", facetsfield);
//
//            paramMap.put("facetcount", new String[]{String.valueOf(facetcount)});
//
//            FacetList facetList = solrFacetService.getFacetEntriesByDomains(domain, paramMap);
//
//
//            return null;
//        }

    }

    @ApiOperation(value="FacetWSClient getFacet")
    @RequestMapping(value = "/{domain}/facet",method = RequestMethod.GET)
    public FacetList getFacetDomains(
            @PathVariable(value = "domain") String domain,
            @RequestParam(value = "query",required = false, defaultValue = "") String query,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "start",required = false, defaultValue = "0") int start,
            @RequestParam(value = "size",required = false, defaultValue = "10") int size,
            @RequestParam(value = "facetcount",required = false, defaultValue = "20") int facetcount,
            @RequestParam(value = "sortfield",required = false, defaultValue = "") String sortfield,
            @RequestParam(value = "order",required = false, defaultValue = "") String order,
            @RequestParam(value = "format",required = false, defaultValue = "JSON") String format,
            @RequestParam(value = "sort",required = false, defaultValue = "") String sort,
            @RequestParam(value = "facetfields",required = false, defaultValue = "") String facetfields
    ) {
        Map<String, String[]> paramMap = new HashMap<>();
        String[] databases = query.substring(15,query.length()-1).split(" OR ");
        if(databases.length < 1) {
            databases = new String[]{"*"};
        }
        paramMap.put("databases", databases);

        String[] facetsfield = facetfields.split(",");
        if(databases.length < 1) {
            // todo exception
        }
        paramMap.put("facetfields", facetsfield);

        paramMap.put("facetcount", new String[]{String.valueOf(facetcount)});

        FacetList facetList = solrFacetService.getFacetEntriesByDomains(domain, paramMap);
        return facetList;
    }

    // 对应DomainWsClient getDomainByName
    // todo Domainlist 数据不完整，缺少关键数据
    @RequestMapping(value = "/{domain}", method = RequestMethod.POST)
    public DomainList getDomainList (@PathVariable(value = "domain") String domain) {
        QueryModel queryModel = new QueryModel();
        queryModel.setQuery("*:*");
        queryModel.setFacets(new String[]{"database"});
        QueryResult queryResult = domainSearchService.getQueryResult(queryModel);

        Facet[] facets = queryResult.getFacets();
        FacetValue[] facetValues = null;
        for (Facet facet : facets ) {
            if(facet.getId().equals("database")) {
                facetValues = facet.getFacetValues();
            }
        }
        DomainList domainList = new DomainList();
        List<Domain> domains = new ArrayList<Domain>();

        for(FacetValue facetValue : facetValues) {
            Domain domainS = new Domain();
            domainS.setId(facetValue.getLabel());
            domainS.setName(facetValue.getLabel());
            domains.add(domainS);
        }

        Domain[] domainCollection = new Domain[domains.size()];
        domainCollection = domains.toArray(domainCollection);

        domainList.setList(domainCollection);

        return domainList;
    }

    // 对应DatasetWsClient getDatasetsById
    // Entry retrieval
    @RequestMapping(value = "/{domain}/entry/{entryids}", method = RequestMethod.GET)
    public QueryResult getEntries(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryids") String entryids,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "fieldurl",required = false, defaultValue = "false") boolean fieldurl,
            @RequestParam(value = "viewurl",required = false, defaultValue = "false") boolean viewurl,
            @RequestParam(value = "format",required = false, defaultValue = "JSON") String format
    ) {
        QueryModel queryModel = new QueryModel();
        queryModel.setDomain(domain);
        queryModel.setEntryids(ParamStringUtil.splitString(entryids));
        queryModel.setFields(ParamStringUtil.splitString(fields));
        queryModel.setFieldurl(fieldurl);
        queryModel.setViewurl(viewurl);
        queryModel.setFormat(format);

        QueryResult queryResult = domainSearchService.getQueryResult(queryModel);

        return queryResult;
    }


    // 对应DatasetWsClient getDatasetsById
    // 但是这个方法好像没用

    @RequestMapping(value = "/{accIds}/entry/{entryids}", method = RequestMethod.GET)
    public QueryResult getPublicationList(@PathVariable(value = "accIds") String[] accIds,
                                          @PathVariable(value = "entryids") String[] entryids)
    {
        QueryModel queryModel = new QueryModel();
        StringBuffer stringBuffer = new StringBuffer("*:*");
        for(String acc : accIds) {
            stringBuffer.append("AND acc:" + acc);
        }
        queryModel.setQuery(stringBuffer.toString());
        QueryResult queryResult = domainSearchService.getQueryResult(queryModel);
        return queryResult;
    }

    // auto complete
    // DictionaryClient  getWordsDomains
    @RequestMapping(value = "/{domain}/autocomplete")
    public Suggestions autoComplete(
            @PathVariable(value = "domain") String domain,
            @RequestParam(value = "term") String term,
            @RequestParam(value = "formatted",required = false, defaultValue = "false") boolean formatted,
            @RequestParam(value = "fields",required = false, defaultValue = "JSON") String format
    ) {

        // 仅做单词的联想
        String regex = "[a-zA-Z0-9]+";
        Boolean match = Pattern.matches(regex,term);

        if(match) {
            Suggestions suggestions = autocompleteService.getSuggestions(domain,term, Suggestion.class);
            return suggestions;
        }else {
            // todo Exception
            return null;
        }


    }

    // Cross-reference search
    @RequestMapping(value = "/{domain}/entry/{entryids}/xref/{targetdomainid}")
    public String crossReferenceSearch(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryids") String entryids,
            @PathVariable(value = "targetdomainid") String targetdomainid,
            @RequestParam(value = "size",required = false, defaultValue = "20") int size,
            @RequestParam(value = "start",required = false, defaultValue = "0") int start,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "fieldurl",required = false, defaultValue = "") boolean fieldurl,
            @RequestParam(value = "viewurl",required = false, defaultValue = "") boolean viewurl,
            @RequestParam(value = "facets",required = false, defaultValue = "") String facets,
            @RequestParam(value = "facetfields",required = false, defaultValue = "") String facetfields,
            @RequestParam(value = "facetcount",required = false, defaultValue = "") int facetcount,
            @RequestParam(value = "format",required = false, defaultValue = "") String format,
            @RequestParam(value = "entryattrs",required = false, defaultValue = "") String entryattrs
    ) {
        return "ok";
    }

    // Finding domains referred by an entry
    @RequestMapping(value = "/{domain}/entry/{entryid}/xref")
    public String findDomainReferredByEntry(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryid") String entryid,
            @RequestParam(value = "format",required = false, defaultValue = "") String format
    ) {
        return "ok";
    }

    // Finding domains referred by a domain
    @RequestMapping(value = "/{domain}/xref")
    public String findDomainReferredByDomain(
            @PathVariable(value = "domain") String domain,
            @RequestParam(value = "format",required = false, defaultValue = "") String format
    ) {
        return "ok";
    }


    // More like this in a same domain
    @RequestMapping(value = "/{domain}/entry/{entryid}/morelikethis")
    public String moreLikeSameDomain(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryid") String entryid,
            @RequestParam(value = "size",required = false, defaultValue = "") int size,
            @RequestParam(value = "start",required = false, defaultValue = "") int start,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "fieldurl",required = false, defaultValue = "") boolean fieldurl,
            @RequestParam(value = "viewurl",required = false, defaultValue = "") boolean viewurl,
            @RequestParam(value = "mltfields",required = false, defaultValue = "") String mltfields,
            @RequestParam(value = "mintermfreq",required = false, defaultValue = "") int mintermfreq,
            @RequestParam(value = "mindocfreq",required = false, defaultValue = "") int mindocfreq,
            @RequestParam(value = "maxqueryterm",required = false, defaultValue = "") int maxqueryterm,
            @RequestParam(value = "excludes",required = false, defaultValue = "") String excludes,
            @RequestParam(value = "excludesets",required = false, defaultValue = "") String excludesets,
            @RequestParam(value = "format",required = false, defaultValue = "") String format,
            @RequestParam(value = "entryattrs",required = false, defaultValue = "") String entryattrs
    ) {
        Map<String,String[]> paramMap = new HashMap<>();

        // 查询文档的库与id
        List<String> list = new ArrayList<>();
        list.add(FieldConstants.FIELD_ID + FieldConstants.PARAM + domain);
        list.add(FieldConstants.DATABASE_ID + FieldConstants.PARAM + entryid);
        String[] q = list.parallelStream().toArray(String[] :: new);
        paramMap.put("q", q);

        // 设置关联相似性field
//        paramMap.put(MoreLikeThisParams.PREFIX + "field", new String[]{targetdomainid});


        // 获取结论数据
        SimilarResult similarResult = solrCustomService.getSimilarResult(domain,paramMap);
        return "ok";
    }

    // More like this
    // @return SimilarResult
    // 通过database+id找到文章，检查固定field的相似性如Constants.MORELIKE_FIELDS
    // 其他属性用solr config.xml决定（size和分页暂时不知道怎么办）
    @RequestMapping(value = "/{domain}/entry/{entryid}/morelikethis/{targetdomainid}")
    public SimilarResult moreLikeThis(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryid") String entryid,
            @PathVariable(value = "targetdomainid") String targetdomainid,
            @RequestParam(value = "size",required = false, defaultValue = "10") int size,
            @RequestParam(value = "start",required = false, defaultValue = "0") int start,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "fieldurl",required = false, defaultValue = "false") boolean fieldurl,
            @RequestParam(value = "viewurl",required = false, defaultValue = "false") boolean viewurl,
            @RequestParam(value = "mltfields",required = false, defaultValue = "") String mltfields,// []
            @RequestParam(value = "mintermfreq",required = false, defaultValue = "0") int mintermfreq,
            @RequestParam(value = "mindocfreq",required = false, defaultValue = "0") int mindocfreq,
            @RequestParam(value = "maxqueryterm",required = false, defaultValue = "0") int maxqueryterm,
            @RequestParam(value = "excludes",required = false, defaultValue = "") String excludes,
            @RequestParam(value = "excludesets",required = false, defaultValue = "") String excludesets,
            @RequestParam(value = "format",required = false, defaultValue = "") String format,
            @RequestParam(value = "entryattrs",required = false, defaultValue = "") String entryattrs
    ) {
        Map<String,String[]> paramMap = new HashMap<>();

        // 查询文档的库与id
        List<String> list = new ArrayList<>();
        list.add(FieldConstants.FIELD_ID + FieldConstants.PARAM + domain);
        list.add(FieldConstants.DATABASE_ID + FieldConstants.PARAM + entryid);
        String[] q = list.parallelStream().toArray(String[] :: new);
        paramMap.put("q", q);

        // 设置关联相似性field
        paramMap.put(MoreLikeThisParams.PREFIX + "field", new String[]{targetdomainid});


        // 获取结论数据
        SimilarResult similarResult = solrCustomService.getSimilarResult(domain,paramMap);
//        QueryModel queryModel = new QueryModel();
//        queryModel.setDomain(domain);
//        queryModel.setEntryid(entryid);
//        queryModel.setTargetdomainid(targetdomainid);
//        queryModel.setSize(size);
//        queryModel.setStart(start);
//        queryModel.setFields(ParamStringUtil.splitString(fields));
//        queryModel.setFieldurl(fieldurl);
//        queryModel.setViewurl(viewurl);
//        queryModel.setMltfields(ParamStringUtil.splitString(mltfields));
//        queryModel.setMintermfreq(mintermfreq);
//        queryModel.setMindocfreq(mindocfreq);
//        queryModel.setMaxqueryterm(maxqueryterm);
//        queryModel.setExcludes(ParamStringUtil.splitString(excludes));
//        queryModel.setExcludesets(excludesets);
//        queryModel.setFormat(format);
//        queryModel.setEntryattrs(entryattrs);
//        QueryResult queryResult = domainSearchService.getQueryResult(queryModel);
//        SimilarResult similarResult = new SimilarResult();
//        similarResult.setEntries(queryResult.getEntries());
        return similarResult;
    }


    // 定义所有API
    // DatasetWS
    /*
        getDatasets
        solr_core
        %s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&format=JSON
        %s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&sortfield=%s&order=%s&format=JSON

        getDatasetsById
        %s://%s/ebisearch/ws/rest/%s/entry/%s?fields=%s&format=JSON

        getFrequentlyTerms
        %s://%s/ebisearch/ws/rest/%s/topterms/%s?excludesets=omics_stopwords&size=%s&excludes=%s&format=JSON

        getSimilarProjects
        %s://%s/ebisearch/ws/rest/%s/entry/%s/morelikethis/omics?mltfields=%s&excludesets=omics_stopwords&entryattrs=score&format=JSON
    */
//
//    @RequestMapping(value = "/{core}", method = RequestMethod.GET)
//    public String getDatasets(
//            @PathVariable(value = "core") String core,
//            @RequestParam(value = "query", required = false, defaultValue = "") String query,
//            @RequestParam(value = "fields", required = false, defaultValue = "") String fields,
//            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
//            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
//            @RequestParam(value = "facetfields",required = false,defaultValue = "") String facetfields,
//            @RequestParam(value = "facetcount", required = false, defaultValue = "20") int facetcount,
//            @RequestParam(value = "sortfield", required = false, defaultValue = "") String sortfield,
//            @RequestParam(value = "order", required = false, defaultValue = "") String order,
//            @RequestParam(value = "format", required = true, defaultValue = "JSON") String format
//    ) {
//        return core;
//    }
//
//    // FacetWS
//    /*
//        getFacetEntriesByDomains
//        %s://%s/ebisearch/ws/rest/%s?query=domain_source:(%s)&facetfields=%s&facetcount=%s&size=0&format=JSON
//     */
//    // 放在了getDatasets中
////    @RequestMapping(value = "/{core}")
////    public String getFacetEntriesByDomains(
////            @PathVariable(value = "core") String core,
////            @RequestParam(value = "query",required = false,defaultValue = "") String query,
////
////            @RequestParam(value = "facetcount",required = false,defaultValue = "") String facetcount,
////            @RequestParam(value = "size",required = false,defaultValue = "0") int size,
////            @RequestParam(value = "format",required = false,defaultValue = "JSON") String format
////    ) {
////        return core+query;
////    }
//
//    @RequestMapping(value = "/{core}/entry/{document}")
//    public String getDatasetsById (
//            @PathVariable(value = "core") String core,
//            @PathVariable(value = "document") String document,
//            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
//            @RequestParam(value = "format", required = true, defaultValue = "JSON") String format
//    ) {
//        return core+"1";
//    }






//    @RequestMapping(value = "/{core}/entry/{document}/morelikethis/omics")
//    public String getSimilarProjects (
//            @PathVariable(value = "core") String core,
//            @PathVariable(value = "document") String document,
//            @RequestParam(value = "mltfields",required = false, defaultValue = "") String mltfields,
//            @RequestParam(value = "excludesets",required = false, defaultValue = "") String excludesets,
//            @RequestParam(value = "entryattrs",required = false, defaultValue = "") String entryattrs,
//            @RequestParam(value = "format", required = true, defaultValue = "JSON") String format
//    ) {
//        return core;
//    }
//    // Dictionary
//    /*
//        getWordsDomains
//        %s://%s/ebisearch/ws/rest/%s/autocomplete?term=%s&format=JSON
//     */
//
//    @RequestMapping(value = "/{core}/autocomplete")
//    public String getWordsDomains (
//            @PathVariable(value = "core") String core,
//            @RequestParam(value = "term",required = false, defaultValue = "") String term,
//            @RequestParam(value = "format", required = true, defaultValue = "JSON") String format
//    ) {
//        return core;
//    }
//
//
//    // DomainWS
//    /*
//        getDomainByName
//        %s://%s/ebisearch/ws/rest/%s?format=JSON
//     */
////    @RequestMapping(value = "/{core}")
////    public String getDomainByName(
////            @PathVariable(value = "core") String core,
////            @RequestParam(value = "format", required = true, defaultValue = "JSON") String format
////    ) {
////        return core;
////    }
//
//
//    // Citation
//    /*
//        getCitations
//        %s://%s/europepmc/webservices/rest/search?query=%s&format=JSON&resulttype=idlist&pageSize=%s&cursorMark=%s
//     */
//    @RequestMapping(value = "/search")
//    public String getCitations(
//            @RequestParam(value = "query",required = false,defaultValue = "") String query,
//            @RequestParam(value = "format",required = false,defaultValue = "JSON") String format,
//            @RequestParam(value = "resulttype",required = false,defaultValue = "") String resulttype,
//            @RequestParam(value = "pageSize",required = false,defaultValue = "20") int pageSize,
//            @RequestParam(value = "cursorMark",required = false,defaultValue = "") String cursorMark
//    ) {
//        return query;
//    }
//
//
//
//
//    // PublicationWs
//    /*
//        getPublications
//        %s://%s/ebisearch/ws/rest/pubmed/entry/%s?fields=%s&format=JSON
//     */
//
//    @RequestMapping(value = "/pubmed/entry/{documents}")
//    public String getPublications (
//            @PathVariable(value = "documents") String documents,
//            @RequestParam(value = "fields",required = false,defaultValue = "") String fields,
//            @RequestParam(value = "format",required = false,defaultValue = "JSON") String format
//
//    ) {
//        return "ok";
//    }
}
