package restaurant.domain;

import java.time.LocalDate;

public class Beer extends Drinks{
	private float alcoholContent;//酒精度数
	

	public Beer() {
		super();
	}
	public Beer(String drinkName, double cost, LocalDate dateInProduced,float alcoholContent) {
		super(drinkName, cost, dateInProduced, 30);//保质期30天
		this.alcoholContent=alcoholContent;
	}


	//获取酒精度数
	public float getAlcoholContent() {
		return alcoholContent;
	}

	
	@Override
	public String toString() {
		
		String details="[酒类："+drinkName+"\t价钱："+cost+"元]";
		return details;

	}

	


	

}
