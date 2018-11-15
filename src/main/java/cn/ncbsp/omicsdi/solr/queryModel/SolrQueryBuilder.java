package cn.ncbsp.omicsdi.solr.queryModel;

import cn.ncbsp.omicsdi.solr.constant.Constants;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.params.TermsParams;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SolrQueryBuilder {

    public static SolrQuery buildSolrQuery(IQModel iqModel) {
        SolrQuery solrQuery = new SolrQuery();
        Field[] fields = iqModel.getClass().getDeclaredFields();
        // 如果有Request_handler 添加进去
        for(Field field : fields) {
            if(field.getName().equalsIgnoreCase("REQUEST_HANDLER")) {
                solrQuery.setRequestHandler(field.getName());
            }
        }
        solrQuery = buildQuery(iqModel, solrQuery, fields);
        return solrQuery;
    }


    public static SolrQuery buildAdvancedSolrQuery(IQModel iqModel, SolrQuery solrQuery) {
        Field[] fields = iqModel.getClass().getDeclaredFields();
        // 如果有Request_handler 添加进去
        for(Field field : fields) {
            if(field.getName().equalsIgnoreCase("REQUEST_HANDLER")) {
                solrQuery.setRequestHandler(field.getName());
            }
        }
        solrQuery = buildQuery(iqModel, solrQuery, fields);
        return solrQuery;
    }

    public static SolrQuery buildQuery(IQModel iqModel, SolrQuery solrQuery, Field[] fields) {
        for(Field field : fields) {
            //首字母大写
            String name = field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
            try {
                //根据方法名获取get方法值
                Method method = iqModel.getClass().getDeclaredMethod("get"+name);
                Object object = method.invoke(iqModel);

                if(object != null) {
                    if (field.getName().equalsIgnoreCase("q")) {
                        solrQuery.setQuery((String) object);
                    } else {
                        String obj = (String) object;
                        //无论前端传进来的是什么样的字符串，在controller都要解析为“***，***，***”类型
                        String[] params = obj.split(",");
                        //由于solr的查询参数是***.***类型，无法直接以属性名写在属性中，所以以“_”代替
                        String paramName = field.getName().replace("_", ".");

                        //设定查询种类
                        // todo 不手动写，自动判定查询种类
                        for(String queryType : Constants.OPERATIONS) {
                            //由于仅传入一种QueryModel,所以每次只需要命中一次即可停止判定查询种类
                            if(paramName.startsWith(queryType)) {
                                solrQuery.set(queryType, true);
                                break;
                            }
                        }


//                        if(paramName.startsWith(FacetParams.FACET)){
//                            solrQuery.setFacet(true);
//                        }
//                        if (paramName.startsWith(MoreLikeThisParams.MLT)) {
//                            solrQuery.setMoreLikeThis(true);
//                        }
//                        if (paramName.startsWith(HighlightParams.HIGHLIGHT)) {
//                            solrQuery.setHighlight(true);
//                        }
//                        if(paramName.startsWith(TermsParams.TERMS)) {
//                            solrQuery.setTerms(true);
//                        }

                        solrQuery.add(paramName, params);
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return solrQuery;
    }
}
