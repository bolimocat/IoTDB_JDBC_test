package com.iotdbControlByJDBC.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.iotdbControlByJDBC.dom.caseLineBean;

public class dispatchCase {

	/**
	 * ���м����ļ��ֵļ�¼
	 * @param caseFile
	 * @return
	 */
	public ArrayList loadCaseLine(String caseFile){
		ArrayList lineList = new ArrayList();
		File f = new File(caseFile);  
        BufferedReader reader = null;  
        try {  
            reader = new BufferedReader(new FileReader(f));  
            String tempString = null;  
            int line = 1;  
            while ((tempString = reader.readLine()) != null) {  
            	caseLineBean clb = new caseLineBean();
            	clb.setLine(tempString);
            	lineList.add(clb);
            	
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
		return lineList;
	}


	public void testJDBC(String exeType,ArrayList caseLine){
		
		
		connectionIotdb cit = new connectionIotdb("192.168.130.5","root","root");
		
		if(exeType.equals("CSG")){
			for(int i=0;i<caseLine.size();i++){
				caseLineBean clb = (caseLineBean)caseLine.get(i);
				if(cit.createGroup(clb.getLine())){}
			}
			System.out.println("�����洢��� ��֧ ����");
		}
		if(exeType.equals("CTS")){
			String devName = "";
			String tsName = ""; 
			String gpName = "";
			String datatype = "";
			String ecoding = "";
			String isCompress = "";
			for(int i=0;i<caseLine.size();i++){
				caseLineBean clb = (caseLineBean)caseLine.get(i);
				String[] lineStr = clb.getLine().split(",");
				System.out.println(lineStr[0]+"_"+lineStr[1]+"_"+lineStr[2]+"_"+lineStr[3]+"_"+lineStr[4]+"_"+lineStr[5]);
				devName=lineStr[1];
				tsName=lineStr[2];
				gpName=lineStr[0];
				datatype=lineStr[3];
				ecoding=lineStr[4];
				isCompress=lineStr[5];
				System.out.println("-- "+lineStr[1]);
				if(cit.createTS( gpName, devName, tsName,datatype, ecoding, isCompress)){
//				if(cit.createTS("ln4","dev1","status","INT64","PLAIN","SNAPPY")){
				}
			}
			System.out.println("����ʱ����Ϣ�� ��֧ ����");
		}
		if(exeType.equals("IR1")){
			for(int i=0;i<caseLine.size();i++){
				caseLineBean clb = (caseLineBean)caseLine.get(i);
				String[] caseStr = clb.getLine().split(",");
				cit.insertRecordBatch(caseStr[0], caseStr[1], caseStr[2], caseStr[3]);
			}
			System.out.println("�����¼batch�� ��֧ ����");
		}
		if(exeType.equals("IR2")){
				String sgName = "";
				String dev = "";
				String sensor = "";
				String sevalue = "";
			for(int i=0;i<caseLine.size();i++){
				caseLineBean clb = (caseLineBean)caseLine.get(i);
				String[] caseStr = clb.getLine().split(",");
				
				sgName = caseStr[0];
				dev = caseStr[1];
				sensor = caseStr[2];
				sevalue = caseStr[3];
				System.out.println(sgName+" - "+dev+" - "+sensor+" - "+sevalue);
//				if(cit.insertRecordPrestat(caseStr[0], caseStr[1], caseStr[2], caseStr[3])){}
				if(cit.insertRecordPrestat(sgName,dev,sensor,sevalue)){}
			}
			System.out.println("�����¼��ͨ�� ��֧ ����");
		}
		if(exeType.equals("STS")){
			System.out.println("���� �鿴ʱ����Ϣ�� ��֧");
		}
		if(exeType.equals("ATS")){
			System.out.println("���� ͳ��ʱ����Ϣ�� ��֧");
		}
		if(exeType.equals("NDL")){
			System.out.println("���� ���ȼ�ͳ�ƽڵ����� ��֧");
		}
		if(exeType.equals("TSL")){
			System.out.println("���� ���ȼ�ͳ��ʱ������ ��֧");
		}
		if(exeType.equals("CMQ")){
			System.out.println("���� ͳһ��ѯ�� ��֧");
		}
		if(exeType.equals("DTS")){
			System.out.println("���� ɾ��ʱ����Ϣ�� ��֧");
		}
	}
}
