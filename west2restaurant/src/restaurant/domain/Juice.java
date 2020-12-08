package restaurant.domain;

import java.time.LocalDate;

public class Juice extends Drinks {

	public Juice() {
		super();

	}

	public Juice(String drinkName, double cost, LocalDate dateInProduced) {
		super(drinkName, cost, dateInProduced, 2);//保质期2天
	}

	@Override
	public String toString() {
		return "[果汁：" + drinkName + "\t价钱：" + cost + "元]";
	}

}
