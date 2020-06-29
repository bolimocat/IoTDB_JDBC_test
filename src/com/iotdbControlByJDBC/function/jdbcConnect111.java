package com.iotdbControlByJDBC.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class jdbcConnect111 {
	
	ResultSet rs = null;
	
	public  Connection getConnection(String ip){
		 String driver = "org.apache.iotdb.jdbc.IoTDBDriver";
		    String url = "jdbc:iotdb://"+ip+":55560/";
		    String username = "root";
		    String password = "root";
		    Connection connection = null;
		    try {
		      Class.forName(driver);
		      connection = DriverManager.getConnection(url, username, password);
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
		    return connection;
		   
	}
	
	//����SG
	public void createSG(Connection cn,String sgname){
		try {//root.demo
			Statement statement = cn.createStatement();
			statement.execute("SET STORAGE GROUP TO "+sgname+"");
			statement.close();
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//�鿴SG
	public void showSG(Connection cn){
		Statement statement;
		try {
			statement = cn.createStatement();
			if(statement.execute("SHOW STORAGE GROUP")){
				rs = statement.getResultSet();
			}
			while(rs.next()){
				System.out.println("storage group :"+rs.getString(1));
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	//����ʱ��
	public void createTS(Connection cn,String sgName,String devName,String senName,String dataType,String encoding){
//		CREATE TIMESERIES root.demo.s0 WITH DATATYPE=INT32,ENCODING=RLE;
		
		try {
			Statement statement = cn.createStatement();
			statement.execute("create timeseries "+sgName+"."+devName+"."+senName+" with DATATYPE="+dataType+" , ENCODING="+encoding);
			System.out.println("create timeseries "+sgName+"."+devName+"."+senName+" with DATATYPE="+dataType+" , ENCODING="+encoding);
			statement.close();
			cn.close();
		} catch (SQLException eCreateTS) {
			// TODO Auto-generated catch block
			eCreateTS.printStackTrace();
		}
	}
	
	//��ʾ����ʱ��
	public void showAllTS(Connection cn){
		try {
			Statement statement = cn.createStatement();
			if(statement.execute("SHOW TIMESERIES;")){
				rs = statement.getResultSet();
				while(rs.next()){
					System.out.println("TimeSeries info : "+rs.getString(1));
				}
			}
			statement.close();
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ָ����ʾĳ��ʱ����Ϣ
	public void showSpecTS(Connection cn,String sgName,String devName,String senName){
		try {
			Statement statement = cn.createStatement();
			if(statement.execute("SHOW TIMESERIES "+sgName+"."+devName+"."+senName)){
				rs = statement.getResultSet();
				while(rs.next()){
					System.out.println("TimeSeries info : "+rs.getString(1));
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ʾ�����豸��Ϣ
	public void showAllDev(Connection cn){
		try {
			Statement statement = cn.createStatement();
			if(statement.execute("SHOW DEVICES")){
				rs = statement.getResultSet();
				while(rs.next()){
					System.out.println("DEVICES info : "+rs.getString(1));
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ͳ��ָ���豸�ϵ�ʱ����*
	public void countTSonDev(Connection cn,String sgName,String devName){
		try {
			Statement statement = cn.createStatement();
			if(statement.execute("select count(*) from "+sgName+"."+devName+"")){
				rs = statement.getResultSet();
				while(rs.next()){
					System.out.println(sgName+"."+devName+" Ts count : "+rs.getString(1));
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Count nodes at the given level
	public void countNodesGivenLevel(Connection cn){
		try {
			Statement statement = cn.createStatement();
			if(statement.execute("COUNT NODES root LEVEL=3")){
				rs = statement.getResultSet();
				while(rs.next()){
					System.out.println("Count nodes at the given level : "+rs.getString(1));
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Count timeseries group by each node at the given level
	public void countTSGroupByEachNode(Connection cn){
		try {
			Statement statement = cn.createStatement();
			if(statement.execute("COUNT TIMESERIES root GROUP BY LEVEL=3")){
				rs = statement.getResultSet();
				while(rs.next()){
					System.out.println("COUNT TIMESERIES root GROUP BY LEVEL=3 : "+rs.getString(1));
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//insert record in batch
	public void insertRecordInBatch(Connection cn,String sgName,String devName,String senName,String value){
		try {
			Date dt= new Date();
			long timeseries = dt.getTime();
			Statement statement = cn.createStatement();
//			insert into root.demo(timestamp,s0) values(1,1);
			System.out.println("����ʱ�����"+timeseries);
			System.out.println("Ҫ�������䣺insert into "+sgName+"."+devName+"(timestamp,"+senName+") values ("+timeseries+",'"+value+"')");
			statement.addBatch("insert into "+sgName+"."+devName+"(timestamp,"+senName+") values ("+timeseries+",'"+value+"')");
			statement.executeBatch();
			statement.clearBatch();
			statement.close();
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
		//Full query statement
		public void fullQuery(Connection cn,String sgName){
			try {
				Statement statement = cn.createStatement();
				if(statement.execute("select * from "+sgName+"")){
					rs = statement.getResultSet();
					while(rs.next()){
						System.out.println("Full query : "+rs.getString(1));
					}
				}
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		//Exact query statement
		public void queryExecutQuery(Connection cn,String sgName){
			try {
				Statement statement = cn.createStatement();
				rs = statement.executeQuery("select * from "+sgName+"");
				while(rs.next()){
					System.out.println("executeQuery : "+rs.getString(1));
				}
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//ɾ��ʱ��
		public void deleteTS(Connection cn,String sgName,String devName,String senName){
			try {
				Statement statement = cn.createStatement();
				statement.execute("delete timeseries "+sgName+"."+devName+"");
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//�ر�����
		public void closeCn(Connection cn){
			try {
				cn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
}
