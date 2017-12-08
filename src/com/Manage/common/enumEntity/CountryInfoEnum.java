package com.Manage.common.enumEntity;

public enum CountryInfoEnum
{
	Greece(202,"希腊","Greece","ギリシャ"),
	Netherlands(204,"荷兰","Netherlands","オランダ"),
	Belgium(206,"比利时","Belgium","ベルギー"),
	France(208,"法国","France","フランス"),
	Monaco(212,"摩纳哥","Monaco","モナコ"),
	Andorra(213,"安道尔","Andorra","アンドラ"), 	
	Spain(214,"西班牙","Spain","スペイン"),
	Hungary(216,"匈牙利","Hungary","ハンガリー"),
	BosniaAndHerzegovina(218,"匈牙利","Bosnia and Herzegovina","ボスニアと赫塞哥维纳"),
	Croatia(219,"克罗地亚","Croatia","クロアチア"),
	Italy(222,"意大利","Italy","イタリア"),
	Vatican(225,"梵蒂冈","Vatican","バチカン"),
	Romania(226,"罗马尼亚","Romania","ルーマニア"),
	Switzerland(228,"瑞士","Switzerland","スイス"),
	Czech(230,"捷克","Czech","チェコ"),
	Slovakia(231,"斯洛伐克","Slovakia","スロバキア"),
	Austria(232,"奥地利","Austria","オーストリア"),
	England(234,"英国","England","イギリス"),
	Denmark(238,"丹麦","Denmark","デンマーク"),
	Sweden(240,"瑞典","Sweden","スウェーデン"),
	Norway(242,"挪威","Norway","ノルウェー"),
	Finland(244,"芬兰","Finland","フィンランド"),
	Lithuania(246,"立陶宛","Lithuania","リトアニア"),
	Latvia(247,"拉脱维亚","Latvia","ラトビア"),
	Estonia(248,"爱沙尼亚","Estonia","エストニア"),
	Russia(250,"俄罗斯","Russia","ロシア"),
	Poland(260,"波兰","Poland","ポーランド"),
	Germany(262,"德国","Germany","ドイツ"),
	Gibraltar(266,"直布罗陀","Gibraltar","ジブラルタル"),
	Portugal(268,"葡萄牙","Portugal","ポルトガル"),
	Luxembourg(270,"卢森堡","Luxembourg","ルクセンブルク"),
	Ireland(272,"爱尔兰","Ireland","アイルランド"),
	Iceland(274,"冰岛","Iceland","アイスランド"),
	Albanian(276,"阿尔巴尼亚","Albanian","アルバニア"),
	Malta(278,"马耳他","Malta","マルタ"),
	Cyprus(280,"塞浦路斯","Cyprus","キプロス"),
	Georgia(282,"格鲁吉亚","Georgia","グルジア"),
	Bulgaria(284,"保加利亚","Bulgaria","ブルガリア"),
	Turkey(286,"土耳其","Turkey","トルコ"),
	TheFaroeIslands(288,"法罗群岛","The Faroe Islands","フェロー諸島"),
	SanMarino(292,"圣马力诺","SanMarino","サンマリノ"),
	Slovenia(293,"斯洛文尼亚","Slovenia","スロベニア"),
	Liechtenstein(295,"列支敦士登","Liechtenstein","リヒテンシュタイン"),
	Canada(302,"加拿大","Canada","カナダ"),
	America(310,"美国","America","アメリカ"),
	Mexico(334,"墨西哥","Mexico","メキシコ"),
	Azerbaijan(400,"阿塞拜疆","Azerbaijan","アゼルバイジャン"),
	Kazakhstan(401,"哈萨克斯坦","Kazakhstan","カザフスタン"),
	India(404,"印度","India","インド"),
	SriLanka(413,"斯里兰卡","Sri Lanka","スリランカ"),
	Myanmar(414,"缅甸","Myanmar","ミャンマー"),
	ArabEmirates(424,"阿联酋","Arab Emirates","アラブ首長国連邦"),
	Israel(425,"以色列","Israel","イスラエル"),
	Qatar(427,"卡塔尔","Qatar","カタール"),
	Japan(440,"日本","Japan","日本"),
	Korea(450,"韩国","Korea","韓国"),
	Vietnam(452,"越南","Vietnam","ベトナム"),
	HongKong(454,"香港","Hong Kong","香港"),
	Macao(455,"澳门","Macao","マカオ"),
	Cambodia(456,"柬埔寨","Cambodia","カンボジア"),
	China(460,"中国","China","中国"),
	Taiwan(466,"台湾","Taiwan","台灣"),
	Maldives(472,"马尔代夫","Maldives","モルディブ"),
	Malaysia(502,"马来西亚","Malaysia","マレーシア"),
	Australia(505,"澳大利亚","Australia","オーストラリア"),
	Indonesia(510,"印尼","Indonesia","インドネシア"),
	Philippines(515,"菲律宾","Philippines","フィリピン"),
	Thailand(520,"泰国","Thailand","タイ"),
	Singapore(525,"新加坡","Singapore","シンガポール"),
	NewZealand(530,"新西兰","New Zealand","ニュージーランド"),
	Guam(535,"美国关岛","Guam","グアム"),
	MatanituTugalalaoViti(542,"斐济","Matanitu Tugalalao Viti","フィジー"),
	Egypt(602,"埃及","Egypt","エジプト"),
	Morocco(604,"摩洛哥","Morocco","モロッコ"),
	Mauritius(617,"毛里求斯","Mauritius","モーリシャス"),
	Ghanaian(620,"加纳","Ghanaian","ガーナ"),
	Ghanaian1(621,"加纳","Ghanaian","ガーナ"),
	Reunion(647,"留尼汪岛","Reunion","レユニオン"),
	SouthAfrica(655,"南非","South Africa","南アフリカ"),
	Brazil(724,"巴西","Brazil","ブラジル");
	
	
	private int index ;
	private String countryName ;
	private String countryNameEn ;
	private String countryNameJapanese ;
	
	private CountryInfoEnum( int index,String countryName,String countryNameEn,String countryNameJapanese){
	    this.index = index ;
	    this.countryName = countryName ;
	    this.countryNameEn = countryNameEn ;
	    this.countryNameJapanese = countryNameJapanese ;
	}

	public int getIndex() {
	    return index;
	}

	public void setIndex(int index) {
	    this.index = index;
	}

	public String getCountryName() {
	    return countryName;
	}

	public void setCountryName(String countryName) {
	    this.countryName = countryName;
	}

	public String getCountryNameEn() {
	    return countryNameEn;
	}

	public void setCountryNameEn(String countryNameEn) {
	    this.countryNameEn = countryNameEn;
	}

	public String getCountryNameJapanese() {
	    return countryNameJapanese;
	}

	public void setCountryNameJapanese(String countryNameJapanese) {
	    this.countryNameJapanese = countryNameJapanese;
	}
	  
	// 普通方法
	   /*public static String getName(String index) {
	      for (CountryInfoEnum c : CountryInfoEnum .values()) {
	        if (c.getIndex() == Integer.parseInt(index)) {
	          return c.name;
	        }
	      }
	      return null;
	  }*/
}
