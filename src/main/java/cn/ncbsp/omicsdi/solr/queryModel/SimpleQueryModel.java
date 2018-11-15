package cn.ncbsp.omicsdi.solr.queryModel;

import org.apache.solr.common.params.CommonParams;


/**
 * 查询参数对象
 */
public class SimpleQueryModel implements IQModel{

    private String q;
    private String q_op;
    private String df;
    private String sow;

    public SimpleQueryModel() {
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getQ_op() {
        return q_op;
    }

    public void setQ_op(String q_op) {
        this.q_op = q_op;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getSow() {
        return sow;
    }

    public void setSow(String sow) {
        this.sow = sow;
    }
}
