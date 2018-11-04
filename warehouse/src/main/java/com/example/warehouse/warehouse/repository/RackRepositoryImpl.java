package com.example.warehouse.warehouse.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.example.warehouse.warehouse.entity.Rack;

public class RackRepositoryImpl implements CustomRackRepository{
	

	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Rack> createWareHouse(Integer wareHouseCapacity) {
	  final List<Rack> savedEntities = new ArrayList<Rack>(wareHouseCapacity);
	  
	  for (int i=0;i<wareHouseCapacity; i++) {
		  Rack rack=new Rack();
		  rack.setSlotStatus(0);
		  rack.setSlotNo(i+1);
	    savedEntities.add(persistOrMerge(rack));
	    if (i % wareHouseCapacity == 0) {
	      // Flush a batch of inserts and release memory.
	      entityManager.flush();
	      entityManager.clear();
	    }
	  }
	  return savedEntities;
	}

	private Rack persistOrMerge(Rack rack) {
	  if (rack.getSlotNo() == null) {
	    entityManager.persist(rack);
	    return rack;
	  } else {
	    return entityManager.merge(rack);
	  }
	}

	@Override
	public Rack getRacksByStatus(Integer status) {
		Rack rack=null;
		 Query query = entityManager.createQuery("select rack from Rack rack " +
	                "where rack.slotStatus =:status order by rack.slotNo ASC");
	        query.setParameter("status", status);
	        List<Rack> racks=query.getResultList();
	       if(!racks.isEmpty())
	    	   rack=racks.get(0);
	        return rack;
	}



}
