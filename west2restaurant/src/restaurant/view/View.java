package restaurant.view;

import restaurant.service.West2FriedChickenRestaurant;

import java.util.Scanner;


public class View {


	public static void main(String[] args) {
//		获取用户的输入
		Scanner sc=new Scanner(System.in);
		System.out.println("欢迎您来到西二炸鸡店");
		System.out.println("请输入当前餐厅的余额");
		
//		获取用户设置的余额值，然后创建餐厅
		double balance=0;
			try {
				balance=sc.nextDouble();

			}catch (Exception e){
				throw new RuntimeException("非法输入，请重新输入");

			}
		West2FriedChickenRestaurant restaurant=new West2FriedChickenRestaurant(balance);
		System.out.println("设置成功！");
		System.out.println("欢迎您来到西二炸鸡店管理系统");
//		用choose来保存用户的选择
		int choose=0;
		boolean loopFlag=true;//控制循环
		while (loopFlag) {
//			创建页面
			String menuList ="-------西二炸鸡店管理主页面-------\n";
			menuList += "1.点餐\n";
			menuList += "2.查看库存\n";
			menuList += "3.开始进货\n";
			menuList += "4.查看餐厅余额\n";
			menuList += "5.退出\n";
			menuList += "请输入您的操作【请输入数字】\n";
			menuList += "----------------------------------\n";
			System.out.println(menuList);
//			获取用户的选择
			try {
				choose = sc.nextInt();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("非法输入");
			}
			//根据选择进行不同的操作
			switch (choose) {
//			1.点餐
			case 1:
				try {
					restaurant.sellMeal();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} 
				break;
//			2.查看库存
			case 2:
				restaurant.checkInventory();
				break;
//			3.开始进货
			case 3:
				try {
					restaurant.purchases();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				break;
//			4.查看餐厅余额
			case 4:
				System.out.println("当前餐厅余额："+restaurant.getBalance()+"元");
				break;
//			5.退出
			case 5:
				System.out.println("请再次确认是否退出(Y/N)");
				char isExit=sc.next().charAt(0);
				if(isExit=='Y'||isExit=='y') {
					loopFlag=false;
					System.out.println("您已经成功退出西二炸鸡店系统");
				}
				break;
			default:
				System.out.println("您的输入无效，请重新输入。");
				break;
			}
		}

		
	}


}
