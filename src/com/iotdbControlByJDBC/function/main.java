package com.iotdbControlByJDBC.function;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import org.apache.iotdb.jdbc.IoTDBSQLException;

import com.iotdbControlByJDBC.dom.resultBean;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Control iotbd by JDBC .");
		connectionIotdb cit = new connectionIotdb(args[0],"root","root");
		String exeType = args[1];
		dispatchCase dc = new dispatchCase();
//		String exeType = "IR2";
		if(exeType.equals("CSG")){
			System.out.println("进入 创建存储组的 分支");
			cit.createGroup(args[2]);//新建存储组名
		}
		if(exeType.equals("CTS")){
			System.out.println("进入 创建时序信息的 分支");
			cit.createTS(args[2], args[3], args[4], args[5],args[6], args[7]);
		}
		if(exeType.equals("IR1")){
			System.out.println("进入 插入记录batch的 分支");
			cit.insertRecordBatch(args[2], args[3], args[4], args[5]);

		}
		if(exeType.equals("IR2")){
			System.out.println("进入 插入记录普通的 分支");
			cit.insertRecordPrestat(args[2], args[3], args[4], args[5]);
		}
		if(exeType.equals("STS")){
			System.out.println("进入 查看时序信息的 分支");
			ArrayList tsList = new ArrayList();
			tsList = cit.showTS(args[2]);
			for(int i=0;i<tsList.size();i++){
				resultBean rb = (resultBean)tsList.get(i);
				System.out.println(i+" TS : "+rb.getResult());
			}
			System.out.println("查看时序信息 分支 结束");
			
		}
		if(exeType.equals("ATS")){
			System.out.println("进入 统计时序信息的 分支");
			if(args[2]==null){
				System.out.println("当前IoTDB中的所有时序信息共有 "+cit.countTS(args[2])+" 条。");
			}
			else{
				System.out.println("当前IoTDB中存储组"+args[2]+" 中时序信息共有 "+cit.countTS(args[2])+" 条。");
			}
			System.out.println("统计时序信息的 分支 结束");
			
		}
		if(exeType.equals("NDL")){
			System.out.println("进入 按等级统计节点数的 分支");
			System.out.println("当前IoTDB中存储组 "+args[2]+" 中，level等级为 "+args[3]+" 的节点数是："+cit.countNDbyLevel(args[2], args[3]));
			System.out.println("按等级统计节点数的 分支 结束");
		}
		if(exeType.equals("TSL")){
			System.out.println("进入 按等级统计时序数的 分支");
			System.out.println("当前IoTDB中存储组 "+args[2]+" 中，level等级为 "+args[3]+" 的时序数是："+cit.countTSbyLevel(args[2], args[3]));
			System.out.println("按等级统计时序数的 分支 结束");
		}
		if(exeType.equals("SDV")){
			System.out.println("进入 设备查看的 分支");
			ArrayList devList = new ArrayList();
			devList = cit.showDEV(args[2]);
			if(args[2].equals("")){
				System.out.println("当前IoTDB系统中所有设备：");
			}
			else{
				System.out.println("当前IoTDB系统中存储组 "+args[2]+"的设备：");
			}
			for(int i=0;i<devList.size();i++){
				resultBean rb = (resultBean)devList.get(i);
				System.out.println(i+" "+rb.getResult());
			}
		}
		if(exeType.equals("CMQ")){
			System.out.println("进入 统一查询的 分支");
			ArrayList queryList = new ArrayList();
			queryList = cit.commonQuery(args[2]);
			for(int i=0;i<queryList.size();i++){
				resultBean rb = (resultBean)queryList.get(i);
				System.out.println(i+" "+rb.getResult());
			}
			System.out.println("统一查询的 分支 结束");
		}
		if(exeType.equals("DTS")){
			System.out.println("进入 删除时序信息的 分支");
			cit.delTS(args[2]);
			System.out.println("删除时序信息的 分支 结束");
		}
		
	}

}
