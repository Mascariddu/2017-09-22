package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.ValidationEvent;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Race> getRaces(Season anno) {
		String sql = "SELECT * FROM races WHERE YEAR = ?";
		List<Race> list = new ArrayList<Race>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno.getYear());
			ResultSet rs = st.executeQuery();
		
			while (rs.next()) {
				list.add(new Race(rs.getInt("raceId"), null, rs.getInt("round"), rs.getInt("circuitId"), rs.getString("name"), rs.getDate("date").toLocalDate(), null, rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getPeso(int raceId, int raceId2) {
		
		String sql = "SELECT COUNT(*) as tot FROM results r,results r2 WHERE r.raceId = ? AND r2.raceId = ? AND r.driverId = r2.driverId AND  r.statusId = 1 AND r2.statusId = 1";
		int val = 0;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, raceId);
			st.setInt(2, raceId2);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				val = rs.getInt("tot");
			}
			conn.close();
			return val;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Driver> getDrivers(Race race,HashMap<Integer, Driver> idMap) {
		String sql = "SELECT DISTINCT driverId FROM laptimes l WHERE l.raceId = ?";
		List<Driver> list = new ArrayList<Driver>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, race.getRaceId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				list.add(idMap.get(rs.getInt("driverId")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Float> getGiro(int id, int raceId) {
		// TODO Auto-generated method stub
		List<Float> milli = new ArrayList<Float>();
		String sql = "SELECT l.milliseconds as milli FROM laptimes l WHERE l.raceId = ? AND l.driverId = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, raceId);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				milli.add(rs.getFloat("milli"));
			}
			conn.close();
			return milli;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void getDrivers(HashMap<Integer, Driver> idMap) {
		// TODO Auto-generated method stub
		String sql = "SELECT DISTINCT driverId,d.forename as n1,d.surname as n2 FROM drivers d";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				idMap.put(rs.getInt("driverId"), new Driver(rs.getInt("driverId"), rs.getString("n1"), rs.getString("n2")));
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getLaps(int raceId) {
		// TODO Auto-generated method stub
		String sql = "SELECT MAX(lap) as laps FROM laptimes l WHERE l.raceId = ? ";
		int laps = 0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, raceId);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				laps = rs.getInt("laps");
			}
			conn.close();
			return laps;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
