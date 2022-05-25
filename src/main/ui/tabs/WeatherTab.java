package ui.tabs;

import ui.OCafe;
import ui.tabs.CategoryPane;
import ui.tabs.Tab;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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
	// data/images 폴더에 이미지 추가 필요함 ( 확장자 jpg 로 통일해줘야 코드 변경 없이 사용하기 편함 ) // 배열 안에 있는
	// 이름과 이미지파일 이름이 같아야함(띄어쓰기, 공백, 숫자 등등 다 전부 다)
	private static final String[] rain = { "고구마 크레페", "아메리카노", "일본 카레", "키나코 모찌", "홍차", "히비스커스 차" };
	private static final String[] noRain = { "봄철 샐러드", "아메리카노", "마끼아또", "런던 포그" };
	private static final String[] snowNrain = { "에스프레소", "아메리카노", "센차", "태국 야채 카레 해산물 링귀네" };
	private static final String[] snow = { "아메리카노", "봄철 샐러드", "에스프레소" ,"버터넛 스쿼시 리조또"};

	//private static final String BEST = "Best";
	private static final String[] categories = { "weatherChange" };
	public static String weather;

	// 현재는 수기로 변경해줘야됨
//    private String weather = "Cold";//
	private String PTY;
	public static String now;
	public static String todayTMX;
	public static String todayTMN;
	public static String nowPTYValue;
	public static String nowPTYTime;
	public static String nowTMPValue;
	public static String nowTMPTime;
	public static String nowTMPValueClone;
	public static String nowTMPTimeClone;
	public static String nowPTYValueClone;
	public static String nowPTYTimeClone;
	public static String year;
	public static String mon;
	public static String day;
	public static String BestMenu;

	private JPanel categorySelectorPane;
	private JPanel categoryContainer;
	private JPanel itemDetailsContainer;
	private GridBagLayout gridBagLayout;
	private JLabel title;

	// creates menu tab with coffee category selected
	public WeatherTab(OCafe controller) throws IOException, SAXException, ParserConfigurationException {
		super(controller);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 25));

		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		getWeather();
		placeTitle();

		placeCategorySelectorPanel();
		placeItemDetailsContainer();
		placeCategoryContainer();
		
		// 빈 공간 날씨 관련 추가
		if (PTY.equals("0")) { // 날씨에 따른 메뉴를 보여주기 위한 테스트 위에 Line 32 참고 하면 됨 ( 현재는 수기로 바꿔줘야됨 )
			displayNewCategory(noRain);
		} else if (PTY.equals("1")) {
			displayNewCategory(rain);
		} else if (PTY.equals("2")) {
			displayNewCategory(snowNrain);
		} else if (PTY.equals("3")) {
			displayNewCategory(snowNrain);
		} else if (PTY.equals("4")) {
			displayNewCategory(snow);
		}
	}
	// String authKey =
	// "N6YNoQqTapDv9A%2BfGAzqVwzRkddWoYfJrSfx1eRoFLAIPZrBxcB%2FtfelMEPnRfGeuL9ev7sFvXLoWXcM8lm1yQ%3D%3D";
	// // 본인 서비스 키

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	public void getWeather() throws IOException, SAXException, ParserConfigurationException {
		// 변수 설정
		String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
		String authKey = "N6YNoQqTapDv9A%2BfGAzqVwzRkddWoYfJrSfx1eRoFLAIPZrBxcB%2FtfelMEPnRfGeuL9ev7sFvXLoWXcM8lm1yQ%3D%3D"; // 본인
																																// 서비스
																																// 키

		// 구하고자 하는 시간과 좌표 대입
		String nx = "93";
		String ny = "91";
		Date todayYHD = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");

		String today = sdf.format(System.currentTimeMillis()); // 오늘자
		String time = sdf2.format(System.currentTimeMillis()); // 현재 시각

		year = today.substring(2, 4) + "년";
		mon = today.substring(4, 6) + "월";
		day = today.substring(6, 8) + "일";

		// 23시에는 안되는 이슈가 있음
		// String todayTime = sdf2.format(todayYHD); //시간
		String viewTime = sdf3.format(todayYHD); // 시간
		now = viewTime;
		// 테스트용
		String todayTime = time; // 시간

		String baseDate = today; // 조회하고싶은 날짜
		// String baseTime = todayTime; //조회하고싶은 시간

		String baseTime = "0200"; // 조회하고싶은 시간( 바꾸면 안됨 이 시간으로 해야 원하고자하는 데이터다 뜸)
		// String dataType = "JSON";

		StringBuilder urlBuilder = new StringBuilder(apiURL);
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); // 조회하고
																													// 싶은
																													// 날짜
																													// (
																													// 이것도
																													// 바꾸면
																													// 안됨
																													// )
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 조회하고싶은
																												// 시간
		// JSON 형식으로 반환을 원하면 주석 제거
