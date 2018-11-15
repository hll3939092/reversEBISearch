import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.model.Money;
//import cn.ncbsp.omicsdi.solr.repo.MoneyRepo;
import cn.ncbsp.omicsdi.solr.model.Suggestion;
import cn.ncbsp.omicsdi.solr.model.Suggestions;
import cn.ncbsp.omicsdi.solr.queryModel.*;
import cn.ncbsp.omicsdi.solr.repo.SolrEntryRepo;
import cn.ncbsp.omicsdi.solr.services.*;
import cn.ncbsp.omicsdi.solr.solrTool.SolrSuggestTemplate;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import cn.ncbsp.omicsdi.solr.solrmodel.TestModel;
import cn.ncbsp.omicsdi.solr.util.SolrQueryBuilder;
import cn.ncbsp.omicsdi.solr.util.XmlHelper;
import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.*;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.core.query.result.TermsPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.GC;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath*:spring/applicationContext-sso.xml")
public class SolrTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    SolrTemplate solrTemplate;

//    @Autowired
//    MoneyRepo moneyRepo;

    @Autowired
    IDatabaseService databaseService;




    @Test
    public void test1() {
        SimpleQuery sq = new SimpleQuery("name:Dollar");
        Optional<Money> m = solrTemplate.queryForObject("new_core",sq,Money.class);
        System.out.println(m.get().toString());
    }


    @Test
    public void test2() {
//        List<Money> list = moneyRepo.findByName("Dollar");
//        for(Money m: list) {
//            System.out.println(m.toString());
//        }
    }


