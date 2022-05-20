package ui.tabs;

import ui.OCafe;
import ui.tabs.CategoryPane;
import ui.tabs.Tab;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jdk.nashorn.internal.parser.JSONParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherTab extends Tab {
	// data/images 폴더에 이미지 추가 필요함 ( 확장자 jpg 로 통일해줘야 코드 변경 없이 사용하기 편함 ) // 배열 안에 있는 이름과 이미지파일 이름이 같아야함(띄어쓰기, 공백, 숫자 등등 다 전부 다)
    private static final String[] rainCoffee = {"Espresso", "Americano", "Macchiato", "Latte", "TEST1", "TEST2","TEST3","TEST4","TEST5"};
    private static final String[] noRainCoffee = {"Espresso", "Americano", "Macchiato", "Latte"};
    private static final String[] tea = {
            "Matcha Latte", "Hojicha Latte", "London Fog", "Chai Latte", "Sencha", "Black Tea"};
    private static final String[] noncaffeinated = {
            "Honey Ginger Tea", "Fruit Tea", "Kumquat Chrysanthemum Tea", "Hibiscus Kombucha", "Mango Kale Smoothie"};
    private static final String[] brunch = {
            "Thai Green Curry Seafood Linguine", "Eggs Benedict", "Omurice", "Butternut Squash Risotto",
            "Japanese Curry Rice", "Dutch Cheese Sandwich", "Spring Salad", "Butter Croissant"};
    private static final String[] dessert = {
            "Kinako Mochi", "Raspberry Pistachio Cream Tart", "Banana Cream Pie", "Sweet Potato Crepe",
            "Hojicha Parfait", "Chestnut Cake", "Tofu Ice Cream"};

    private static final String COFFEE = "Coffee";
    private static final String TEA = "Tea";
    private static final String NONCAFFEINATED = "Noncaffeinated";
    private static final String BRUNCH = "Brunch";
    private static final String DESSERT = "Dessert";
    private static final String[] categories = {COFFEE, TEA, NONCAFFEINATED, BRUNCH, DESSERT};

    // 현재는 수기로 변경해줘야됨
    private String weather = "Cold";//
    
    
    private JPanel categorySelectorPane;
    private JPanel categoryContainer;
    private JPanel itemDetailsContainer;
    private String[] categories2;
    private String[] obsrValue2;
    private GridBagLayout gridBagLayout;
    private JLabel title;

    // creates menu tab with coffee category selected
    public WeatherTab(OCafe controller) throws IOException, SAXException, ParserConfigurationException {
        super(controller);
        setBorder(BorderFactory.createEmptyBorder(20, 20,30,25));

        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        placeTitle();

        placeCategorySelectorPanel();

        placeItemDetailsContainer();
        getWeather();
        placeCategoryContainer();

        //빈 공간 날씨 관련 추가
        //displayNewCategory(raincoffee);
    }
    //String authKey = "N6YNoQqTapDv9A%2BfGAzqVwzRkddWoYfJrSfx1eRoFLAIPZrBxcB%2FtfelMEPnRfGeuL9ev7sFvXLoWXcM8lm1yQ%3D%3D"; // 본인 서비스 키

    private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
    
    public void getWeather() throws IOException, SAXException, ParserConfigurationException {
    	// 변수 설정
        String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String authKey = "N6YNoQqTapDv9A%2BfGAzqVwzRkddWoYfJrSfx1eRoFLAIPZrBxcB%2FtfelMEPnRfGeuL9ev7sFvXLoWXcM8lm1yQ%3D%3D"; // 본인 서비스 키

		// 구하고자 하는 시간과 좌표 대입
        String nx = "93";
        String ny = "91";
        Date todayYHD = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
		
		String today = sdf.format(todayYHD); // 년월일
		
		//23시에는 안되는 이슈가 있음
		String todayTime = sdf2.format(todayYHD); //시간
		System.out.println(todayTime);
		
		String baseDate = "20220519";	//조회하고싶은 날짜
		String baseTime = "1500";	//조회하고싶은 시간

        String dataType = "JSON";

        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows=10", "UTF-8"));    // 표 개수
        urlBuilder.append("&" + URLEncoder.encode("pageNo=1", "UTF-8"));    // 페이지 수
        // JSON 형식으로 반환을 원하면 주석 제거
//         urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 받으려는 타입
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); // 조회하고 싶은 날짜
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); // 조회하고싶은 시간
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); // x좌표
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); // y좌표

        URL url = new URL(urlBuilder.toString());
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();

		// 테스트를 위해 출력
        System.out.println(result);
        String url2 = urlBuilder.toString();
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(url2);
        
        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); 
        
        NodeList nList = doc.getElementsByTagName("item");
        System.out.println("파싱할 리스트 수 : "+ nList.getLength());
        
        
        for(int temp = 0; temp < nList.getLength(); temp++){		
        	Node nNode = nList.item(temp);
        	if(nNode.getNodeType() == Node.ELEMENT_NODE){
        						
        		Element eElement = (Element) nNode;
        		System.out.println("######################");
        		//System.out.println(eElement.getTextContent());
//        		System.out.println("baseDate  : " + getTagValue("baseDate", eElement));

        		String baseDateXML = getTagValue("baseDate", eElement);
        		String baseTimeXML = getTagValue("baseTime", eElement);
        		String categoryXML = getTagValue("category", eElement);
        		String nxXML = getTagValue("nx", eElement);
        		String nyXML = getTagValue("ny", eElement);
        		String obsrValueXML = getTagValue("obsrValue", eElement);     		
        		System.out.println("baseDate = " + baseDateXML);
        		System.out.println("baseTime = " + baseTimeXML);
        		System.out.println("category = " + categoryXML);
        		System.out.println("nx = " + nxXML);
        		System.out.println("ny = " + nyXML);
        		System.out.println("obsrValue = " + obsrValueXML);
        		
        		System.out.println("-----------------------------------------------------------");
        		
        		System.out.println("baseTime  : " + getTagValue("baseDate", eElement));
        		System.out.println("baseTime  : " + getTagValue("baseTime", eElement));
        		System.out.println("category : " + getTagValue("category", eElement));
        		System.out.println("nx  : " + getTagValue("nx", eElement));
        		System.out.println("ny : " + getTagValue("ny", eElement));
        		System.out.println("obsrValue : " + getTagValue("obsrValue", eElement));
        	}	// for end
        }
        
