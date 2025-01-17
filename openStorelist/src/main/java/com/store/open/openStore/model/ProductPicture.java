package com.store.open.openStore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ProductPicture {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private long id;

        
		@Lob @Column(name="ATTACHMENT")
        private String file;

        @ManyToOne
        private Product product;


		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		
		public String getFile() {
			return file;
		}
		
		public void setFile(String file) {
			this.file = file;
		}
		
		public Product getProduct() {
			return product;
		}
		
		public void setProduct(Product product) {
			this.product = product;
		}
}
		