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
			System.out.println("���� �����洢��� ��֧");
			cit.createGroup(args[2]);//�½��洢����
		}
		if(exeType.equals("CTS")){
			System.out.println("���� ����ʱ����Ϣ�� ��֧");
			cit.createTS(args[2], args[3], args[4], args[5],args[6], args[7]);
		}
		if(exeType.equals("IR1")){
			System.out.println("���� �����¼batch�� ��֧");
			cit.insertRecordBatch(args[2], args[3], args[4], args[5]);

		}
		if(exeType.equals("IR2")){
			System.out.println("���� �����¼��ͨ�� ��֧");
			cit.insertRecordPrestat(args[2], args[3], args[4], args[5]);
		}
		if(exeType.equals("STS")){
			System.out.println("���� �鿴ʱ����Ϣ�� ��֧");
			ArrayList tsList = new ArrayList();
			tsList = cit.showTS(args[2]);
			for(int i=0;i<tsList.size();i++){
				resultBean rb = (resultBean)tsList.get(i);
				System.out.println(i+" TS : "+rb.getResult());
			}
			System.out.println("�鿴ʱ����Ϣ ��֧ ����");
			
		}
		if(exeType.equals("ATS")){
			System.out.println("���� ͳ��ʱ����Ϣ�� ��֧");
			if(args[2]==null){
				System.out.println("��ǰIoTDB�е�����ʱ����Ϣ���� "+cit.countTS(args[2])+" ����");
			}
			else{
				System.out.println("��ǰIoTDB�д洢��"+args[2]+" ��ʱ����Ϣ���� "+cit.countTS(args[2])+" ����");
			}
			System.out.println("ͳ��ʱ����Ϣ�� ��֧ ����");
			
		}
		if(exeType.equals("NDL")){
			System.out.println("���� ���ȼ�ͳ�ƽڵ����� ��֧");
			System.out.println("��ǰIoTDB�д洢�� "+args[2]+" �У�level�ȼ�Ϊ "+args[3]+" �Ľڵ����ǣ�"+cit.countNDbyLevel(args[2], args[3]));
			System.out.println("���ȼ�ͳ�ƽڵ����� ��֧ ����");
		}
		if(exeType.equals("TSL")){
			System.out.println("���� ���ȼ�ͳ��ʱ������ ��֧");
			System.out.println("��ǰIoTDB�д洢�� "+args[2]+" �У�level�ȼ�Ϊ "+args[3]+" ��ʱ�����ǣ�"+cit.countTSbyLevel(args[2], args[3]));
			System.out.println("���ȼ�ͳ��ʱ������ ��֧ ����");
		}
		if(exeType.equals("SDV")){
			System.out.println("���� �豸�鿴�� ��֧");
			ArrayList devList = new ArrayList();
			devList = cit.showDEV(args[2]);
			if(args[2].equals("")){
				System.out.println("��ǰIoTDBϵͳ�������豸��");
			}
			else{
				System.out.println("��ǰIoTDBϵͳ�д洢�� "+args[2]+"���豸��");
			}
			for(int i=0;i<devList.size();i++){
				resultBean rb = (resultBean)devList.get(i);
				System.out.println(i+" "+rb.getResult());
			}
		}
		if(exeType.equals("CMQ")){
			System.out.println("���� ͳһ��ѯ�� ��֧");
			ArrayList queryList = new ArrayList();
			queryList = cit.commonQuery(args[2]);
			for(int i=0;i<queryList.size();i++){
				resultBean rb = (resultBean)queryList.get(i);
				System.out.println(i+" "+rb.getResult());
			}
			System.out.println("ͳһ��ѯ�� ��֧ ����");
		}
		if(exeType.equals("DTS")){
			System.out.println("���� ɾ��ʱ����Ϣ�� ��֧");
			cit.delTS(args[2]);
			System.out.println("ɾ��ʱ����Ϣ�� ��֧ ����");
		}
		
	}

}
