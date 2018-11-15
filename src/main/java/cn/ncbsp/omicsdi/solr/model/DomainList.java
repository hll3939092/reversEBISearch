package cn.ncbsp.omicsdi.solr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ypriverol
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainList {

    @JsonProperty("domains")
    public Domain[] list;

    public Domain[] getList() {
        return list;
    }

    public void setList(Domain[] list) {
        this.list = list;
    }


}
