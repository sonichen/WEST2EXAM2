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
	 * �������
	 */
	private double balance;//���
	private LinkedList<Beer> beerList;//ơ���б�
	private  LinkedList <Juice> juiceList;//��֭�б�
	private final static LinkedList <SetMeal> setMealList;//�ײ��б�

	/**
	 * ���캯��
	 */
	public West2FriedChickenRestaurant(double balance) {
		super();
		this.balance = balance;
		this.beerList = new LinkedList<Beer>();
		this.juiceList = new LinkedList<Juice>();
		repertory();//Ϊ��ʹ�����ܹ�Ӫҵ���ṩ�����ײͿ���һ��
	}
	/**
	 * ��ʼ���ײ��б�
	 */
	static {
		setMealList=new LinkedList<SetMeal>();
		setMealList.add(new SetMeal("�ײ�1", 35, "��ʽը��", new Beer("��ơ", 6, LocalDate.now(), 2.5f)));
		setMealList.add(new SetMeal("�ײ�2", 45, "��ʽը��", new Beer("ѩ��", 7, LocalDate.now(), 3.5f)));
		setMealList.add(new SetMeal("�ײ�3", 30, "��ʽը��", new Juice("������", 4, LocalDate.now())));
		setMealList.add(new SetMeal("�ײ�4", 35, "̩ʽը��", new Juice("Ҭ֭", 5, LocalDate.now())));
	}

	/**
	 * Ϊ��ʹ�����ܹ�Ӫҵ���ṩ�����ײͿ���һ��
	 */
	public void repertory(){
		beerList.add(new Beer("��ơ", 6, LocalDate.now(),2.5f));
		beerList.add(new Beer("ѩ��", 7, LocalDate.now(),3.5f));
		juiceList.add(new Juice("������", 5, LocalDate.now()));
		juiceList.add(new Juice("Ҭ֭", 5, LocalDate.now()));
	}
	/**
	 * �ڳ����ײͣ�ͬʱ�Ƴ���Ӧ�ľ���
	 *
	 * @param beer Ҫ�����۵ľ���
	 */
	public void use(Beer beer) {

		//��¼�Ƿ��ҵ�������
		boolean found=false;
		if(beerList!=null) {
//			�������֣������ҵ���Ӧ�ľ���
			for(Beer oneBeer:beerList) {
				if(oneBeer.getDrinkName().equals(beer.getDrinkName())) {
					beerList.remove(oneBeer);
					found=true;
					break;
				}
			}
		}
		//���û���ҵ����׳��쳣
		if(!found) {
			String msg="�ܱ�Ǹ�� "+beer.getDrinkName()+"�Ѿ��ۿա�";
			throw new IngredientSortOutException(msg);
		}
	}

	/**
	 * �ڳ����ײͣ�ͬʱ�Ƴ���Ӧ�Ĺ�֭
	 *
	 * @param juice Ҫ�����۵Ĺ�֭
	 */
	public void use(Juice juice) {

		//��¼�Ƿ��ҵ�������
		boolean found=false;

		if(juiceList!=null) {
//			�������֣������ҵ���Ӧ�Ĺ�֭
			for(Juice oneJuice:juiceList) {
				if(oneJuice.getDrinkName().equals(juice.getDrinkName())) {
					juiceList.remove(oneJuice);
					found=true;
					break;
				}
			}
		}
		//���û���ҵ����׳��쳣
		if(!found) {
			String msg="�ܱ�Ǹ�� "+juice.getDrinkName()+"�Ѿ��ۿա�";
			throw new IngredientSortOutException(msg);
		}
	}
	/**
	 * �����ײ�
	 */
	public void use(String setMealName ) {
		//��¼�Ƿ��ҵ����ײ�
		boolean found=false;
		SetMeal choose = new SetMeal();
		if(setMealList!=null) {
//			�������֣������ҵ���Ӧ���ײ�
			for(SetMeal meal:setMealList) {
				if(setMealName.equals(meal.getMealName())) {
					found=true;
					choose=meal;
					break;
				}
			}
		}
		//���û�ҵ�
		if(!found) {
			String msg="�ܱ�Ǹ�� "+setMealName+"�����ˡ�";
			throw new IngredientSortOutException(msg);
		}else {
			//����ҵ���Ҫ�Ƴ���Ӧ�������б��е�����
			boolean checkDrinks=true;
			Drinks drinkType=choose.getType();
			try {
				//����Ǿ���
				if(drinkType instanceof Beer) {
					Beer beer=(Beer)drinkType;
					use(beer);
				}else if(drinkType instanceof Drinks) {//����ǹ�֭
					Juice juice=(Juice)drinkType;
					use(juice);
				}
			} catch (IngredientSortOutException e) {
				checkDrinks=false;
				throw new IngredientSortOutException("ʮ�ֱ�Ǹ�����ײ��Ѿ��ۿա�");
			}
			//���۳ɹ�������տ�
			if(checkDrinks) {
				balance+=choose.getMealPrice();//�տ�
				System.out.println(setMealName+"����ɹ���ף���ò����!");
			}
		}

	}

	//��������
	@Override
	public void sellMeal() {
		// TODO Auto-generated method stub
		//չʾ�ײͲ˵�
		for(SetMeal meal:setMealList) {
			System.out.println(meal);
		}
		System.out.println("��������Ҫ������ײ͡���ʾ������1,2,3,4���ɡ�");
		Scanner sc = new Scanner(System.in);
		//��ȡ�û�������
		int choose=0;
		try {
			choose = sc.nextInt();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw new RuntimeException("�Ƿ����룬����������");
		}
		String chooseName="�ײ�"+choose;
		try {
			// �۳��ײ�
			use(chooseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("��������ײͲ����ڣ�����������򵽽����㹺��");
		}

	}


	/**
	 * ��������
	 */
	@Override
	public void purchases() {
		Scanner sc=new Scanner(System.in);
		System.out.println("��ӭ��������������");
		while(true) {

			System.out.println("��ѡ������Ҫ�������Ʒ");
			System.out.println("1.����\n2.��֭��\n3.�뿪");
			int choose=0;
			try {
				choose = sc.nextInt();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException("�Ƿ����룬����������");
			}
			switch (choose) {
//			-----------------------------------------------------------------------
				/**
				 * �������
				 */
				case 1:
					System.out.println("��ӭ���������������");
					System.out.println("�����ִ������������");
					System.out.println("1.��ơ\n2.ѩ��\n");
					System.out.println("������Ҫ����ľ���");
					int type=sc.nextInt();
					int count=0;//��������
					switch (type) {
						/**
						 * �����ơ
						 */
						case 1:
							System.out.println("������Ҫ���������");
							count = sc.nextInt();
							//�������Ƿ��㹻
							//	���㹺��Ԥ�ƻ���
							double expectMoney1=count*6;
							if(balance-expectMoney1<0) {

								//������������Ǯ
								double overBudget=-(balance-expectMoney1);
								throw new OverdraftBalanceException("����������ó���ӵ��������"+overBudget+"Ԫ");
							}

							int i=0;//������¼�ڼ�����Ʒ
							System.out.println("��������Ҫ�������Ʒ����������");
							while(count>0) {
								i++;
//								���û�����Ҫ�������Ƶ������
								System.out.println("�������"+i+"����Ʒ���������");
								int year=sc.nextInt();
								System.out.println("�������"+i+"������Ʒ�������·�");
								int month=sc.nextInt();
								System.out.println("�������"+i+"������Ʒ����������");
								int day=sc.nextInt();

								Beer b=new Beer("��ơ", 6, LocalDate.of(year, month, day), 2.5f);
								if(b.checkIfOverDue()){
									System.out.println("����Ʒ�Ѿ����ڣ�������ѡ��");
									i--;
									continue;
								}
								beerList.add(b);
								count--;
							}
							System.out.println("����ɹ���");
							balance-=expectMoney1;//����

							break;
						/**
						 * ����ѩ��
						 */
						case 2:
							System.out.println("������Ҫ���������");

							count=sc.nextInt();

							//�������Ƿ��㹻
							//					���㹺��Ԥ�ƻ���
							double expectMoney2=count*7;
							if(balance-expectMoney2<0) {

								//������������Ǯ
								double overBudget=-(balance-expectMoney2);
								throw new OverdraftBalanceException("�������ó���ӵ��������"+overBudget+"Ԫ");
							}
							//					������¼�ڼ�����Ʒ
							int i2=0;
							System.out.println("��������Ҫ�������Ʒ����������");
							while(count>0) {
								i2++;
								//						���û�����Ҫ�������Ƶ������
								System.out.println("�������"+i2+"����Ʒ���������");
								int year=sc.nextInt();
								System.out.println("�������"+i2+"������Ʒ�������·�");
								int month=sc.nextInt();
								System.out.println("�������"+i2+"������Ʒ����������");
								int day=sc.nextInt();
								Beer b=new Beer("ѩ��", 7, LocalDate.of(year, month, day), 3.5f);
								if(b.checkIfOverDue()){
									System.out.println("����Ʒ�Ѿ����ڣ�������ѡ��");
									i2--;
									continue;
								}
								beerList.add(b);
								count--;
							}
							System.out.println("����ɹ���");
							balance-=expectMoney2;
							break;

						default:
							System.out.println("�Ƿ����롣");
							break;
					}

					break;
//					-----------------------------------------------------------------------
//					-----------------------------------------------------------------------
				/**
				 * �����֭
				 */
				case 2:
					System.out.println("��ӭ��������֭������");
					System.out.println("�����ִ��֭��������");
					System.out.println("1.������\n2.Ҭ֭\n");
					System.out.println("������Ҫ����Ĺ�֭");
					int type2=sc.nextInt();

					switch (type2) {
						/**
						 * ���������
						 */
						case 1:
							System.out.println("������Ҫ���������");
							count=sc.nextInt();

							//�������Ƿ��㹻

							//					���㹺��Ԥ�ƻ���
							double expectMoney1=count*4;
							//					������Թ��������㹻������
							if(balance-expectMoney1<0) {
								//������������Ǯ
								double overBudget=-(balance-expectMoney1);
								throw new OverdraftBalanceException("�������ó���ӵ��������"+overBudget+"Ԫ");
							}
							//					������¼�ڼ�����Ʒ
							int i=0;
							System.out.println("��������Ҫ�������Ʒ����������");
							while(count>0) {
								i++;
								//						���û�����Ҫ�������Ƶ������
								System.out.println("�������"+i+"����Ʒ���������");
								int year=sc.nextInt();
								System.out.println("�������"+i+"������Ʒ�������·�");
								int month=sc.nextInt();
								System.out.println("�������"+i+"������Ʒ����������");
								int day=sc.nextInt();
								Juice j=new Juice("������", 4, LocalDate.of(year, month, day));
								if(j.checkIfOverDue()){
									System.out.println("����Ʒ�Ѿ������ˣ�����������");
									i--;
									continue;
								}
								juiceList.add(j);
								count--;
							}
							System.out.println("����ɹ���");
							balance-=expectMoney1;//����
							break;
						/**
						 * ����Ҭ֭
						 */
						case 2:
							System.out.println("������Ҫ���������");
							count=sc.nextInt();
							//�������Ƿ��㹻

							//					���㹺��Ԥ�ƻ���
							double expectMoney2=count*5;
							//					������Թ��������㹻������
							if(balance-expectMoney2<0) {
								//������������Ǯ
								double overBudget=-(balance-expectMoney2);
								throw new OverdraftBalanceException("�������ó���ӵ��������"+overBudget+"Ԫ");
							}
							//					������¼�ڼ�����Ʒ
							int i2=0;
							System.out.println("��������Ҫ�������Ʒ����������");
							while(count>0) {
								i2++;
								//						���û�����Ҫ�������Ƶ������
								System.out.println("�������"+i2+"����Ʒ���������");
								int year=sc.nextInt();
								System.out.println("�������"+i2+"������Ʒ�������·�");
								int month=sc.nextInt();
								System.out.println("�������"+i2+"������Ʒ����������");
								int day=sc.nextInt();
								Juice j=new Juice("Ҭ֭", 5, LocalDate.of(year, month, day));
								if(j.checkIfOverDue()){
									System.out.println("����Ʒ�Ѿ����ڣ�������ѡ��");
									i2--;
									continue;
								}
								juiceList.add(j);
								count--;
							}
							System.out.println("����ɹ���");
							balance-=expectMoney2;
							break;

						default:
							System.out.println("�Ƿ�����");
							break;
					}

					break;
//							-----------------------------------------------------------------------

				case 3:
					System.out.println("������ҳ��ɹ�");
					return;

				default:
					System.out.println("�Ƿ�����");
					break;
			}
			/**
			 * ѡ�����
			 */
			System.out.println("��ѡ��\n1.��������\n2.������ҳ��");
			int choose1=sc.nextInt();
			switch (choose1) {
				case 1:
					//��������
					break;
				case 2:
					System.out.println("��������");
					return;
				default:
					System.out.println("�Ƿ����룬��������������ѡ��");
					break;
			}
		}

	}

	/**
	 * 	 �������
	 * @return
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * �鿴���
	 */
	public void checkInventory () {
		int hapiCount=0,baiweiCount=0,minuteMaid=0,coconutJuice=0;
		for(Beer beer:beerList) {
			String name=beer.getDrinkName();
			switch (name) {
				case "��ơ":
					hapiCount++;
					break;
				case "ѩ��":
					baiweiCount++;
					break;
				default:
					break;
			}

		}
		for(Juice juice:juiceList) {
			String name=juice.getDrinkName();
			switch (name) {
				case "������":
					minuteMaid++;
					break;
				case "Ҭ֭":
					coconutJuice++;
					break;
				default:
					break;
			}


		}
		System.out.println("�ײ�һ����ơ�����п��"+hapiCount+"��");
		System.out.println("�ײͶ���ѩ�������п��"+baiweiCount+"��");
		System.out.println("�ײ����������ȡ����п��"+minuteMaid+"��");
		System.out.println("�ײ��ġ�Ҭ֭�����п��"+coconutJuice+"��");

	}


}