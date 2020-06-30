package com.iotdbControlByJDBC.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

import org.apache.iotdb.jdbc.IoTDBSQLException;
import org.apache.iotdb.service.rpc.thrift.TSIService.AsyncProcessor.executeBatchStatement;
import org.apache.iotdb.service.rpc.thrift.TSIService.AsyncProcessor.executeStatement;

import com.iotdbControlByJDBC.dom.resultBean;

public class connectionIotdb {

	private String dblink;
	private String user;
	private String password;
	private String dbdriver;
	PreparedStatement ps = null;
	Connection ct = null;
	ResultSet rs = null;
	Statement  stmt = null;
	
	//���캯��
	public connectionIotdb(String dbip,String user,String password ){
		this.dblink = "jdbc:iotdb://"+dbip+":6667";
		this.user = user;
		this.password = password;
		this.dbdriver = "org.apache.iotdb.jdbc.IoTDBDriver";
	}
	

	
	/**
	 * �����洢��
	 * @param gpName ����
	 * @param gpIdx ���
	 * @return
	 */
	public boolean createGroup(String gpName){
		boolean result = false;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			ps = ct.prepareStatement("set storage group to root."+gpName);
			int status = ps.executeUpdate();
				if(status==0){
					result = true;
				}
				else{
					result = false;
				}
				
		} catch (Exception eCreateGPjdbc) {
			eCreateGPjdbc.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		if(result){
			System.out.println("�洢�� "+gpName+"�����ɹ��� ");
		}
		else{
			System.out.println("�洢�� "+gpName+"����ʧ�ܡ� ");
		}
		return result;
	}
	
	/**
	 * ����ʱ����Ϣ
	 * @param devName �豸��
	 * @param tsName ��������
	 * @param gpName �洢����
	 * @param datatype ��������
	 * @param ecoding ����
	 * @param isCompress �Ƿ�ѹ��
	 * @return
	 */
	public boolean createTS(String gpName,String devName,String tsName,String datatype,String ecoding,String isCompress,String ptNum){
		boolean result = false;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			ps = ct.prepareStatement("create timeseries root."+gpName+"."+devName+"."+tsName+" with datatype="+datatype+",encoding="+ecoding+" , COMPRESSOR="+isCompress+", MAX_POINT_NUMBER="+ptNum+"");
			int status = ps.executeUpdate();
			if(status==0){
				result = true;
			}
			else{
				result = false;
			}
			} catch (Exception eCreateGPjdbc) {
			eCreateGPjdbc.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		if(result){
			System.out.println("����ʱ����Ϣ root."+gpName+"."+devName+"."+tsName+"�ɹ�");
		}
		else{
			System.out.println("����ʱ����Ϣ root."+gpName+"."+devName+"."+tsName+"ʧ��");
		}
		return result;
	}

	//����ִ��DDL����
	public boolean tDDL(String sqlStr){
		boolean result = false;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			ps = ct.prepareStatement(sqlStr);
			int status = ps.executeUpdate();
			if(status==0){
				result = true;
			}
			else{
				result = false;
			}
			} catch (Exception eCreateGPjdbc) {
			eCreateGPjdbc.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		if(result){
			System.out.println("ִ��DDL��� "+sqlStr+" �ɹ���");
		}
		else{
			System.out.println("ִ��DDL��� "+sqlStr+" ʧ�ܡ�");
		}
		return result;
	}
	
	
	
