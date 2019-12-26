import java.util.Comparator;

public class MyComparator implements Comparator<Discount> {

	@Override
	public int compare(Discount arg0, Discount arg1) {
		// TODO Auto-generated method stub
		if(arg0.price>arg1.price)
			return 1;
		else if(arg0.price<arg1.price)
			return -1;
		return 0;
	}
	
	

}
