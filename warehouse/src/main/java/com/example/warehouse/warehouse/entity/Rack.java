package com.example.warehouse.warehouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rackdetailstb")
public class Rack {
	
	 	@Id
	    @Column(name = "slotno")
	    private Integer slotNo;
	 	
	    @Column(name = "slotstatus")
	    private Integer slotStatus;
	    
	    /*@OneToOne(mappedBy="rack")
	    private Product product;*/

		public Integer getSlotNo() {
			return slotNo;
		}

		public void setSlotNo(Integer slotNo) {
			this.slotNo = slotNo;
		}

		public Integer getSlotStatus() {
			return slotStatus;
		}

		public void setSlotStatus(Integer slotStatus) {
			this.slotStatus = slotStatus;
		}

/*		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}*/
}
