package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class MenuLoader {

    //menu categories
    protected List<Beverage> coffee = new ArrayList<>();
    protected List<Beverage> tea = new ArrayList<>();
    protected List<Beverage> nonCaffeinated = new ArrayList<>();
    protected List<Dish> brunch = new ArrayList<>();
    protected List<Dish> dessert = new ArrayList<>();
    protected List<AdditionalOptions> additionalOptions = new ArrayList<>();

    //constructs the cafe menu, initializes new Order object, calls Kiosk with this cafe
    public MenuLoader() {
        loadCoffee();
        loadTea();
        loadNonCaffeinated();
        loadAdditionalOptions();
        loadBrunch();
        loadDessert();

    }

    //MODIFIES: this
    //EFFECTS: initializes and adds beverages to coffee category
    public void loadCoffee() {
        coffee.add(new Beverage("에스프레소", 3.45, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("아메리카노", 3.45, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        coffee.add(new Beverage("마끼아또", 3.65, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        coffee.add(new Beverage("라떼", 4.75, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("아이스 커피", 4.75, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        coffee.add(new Beverage("콜드브루", 4.25, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
//        coffee.add(new Beverage("Hot Cocoa", 5, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds beverages to tea category
    public void loadTea() {
        tea.add(new Beverage("말차 라떼", 5.95, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        tea.add(new Beverage("호지차 라떼", 5.95, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        tea.add(new Beverage("런던 포그", 4.95, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("차이 라떼", 4.95, Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
//        tea.add(new Beverage("Oolong Milk Tea", 5, Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
//        tea.add(new Beverage("Genmaicha", 4, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("센차", 3.95, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        tea.add(new Beverage("홍차", 3.25, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds beverages to non-caffeinated category
    public void loadNonCaffeinated() {
        nonCaffeinated.add(new Beverage(
                "생강 꿀 차",
                4.25,
                Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        nonCaffeinated.add(new Beverage(
                "과실 차",
                4.65,
                Beverage.NOT_CUSTOMIZABLE, Beverage.REGULAR));
        nonCaffeinated.add(new Beverage(
                "금귤 국화 차",
                4.65,
                Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        nonCaffeinated.add(new Beverage(
                "히비스커스 차",
                3.95, Beverage.NOT_CUSTOMIZABLE, Beverage.NOT_CUSTOMIZABLE));
        nonCaffeinated.add(new Beverage(
                "망고 케일 스무디",
                3.95,
                Beverage.REGULAR, Beverage.NOT_CUSTOMIZABLE));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds dishes to brunch category
    public void loadBrunch() {
        brunch.add(new Dish("태국 야채 카레 해산물 링귀네", 15.95));
        Dish eb = new Dish("에그 베네딕트", 12.95);
        eb.addSideToOptions(additionalOptions.get(0));
        eb.addSideToOptions(additionalOptions.get(1));
//        eb.addSideToAddOns(additionalOptions.get(2));
//        eb.addSideToAddOns(additionalOptions.get(3));
        brunch.add(eb);
        Dish bo = new Dish("오므라이스", 11.95);
        bo.addSideToOptions(additionalOptions.get(4));
        brunch.add(bo);
        brunch.add(new Dish("버터넛 스쿼시 리조또", 13.95));
        Dish jcr = new Dish("일본 카레", 11.95);
        jcr.addSideToOptions(additionalOptions.get(5));
        jcr.addSideToOptions(additionalOptions.get(6));
        brunch.add(jcr);
        Dish dcs = new Dish("더치 치즈 샌드위치", 10.95);
        brunch.add(dcs);
        Dish ss = new Dish("봄철 샐러드", 10.95);
        ss.addSideToOptions(additionalOptions.get(7));
        ss.addSideToOptions(additionalOptions.get(8));
        ss.addSideToOptions(additionalOptions.get(9));
        brunch.add(ss);
        Dish bc = new Dish("버터 크로아상", 3.45);
        bc.addSideToOptions(additionalOptions.get(10));
        brunch.add(bc);
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds dishes to dessert category
    public void loadDessert() {
        dessert.add(new Dish("키나코 모찌", 4.65));
        dessert.add(new Dish("라즈베리 피스타치오 크림 타르트", 5.95));
        dessert.add(new Dish("바나나 크림 파이", 5.95));
        dessert.add(new Dish("고구마 크레페", 4.65));
        dessert.add(new Dish("Hojicha Parfait", 4.65));
        dessert.add(new Dish("Chestnut Cake", 5.25));
        dessert.add(new Dish("Tofu Ice Cream", 3.75));
    }

    //MODIFIES: this
    //EFFECTS: initializes and adds dishes to additional options category
    public void loadAdditionalOptions() {
        AdditionalOptions bacon = new AdditionalOptions("Canadian Bacon", 3.00);
        AdditionalOptions salmon = new AdditionalOptions("Smoked Salmon", 4.00);
        AdditionalOptions potatoes = new AdditionalOptions("Crispy Potatoes", 0.00);
        AdditionalOptions greens = new AdditionalOptions("Mixed Greens", 0.00);
        AdditionalOptions vegetarian = new AdditionalOptions("Beef", 4.00);
        AdditionalOptions pork = new AdditionalOptions("Fried Pork Cutlet", 4.00);
        AdditionalOptions prawns = new AdditionalOptions("Assorted Tempura", 3.00);
        AdditionalOptions chicken = new AdditionalOptions("Chicken Breast", 4.00);
        AdditionalOptions tuna = new AdditionalOptions("Albacore Tuna", 5.00);
        AdditionalOptions shrimp = new AdditionalOptions("Grilled Shrimp", 5.00);
        AdditionalOptions almond = new AdditionalOptions("Almond Croissant", 1.00);

        additionalOptions.add(bacon);
        additionalOptions.add(salmon);
        additionalOptions.add(potatoes);
        additionalOptions.add(greens);
        additionalOptions.add(vegetarian);
        additionalOptions.add(pork);
        additionalOptions.add(prawns);
        additionalOptions.add(chicken);
        additionalOptions.add(tuna);
        additionalOptions.add(shrimp);
        additionalOptions.add(almond);
    }

    //getters
    public List<Beverage> getCoffee() {
        return coffee;
    }

    public List<Beverage> getTea() {
        return tea;
    }

    public List<Beverage> getNonCaffeinated() {
        return nonCaffeinated;
    }

    public List<Dish> getBrunch() {
        return brunch;
    }

    public List<Dish> getDessert() {
        return dessert;
    }
}
