package cn.ncbsp.omicsdi.solr.solrmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * @author @ypriverol Yasset Perez-Riverol
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class TermResult {

    @JsonProperty("topTerms")
    Term[] terms;

    @JsonProperty("totalTermCount")
    int totalTermCount;

    public Term[] getTerms() {
        return terms;
    }

    public void setTerms(Term[] terms) {
        this.terms = terms;
    }

    public int getTotalTermCount() {
        return totalTermCount;
    }

    public void setTotalTermCount(int totalTermCount) {
        this.totalTermCount = totalTermCount;
    }

    @Override
    public String toString() {
        return "TermResult{" +
                "terms=" + Arrays.toString(terms) +
                ", totalTermCount=" + totalTermCount +
                '}';
    }
}
