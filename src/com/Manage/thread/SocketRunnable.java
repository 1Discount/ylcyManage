package com.Manage.thread;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.exception.SCException;
import com.Manage.common.util.FileWrite;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.common.ApplicationContext2;
import com.Manage.entity.common.SocketMessage;
import com.Manage.service.SocketService.BusinessService;
import com.Manage.service.SocketService.SocketServer;



/**
 * 获取设备日志（本地、漫游）type=1 || 2
 * /

/** * @author  wangbo: * @date 创建时间：2015-6-11 上午9:49:02 * @version 1.0 * @parameter  * @since  * @return  */
public class SocketRunnable implements Runnable {
	/**
	 * debug日志
	 */
	private Logger logger = LogUtil.getInstance(SocketRunnable.class);

	private Socket socket;
	private SocketMessage socketMessage;
	private int filelength=0;

	public SocketRunnable(Socket args) {
		this.socket = args;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		DataInputStream dis = null;
		DataOutputStream dos = null;
		String resultsString="";
		/*if(!socket.isConnected()){
			Thread.interrupted();
		}*/
		while(socket!=null && !socket.isClosed()){
		try
		{
				Thread.sleep(50);
				// 获取输入流管道.
				dis = new DataInputStream(socket.getInputStream());

			    //logger.info("------Obtain input pipe flow------");
				//System.out.print("------获取输入管道流量------");
				convertInMsg(dis);
				if(socketMessage==null){
					continue;
				}
				if(Integer.parseInt(socketMessage.getType())<3 || Integer.parseInt(socketMessage.getType())>=30 || Integer.parseInt(socketMessage.getType())>=50){
					System.out.println("进入提取日志！。。。。。。。。。");
					BusinessService bs =new SocketServer();
					bs.setMessageHead(socketMessage);
					bs.processBusiness(bs);
				}else if(Integer.parseInt(socketMessage.getType())>2){
					if(Integer.parseInt(socketMessage.getNum())==65535){
						dis.close();
						socket.close();
						logger.info("------File upgrade success!------");
						FileWrite.printlog("-----65535-File upgrade success!------");
						CacheUtils.put("upgradesuccess",1);
						return;
					}
					logger.info("------begin To upgrade--num:"+socketMessage.getNum()+"-type:"+socketMessage.getType()+"-");
					FileWrite.printlog("---To upgrade-----num:"+socketMessage.getNum()+"-type:"+socketMessage.getType()+"-");
					String pathroot=ApplicationContext2.getRootpath()+"upload"+"/";
					int sizetemp=1024;
					String path=pathroot+"xmclient";
					if(Integer.parseInt(socketMessage.getType())==3){
						path=pathroot+"xmclient";
					}else if(Integer.parseInt(socketMessage.getType())==4){
						path=pathroot+"local_client";
					}else if(Integer.parseInt(socketMessage.getType())==5){
						path=pathroot+"CellDataUpdaterRoam.apk";
					}else if(Integer.parseInt(socketMessage.getType())==6){
						path=pathroot+"CellDataUpdater.apk";
					}else if(Integer.parseInt(socketMessage.getType())==7){
						path=pathroot+"MIP_List.ini";
					}else if(Integer.parseInt(socketMessage.getType())==18){
						path=pathroot+"Phone.apk";
						sizetemp=32768;
					}
					else if(Integer.parseInt(socketMessage.getType())==17){
						path=pathroot+"Settings.apk";
						sizetemp=32768;
					}
					java.io.File file=new File(path);
					if(file.exists()){
						logger.info("------Began to parse the file------");
						long flen=file.length();
						filelength=Integer.parseInt(flen+"");
						logger.info("------file length:"+filelength+" ------");
						//文件总片段数
						long total=1;
						//要获取的片段编号
						int num=Integer.parseInt(socketMessage.getNum());
						int type=Integer.parseInt(socketMessage.getType());
						if(flen<=sizetemp){
							total=1;
						}else if(flen%sizetemp==0){
							total=flen/sizetemp;
						}else if(flen%sizetemp!=0){
							total=flen/sizetemp+1;
						}
						FileInputStream fStream = new FileInputStream(file);
						int lastnum=0;
						byte [] sendbyte=null;
						if(num==total){
							if(flen%sizetemp==0){
								lastnum=sizetemp;
							}else{
								lastnum=Integer.parseInt(flen%sizetemp+"");
							}
							sendbyte=new byte[lastnum+11];
						}else{
							sendbyte=new byte[sizetemp+7];
						}
						logger.info("----lastnum:"+lastnum+"----");
						byte []temp =new byte[sizetemp];
						int count=0;
						while((fStream.read(temp)!=-1)){
							if(count+1==num){
								break;
							}
							count++;
						}
						if(num==total){
							byte [] temp1=new byte[lastnum];
							for(int i=0;i<lastnum;i++){
								temp1[i]=temp[i];
							}
							temp=temp1;
						}



						System.arraycopy(toByteArray(type,1),0,sendbyte, 0,1);
						System.arraycopy(toByteArray(num,2),0,sendbyte, 1,2);
						System.arraycopy(toByteArray(Integer.parseInt(total+""),2),0,sendbyte,3,2);
						System.arraycopy(toByteArray(temp.length,2),0,sendbyte, 5,2);
						System.arraycopy(temp,0,sendbyte, 7,temp.length);
						if(num==total){
							BigInteger bi = new BigInteger(1,temp);

							byte[] b=getBytes(path);
							int crcInt=getCRC32Value(b);
							logger.info("------CRC32:"+crcInt);
							System.arraycopy(toByteArray(crcInt,4),0,sendbyte,temp.length+7,4);
						}
						try
						{
							// 获得输出流
							dos = new DataOutputStream(socket.getOutputStream());
							dos.write(sendbyte);
							dos.flush();
							logger.info("------The message returned------");
							FileWrite.printlog("------The message returned------");
						}
						catch (Exception e)
						{
							logger.debug(e.getMessage());

							//e.printStackTrace();
						}
					}else{
						logger.info("------File does not exist------");
					}
				}
				 /*if(!dis.readBoolean()){
					 continue;
				 }*/




		}
		catch (Exception e)
		{
			//System.out.println(e.getMessage());
			//logger.error("处理失败", e);
			logger.info(e.getMessage());
		}

		}
	}