//        JSONArray jArray = jObject.getJSONArray("items");
//        
//        for(int i = 0 ; i < jArray.length(); i++) {
//        	JSONObject obj = jArray.getJSONObject(i);
//        	String baseDateJSON = obj.getString("baseDate");
//        	System.out.println(i + " : " + baseDateJSON);
//        }
        
        
        
    }
    //EFFECTS: creates title at top of console
    private void placeTitle() {
        title = new JLabel();
        setTitle("MENU");

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;

        add(title, c);
    }
    
    
    //EFFECTS: places panel with buttons for each menu category,
    //         changes display of categoryContainer and title when clicked
    //각 메뉴 범주에 대한 버튼이 있는 패널을 배치합니다.
    // 클릭하면 범주 컨테이너 및 제목 표시 변경
    private void placeCategorySelectorPanel() {
        categorySelectorPane = initializeDefaultPanel();

        for (String s : categories) { // COFFEE, TEA, NONCAFFEINATED, BRUNCH, DESSERT
            JButton b = new JButton(s);
            b.addActionListener(new CategorySelector());
            categorySelectorPane.add(b);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;

        add(categorySelectorPane, c);
    }

    //EFFECTS: creates container for category pane
    // 범주 창에 대한 컨테이너 생성
    private void placeCategoryContainer() {
        categoryContainer = initializeDefaultPanel();
        add(categoryContainer);
    }

    //EFFECTS: creates container for item details pane
    // 항목 상세 내역 창에 대한 컨테이너 생성

    private void placeItemDetailsContainer() {
        itemDetailsContainer = initializeDefaultPanel();
        add(itemDetailsContainer);
    }

    //EFFECTS: sets the title displayed at the top of MenuTab
    // 메뉴 탭의 맨 위에 표시되는 제목을 설정합니다.

    private void setTitle(String category) {
        title.setText(category);
        title.setFont(new Font("", Font.PLAIN, 16));
        title.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: creates a panel of buttons representing each menu item in a category,
    //         buttons display item name and price, displays further details when clicked
    // 범주의 각 메뉴 항목을 나타내는 버튼 패널을 만듭니다.
 // 단추는 품목 이름 및 가격을 표시하고, 클릭하면 추가 세부 정보를 표시합니다.
    private void displayNewCategory(String[] category) {
        setNewCategoryGridBagConstraints();
        // 여기서 this 는 해당 Class 를 의미 ( WeatherTab )
        CategoryPane cp = new CategoryPane(this, getController(), category);
        setContainerContent(categoryContainer, cp);

        itemDetailsContainer.removeAll();
        itemDetailsContainer.revalidate();
    }

    //MODIFIED: this
    //EFFECTS: sets layout to show the category panel and the item details panel
    //         removes previous panel and adds parameter to itemDetailsContainer
    //범주 패널 및 항목 세부 정보 패널을 표시하도록 레이아웃을 설정합니다.
    // 이전 패널을 제거하고 itemDetailsContainer에 매개 변수를 추가합니다.
    public void displayItemDetails(ItemDetailsPane p) {
        setDisplayItemDetailsGridBagConstraints();
        categoryContainer.revalidate();
        setContainerContent(itemDetailsContainer, p);
    }
    
    // 위 내용 Weather 로 변환 ------------------------------------------------
    public void displayItemDetailsWeather(ItemDetailsPaneWeather p) {
        setDisplayItemDetailsGridBagConstraints();
        categoryContainer.revalidate();
        setContainerContent(itemDetailsContainer, p);
    }
    //---------------------------------------------------------------------
    //MODIFIES: this
    //EFFECTS: sets GridBagConstraints for categoryContainer and itemDetailsContainer to only display categoryContainer
    // 범주 Container 및 itemDetailsContainer에 대한 GridBagConstraints를 범주 Container만 표시하도록 설정합니다.

    private void setNewCategoryGridBagConstraints() {
        GridBagConstraints categoryConstraints = new GridBagConstraints();
        categoryConstraints.weightx = 1.0;
        categoryConstraints.weighty = 1.0;
        categoryConstraints.gridx = 0;
        categoryConstraints.gridy = 2;
        categoryConstraints.gridwidth = 2;
        categoryConstraints.gridheight = 9;
        categoryConstraints.fill = GridBagConstraints.BOTH;
        categoryConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagLayout.setConstraints(categoryContainer, categoryConstraints);
        gridBagLayout.setConstraints(itemDetailsContainer, new GridBagConstraints());
    }

    //MODIFIES: this
    //EFFECTS: sets GridBag Constraints for CategoryContainer and itemDetailsContainer to show both panels
    //CategoryContainer 및 itemDetailsContainer에 대한 GridBag 구속조건을 두 패널을 모두 표시하도록 설정합니다.

    private void setDisplayItemDetailsGridBagConstraints() {
        GridBagConstraints categoryConstraints = new GridBagConstraints();
        categoryConstraints.weightx = 0.5;
        categoryConstraints.weighty = 1.0;
        categoryConstraints.gridx = 0;
        categoryConstraints.gridy = 2;
        categoryConstraints.gridheight = 9;
        categoryConstraints.fill = GridBagConstraints.HORIZONTAL;
        categoryConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        gridBagLayout.setConstraints(categoryContainer, categoryConstraints);

        GridBagConstraints itemDetailsConstraints = new GridBagConstraints();
        itemDetailsConstraints.weightx = 0.5;
        itemDetailsConstraints.gridx = 1;
        itemDetailsConstraints.gridy = 2;
        itemDetailsConstraints.gridheight = 9;
        itemDetailsConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        gridBagLayout.setConstraints(itemDetailsContainer, itemDetailsConstraints);
    }

    //MODIFIES: this
    //EFFECTS: replaces previous panel in container with parameter p
    //https://stackoverflow.com/questions/9401353/how-to-change-the-jpanel-in-a-jframe-at-runtime
    //컨테이너의 이전 패널을 매개 변수 p로 바꿉니다.
    
    private void setContainerContent(JPanel container, Tab p) {
        container.removeAll();
        container.setSize(p.getSize());
        container.add(p);
        container.revalidate();
    }

    //action listener for buttons in category selector panel
    //범주 선택기 패널의 단추에 대한 작업 수신기

    private class CategorySelector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonPressed = e.getActionCommand();
            switch (buttonPressed) {
                case COFFEE:
                	if(weather.equals("Cold")) { // 날씨에 따른 메뉴를 보여주기 위한 테스트 위에 Line 32 참고 하면 됨 ( 현재는 수기로 바꿔줘야됨 )
                		//날씨에 따라 메뉴가 다르게 나오는지만 확인 하면 되는 부분
                		displayNewCategory(noRainCoffee);
                	}else if(weather.equals("Hot")) {
                		displayNewCategory(rainCoffee);
                	}
                    break;
                case TEA:
                    displayNewCategory(tea);
                    break;
                case NONCAFFEINATED:
                    displayNewCategory(noncaffeinated);
                    break;
                case BRUNCH:
                    displayNewCategory(brunch);
                    break;
                case DESSERT:
                    displayNewCategory(dessert);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + buttonPressed);
            }
            setTitle(buttonPressed);
            controller.playSound("./data/sounds/Morse.wav");
        }
    }

}