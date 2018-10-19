import cn.ncbsp.omicsdi.solr.model.Database;
import cn.ncbsp.omicsdi.solr.model.Money;
//import cn.ncbsp.omicsdi.solr.repo.MoneyRepo;
import cn.ncbsp.omicsdi.solr.queryModel.QueryModel;
import cn.ncbsp.omicsdi.solr.repo.SolrEntryRepo;
import cn.ncbsp.omicsdi.solr.services.IDatabaseService;
import cn.ncbsp.omicsdi.solr.services.IDomainSearchService;
import cn.ncbsp.omicsdi.solr.services.ISolrEntryService;
import cn.ncbsp.omicsdi.solr.solrmodel.SolrEntry;
import cn.ncbsp.omicsdi.solr.solrmodel.TestModel;
import cn.ncbsp.omicsdi.solr.util.SolrQueryBuilder;
import cn.ncbsp.omicsdi.solr.util.XmlHelper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.GC;

import java.io.IOException;
import java.util.*;

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
        queryModel.setQuery("id:PXD000025");
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


}
