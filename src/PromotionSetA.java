import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface DiscountPromotionA extends CommonDiscounts{
	
	Discount discountOnAfricaProducts();
	Discount discountOnRating();
    Discount discountOnCategories();
}

public class PromotionSetA implements DiscountPromotionA{
	
	double rating,price;
	String origin,category;
	List<Discount> discounts;
	
	public PromotionSetA(Product product) {
		this.rating=product.getRating();
		this.price=product.getPrice();
		this.origin=product.getOrigin();
		this.category=product.getCategory();
		discounts=new ArrayList<>();
		
	}

	@Override
	public Discount defaultDiscount(double price) {
		// TODO Auto-generated method stub
		Discount discount=new Discount(" ");
		double discountPrice=0;
		if(price>1000) {
			discount.discountName="get 2% off";
			discountPrice=(0.02*price);
			discount.price=discountPrice;
		}
		
		return discount;
	}

	@Override
	public Discount finalDiscount() {
		// TODO Auto-generated method stu
		Discount discount=defaultDiscount(price);
		if(discount.price==0) {
			discounts.add(discountOnAfricaProducts());
			discounts.add(discountOnCategories());
			discounts.add(discountOnRating());
			Collections.sort(discounts,new MyComparator());
			return discounts.get(2);
		}
		
		return discount;
	}

	@Override
	public Discount discountOnAfricaProducts() {
		// TODO Auto-generated method stub
		Discount discount=new Discount(" ");
		double discountPrice=0;
		if(origin.equalsIgnoreCase("Africa")) {
			discount.discountName="get 7% off";
			discount.price= (0.07*price);
		}
		
		
		return discount ;
	}

	@Override
	public Discount discountOnRating() {
		// TODO Auto-generated method stub
		Discount discount=new Discount(" ");
		double discountPrice=0;
		if(rating==2) {
			discount.discountName="4% discount";
			discountPrice=(0.04*price);
			discount.price=discountPrice;
			return discount;
		}
		else if(rating<2) {
			discount.discountName="8% discount";
			discountPrice=(0.08*price);
			discount.price=discountPrice;
			return discount;
			
		}
		else		
		
		return discount ;
	}

	@Override
	public Discount discountOnCategories() {
		// TODO Auto-generated method stub
		Discount discount=new Discount("");
		double discountPrice=0;
		switch(category)
		{
		case "electronics":
			    if(price>=500) {
			    	discount.discountName="get Rs 100 off";
			    	discount.price=100.00;
			    	return discount;
			    }
			    else
			    	return discount;
		case "furnishing":
			if(price>=500) {
				discount.discountName="get Rs 100 off";
		    	discount.price=100.00;
		    	return discount;
		    	
		    }
		    else
		    	return discount;
	   default:
		     return discount;
		}
		
	}
	
	

}
