package com.acme.delivery.domain;

import com.acme.delivery.transfer.KeyValue;
import com.acme.delivery.transfer.KeyValues;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@NamedNativeQuery(name = "Store.mostFamousStores",
        query ="""
			SELECT store_id as sid,name, COUNT(store_id) as popularity
                                    FROM STORES INNER JOIN (
                                             SELECT order_id, store_id
                                             FROM items
                                                      INNER JOIN products ON products.id=items.product_id
                                             GROUP BY order_id, store_id
                                         )ON STORE_ID=stores.ID
                                    GROUP BY store_id, name
                                    ORDER BY popularity DESC
			""",
        resultSetMapping = "MostFamousStores")
@SqlResultSetMapping(name = "MostFamousStores",
        classes = @ConstructorResult(
                targetClass = KeyValue.class,
                columns = {
                        @ColumnResult(name = "sid", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                }
        )
)

@NamedNativeQuery(name = "Store.mostFamousStoresPerCategory",
        query ="""
			SELECT store_id as sid, storeName, COUNT(store_id) as popularity
   FROM (
            SELECT  store_id, storeName
            FROM items
                     INNER JOIN (
                SELECT store_id, stores.name as storeName, PRODUCTS.id as pid
                FROM PRODUCTS
                         INNER JOIN STORES ON stores.id=PRODUCTS.store_id
                WHERE storecategory_id=?
            )
                                ON pid=ITEMS.PRODUCT_ID
            Group by order_id, store_id, storeName
        )
   GROUP BY store_id,storeName
   ORDER BY popularity DESC
   
			""",
        resultSetMapping = "MostFamousStoresPerCategory")
@SqlResultSetMapping(name = "MostFamousStoresPerCategory",
        classes = @ConstructorResult(
                targetClass = KeyValue.class,
                columns = {
                        @ColumnResult(name = "sid", type = String.class),
                        @ColumnResult(name = "storeName", type = String.class)
                }
        )
)

@NamedNativeQuery(name = "Store.top10Products",
        query ="""
		   SELECT sid, productName, storeName, COUNT(PRODUCT_ID)
     FROM items
              INNER JOIN (
         SELECT stores.id as sid, PRODUCTS.PRODUCTNAME as productName, stores.name as storeName,PRODUCTS.ID as pid
         FROM STORES
                  INNER JOIN PRODUCTS ON PRODUCTS.store_id=stores.id
     )
                         ON pid=ITEMS.PRODUCT_ID
     GROUP BY productName,storeName,sid
     ORDER BY COUNT(PRODUCT_ID) DESC
         FETCH NEXT 10 ROWS ONLY
			""",
        resultSetMapping = "Top10Products")
@SqlResultSetMapping(name = "Top10Products",
        classes = @ConstructorResult(
                targetClass = KeyValues.class,
                columns = {
                        @ColumnResult(name = "sid", type = String.class),
                        @ColumnResult(name = "productName", type = String.class),
                        @ColumnResult(name = "storeName", type = String.class)

                }
        )
)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@SuperBuilder
@Table(name = "STORES")
@SequenceGenerator(name = "idGenerator", sequenceName = "STORES_SEQ", initialValue = 1, allocationSize = 1)
public class Store extends BaseModel {
        @Column(length = 30, nullable = false)
        @NotNull
        private String name;

        @ToString.Exclude
        @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        private Set<Product> products;

        @NotNull
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        private StoreCategory storeCategory;
}
