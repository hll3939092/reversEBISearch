package cn.ncbsp.omicsdi.solr.controller;

import cn.ncbsp.omicsdi.solr.model.Money;
//import cn.ncbsp.omicsdi.solr.services.IMoneyService;
import cn.ncbsp.omicsdi.solr.queryModel.QueryModel;
import cn.ncbsp.omicsdi.solr.services.IDomainSearchService;
import cn.ncbsp.omicsdi.solr.solrmodel.QueryResult;
import cn.ncbsp.omicsdi.solr.util.SolrClientBuilderUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class DatasetController {

    private static final Logger logger = LoggerFactory.getLogger(DatasetController.class);

    @Autowired
    IDomainSearchService domainSearchService;


//    @Autowired
//    IMoneyService moneyService;

    // EBI Search RESTful Web Services R.
    // Top terms
    @RequestMapping(value = "/{domain}/topterms/{fieldid}")
    public String getFrequentlyTerms (
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "fieldid") String fieldid,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size,
            @RequestParam(value = "excludes",required = false, defaultValue = "") String excludes,
            @RequestParam(value = "excludesets", required = false, defaultValue = "") String excludesets,
            @RequestParam(value = "format", required = true, defaultValue = "JSON") String format
    ) {
        return domain+"2";
    }



    // All EBI search
    public String getAllEBISearch(
            @RequestParam(value = "query", required = false, defaultValue = "20") int query,
            @RequestParam(value = "format",required = false, defaultValue = "") String format,
            @RequestParam(value = "excludezero", required = false, defaultValue = "") String excludezero
    ) {
        return "ok";
    }

    // Domain search
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
            @RequestParam(value = "sort",required = false, defaultValue = "") String sort,
            @RequestParam(value = "fieldurl",required = false, defaultValue = "false") boolean fieldurl,
            @RequestParam(value = "viewurl",required = false, defaultValue = "false") boolean viewurl,
            @RequestParam(value = "facets",required = false, defaultValue = "") String facets,
            @RequestParam(value = "facetfields",required = false, defaultValue = "") String facetfields,
            @RequestParam(value = "facetsdepth",required = false, defaultValue = "0") int facetsdepth,
            @RequestParam(value = "feedtitle",required = false, defaultValue = "") String feedtitle,
            @RequestParam(value = "feedmaxdays",required = false, defaultValue = "") String feedmaxdays,
            @RequestParam(value = "feedmaxdaysfield",required = false, defaultValue = "") String feedmaxdaysfield,
            @RequestParam(value = "hlfields",required = false, defaultValue = "") String hlfields,
            @RequestParam(value = "hlpretag",required = false, defaultValue = "") String hlpretag,
            @RequestParam(value = "hlposttag",required = false, defaultValue = "") String hlposttag,
            @RequestParam(value = "entryattrs",required = false, defaultValue = "") String entryattrs

    ) {
        QueryModel queryModel = new QueryModel();
        queryModel.setDomain(domain);

        if(fields.equals("") || null == fields) {
            queryModel.setFields(new String[]{});
        } else if (fields.indexOf(",") <= 0) {
            queryModel.setFields(new String[]{fields});
        } else {
            String[] fieldCollection = fields.split(",");
            queryModel.setFields(fieldCollection);
        }

        queryModel.setStart(start);
        queryModel.setSize(size);
        queryModel.setFacetcount(facetcount);

        queryModel.setSortfield(sortfield);
        queryModel.setOrder(order);
        queryModel.setFormat(format);
        queryModel.setSort(sort);
        queryModel.setFieldurl(fieldurl);
        queryModel.setViewurl(viewurl);

        if(facets.equals("") || null == facets) {
            queryModel.setFacets(new String[]{});
        } else if (facets.indexOf(",") <= 0) {
            queryModel.setFacets(new String[]{facets});
        } else {
            String[] facetsCollection = facets.split(",");
            queryModel.setFacets(facetsCollection);
        }

        if(facetfields.equals("") || null == facetfields) {
            queryModel.setFacetfields(new String[]{});
        } else if (facetfields.indexOf(",") <= 0) {
            queryModel.setFacetfields(new String[]{facetfields});
        } else {
            String[] facetfieldsCollection = facetfields.split(",");
            queryModel.setFacetfields(facetfieldsCollection);
        }

        queryModel.setFacetsdepth(facetsdepth);
        queryModel.setFeedtitle(feedtitle);
        queryModel.setFeedmaxdays(feedmaxdays);
        queryModel.setFeedmaxdaysfield(feedmaxdaysfield);
        queryModel.setHlfields(hlfields);
        queryModel.setHlpretag(hlpretag);
        queryModel.setHlposttag(hlposttag);
        queryModel.setEntryattrs(entryattrs);

        QueryResult queryResult = domainSearchService.getQueryResult(queryModel);


//        List<Money> lmoney = moneyService.getMoneyByName(domain);
//        String money = lmoney.get(0).toString();
        return queryResult;
    }

    // Entry retrieval
    @RequestMapping(value = "/{domain}/entry/{entryids}")
    public String getEntries(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryids") String entryids,
            @RequestParam(value = "fields",required = false, defaultValue = "") String fields,
            @RequestParam(value = "fieldurl",required = false, defaultValue = "") boolean fieldurl,
            @RequestParam(value = "viewurl",required = false, defaultValue = "") boolean viewurl,
            @RequestParam(value = "format",required = false, defaultValue = "") String format
    ) {
        return "ok";
    }

    // auto complete
    @RequestMapping(value = "/{domain}/autocomplete")
    public String autoComplete(
            @PathVariable(value = "domain") String domain,
            @RequestParam(value = "term") String term,
            @RequestParam(value = "formatted",required = false, defaultValue = "") boolean formatted,
            @RequestParam(value = "fields",required = false, defaultValue = "JSON") String format
    ) {
        return "ok";
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
        return "ok";
    }

    // More like this
    @RequestMapping(value = "/{domain}/entry/{entryid}/morelikethis/{targetdomainid}")
    public String moreLikeThis(
            @PathVariable(value = "domain") String domain,
            @PathVariable(value = "entryid") String entryid,
            @PathVariable(value = "targetdomainid") String targetdomainid,
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
        return "ok";
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