//         urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 받으려는 타입
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); // 조회하고
																														// 싶은
																														// 날짜
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); // 조회하고싶은
																														// 시간
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); // x좌표
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); // y좌표

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");

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

		String url2 = urlBuilder.toString();
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		Document doc = dBuilder.parse(url2);

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("item");
		System.out.println("파싱할 리스트 수 : " + nList.getLength());

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// System.out.println("######################");
				// System.out.println(eElement.getTextContent());
//              System.out.println("baseDate  : " + getTagValue("baseDate", eElement));

				String baseDateXML = getTagValue("baseDate", eElement);
				String baseTimeXML = getTagValue("baseTime", eElement);
				String categoryXML = getTagValue("category", eElement);
				String nxXML = getTagValue("nx", eElement);
				String nyXML = getTagValue("ny", eElement);
				String fcstValueXML = getTagValue("fcstValue", eElement);
				String fcstTimeXML = getTagValue("fcstTime", eElement);

				if (categoryXML.equals("TMX")) {
					todayTMX = fcstValueXML + "℃";
				}

				if (categoryXML.equals("TMN")) {
					todayTMN = fcstValueXML + "℃";
				}

				// 테스트용 weather = "비";
				if (categoryXML.equals("TMP")) {
					nowTMPTimeClone = fcstTimeXML;
					nowTMPValueClone = fcstValueXML;

					int nowTimeInteger = Integer.parseInt(todayTime.substring(0, 2)); // 현재시각
					int tmpTimeInteger = Integer.parseInt(nowTMPTimeClone.substring(0, 2)); // XML 데이터 가져온거

					// 데이터 조회가 19시까지라 19시가 맥시멈으로 잡음
					if (nowTimeInteger >= 20) {
						nowTimeInteger = 19;
					}

					if (nowTimeInteger == tmpTimeInteger) {
						nowTMPTime = nowTMPTimeClone;
						nowTMPValue = nowTMPValueClone + "℃";
					}

				}
				if (categoryXML.equals("PTY")) {
					nowPTYTimeClone = fcstTimeXML;
					nowPTYValueClone = fcstValueXML;

					// 현재시각임
					int nowTimeInteger = Integer.parseInt(todayTime.substring(0, 2));
					int tmpTimeInteger = Integer.parseInt(nowPTYTimeClone.substring(0, 2));

					// 데이터 조회가 18시까지라 18시가 맥시멈으로 잡음
					if (nowTimeInteger >= 20) {
						nowTimeInteger = 18;
					}

					if (nowTimeInteger == tmpTimeInteger) {
						nowPTYTime = nowPTYTimeClone;
						nowPTYValue = nowPTYValueClone;
						PTY = nowPTYValue;
						if (PTY.equals("0")) {
							weather = "맑음";
						} else if (PTY.equals("2")) {
							weather = "흐림";
						} else if (PTY.equals("3")) {
							weather = "비";
						} else if (PTY.equals("4")) {
							weather = "구름많음";
						} else if (PTY.equals("5")) {
							weather = "눈";
						}
					}
				}
			}
		}
	}

	// EFFECTS: creates title at top of console
	private void placeTitle() {
		title = new JLabel();
		setTitle(BestMenu);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;

		add(title, c);

	}

	// EFFECTS: places panel with buttons for each menu category,
	// changes display of categoryContainer and title when clicked
	// 각 메뉴 범주에 대한 버튼이 있는 패널을 배치합니다.
	// 클릭하면 범주 컨테이너 및 제목 표시 변경
	private void placeCategorySelectorPanel() {
		categorySelectorPane = initializeDefaultPanel();

		for (String s : categories) { // Best
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

	// EFFECTS: creates container for category pane
	// 범주 창에 대한 컨테이너 생성
	private void placeCategoryContainer() {
		categoryContainer = initializeDefaultPanel();
		add(categoryContainer);
	}

	// EFFECTS: creates container for item details pane
	// 항목 상세 내역 창에 대한 컨테이너 생성

	private void placeItemDetailsContainer() {
		itemDetailsContainer = initializeDefaultPanel();
		add(itemDetailsContainer);
	}

	// EFFECTS: sets the title displayed at the top of MenuTab
	// 메뉴 탭의 맨 위에 표시되는 제목을 설정합니다.

	private void setTitle(String BestMenu) {
		if (PTY.equals("0")) { // 날씨에 따른 메뉴를 보여주기 위한 테스트 위에 Line 32 참고 하면 됨 ( 현재는 수기로 바꿔줘야됨 )
			BestMenu = "맑은 날 추천 메뉴";
		} else if (PTY.equals("1")) {
			BestMenu = "비 오는 날 추천 메뉴";
		} else if (PTY.equals("2")) {
			BestMenu = "비/눈 오는 날 추천 메뉴";
		} else if (PTY.equals("3")) {
			BestMenu = "눈/비 오는 날 추천 메뉴";
		} else if (PTY.equals("4")) {
			BestMenu = "눈 오는 날 추천 메뉴";
		}
		
		title.setText(BestMenu);
		title.setFont(new Font("", Font.PLAIN, 30));
		title.revalidate();
	}

	// MODIFIES: this
	// EFFECTS: creates a panel of buttons representing each menu item in a
	// category,
	// buttons display item name and price, displays further details when clicked
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

	// MODIFIED: this
	// EFFECTS: sets layout to show the category panel and the item details panel
	// removes previous panel and adds parameter to itemDetailsContainer
	// 범주 패널 및 항목 세부 정보 패널을 표시하도록 레이아웃을 설정합니다.
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
	// ---------------------------------------------------------------------
	// MODIFIES: this
	// EFFECTS: sets GridBagConstraints for categoryContainer and
	// itemDetailsContainer to only display categoryContainer
	// 범주 Container 및 itemDetailsContainer에 대한 GridBagConstraints를 범주 Container만
	// 표시하도록 설정합니다.

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

	// MODIFIES: this
	// EFFECTS: sets GridBag Constraints for CategoryContainer and
	// itemDetailsContainer to show both panels
	// CategoryContainer 및 itemDetailsContainer에 대한 GridBag 구속조건을 두 패널을 모두 표시하도록
	// 설정합니다.

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

	// MODIFIES: this
	// EFFECTS: replaces previous panel in container with parameter p
	// https://stackoverflow.com/questions/9401353/how-to-change-the-jpanel-in-a-jframe-at-runtime
	// 컨테이너의 이전 패널을 매개 변수 p로 바꿉니다.

	private void setContainerContent(JPanel container, Tab p) {
		container.removeAll();
		container.setSize(p.getSize());
		container.add(p);
		container.revalidate();
	}

	// action listener for buttons in category selector panel
	// 범주 선택기 패널의 단추에 대한 작업 수신기
	
	private class CategorySelector implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String buttonPressed = e.getActionCommand();
			
			 // 0 : 없음 1 : 비 2 : 비/눈 3 : 눈/비 4 : 눈
			 
			// 우리가 API 로 따온거는 그 날의 날씨만 볼 수 있으므로 분기가 한 가지로만 탈 수 있는거지
			//
			switch (buttonPressed) {
			case "weatherChange":
				String change = JOptionPane.showInputDialog("변환 하실 날씨를 입력해주세요");
				if (change.equals("맑음")) {
					PTY = "0";
					displayNewCategory(noRain);
				} else if (change.equals("비")) {
					PTY = "1";
					displayNewCategory(rain);
				}else if (change.equals("비/눈")) {
					PTY = "2";
					displayNewCategory(snowNrain);
				} else if (change.equals("눈/비")) {
					PTY = "3";
					displayNewCategory(snowNrain);
				} else if (change.equals("눈")) {
					PTY = "4";
					displayNewCategory(snow);
				}
				setTitle(buttonPressed);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + buttonPressed);
			}
			controller.playSound("./data/sounds/Morse.wav");
		}
	}

}