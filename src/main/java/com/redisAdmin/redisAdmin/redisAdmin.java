// bitcoinjake09 09/19/2021 - basic redis Admin
package com.redisadmin.redisadmin;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.net.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisAdmin extends Applet implements ActionListener {
	TextArea textOut;
	Button redisConnectBTN, redisListKeysBTN, eraseKeyBTN, setKeyBTN;
	String welcomeString, redisStat, RedisUrl;
	Integer RedisPort;
	Label redisURLlabel, redisPORTlabel,redisStatLabel , rdbKeyLabel, rdbDataLabel;
	TextField redisURL, redisPORT, rdbKey, rdbInput;
	boolean redisConnected;
	List<String> arrOfKeys = new ArrayList<String>();
	List<String> arrOfGet = new ArrayList<String>();
	public Jedis rdb;

    public static void main(String[] args) {
	System.out.println("[redisAdmin] main class");
    }
    public void init() {
    	this.setLayout(null);
			redisStat = "NaN";
        setSize(800, 640);
    	Dimension appletSize = this.getSize();
    	int fh = appletSize.height;
    	int fw = appletSize.width;
        //resize(800, 640);
      	//repaint();
    	System.out.println("[redisAdmin] Starting redisAdmin");
    	textOut = new TextArea("", 5, 30, TextArea.SCROLLBARS_NONE);
	add(textOut);
	textOut.setBounds(5, 20, fw - 200, fh - 70);
	welcomeString="This is a free open source redis database explorer and manipulator. \n" + "Use at your own risk.\n More info at https://xxx.xxx \n"
	+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
	textOut.setText(welcomeString);

	rdbKeyLabel=new Label("Redis URL:");
	rdbKeyLabel.setBounds(fw-190, 15 , 180, 25);
	add(rdbKeyLabel);

	redisURL=new TextField();
	redisURL.setText("localhost");
	redisURL.setBounds(fw-190, 40, 180, 25);
	add(redisURL);

	redisPORTlabel=new Label("Redis PORT:");
	redisPORTlabel.setBounds(fw-190, 65, 180, 25);
	add(redisPORTlabel);

	redisPORT=new TextField(4);
	redisPORT.setText("6379");
	redisPORT.setBounds(fw-190, 90, 180, 25);
	add(redisPORT);

	redisStatLabel=new Label("Redis Stat: " + redisStat);
	redisStatLabel.setBounds(fw-190, 115, 180, 25);
	add(redisStatLabel);

	redisConnectBTN=new Button("Connect");
	redisConnectBTN.setBounds(fw-190, 140, 180, 25);
	add(redisConnectBTN);
	redisConnectBTN.addActionListener(this);

	redisListKeysBTN=new Button("List DB");
	redisListKeysBTN.setBounds(fw-190, 165, 180, 25);
	add(redisListKeysBTN);
	redisListKeysBTN.addActionListener(this);

	rdbKeyLabel=new Label("Key:");
	rdbKeyLabel.setBounds(5, fh-40 , 39, 20);
	add(rdbKeyLabel);

	rdbKey=new TextField();
	rdbKey.setText("");
	rdbKey.setBounds(41, fh-40, fw-240, 20);
	add(rdbKey);

	rdbDataLabel=new Label("Data:");
	rdbDataLabel.setBounds(5, fh-20 , 39, 20);
	add(rdbDataLabel);

	rdbInput=new TextField();
	rdbInput.setText("");
	rdbInput.setBounds(41, fh-20, fw-240, 20);
	add(rdbInput);

	eraseKeyBTN=new Button("Erase");
	eraseKeyBTN.setBounds(fw-200, fh-40, 90, 22);
	add(eraseKeyBTN);
	eraseKeyBTN.addActionListener(this);

	setKeyBTN=new Button("Set");
	setKeyBTN.setBounds(fw-200, fh-20, 90, 22);
	add(setKeyBTN);
	setKeyBTN.addActionListener(this);

	repaint();
    }
    public void actionPerformed(ActionEvent e)
                    {
                      int start,end;
                      if(e.getSource()==redisConnectBTN)
                         {
													 RedisUrl = redisURL.getText();
												 	 RedisPort = Integer.parseInt(redisPORT.getText());
													 rdb = new Jedis(RedisUrl, RedisPort);
													 String connection = redisCheck();
													 if (connection == "OK") {
													 	redisConnected = true;
												 	 }
													 redisStatLabel.setText("Redis Stat: " + connection);
                           //textOut.insert("",12);
                        }
                           if(e.getSource()==redisListKeysBTN)
                             {
															 if (redisConnected) {
																 arrOfKeys = new ArrayList<String>();
																 arrOfGet = new ArrayList<String>();
																 Set<String> fKeys = rdb.keys("*");
																 java.util.Iterator<String> it = fKeys.iterator();
																 int x=0;
																 textOut.setText(welcomeString);
																 while(it.hasNext()) {
																	 String s = it.next();
																	 if (x==0){rdbKey.setText(s);rdbInput.setText(rdb.get(s));x++;}
																	 	 arrOfKeys.add(s);
																		 arrOfGet.add(rdb.get(s));
																		 textOut.append("Key: " + s + "\n");
																		 textOut.append("Data: " + rdb.get(s) + "\n\n");
															 	}
															}
                             }
														 if(e.getSource()==setKeyBTN){
															 if (redisConnected) {
																 rdb.set(rdbKey.getText(), rdbInput.getText());
															 }
														 }
														 if(e.getSource()==eraseKeyBTN){
															 if (redisConnected) {
																 rdb.del(rdbKey.getText(), rdbInput.getText());
															 }
														 }
                     }
  public void onEnable() {
	System.out.println("[redisAdmin] Starting redisAdmin");
	repaint();
  }
  public void paint(Graphics g){
	g.drawString("welcome to Redis Admin by @BitcoinJake09",5,15);
  }

	public String redisCheck() {
  try {
							if (rdb.ping().equals("PONG")){
					     return "OK";
					    }

 } catch (Exception e) {e.printStackTrace();}
   return "Failed";
}
} // EOF