	/**
	 * 将外部消息转换为内部对象
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private void convertInMsg(DataInputStream in) throws SCException
	{
		try {
			//logger.info("------Began to parse the message------");
			//System.out.println("开始解析消息");
			String contentString="";
			byte[] data = new byte[1024];
			int len=0;
			while((len=in.read(data))!=-1){
					String mesString=new String(data,"utf-8");
					contentString+=mesString.toString();
					if(len<200 || "".equals(mesString.toString())){
						break;
					}
					data=null;
					data = new byte[1024];
			}
			if("".equals(contentString)){
				socketMessage=null;
				return;
			}

				FileWrite.printlog("收到消息:"+contentString);


			logger.info("MES:"+contentString);
			System.out.println("服务端消息:"+contentString);
			if(contentString!=null && !"".equals(contentString)){
				if(contentString.indexOf("{")!=0){
					FileWrite.printlog("开头不是{:");
					socketMessage.setContent(contentString);
					return;
				}

				socketMessage=new SocketMessage();
				FileWrite.printlog("开始转化为json");
				contentString =contentString.replaceAll("(\r\n|\r|\n|\n\r)","");
				JSONObject obj =JSONObject.fromObject(contentString);
				FileWrite.printlog("已转化为json");
				socketMessage.setType(obj.getString("type"));
				socketMessage.setSn(obj.getString("sn"));
				socketMessage.setNum(obj.getString("num"));
				FileWrite.printlog("前3个已组装");
				if(obj.containsKey("content")){
					/*String temp =new String(obj.getString("content").getBytes(),"utf-8");*/
					socketMessage.setContent(obj.getString("content"));
				}else{
					socketMessage.setContent(null);
				}
				FileWrite.printlog("收到消息组装后:"+socketMessage.getType()+"-"+socketMessage.getSn()+"-"+socketMessage.getNum()+"-"+socketMessage.getContent());
			}else{
				logger.info("--The message format is not standard--");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info(e.getMessage());
			if(e.getMessage().equals("Connection reset")){
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 转换结果消息反馈
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private void responseMsg(String result, DataOutputStream dos) throws Exception
	{
		if(null==result || "".equals(result)){
			System.out.println("需反馈为空");
			return;
		}
		try {
			dos.write(result.getBytes("UTF-8"));
			dos.flush();
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new Exception();
		}

		logger.info("RMES:"+result);
		System.out.println("消息返回为:"+result);
	}

	public byte[] toByteArray(int iSource, int iArrayLen) {
	    byte[] bLocalArr = new byte[iArrayLen];
	    for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
	        bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
	    }
	    return bLocalArr;
	}

	private  static int[] CRC_TABLE =
        {0x00000000, 0x77073096, 0xee0e612c, 0x990951ba, 0x076dc419, 0x706af48f, 0xe963a535,
         0x9e6495a3, 0x0edb8832, 0x79dcb8a4, 0xe0d5e91e, 0x97d2d988, 0x09b64c2b, 0x7eb17cbd,
         0xe7b82d07, 0x90bf1d91, 0x1db71064, 0x6ab020f2, 0xf3b97148, 0x84be41de, 0x1adad47d,
         0x6ddde4eb, 0xf4d4b551, 0x83d385c7, 0x136c9856, 0x646ba8c0, 0xfd62f97a, 0x8a65c9ec,
         0x14015c4f, 0x63066cd9, 0xfa0f3d63, 0x8d080df5, 0x3b6e20c8, 0x4c69105e, 0xd56041e4,
         0xa2677172, 0x3c03e4d1, 0x4b04d447, 0xd20d85fd, 0xa50ab56b, 0x35b5a8fa, 0x42b2986c,
         0xdbbbc9d6, 0xacbcf940, 0x32d86ce3, 0x45df5c75, 0xdcd60dcf, 0xabd13d59, 0x26d930ac,
         0x51de003a, 0xc8d75180, 0xbfd06116, 0x21b4f4b5, 0x56b3c423, 0xcfba9599, 0xb8bda50f,
         0x2802b89e, 0x5f058808, 0xc60cd9b2, 0xb10be924, 0x2f6f7c87, 0x58684c11, 0xc1611dab,
         0xb6662d3d, 0x76dc4190, 0x01db7106, 0x98d220bc, 0xefd5102a, 0x71b18589, 0x06b6b51f,
         0x9fbfe4a5, 0xe8b8d433, 0x7807c9a2, 0x0f00f934, 0x9609a88e, 0xe10e9818, 0x7f6a0dbb,
         0x086d3d2d, 0x91646c97, 0xe6635c01, 0x6b6b51f4, 0x1c6c6162, 0x856530d8, 0xf262004e,
         0x6c0695ed, 0x1b01a57b, 0x8208f4c1, 0xf50fc457, 0x65b0d9c6, 0x12b7e950, 0x8bbeb8ea,
         0xfcb9887c, 0x62dd1ddf, 0x15da2d49, 0x8cd37cf3, 0xfbd44c65, 0x4db26158, 0x3ab551ce,
         0xa3bc0074, 0xd4bb30e2, 0x4adfa541, 0x3dd895d7, 0xa4d1c46d, 0xd3d6f4fb, 0x4369e96a,
         0x346ed9fc, 0xad678846, 0xda60b8d0, 0x44042d73, 0x33031de5, 0xaa0a4c5f, 0xdd0d7cc9,

         0x5005713c, 0x270241aa, 0xbe0b1010, 0xc90c2086, 0x5768b525, 0x206f85b3, 0xb966d409,
         0xce61e49f, 0x5edef90e, 0x29d9c998, 0xb0d09822, 0xc7d7a8b4, 0x59b33d17, 0x2eb40d81,
         0xb7bd5c3b, 0xc0ba6cad, 0xedb88320, 0x9abfb3b6, 0x03b6e20c, 0x74b1d29a, 0xead54739,
         0x9dd277af, 0x04db2615, 0x73dc1683, 0xe3630b12, 0x94643b84, 0x0d6d6a3e, 0x7a6a5aa8,
         0xe40ecf0b, 0x9309ff9d, 0x0a00ae27, 0x7d079eb1, 0xf00f9344, 0x8708a3d2, 0x1e01f268,
         0x6906c2fe, 0xf762575d, 0x806567cb, 0x196c3671, 0x6e6b06e7, 0xfed41b76, 0x89d32be0,
         0x10da7a5a, 0x67dd4acc, 0xf9b9df6f, 0x8ebeeff9, 0x17b7be43, 0x60b08ed5, 0xd6d6a3e8,
         0xa1d1937e, 0x38d8c2c4, 0x4fdff252, 0xd1bb67f1, 0xa6bc5767, 0x3fb506dd, 0x48b2364b,
         0xd80d2bda, 0xaf0a1b4c, 0x36034af6, 0x41047a60, 0xdf60efc3, 0xa867df55, 0x316e8eef,
         0x4669be79, 0xcb61b38c, 0xbc66831a, 0x256fd2a0, 0x5268e236, 0xcc0c7795, 0xbb0b4703,
         0x220216b9, 0x5505262f, 0xc5ba3bbe, 0xb2bd0b28, 0x2bb45a92, 0x5cb36a04, 0xc2d7ffa7,
         0xb5d0cf31, 0x2cd99e8b, 0x5bdeae1d, 0x9b64c2b0, 0xec63f226, 0x756aa39c, 0x026d930a,
         0x9c0906a9, 0xeb0e363f, 0x72076785, 0x05005713, 0x95bf4a82, 0xe2b87a14, 0x7bb12bae,
         0x0cb61b38, 0x92d28e9b, 0xe5d5be0d, 0x7cdcefb7, 0x0bdbdf21, 0x86d3d2d4, 0xf1d4e242,
         0x68ddb3f8, 0x1fda836e, 0x81be16cd, 0xf6b9265b, 0x6fb077e1, 0x18b74777, 0x88085ae6,
         0xff0f6a70, 0x66063bca, 0x11010b5c, 0x8f659eff, 0xf862ae69, 0x616bffd3, 0x166ccf45,
         0xa00ae278, 0xd70dd2ee, 0x4e048354, 0x3903b3c2, 0xa7672661, 0xd06016f7, 0x4969474d,
         0x3e6e77db, 0xaed16a4a, 0xd9d65adc, 0x40df0b66, 0x37d83bf0, 0xa9bcae53, 0xdebb9ec5,
         0x47b2cf7f, 0x30b5ffe9, 0xbdbdf21c, 0xcabac28a, 0x53b39330, 0x24b4a3a6, 0xbad03605,
         0xcdd70693, 0x54de5729, 0x23d967bf, 0xb3667a2e, 0xc4614ab8, 0x5d681b02, 0x2a6f2b94,
         0xb40bbe37, 0xc30c8ea1, 0x5a05df1b, 0x2d02ef8d, };



private  int getCRC32Value(byte[] bytes) {
    int crc = 0;
    for (byte b : bytes) {
        crc = (crc >> 8 ^ CRC_TABLE[(crc ^ b) & 0xFF]);
    }
    crc = crc ^ 0xffffffff;
    return crc;
}

private static byte[] getBytes(String filePath){
    byte[] buffer = null;
    try {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return buffer;
}

}
