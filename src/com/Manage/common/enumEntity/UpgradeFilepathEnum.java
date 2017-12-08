package com.Manage.common.enumEntity;

public enum UpgradeFilepathEnum
{
	CellDataUpdaterRoam("CellDataUpdaterRoam.apk",5),
	Xmclient("xmclient",3),	
	MIP_List("MIP_List.ini",7),	
	Phone("Phone.apk",18),	
	CellDataUpdater("CellDataUpdater.apk",6),	
	Settings("Settings.apk",17),	
	Local_client("local_client",4),	
	Wifidog("wifidog",218),
	Root("root.war",217),
	Wifidog_conf("wifidog.conf",219),
	Wifidog_msg("wifidog-msg.html",220),
	I_jetty_32_SNAPSHOT("i-jetty-3.2-SNAPSHOT.apk",221),
	ImeiTool("ImeiTool.apk",222),
	Gdlocationserver("gdlocationserver.apk",223),
	
	
	CellDataUpdaterRoam_4G("CellDataUpdaterRoam_4G.apk",110),
	Xmclient_4G("xmclient_4G",111),	
	MIP_List_4G("MIP_List_4G.ini",112),	
	Phone_4G("Phone_4G.apk",113),	
	CellDataUpdater_4G("CellDataUpdater_4G.apk",114),	
	Settings_4G("Settings_4G.apk",115),	
	Local_client_4G("local_client_4G",116),	
	Root_4G("root_4G.war",117),
	Wifidog_4G("wifidog_4G",118),
	Wifidog_4G_conf("wifidog_4G.conf",119),
	Wifidog_msg_4G("wifidog-msg_4G.html",120),
	I_jetty_32_SNAPSHOT_4G("i-jetty-3.2-SNAPSHOT_4G.apk",121),
	ImeiTool_4G("ImeiTool_4G.apk",122),
	Gdlocationserver_4G("gdlocationserver_4G.apk",123);
	 	
	 	
     
	    private String name ;
	    private int index ;
	     
	    private UpgradeFilepathEnum( String name , int index ){
	        this.name = name ;
	        this.index = index ;
	    }
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    public int getIndex() {
	        return index;
	    }
	    public void setIndex(int index) {
	        this.index = index;
	    }
	    
	 // 普通方法
	   public static String getName(String index) {
	      for (UpgradeFilepathEnum c : UpgradeFilepathEnum .values()) {
	        if (c.getIndex() == Integer.parseInt(index)) {
	          return c.name;
	        }
	      }
	      return null;
	  }
}