	/**
	 * jdbc�����¼batch
	 * @param sgName
	 * @param devName
	 * @param senName
	 * @param value
	 */
	public void insertRecordBatch(String sgName,String devName,String senName,String value){
		try {
			Date dt= new Date();
			long timeseries = dt.getTime();
			try {
				Class.forName(dbdriver);
				ct = DriverManager.getConnection(dblink, user, password);
				stmt = ct.createStatement();
				stmt.addBatch("insert into root."+sgName+"."+devName+"(timestamp,"+senName+") values ("+timeseries+","+value+")");
//				....�����������
				stmt.executeBatch();
				stmt.clearBatch();
				stmt.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException einsertRecordBatch) {
			einsertRecordBatch.printStackTrace();
		}
		finally{
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		System.out.println("IR1 �����¼ batch �ķ�ʽ��ɡ�");
	}
	
	/**
	 * jdbc�����¼prepareState
	 * @param gpName
	 * @param devName
	 * @param time
	 * @param senName
	 * @param senValue
	 * @return
	 */
	public void insertRecordPrestat(String gpName,String devName,String senName,String senValue){
		boolean result = false;
		Date dt= new Date();
		long timeseries = dt.getTime();
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			ps = ct.prepareStatement("insert into root."+gpName+"."+devName+"(timestamp,"+senName+") values ("+timeseries+","+senValue+");");
			if(ps.executeUpdate()==0){
				result = true;
				System.out.println("IR2��ʽ�����¼�ɹ���");
			}
			else{
				result = false;
				System.out.println("IR2��ʽ�����¼ʧ�ܡ�");
			}
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
//		return result;
	}

	/**
	 * jdbc�����¼ execute (����������)
	 * @param gpName
	 * @param devName
	 * @param time
	 * @param senName
	 * @param senValue
	 * @return
	 */
	public boolean insertRecordExecute(String gpName,String devName,String time,String senName,String senValue){
		boolean reuslt = false;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			if(stmt.execute("insert into "+gpName+"."+devName+"(timestamp,"+senName+") "
					+ "values ("+time+","+senValue+");")){
				System.out.println("execute �����¼�ɹ���");
			}
			else{
				System.out.println("execute �����¼ʧ�ܡ�");
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return reuslt;
	}

	/**
	 * �鿴�洢��
	 * @return
	 */
	public ArrayList showGP(){
		ArrayList list = new ArrayList();
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			rs = stmt.executeQuery("SHOW STORAGE GROUP");
			while(rs.next()){
				resultBean rb = new resultBean();
				rb.setResult(rs.getString(1));
				list.add(rb);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
		
	}
	
	/**
	 * �鿴ʱ����Ϣ
	 * @param sgName
	 * @return
	 */
	public ArrayList showTS(String sgName){
		ArrayList list = new ArrayList();
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			if(sgName.equals("")){
				rs = stmt.executeQuery("SHOW TIMESERIES ");
			}
			else{
				rs = stmt.executeQuery("SHOW TIMESERIES root."+sgName);
			}
			
			while(rs.next()){
				resultBean rb = new resultBean();
				rb.setResult(rs.getString(1));
				list.add(rb);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * �鿴�豸��Ϣ
	 * @return
	 */
	public ArrayList showDEV(String sgName){
		ArrayList list = new ArrayList();
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			if(sgName.equals("")){
				rs = stmt.executeQuery("SHOW DEVICES ");
			}
			else{
				rs = stmt.executeQuery("SHOW DEVICES root."+sgName);
			}
				
			
			while(rs.next()){
				resultBean rb = new resultBean();
				rb.setResult(rs.getString(1));
				list.add(rb);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * ͳ��ʱ����Ϣ
	 * @param sgName
	 * @return
	 */
	public int countTS(String sgName){
		int count = 0;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			if(sgName.equals("")){
				rs = stmt.executeQuery("COUNT TIMESERIES ");
			}
			else{
				rs = stmt.executeQuery("COUNT TIMESERIES root."+sgName);
			}
			
			if(rs.next()){
				count = rs.getInt(1);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * ���ȼ�ͳ�ƽڵ���
	 * @param sgName
	 * @param level
	 * @return
	 */
	public int countNDbyLevel(String sgName,String level){
		int count = 0;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			rs = stmt.executeQuery("COUNT NODES root."+sgName+" LEVEL="+level+"");
			if(rs.next()){
				count = rs.getInt(1);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * ���ȼ�ͳ��ʱ���¼��
	 * @param sgName
	 * @param level
	 * @return
	 */
	public int countTSbyLevel(String sgName,String level){
		int count = 0;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			rs = stmt.executeQuery("COUNT TIMESERIES root."+sgName+" GROUP BY LEVEL="+level+"");
			if(rs.next()){
				count = rs.getInt(1);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * ִ��ͳһ��ѯ
	 * @param sql
	 * @return
	 */
	public ArrayList commonQuery(String sql){
		ArrayList result = new ArrayList();
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			stmt = ct.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				resultBean rb = new resultBean();
				rb.setResult(rs.getString(1));
				result.add(rb);
			}
				
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ɾ��ʱ����Ϣ
	 * @param tsName
	 * @return
	 */
	public void delTS(String tsName){
		boolean result = false;
		try {
			Class.forName(dbdriver);
			ct = DriverManager.getConnection(dblink, user, password);
			ps = ct.prepareStatement("DELETE TIMESERIES "+tsName);
			if(ps.executeUpdate()==0){
				result = true;
				System.out.println(tsName+" ��Ϣɾ���ɹ���");
			}
			else{
				result = false;
				System.out.println(tsName+" ��Ϣɾ��ʧ�ܡ�");
			}
			} catch (Exception insertRecordPrestat) {
				insertRecordPrestat.printStackTrace();
		}
		finally{
			
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception eCloseDb) {
				eCloseDb.printStackTrace();
			}
		}
//		return result;
	}
}
