package com.acme.delivery.repository;

import com.acme.delivery.domain.Product;
import com.acme.delivery.domain.Store;
import com.acme.delivery.transfer.KeyValue;
import com.acme.delivery.transfer.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);
    List<Store> findByStoreCategory_Id(Long storeCategory_id);
    @Query("select distinct o from Store o join fetch o.products where o.id = :id")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false"))
    Optional<Store> getLazy(Long id);

   @Query("select p from Product p where p.id = :id")
    Product findProductById(Long id);

    @Query(name = "Store.mostFamousStores", nativeQuery = true)
    List<KeyValue<String,String>> findStoresMostFamous();

    @Query(name = "Store.mostFamousStoresPerCategory", nativeQuery = true)
    List<KeyValue<String, String>> findStoresMostFamousPerCategory(Long id);

    @Query(name = "Store.top10Products", nativeQuery = true)
    List<KeyValues<String, String, String>> findTop10Products();
}