//    @Test
//    public void test3() {
//        Money money = new Money();
//        money.setId("Pony Coin");
//        money.setName("PCI");
//        solrTemplate.saveDocument();
//    }

    @Autowired
    HttpSolrClient solrClient;

        @Test
    public void test3() {
            SolrQuery query = new SolrQuery();
            query.set("q","name:Dollar");
            try {
                SolrDocumentList list = solrClient.query(query).getResults();
                System.out.println(list.toString());
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Test
    public void test4 () {
            Database database = new Database();
            database = XmlHelper.xmlToObject("C:\\Users\\MS\\Desktop\\solr\\PRIDE_EBEYE_1.xml",database);
            System.out.println(database.getName().toString());
        }

        @Test
    public void test5() {
            Database database = new Database();
            database = XmlHelper.xmlToObject("C:\\Users\\MS\\Desktop\\solr\\PRIDE_EBEYE_1.xml",database);
            solrTemplate.saveBean("omicsdi",database);
            solrTemplate.commit("omicsdi");
//            String updateResponse = databaseService.indexSolrData("omicsdi",database);
//            System.out.println(updateResponse);
//            SolrInputDocument inputDocument = new SolrInputDocument();
//            inputDocument.addField("database_name",database.getName());
//            UpdateResponse updateResponse = solrTemplate.saveDocument("omicsdi",inputDocument);
//            System.out.println(updateResponse.getResponse().toString());
//            try {
//                solrClient.add(inputDocument);
//                UpdateResponse updateResponse = solrClient.commit();
//                System.out.println(updateResponse);
//            } catch (SolrServerException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        @Test
    public void test6() {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", "552199");
            document.addField("name", "Gouda cheese wheel");
            document.addField("price", "49.99");
            try {
                UpdateResponse response = solrClient.add(document);
                UpdateResponse response2 = solrClient.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Test
    public void addDocument() {
        String SOLR_URL = new String("http://localhost:8983/solr/");
        //创建一个连接,my_core为我创建的一个实例名
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder(SOLR_URL + "new_core")
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000);
        HttpSolrClient solr = builder.build();
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();

        //往doc中添加字段,在客户端这边添加的字段必须在服务端中有过定义
        document.addField("id", "test001");
        document.addField("item_price", 123);
        document.addField("item_title", "测试商品1");

        try {
            //把这个文档对象写入索引库
            solr.add(document);
            //提交
            solr.commit();
            solr.close();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Test
    public void test7() {
        Database database = new Database();
        database = XmlHelper.xmlToObject("C:\\Users\\MS\\Desktop\\solr\\PRIDE_EBEYE_1.xml",database);
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("name",database.getName());
        solrInputDocument.addField("description",database.getDescription());
        solrInputDocument.addField("release",database.getRelease());
        solrInputDocument.addField("release_date",database.getReleaseDate());
        solrInputDocument.addField("entry_count",database.getEntryCount());

        database.getEntries().getEntry().stream().forEach(x -> {
            x.getCrossReferences().getRef().stream().forEach(ycr -> {
                solrInputDocument.addField(ycr.getDbname(),ycr.getDbkey());
            });
            x.getAdditionalFields().getField().stream().forEach(yaf -> {
                solrInputDocument.addField(yaf.getName(),yaf.getValue());
            });
        });

        databaseService.indexSolrData("omicsdi",solrInputDocument);


    }

//    @Test
//    public void test8() {
//        databaseService.deleteDocument("omicsdi","Pride");
//    }
    @Test
    public void test8() {
//        SolrEntry solrEntry = new SolrEntry();
//        solrEntry.setDatabase("dbdbdbdb2");
//        List<String> list = new ArrayList<>();
//        list.add("test1 test4");
//        list.add("test2 test5");
//        list.add("test3 test6");
//        solrEntry.setPublication(list);
//        solrEntry.setAcc("AMD 1700X");
//        DataDate dataDate = new DataDate();
//        dataDate.setDay("11");
//        dataDate.setMonth("09");
//        dataDate.setYear("17");
//        java.util.Date date = new java.util.Date(2018,10,27);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-mm-dd");
//        String str = simpleDateFormat.format(date);
//
//        System.out.println(str);
        HashMap<String,List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("heiheihei");
        list.add("hohoho");
        list.add("hoheiho");
        map.put("test",list);
        map.put("publication",list);
        map.put("submitter_mail",list);
        TestModel testModel = new TestModel(map);

//        solrEntry.setDatePublication(date);
//        List<String> list = new ArrayList<String>();
//        list.add("omicsDI_type1");
//        list.add("omicsDI_type2");
//        solrEntry.setOmicsType(list);
        solrTemplate.saveBean("omicsdi",testModel);
        solrTemplate.commit("omicsdi");
    }


    @Autowired
    ISolrEntryService iSolrEntryService;

        @Test
    public void test9 () {
        iSolrEntryService.saveSolrEntry("C:\\Users\\MS\\Desktop\\solr\\PRIDE_EBEYE_2.xml","omicsdi");
    }


    @Test
    public void test10 (){
//        iSolrEntryService.saveSolrEntries("C:\\Users\\MS\\Desktop\\solr","omicsdi");
    }

    @Autowired
    SolrEntryRepo solrEntryRepo;

    @Test
    public void test11() {
//        solrEntryRepo.getEntry("omicsdi");
//        solrClient.
        FacetQuery facetQuery = new SimpleFacetQuery();
        FacetOptions facetOptions = new FacetOptions();
        facetOptions.addFacetOnField("database");
        facetQuery.setFacetOptions(facetOptions);
        facetQuery.addCriteria(new Criteria().where("id").isNotNull());
        FacetPage<SolrEntry> facetPage = solrTemplate.queryForFacetPage("omicsdi",facetQuery,SolrEntry.class);

        System.out.println("ok");
        System.gc();
    }

    @Test
    public void test12() {
        solrEntryRepo.getQueryResult("omicsdi",new SimpleQuery(new Criteria().where("id").contains("PXD").and("name").isNotNull()),new SolrEntry().getClass());
        System.out.println();
    }

    @Test
    public void test13() {
        solrEntryRepo.getFacetQueryResult("omicsdi",new SimpleFacetQuery(new Criteria().where("id").in(new String[]{"PXD000025","PXD004655"})).setFacetOptions(new FacetOptions("database")),SolrEntry.class);
    }

    @Test
    public void test14() {
//        QueryModel queryModel = new QueryModel();
//        Map<String,List<String>> map = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("PXD004655");
//        list.add("PXD000025");
//        map.put("id",list);
//        List<String> listName = new ArrayList<>();
//        list.add("Phosphoproteomic analysis of Rhodopseudomonas palustris");
//        list.add("Arginine (di)methylated Human Leukocyte Antigen class I peptides are favorably presented by HLA-B*07");
//        map.put("name",listName);
//        queryModel.setQ(map);
//
//        List<String> listFacet = new ArrayList<>();
//        listFacet.add("database");
//        queryModel.setFacet(listFacet);
//        SimpleFacetQuery simpleFacetQuery = SolrQueryBuilder.buildSimpleFacetQuery(queryModel);
//        SolrResultPage<SolrEntry> solrResultPage = (SolrResultPage<SolrEntry>) solrEntryRepo.getFacetQueryResult("omicsdi",simpleFacetQuery,SolrEntry.class);
//
//

//        SimpleQuery simpleQuery = SolrQueryBuilder.buildQuery(queryModel);
//        simpleQuery.setRows(3);
//        SolrResultPage<SolrEntry> solrEntrySolrResultPage =  solrEntryRepo.getQueryResult("omicsdi",simpleQuery,SolrEntry.class);
//        System.out.println(solrResultPage);
    }

    @Test
    public void test15() {
        SimpleQuery simpleQuery = new SimpleQuery("*:*");
        SolrResultPage<SolrEntry> solrEntrySolrResultPage = solrTemplate.query("omicsdi",simpleQuery,SolrEntry.class);
        System.out.println(solrEntrySolrResultPage);

    }

    @Test
    public void test16() {
//        Set<String> set = new HashSet<String>();
//        set.add("a");
//        set.add("b");
//        solrClient.setQueryParams(set);
//        solrClient.query("omicsdi",new SolrQuery());
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setFacet(true).addFacetField("id","name");
        solrQuery.setQuery("*:* AND id:PXD000025 AND acc:PXD000025");
        try {
            QueryResponse queryResponse = solrClient.query("omicsdi",solrQuery);
//            List<SolrEntry> solrEntry = queryResponse.getBeans(SolrEntry.class);
            SolrDocumentList solrDocuments = queryResponse.getResults();
//            for(SolrEntry s : solrEntry) {
//                System.out.println(s);
//            }
            for(SolrDocument solrDocument : solrDocuments) {
                Set<String> set = solrDocument.keySet();
                for(String str: set) {
                    Object o = solrDocument.get(str);
                    System.out.println(o);
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUtils1(){
        QueryModel queryModel = new QueryModel();
        queryModel.setQuery("");
        SolrQueryBuilder.buildQuery(queryModel);
    }

    @Test
    public void testUtils2() {
        QueryModel queryModel = new QueryModel();
        queryModel.setSize(5);
        queryModel.setQuery("*:*");
        queryModel.setFacets(new String[] {"id"});
        SimpleFacetQuery simpleFacetQuery = SolrQueryBuilder.buildSimpleFacetQuery(queryModel);
        FacetPage<SolrEntry> facetPage = solrTemplate.queryForFacetPage("omicsdi",simpleFacetQuery,SolrEntry.class);
        System.out.println(facetPage);
    }

    @Autowired
    IDomainSearchService domainSearchService;

    @Test
    public void testService() {
        QueryModel queryModel = new QueryModel();
        queryModel.setSize(5);
        queryModel.setDomain("omicsdi");
        queryModel.setQuery("*:*");
        queryModel.setFacets(new String[] {"database","additional_submitter"});
        queryModel.setFacetcount(6);
        domainSearchService.getQueryResult(queryModel);
    }


    @Test
    public void testAllField() {
        try {
            NamedList<Object> namedList = solrClient.request(new SchemaRequest.Fields(),"omicsdi");
            NamedList<Object> namedList2 = solrClient.request(new SchemaRequest.DynamicFields(),"omicsdi");
            NamedList<Object> namedList3 = solrClient.request(new LukeRequest(),"omicsdi");

            Object o = namedList3.get("fields");
            NamedList<Map<String,String>> nl = (NamedList<Map<String, String>>) o;

            ArrayList<String> arr = new ArrayList<String>();

            for(int i = 0;i<nl.size();i++) {
                arr.add(nl.getName(i));
            }
            System.out.println("ok");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private SolrSuggestTemplate solrSuggestTemplate;

    @Test
    public void testSuggest() {
//
        SimpleQuery simpleQuery = new SimpleQuery("a");
        simpleQuery.setRequestHandler("/suggest");
//        SolrResultPage<Suggestions> solrEntrySolrResultPage = solrTemplate.query("omicsdi",simpleQuery,Suggestions.class);
        SolrResultPage<Suggestions> solrResultPage = solrSuggestTemplate.query("omicsdi",simpleQuery,Suggestions.class);

        System.out.println("a?");





//        System.out.println("a?");
//
//        SimpleTermsQuery simpleTermsQuery = new SimpleTermsQuery();
//        simpleTermsQuery.addCriteria(new Criteria().expression("a"));
//        TermsPage termsPage = solrTemplate.queryForTermsPage("omicsdi",simpleTermsQuery);
        System.out.print("a?");

//        SimpleHighlightQuery


//        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setRequestHandler("/suggest");
//        solrQuery.set("q","a");
//        try {
//            QueryResponse queryResponse = solrClient.query("omicsdi",solrQuery);
//            System.out.println("ok?");
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("ok?");
//        SimpleQuery simpleQuery = new SimpleQuery();
//        simpleQuery.setRequestHandler("/suggest");
//        SolrResultPage<Suggestions> solrEntrySolrResultPage = (SolrResultPage<Suggestions>) solrTemplate.queryForPage("omicsdi",simpleQuery, Suggestions.class);
////        SolrResultPage<Suggestions> solrEntrySolrResultPage = solrTemplate.query("omicsdi",simpleQuery, Suggestions.class);

//        SuggesterResponse suggesterResponse = new SuggesterResponse();
//
//        SimpleTermsQuery simpleTermsQuery = new SimpleTermsQuery();
//        simpleTermsQuery.setRequestHandler("/suggest");
//        simpleTermsQuery.addCriteria(new Criteria().expression("q=an"));
//        TermsPage termsPage = solrTemplate.queryForTermsPage("omicsdi",simpleTermsQuery);
//        SolrResultPage<Suggestions> solrEntrySolrResultPage = solrTemplate.query("omicsdi",simpleQuery, Suggestions.class);
//        System.out.println("a?");
//
//        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setRequestHandler("/suggest");
//        solrQuery.set("q","a");
//
//        try {
//            QueryResponse queryResponse = solrClient.query("omicsdi",solrQuery);
//            SuggesterResponse suggesterResponse = queryResponse.getSuggesterResponse();
//            Map<String, List<Suggestion>> mapSuggest = suggesterResponse.getSuggestions();
//            System.out.println("a");
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Autowired
    IAutocompleteService autocompleteService;

    @Test
    public void testSuggestService() {
        Suggestions suggestions = autocompleteService.getSuggestions("omicsdi","an", Suggestion.class);
        System.out.println("a");
    }


    @Test
    public void testRegex() {
        String regex = "[a-zA-Z0-9]+";
        Boolean b = Pattern.matches(regex,"asdf9087sdfKHE");
        System.out.println(b);

    }

    @Autowired
    ISolrCustomService solrCustomService;

    @Test
    public void testSuggestionService () {
        Map<String,String[]> map = new HashMap<>();
        map.put("q",new String[]{"a"});
        solrCustomService.getSuggestResult("omicsdi",map);
    }


    @Test
    public void testMoreLikeThisSerivce() {
        Map<String,String[]> map = new HashMap<>();
        map.put("q", new String[]{"id:PXD002734"});
        solrCustomService.getSimilarResult("omicsdi", map);
    }


    @Test
    public void testAPI() {
        SolrQuery solrQuery = new SolrQuery();
//        FacetParams

        Map<String,String[]> paramMap = new HashMap<>();

        String[] fields = new String[]{"description", "database"};
        paramMap.put(TermsParams.TERMS_FIELD, fields);

        solrCustomService.getFrequentlyTerms("omicsdi", paramMap);
    }

    @Autowired
    ISolrFacetService solrFacetService;


    @Test
    public void testhihihi() {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q","id:PXD002734");
        solrQuery.setFacet(true);
        solrQuery.addFacetField("acc");
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query("omicsdi", solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("hihihihihihi");
    }

    @Test
    public void testfacetImple() {
//        Map<String,String[]> paramMap = new HashMap<>();
//        paramMap.put("q", new String[]{"*:*"});
//        paramMap.put(FacetParams.FACET, new String[]{"database"});
//        solrFacetService.getFacetEntriesByDomains("omicsdi", paramMap);
        FacetQuery facetQuery = new SimpleFacetQuery(new Criteria().expression("*:*"));
        FacetOptions facetOptions = new FacetOptions();
        facetOptions.addFacetOnField("database");
        facetQuery.setFacetOptions(facetOptions);

//        FacetPage<SolrEntry> facetPage = solrTemplate.queryForFacetPage("omicsdi",simpleFacetQuery,SolrEntry.class);
        FacetPage<SolrEntry> facetPage =  solrTemplate.queryForFacetPage("omicsdi", facetQuery, SolrEntry.class);

        System.out.println("x");
    }


    @Test
    public void TestParam() {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set(CommonParams.Q, "*:*");
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query("omicsdi", solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    @Test
    public void testInvoke() {
//        Field[] fields = FacetParams.class.getDeclaredFields();
//        for(Field field : fields) {
//            field.setAccessible(true);
//            String name = field.getName();
//        }
//
//        FacetParams.class.getMethods();

//        Field[] fields = QueryModel.class.getFields();
//        Method[] methods = QueryModel.class.getMethods();
        QueryModel queryModel = new QueryModel();
        queryModel.setDomain("asdfdsa");
        queryModel.setQuery("hihihihihi");

        try {
            Field field = queryModel.getClass().getDeclaredField("domain");
            field.setAccessible(true);
            String str = (String) field.get(queryModel);
            System.out.println(str);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        Class clazz = queryModel.getClass();
//        Field[] fields = clazz.getFields();
//        for(Field field : fields) {
//            try {
//                Method method = clazz.getMethod("get" + field.getName());
//                String value = (String) method.invoke(queryModel);
//                System.out.println(value);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }


//        try {
//            Class clazz = Class.forName(queryModel.getClass().getName());
//            Method[] methods = clazz.getMethods();
//            Field[] fields = clazz.getFields();
//
//
//            for(Field field : fields) {
//                Method method = clazz.getMethod("get" + field.getName());
//                String name = (String) method.invoke(clazz.newInstance());
//                System.out.println(name);
//            }
//
//
//            for(Method method : methods) {
//
//            }
////            clazz.getMethod()
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }


    @Test
    public void testAutoSolr() {
        String str = FacetParams.FACET;
        String str2 = CommonParams.Q;
        System.out.println(QueryModel.class.getName());
    }


    @Test
    public void testFacetImpl() {
        Map<String, String[]> paramMap = new HashMap<>();
        paramMap.put("facetfields", new String[]{"TAXONOMY"});
        paramMap.put("database", new String[] {"Pride"});
        paramMap.put("q",new String[]{"*:*"});
        solrFacetService.getFacetEntriesByDomains("omicsdi",paramMap);
    }

    @Test
    public void TT() {
        String str = "domain_source:(arrayexpress-repository OR atlas-experiments OR biomodels OR dbgap OR ega OR eva OR geo OR gnps OR gpmdb OR jpost OR lincs OR massive OR metabolights_dataset OR metabolome_express OR metabolomics_workbench OR omics_ena_project OR paxdb OR peptide_atlas OR pride)";
        String[] str1 = str.substring(15,str.length()-1).split(" OR ");
        System.out.println(str1);
    }


    @Test
    public void autoQueryBuilder() {

        SimpleQueryModel simpleQueryModel = new SimpleQueryModel();
        simpleQueryModel.setQ("id:PXD002734"+" "+SimpleParams.OR_OPERATOR+" "+"id:PXD002565");


//        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setQuery("id:PXD002734"+" "+SimpleParams.OR_OPERATOR+" "+"id:PXD002565");
////        solrQuery.setParam(CommonParams.Q, "id:PXD001224 OR PXD002734");
////        solrQuery.set(CommonParams.Q, new String[]{"id:PXD011618 OR PXD021618"});
////        solrQuery.set(CommonParams.Q, "id:PXD001224 OR PXD002734");
////        solrQuery.add(CommonParams.Q, new String[]{"id:PXD002734","id:PXD002565"});
////        solrQuery.add(CommonParams.Q, new String[]{"id:PXD002565"});
////        solrQuery.set("id", new String[]{"*"});
////        solrQuery.set("database", new String[]{"Pride","Jpost"});
//        QueryResponse queryResponse = null;
//        try {
//            queryResponse = solrClient.query("omicsdi", solrQuery);
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("ok");
    }

    @Test
    public void testInvoke2() {
        IQModel iqModel = new SimpleQueryModel();
        SolrQuery solrQuery = new SolrQuery();
        Field[] fields = iqModel.getClass().getDeclaredFields();
        cn.ncbsp.omicsdi.solr.queryModel.SolrQueryBuilder.buildQuery(iqModel, solrQuery, fields);
//        cn.ncbsp.omicsdi.solr.queryModel.SolrQueryBuilder.buildSolrQuery(iqModel);
        System.out.println("x");
    }

    @Test
    public void newUtils() {
        IQModel iqModel = new SimpleQueryModel();
        ((SimpleQueryModel) iqModel).setQ("*:*");
        SolrQuery solrQuery = cn.ncbsp.omicsdi.solr.queryModel.SolrQueryBuilder.buildSolrQuery(iqModel);
        FacetQueryModel facetQueryModel = new FacetQueryModel();
//        facetQueryModel.setFacet("true");
        facetQueryModel.setFacet_field("database,id");
        solrQuery = cn.ncbsp.omicsdi.solr.queryModel.SolrQueryBuilder.buildAdvancedSolrQuery(facetQueryModel, solrQuery);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query("omicsdi", solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("x");
    }


    @Test
    public void testFinalStatic() {
        MLTQueryModel mltQueryModel = new MLTQueryModel();
        Field[] fields = mltQueryModel.getClass().getDeclaredFields();
        for(Field field: fields) {
            System.out.println(field.getName());
        }
        System.out.println("xxx");
    }

}
