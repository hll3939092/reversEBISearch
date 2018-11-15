package cn.ncbsp.omicsdi.solr.services.Impl;

import cn.ncbsp.omicsdi.solr.services.ISolrFacetService;
import cn.ncbsp.omicsdi.solr.solrmodel.Facet;
import cn.ncbsp.omicsdi.solr.solrmodel.FacetList;
import cn.ncbsp.omicsdi.solr.solrmodel.FacetValue;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SolrFacetServiceImpl implements ISolrFacetService {


    @Autowired
    SolrClient solrClient;


    @Override
    public FacetList getFacetEntriesByDomains(String core, Map<String, String[]> paramMap) {
        if(paramMap.isEmpty()){
            // todo exception
            return null;
        }


        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setFacet(true);
        String[] facetFields = paramMap.get(FacetParams.FACET);
        solrQuery.addFacetField(facetFields);

        solrQuery.set(CommonParams.Q, paramMap.get(CommonParams.Q)[0]);

        String[] databases = paramMap.get("databases");
        solrQuery.add("database", databases);



        String[] fields = paramMap.get("facetfields");
        solrQuery.addFacetField(fields);



//        //Type:
//        //organisms
//        if(paramMap.get("organisms") != null && paramMap.get("organisms").length > 0) {
//            // 也就是TAXONOMY
//            String[] additional_TAXONOMIES = paramMap.get("organisms");
//            solrQuery.addFacetField(additional_TAXONOMIES);
//        }
//
//        //tissue
//        if(paramMap.get("tissue") != null && paramMap.get("tissue").length > 0) {
//            String[] tissues = paramMap.get("tissue");
//            solrQuery.addFacetField(tissues);
//        }
//
//        //omics_type
//        if(paramMap.get("omics_type") != null && paramMap.get("omics_type").length > 0) {
//            String[] omics_type = paramMap.get("omics_type");
//            solrQuery.addFacetField(omics_type);
//        }
//
//        //diseases
//        if(paramMap.get("diseases") != null && paramMap.get("diseases").length > 0) {
//            String[] diseases = paramMap.get("diseases");
//            solrQuery.addFacetField(diseases);
//        }

        QueryResponse queryResponse = null;
        long foundNum = 0l;
        List<FacetField> facetFieldList = null;
        try {
            queryResponse = solrClient.query(core, solrQuery);
            foundNum = queryResponse.getResults().getNumFound();
            facetFieldList = queryResponse.getFacetFields();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        FacetList facetList = new FacetList();
////        Facet[] facets = new Facet[facetFieldList.size()];
////        for(int i = 0;i<facetFieldList.size();i++) {
////            String facetName = solrQuery.getFacetFields()[i];
////            Facet facet = new Facet();
////            facet.setId(facetName);
////            facet.setLabel(facetName);
////            Long l = facetFieldList.stream().mapToLong(x -> x.getValues().stream().mapToLong( y -> y.getCount()).sum()).sum();
////        }

        FacetList facetList = new FacetList();
        facetList.setHitCount(String.valueOf(foundNum));

        Facet[] facets = facetFieldList.stream().map(x -> {
            Facet facet = new Facet();
            facet.setId(x.getName());
            facet.setLabel(labelConverter(x.getName()));
            FacetValue[] facetValues = x.getValues().stream().map(count -> {
                FacetValue facetValue = new FacetValue();
                facetValue.setLabel(count.getName());
                facetValue.setValue(count.getName());
                facetValue.setCount(String.valueOf(count.getCount()));
                return facetValue;
            }).toArray(FacetValue[] :: new);
            facet.setFacetValues(facetValues);
            facet.setTotal(Math.toIntExact(x.getValues().stream().mapToLong(count -> count.getCount()).sum()));
            return facet;
        }).toArray(Facet[] :: new);
        facetList.setFacets(facets);
        return facetList;
    }

    String labelConverter(String label) {
        switch (label) {
                case "TAXONOMY" :
                    return "Organisms";
                case "tissue" :
                    return "Tissue";
                case "omics_type":
                return "Omics type";
            case "disease":
                return "Disease";
            default:
                return label;
        }
    }
}
