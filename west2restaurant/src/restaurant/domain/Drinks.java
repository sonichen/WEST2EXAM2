package restaurant.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public abstract class Drinks {
	
//	定义变量
	protected String drinkName;//名字
	protected double cost;//成本
	protected LocalDate dateInProduced;//生产日期
	protected int shelfLife;//保质期



	protected Drinks() {
		super();
	}
	
	/**
	 * 全参构造器
	 * 
	 * @param drinkName      姓名
	 * @param cost           成本
	 * @param dateInProduced 生产日期
	 * @param shelfLife 保质期
	 */
	protected Drinks(String drinkName, double cost, LocalDate dateInProduced, int shelfLife) {
		super();
		this.drinkName = drinkName;
		this.cost = cost;
		this.dateInProduced = dateInProduced;
		this.shelfLife = shelfLife;
	}

	/**
	 * 抽象的toString方法
	 */
	public abstract String toString();
	

	/**
	 * 
	 * 判断是否过期
	 * @return true 过期；false 未过期
	 */
	public boolean checkIfOverDue() {
		DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate start=LocalDate.parse(dateInProduced+"",format);
		LocalDate end=LocalDate.parse(LocalDate.now()+"",format);
		long time=start.until(end, ChronoUnit.DAYS);
		//过期
		if(time>shelfLife) {
			return true;
		}
		return false;
		
	}
	//	获取饮料的名字
	public String getDrinkName() {
		return drinkName;
	}
}
