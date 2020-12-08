package restaurant.domain;

public class SetMeal {

	private String mealName;//套餐名
	private double mealPrice;//套餐价格
	private String chickenName;//炸鸡名
	private Drinks type;//饮料类型

	public SetMeal() {
		super();
	}

	public SetMeal(String mealName, double mealPrice, String chickenName, Drinks type) {
		super();
		this.mealName = mealName;
		this.mealPrice = mealPrice;
		this.chickenName = chickenName;
		this.type = type;
	}
	
	public String getMealName() {
		return mealName;
	}


	public double getMealPrice() {
		return mealPrice;
	}


	public String getChickenName() {
		return chickenName;
	}


	public Drinks getType() {
		return type;
	}


	@Override
	public String toString() {
		String details = "-------套餐信息-------";
		details += "\n套餐名：" + mealName+"\t";
		details += "套餐价格：" + mealPrice+"元\t";
		details += "炸鸡名：" + chickenName+"\t";
		details += "饮料：" + type;
		return details;
	}

	
}
