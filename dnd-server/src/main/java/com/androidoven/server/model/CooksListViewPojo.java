package com.androidoven.server.model;

import com.androidoven.transport.xsd.common.CookView;
import com.androidoven.transport.xsd.common.Dish;
import com.androidoven.transport.xsd.customerservice.CooksListView;

public class CooksListViewPojo {
	
	private static CooksListViewPojo I = null;
	private static String NAME[] = {"Tom", "Jerry", "George", "William", "Jacob"};
	private CooksListView list = null;
	
	private CooksListViewPojo() {}
	
	private void createList() {
		this.list = new CooksListView();
		
		for (int i=0; i<5; i++) {
			CookView cv = new CookView();
			cv.setId(i);
			cv.setName(NAME[i]);
			cv.setFavourite(false);
			
			Dish dish = new Dish();
			dish.setName("Chicken Curry");
			dish.setPrice("5");
			cv.getDishes().add(dish);
			
			dish = new Dish();
			dish.setName("Beef Curry");
			dish.setPrice("5");
			cv.getDishes().add(dish);
			
			dish = new Dish();
			dish.setName("Prawn Curry");
			dish.setPrice("8");
			cv.getDishes().add(dish);
			
			dish = new Dish();
			dish.setName("Vegi Curry");
			dish.setPrice("4");
			cv.getDishes().add(dish);
			
			dish = new Dish();
			dish.setName("Tofu Curry");
			dish.setPrice("6");
			cv.getDishes().add(dish);
			
			this.list.getList().add(cv);
		}
		
	}
	
	public CooksListView getCooksListView() {
		return this.list;
	}
	
	public static CooksListViewPojo getInstance() {
		if (null == I) {
			I = new CooksListViewPojo();
			I.createList();
		}
		return I;
	}
	
}