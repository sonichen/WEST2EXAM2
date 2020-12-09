package restaurant.service;

import restaurant.domain.Beer;
import restaurant.domain.Drinks;
import restaurant.domain.Juice;
import restaurant.domain.SetMeal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class West2FriedChickenRestaurant implements FriedChickenRestaurant {

	/**
	 * 定义变量
	 */
	private double balance;//余额
	private LinkedList<Beer> beerList;//啤酒列表
	private  LinkedList <Juice> juiceList;//果汁列表
	private final static LinkedList <SetMeal> setMealList;//套餐列表

	/**
	LinkedList的优点在于删除和添加数据所消耗的资源较少，且比ArrayList效率高。
	
	*/
	/**
	 * 构造函数
	 */
	public West2FriedChickenRestaurant(double balance) {
		super();
		this.balance = balance;
		this.beerList = new LinkedList<Beer>();
		this.juiceList = new LinkedList<Juice>();
		repertory();//为了使餐厅能够营业，提供各类套餐库存各一份
	}
	/**
	 * 初始化套餐列表
	 */
	static {
		setMealList=new LinkedList<SetMeal>();
		setMealList.add(new SetMeal("套餐1", 35, "美式炸鸡", new Beer("哈啤", 6, LocalDate.now(), 2.5f)));
		setMealList.add(new SetMeal("套餐2", 45, "韩式炸鸡", new Beer("雪花", 7, LocalDate.now(), 3.5f)));
		setMealList.add(new SetMeal("套餐3", 30, "广式炸鸡", new Juice("果粒橙", 4, LocalDate.now())));
		setMealList.add(new SetMeal("套餐4", 35, "泰式炸鸡", new Juice("椰汁", 5, LocalDate.now())));
	}

	/**
	 * 为了使餐厅能够营业，提供各类套餐库存各一份
	 */
	public void repertory(){
		beerList.add(new Beer("哈啤", 6, LocalDate.now(),2.5f));
		beerList.add(new Beer("雪花", 7, LocalDate.now(),3.5f));
		juiceList.add(new Juice("果粒橙", 5, LocalDate.now()));
		juiceList.add(new Juice("椰汁", 5, LocalDate.now()));
	}
	/**
	 * 在出售套餐，同时移除对应的酒类
	 *
	 * @param beer 要被出售的酒类
	 */
	public void use(Beer beer) {

		//记录是否找到该饮料
		boolean found=false;
		if(beerList!=null) {
//			根据名字，遍历找到对应的酒类
			for(Beer oneBeer:beerList) {
				if(oneBeer.getDrinkName().equals(beer.getDrinkName())) {
					beerList.remove(oneBeer);
					found=true;
					break;
				}
			}
		}
		//如果没有找到，抛出异常
		if(!found) {
			String msg="很抱歉， "+beer.getDrinkName()+"已经售空。";
			throw new IngredientSortOutException(msg);
		}
	}

	/**
	 * 在出售套餐，同时移除对应的果汁
	 *
	 * @param juice 要被出售的果汁
	 */
	public void use(Juice juice) {

		//记录是否找到该饮料
		boolean found=false;

		if(juiceList!=null) {
//			根据名字，遍历找到对应的果汁
			for(Juice oneJuice:juiceList) {
				if(oneJuice.getDrinkName().equals(juice.getDrinkName())) {
					juiceList.remove(oneJuice);
					found=true;
					break;
				}
			}
		}
		//如果没有找到，抛出异常
		if(!found) {
			String msg="很抱歉， "+juice.getDrinkName()+"已经售空。";
			throw new IngredientSortOutException(msg);
		}
	}
	/**
	 * 出售套餐
	 */
	public void use(String setMealName ) {
		//记录是否找到该套餐
		boolean found=false;
		SetMeal choose = new SetMeal();
		if(setMealList!=null) {
//			根据名字，遍历找到对应的套餐
			for(SetMeal meal:setMealList) {
				if(setMealName.equals(meal.getMealName())) {
					found=true;
					choose=meal;
					break;
				}
			}
		}
		//如果没找到
		if(!found) {
			String msg="很抱歉， "+setMealName+"售完了。";
			throw new IngredientSortOutException(msg);
		}else {
			//如果找到，要移除相应的饮料列表中的饮料
			boolean checkDrinks=true;
			Drinks drinkType=choose.getType();
			try {
				//如果是酒类
				if(drinkType instanceof Beer) {
					Beer beer=(Beer)drinkType;
					use(beer);
				}else if(drinkType instanceof Drinks) {//如果是果汁
					Juice juice=(Juice)drinkType;
					use(juice);
				}
			} catch (IngredientSortOutException e) {
				checkDrinks=false;
				throw new IngredientSortOutException("十分抱歉，该套餐已经售空。");
			}
			//出售成功，获得收款
			if(checkDrinks) {
				balance+=choose.getMealPrice();//收款
				System.out.println(setMealName+"购买成功，祝您用餐愉快!");
			}
		}

	}

	//进行售卖
	@Override
	public void sellMeal() {
		// TODO Auto-generated method stub
		//展示套餐菜单
		for(SetMeal meal:setMealList) {
			System.out.println(meal);
		}
		System.out.println("请输入您要购买的套餐【提示：输入1,2,3,4即可】");
		Scanner sc = new Scanner(System.in);
		//获取用户的输入
		int choose=0;
		try {
			choose = sc.nextInt();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw new RuntimeException("非法输入，请重新输入");
		}
		String chooseName="套餐"+choose;
		try {
			// 售出套餐
			use(chooseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("您输入的套餐不存在，请重新输入或到进货点购买。");
		}

	}


	/**
	 * 批量进货
	 */
	@Override
	public void purchases() {
		Scanner sc=new Scanner(System.in);
		System.out.println("欢迎进入西二进货点");
		while(true) {

			System.out.println("请选择您需要购买的商品");
			System.out.println("1.酒类\n2.果汁类\n3.离开");
			int choose=0;
			try {
				choose = sc.nextInt();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException("非法输入，请重新输入");
			}
			switch (choose) {
//			-----------------------------------------------------------------------
				/**
				 * 购买酒类
				 */
				case 1:
					System.out.println("欢迎您来到酒类进货点");
					System.out.println("超市现存酒类类型如下");
					System.out.println("1.哈啤\n2.雪花\n");
					System.out.println("请输入要购买的酒类");
					int type=sc.nextInt();
					int count=0;//购买数量
					switch (type) {
						/**
						 * 购买哈啤
						 */
						case 1:
							System.out.println("请输入要购买的数量");
							count = sc.nextInt();
							//检查余额是否足够
							//	计算购买预计花费
							double expectMoney1=count*6;
							if(balance-expectMoney1<0) {

								//计算进货差多少钱
								double overBudget=-(balance-expectMoney1);
								throw new OverdraftBalanceException("进货所需费用超出拥有余额，还差"+overBudget+"元");
							}

							int i=0;//用来记录第几件商品
							System.out.println("请输入您要购买的商品的生产日期");
							while(count>0) {
								i++;
//								让用户输入要购买的视频的日期
								System.out.println("请输入第"+i+"件商品的生产年份");
								int year=sc.nextInt();
								System.out.println("请输入第"+i+"件的商品的生产月份");
								int month=sc.nextInt();
								System.out.println("请输入第"+i+"件的商品的生产日期");
								int day=sc.nextInt();

								Beer b=new Beer("哈啤", 6, LocalDate.of(year, month, day), 2.5f);
								if(b.checkIfOverDue()){
									System.out.println("该商品已经过期，请重新选择");
									i--;
									continue;
								}
								beerList.add(b);
								count--;
							}
							System.out.println("购买成功！");
							balance-=expectMoney1;//收银

							break;
						/**
						 * 购买雪花
						 */
						case 2:
							System.out.println("请输入要购买的数量");

							count=sc.nextInt();

							//检查余额是否足够
							//					计算购买预计花费
							double expectMoney2=count*7;
							if(balance-expectMoney2<0) {

								//计算进货差多少钱
								double overBudget=-(balance-expectMoney2);
								throw new OverdraftBalanceException("进货费用超出拥有余额，还差"+overBudget+"元");
							}
							//					用来记录第几件商品
							int i2=0;
							System.out.println("请输入您要购买的商品的生产日期");
							while(count>0) {
								i2++;
								//						让用户输入要购买的视频的日期
								System.out.println("请输入第"+i2+"件商品的生产年份");
								int year=sc.nextInt();
								System.out.println("请输入第"+i2+"件的商品的生产月份");
								int month=sc.nextInt();
								System.out.println("请输入第"+i2+"件的商品的生产日期");
								int day=sc.nextInt();
								Beer b=new Beer("雪花", 7, LocalDate.of(year, month, day), 3.5f);
								if(b.checkIfOverDue()){
									System.out.println("该商品已经过期，请重新选择");
									i2--;
									continue;
								}
								beerList.add(b);
								count--;
							}
							System.out.println("购买成功！");
							balance-=expectMoney2;
							break;

						default:
							System.out.println("非法输入。");
							break;
					}

					break;
//					-----------------------------------------------------------------------
//					-----------------------------------------------------------------------
				/**
				 * 购买果汁
				 */
				case 2:
					System.out.println("欢迎您来到果汁进货点");
					System.out.println("超市现存果汁类型如下");
					System.out.println("1.果粒橙\n2.椰汁\n");
					System.out.println("请输入要购买的果汁");
					int type2=sc.nextInt();

					switch (type2) {
						/**
						 * 购买果粒橙
						 */
						case 1:
							System.out.println("请输入要购买的数量");
							count=sc.nextInt();

							//检查余额是否足够

							//					计算购买预计花费
							double expectMoney1=count*4;
							//					如果可以购买后余额足够，则购买
							if(balance-expectMoney1<0) {
								//计算进货差多少钱
								double overBudget=-(balance-expectMoney1);
								throw new OverdraftBalanceException("进货费用超出拥有余额，还差"+overBudget+"元");
							}
							//					用来记录第几件商品
							int i=0;
							System.out.println("请输入您要购买的商品的生产日期");
							while(count>0) {
								i++;
								//						让用户输入要购买的视频的日期
								System.out.println("请输入第"+i+"件商品的生产年份");
								int year=sc.nextInt();
								System.out.println("请输入第"+i+"件的商品的生产月份");
								int month=sc.nextInt();
								System.out.println("请输入第"+i+"件的商品的生产日期");
								int day=sc.nextInt();
								Juice j=new Juice("果粒橙", 4, LocalDate.of(year, month, day));
								if(j.checkIfOverDue()){
									System.out.println("该商品已经过期了，请重新输入");
									i--;
									continue;
								}
								juiceList.add(j);
								count--;
							}
							System.out.println("购买成功！");
							balance-=expectMoney1;//收银
							break;
						/**
						 * 购买椰汁
						 */
						case 2:
							System.out.println("请输入要购买的数量");
							count=sc.nextInt();
							//检查余额是否足够

							//					计算购买预计花费
							double expectMoney2=count*5;
							//					如果可以购买后余额足够，则购买
							if(balance-expectMoney2<0) {
								//计算进货差多少钱
								double overBudget=-(balance-expectMoney2);
								throw new OverdraftBalanceException("进货费用超出拥有余额，还差"+overBudget+"元");
							}
							//					用来记录第几件商品
							int i2=0;
							System.out.println("请输入您要购买的商品的生产日期");
							while(count>0) {
								i2++;
								//						让用户输入要购买的视频的日期
								System.out.println("请输入第"+i2+"件商品的生产年份");
								int year=sc.nextInt();
								System.out.println("请输入第"+i2+"件的商品的生产月份");
								int month=sc.nextInt();
								System.out.println("请输入第"+i2+"件的商品的生产日期");
								int day=sc.nextInt();
								Juice j=new Juice("椰汁", 5, LocalDate.of(year, month, day));
								if(j.checkIfOverDue()){
									System.out.println("该商品已经过期，请重新选择");
									i2--;
									continue;
								}
								juiceList.add(j);
								count--;
							}
							System.out.println("购买成功！");
							balance-=expectMoney2;
							break;

						default:
							System.out.println("非法输入");
							break;
					}

					break;
//							-----------------------------------------------------------------------

				case 3:
					System.out.println("返回主页面成功");
					return;

				default:
					System.out.println("非法输入");
					break;
			}
			/**
			 * 选择操作
			 */
			System.out.println("请选择\n1.继续进货\n2.返回主页面");
			int choose1=sc.nextInt();
			switch (choose1) {
				case 1:
					//继续进货
					break;
				case 2:
					System.out.println("进货结束");
					return;
				default:
					System.out.println("非法输入，请重新输入您的选择");
					break;
			}
		}

	}

	/**
	 * 	 返回余额
	 * @return
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * 查看库存
	 */
	public void checkInventory () {
		int hapiCount=0,baiweiCount=0,minuteMaid=0,coconutJuice=0;
		for(Beer beer:beerList) {
			String name=beer.getDrinkName();
			switch (name) {
				case "哈啤":
					hapiCount++;
					break;
				case "雪花":
					baiweiCount++;
					break;
				default:
					break;
			}

		}
		for(Juice juice:juiceList) {
			String name=juice.getDrinkName();
			switch (name) {
				case "果粒橙":
					minuteMaid++;
					break;
				case "椰汁":
					coconutJuice++;
					break;
				default:
					break;
			}


		}
		System.out.println("套餐一【哈啤】现有库存"+hapiCount+"套");
		System.out.println("套餐二【雪花】现有库存"+baiweiCount+"套");
		System.out.println("套餐三【果粒橙】现有库存"+minuteMaid+"套");
		System.out.println("套餐四【椰汁】现有库存"+coconutJuice+"套");

	}


}
