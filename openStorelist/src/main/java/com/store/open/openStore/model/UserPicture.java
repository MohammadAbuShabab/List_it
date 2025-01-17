package com.store.open.openStore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class UserPicture {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private long id;

        
		@Lob @Column(name="ATTACHMENT")
        private String file;

        @OneToOne
        private User user;


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

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		
		
		
}
		