package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import domain.MemberBean;
import enums.*;
import factory.*;
import pool.DBConstant;


public class MemberDAOImpl implements MemberDAO {

	private static MemberDAO  instance = new MemberDAOImpl();
	public static MemberDAO getInstance() {return instance;}
	Statement state;
	Connection conn;
	private MemberDAOImpl() {}
	
	
	@Override
	public void insertMember(MemberBean member) {
		try {
			DatabaseFactory.createDatabase(Vendor.ORACLE, DBConstant.USERNAME, DBConstant.PASSWORD)
			.getConnection()
			.createStatement()
			.executeUpdate(String.format(MemberQuery.INSERT_MEMBER.toString(),
					member.getUserId(),
					member.getName(),
					member.getPassword(),
					member.getSsn(),
					member.getAge()
					));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<MemberBean> selectAllMember() {
		List<MemberBean> list = new ArrayList<>();
		try {
			ResultSet rs = DatabaseFactory.createDatabase(Vendor.ORACLE,DBConstant.USERNAME,DBConstant.PASSWORD)
					.getConnection()
					.createStatement()
					.executeQuery(MemberQuery.SELECT_ALL.toString());
			MemberBean member = null;
			while(rs.next()) {
				member = new MemberBean();
				member.setUserId(rs.getString("USERID"));
				member.setTeamId(rs.getString("TEAMID"));
				member.setName(rs.getString("NAME"));
				member.setAge(rs.getString("AGE"));
				member.setRoll(rs.getString("ROLL"));
				member.setPassword(rs.getString("PASSWORD"));
				member.setSsn(rs.getString("SSN"));
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<MemberBean> selectMemberByName(String name) {
		List<MemberBean> list = new ArrayList<>();
		
		try {
			ResultSet rs = DatabaseFactory.createDatabase(Vendor.ORACLE,DBConstant.USERNAME,DBConstant.PASSWORD)
					.getConnection()
					.createStatement()
					.executeQuery(String.format(MemberQuery.SELECT_SOME.toString(), name));
			MemberBean member = null;
			while(rs.next()) {
				member = new MemberBean();
				member.setUserId(rs.getString("USERID"));
				member.setTeamId(rs.getString("TEAMID"));
				member.setName(rs.getString("NAME"));
				member.setAge(rs.getString("AGE"));
				member.setRoll(rs.getString("ROLL"));
				member.setPassword(rs.getString("PASSWORD"));
				member.setSsn(rs.getString("SSN"));
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public MemberBean selectMemberById(String id) {
		MemberBean member = new MemberBean();
		try {
			ResultSet rs = DatabaseFactory.createDatabase(Vendor.ORACLE,DBConstant.USERNAME,DBConstant.PASSWORD)
					.getConnection()
					.createStatement()
					.executeQuery(String.format(MemberQuery.SELECT_ONE.toString(),id));
			while(rs.next()) {
				member.setUserId(rs.getString("USERID"));
				member.setTeamId(rs.getString("TEAMID"));
				member.setName(rs.getString("NAME"));
				member.setAge(rs.getString("AGE"));
				member.setRoll(rs.getString("ROLL"));
				member.setPassword(rs.getString("PASSWORD"));
				member.setSsn(rs.getString("SSN"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	@Override
	public int memberCount() {
		int count=0;
		try {
			ResultSet rs = DatabaseFactory.createDatabase(Vendor.ORACLE, DBConstant.USERNAME, DBConstant.PASSWORD)
			.getConnection()
			.createStatement()
			.executeQuery(MemberQuery.COUNT_MEMBER.toString());
			while(rs.next()) {
				count = rs.getInt("COUNT");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void updateMember(MemberBean member) {
		try {
			DatabaseFactory.createDatabase(Vendor.ORACLE, DBConstant.USERNAME, DBConstant.PASSWORD)
			.getConnection()
			.createStatement()
			.executeUpdate(String.format(MemberQuery.UPDATE.toString(),member.getPassword().split("/")[1],member.getPassword().split("/")[0],member.getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMember(MemberBean member) {
		try {
			System.out.println("====\n"+String.format(MemberQuery.DELETE.toString(), member.getUserId(),member.getPassword()));
			DatabaseFactory.createDatabase(Vendor.ORACLE, DBConstant.USERNAME, DBConstant.PASSWORD)
			.getConnection()
			.createStatement()
			.executeUpdate(String.format(MemberQuery.DELETE.toString(), member.getUserId(),member.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public MemberBean login(MemberBean bean) {
		MemberBean mem = null;
		try {
			System.out.println("MemberDAOImpl --- login");
			ResultSet rs = DatabaseFactory.createDatabase(
					Vendor.ORACLE, 
					DBConstant.USERNAME,
					DBConstant.PASSWORD)
				.getConnection()
				.createStatement()
				.executeQuery(String.format(MemberQuery.LOGIN.toString(),
						bean.getUserId(),bean.getPassword()));
		
			while(rs.next()){
				 mem = new MemberBean();
				 mem.setUserId(rs.getString("USERID"));
				 mem.setPassword(rs.getString("PASSWORD"));
				 mem.setName(rs.getString("NAME"));
				 mem.setRoll(rs.getString("ROLL"));
				 mem.setTeamId(rs.getString("TEAMID"));
				 mem.setSsn(rs.getString("SSN"));
			 }
		//Exception이 여러개일 경우는 Exception으로 처리, 상위 예외처리, 모든 예외를 한번에 처리
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mem;
	}

}
