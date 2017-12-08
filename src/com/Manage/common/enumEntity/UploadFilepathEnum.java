package com.Manage.common.enumEntity;

public enum UploadFilepathEnum
{
	CellDataUpdaterRoam("CellDataUpdaterRoam.apk","CellDataUpdaterRoam","漫游APK",5),
	Xmclient("xmclient","xmclient","漫游",3),	
	MIP_List("MIP_List.ini","MIP_List","MIP",7),	
	Phone("Phone.apk","Phone","Phone.apk",18),	
	CellDataUpdater("CellDataUpdater.apk","CellDataUpdater","本地APK",6),	
	Settings("Settings.apk","Settings","本地Settings",17),	
	Local_client("local_client","local_client","本地",4),	
	Wifidog("wifidog","wifidog","wifidog",218),
	Wifidog_conf("wifidog.conf","wifidog_conf","wifidog_conf",219),
	Wifidog_msg("wifidog-msg.html","wifidog-msg","wifidog-msg",220),
	I_jetty_32_SNAPSHOT("i-jetty-3.2-SNAPSHOT.apk","i-jetty-3.2-SNAPSHOT","i-jetty-3.2-SNAPSHOT",221),
	ImeiTool("ImeiTool.apk","ImeiTool","ImeiTool.apk",222),
	Gdlocationserver("gdlocationserver.apk","gdlocationserver","gdlocationserver.apk",223),
	Root("root.war","root","protal",217),
	
	CellDataUpdaterRoam_4G("CellDataUpdaterRoam_4G.apk","CellDataUpdaterRoam_4G","漫游APK_4G",110),
	Xmclient_4G("xmclient_4G","xmclient_4G","漫游_4G",111),	
	MIP_List_4G("MIP_List_4G.ini","MIP_List_4G","MIP_4G",112),	
	Phone_4G("Phone_4G.apk","Phone_4G","Phone_4G.apk",113),	
	CellDataUpdater_4G("CellDataUpdater_4G.apk","CellDataUpdater_4G","本地APK_4G",114),	
	Settings_4G("Settings_4G.apk","Settings_4G","本地Settings_4G",115),	
	Local_client_4G("local_client_4G","local_client_4G","本地4G",116),	
	Wifidog_4G("wifidog_4G","wifidog_4G","wifidog_4G",118),
	Wifidog_4G_conf("wifidog_4G.conf","wifidog_conf_4G","wifidog_4G.conf",119),
	Wifidog_msg_4G("wifidog-msg_4G.html","wifidog-msg_4G","wifidog-msg_4G",120),
	I_jetty_32_SNAPSHOT_4G("i-jetty-3.2-SNAPSHOT_4G.apk","i-jetty-3.2-SNAPSHOT_4G","i-jetty-3.2-SNAPSHOT_4G",121),
	ImeiTool_4G("ImeiTool_4G.apk","ImeiTool_4G","ImeiTool_4G.apk",122),
	Gdlocationserver_4G("gdlocationserver_4G.apk","gdlocationserver_4G","gdlocationserver_4G.apk",123),
	Root_4G("root_4G.war","root_4G","protal_4G",117);
	 	
	 	
     
	    private String name ;
	    private String index ;
	    private String subName ;
	    private int code; 
	    private UploadFilepathEnum( String name , String index ,String subName,int code){
	        this.name = name ;
	        this.index = index ;
	        this.subName = subName ;
	        this.code=code;
	    }
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    public String getIndex() {
	        return index;
	    }
	    public void setIndex(String index) {
	        this.index = index;
	    }
	    public String getSubName() {
	        return subName;
	    }
	    public void setSubName(String subName) {
	        this.subName = subName;
	    }
	    public int getCode() {
	        return code;
	    }
	    public void setCode(int code) {
	        this.code = code;
	    }
	// 普通方法 通过index获取name
	   public static String getName(String index) {
	      for (UploadFilepathEnum c : UploadFilepathEnum .values()) {
	        if (c.getIndex().equals(index)) {
	          return c.name;
	        }
	      }
	      return null;
	  }
	// 普通方法 通过index获取subName
	   public static String getSubName(String index) {
	      for (UploadFilepathEnum c : UploadFilepathEnum .values()) {
	        if (c.getIndex().equals(index)) {
	          return c.subName;
	        }
	      }
	      return null;
	  }
	// 普通方法 通过name获取index
	   public static String getIndex(String name) {
	      for (UploadFilepathEnum c : UploadFilepathEnum.values()) {
	        if (c.getName().equals(name)) {
	          return c.index;
	        }
	      }
	      return null;
	  }
	// 普通方法 通过name获取code
	   public static int getCode(String name) {
	      for (UploadFilepathEnum c : UploadFilepathEnum.values()) {
	        if (c.getName().equals(name)) {
	          return c.code;
	        }
	      }
	      return 0;
	  }
	// 普通方法 通过subName获取name
	   public static String getNameBySubname(String subName) {
	      for (UploadFilepathEnum c : UploadFilepathEnum.values()) {
	        if (c.getSubName().equals(subName)) {
	          return c.name;
	        }
	      }
	      return null;
	  }
	// 普通方法 通过code获取name
	   public static String getNameByCode(int code) {
	      for (UploadFilepathEnum c : UploadFilepathEnum.values()) {
	        if (c.getCode()==code) {
	          return c.name;
	        }
	      }
	      return null;
	  }
	   
}
