package cn.ncbsp.omicsdi.solr.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 26/06/2015
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Suggestions {

    @JsonProperty("suggestions")
    Suggestion[] entries;

    public Suggestion[] getEntries() {
        return entries;
    }

    public void setEntries(Suggestion[] entries) {
        this.entries = entries;
    }
}
