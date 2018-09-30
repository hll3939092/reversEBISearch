package cn.ncbsp.omicsdi.solr.repo;

import cn.ncbsp.omicsdi.solr.model.Money;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.repository.SolrRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


// solr core 被定義在了model類中，使用@solrdocument
@Repository
public interface MoneyRepo extends SolrCrudRepository<Money, String> {

    List<Money> findByName(String name);

}
