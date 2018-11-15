package cn.ncbsp.omicsdi.solr.queryModel;

import org.apache.solr.common.params.MoreLikeThisParams;

public class MLTQueryModel implements IQModel {
    public static final String REQUEST_HANDLER = "/" + MoreLikeThisParams.MLT;

    public String mlt1;
    public String mlt2;

    public MLTQueryModel() {
    }

    public String getMlt1() {
        return mlt1;
    }

    public void setMlt1(String mlt1) {
        this.mlt1 = mlt1;
    }

    public String getMlt2() {
        return mlt2;
    }

    public void setMlt2(String mlt2) {
        this.mlt2 = mlt2;
    }
}
