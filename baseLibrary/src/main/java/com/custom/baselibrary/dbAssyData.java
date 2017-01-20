package com.custom.baselibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbAssyData extends SQLiteOpenHelper {
	public static final String LOG = "dbAssyData";
	public static final String DATABASE_NAME = "assyData.db";

	public Commons common;
	private static final String TBL_ASSY_LINE = "tAssyLine";
	private static final String TBL_ASSY_STATION = "tAssyStation";
	private static final String TBL_ASSY_MACHINES = "tAssyMachines";
	private static final String CREATE_TABLE_ASSYLINE = "CREATE TABLE "
			+ TBL_ASSY_LINE + " (" + "id integer primary key,"
			+ "AssyLine text)";

	private static final String CREATE_TABLE_ASSYSTATION = "CREATE TABLE "
			+ TBL_ASSY_STATION + " (" + "id integer primary key, "
			+ "AssyStation text, " + "AssyLine text)";

	private static final String CREATE_TABLE_ASSYMACHINES = "CREATE TABLE "
			+ TBL_ASSY_MACHINES + " (" + "id integer primary key, "
			+ "AssyMachine text, " + "MachineFamily text, "
			+ "AssyStation text)";

	public dbAssyData(Context context) {
		super(context, DATABASE_NAME, null, 1);
		common = new Commons(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_ASSYLINE);
		db.execSQL(CREATE_TABLE_ASSYSTATION);
		db.execSQL(CREATE_TABLE_ASSYMACHINES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TBL_ASSY_LINE);
		db.execSQL("DROP TABLE IF EXISTS " + TBL_ASSY_STATION);
		db.execSQL("DROP TABLE IF EXISTS " + TBL_ASSY_MACHINES);
		onCreate(db);
	}

	public long insertAssyLine(String pStrAssyLine) {
		long lLngLineId = 0;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues lCvRow = new ContentValues();
			lCvRow.put("AssyLine", pStrAssyLine);
			lLngLineId = db.insert(TBL_ASSY_LINE, null, lCvRow);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lLngLineId;
	}

	public long insertAssyStation(String pStrAssyLine, String pStrAssyStation) {
		long lLngStationId = 0;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues lCvRow = new ContentValues();
			lCvRow.put("AssyLine", pStrAssyLine);
			lCvRow.put("AssyStation", pStrAssyStation);
			lLngStationId = db.insert(TBL_ASSY_STATION, null, lCvRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lLngStationId;
	}

	public long insertAssyMachines(String pStrAssyMachine,
			String pStrMachineFamily, String pStrAssyStation) {
		long lLngMachineId = 0;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues lCvRow = new ContentValues();
			lCvRow.put("AssyMachine", pStrAssyMachine);
			lCvRow.put("MachineFamily", pStrMachineFamily);
			lCvRow.put("AssyStation", pStrAssyStation);
			lLngMachineId = db.insert(TBL_ASSY_MACHINES, null, lCvRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lLngMachineId;
	}

	public long SyncAssyLines() {
		long lLngInserted = 0;
		try {
			Object lJsnObj = common.GetData("GetAssyStations",
					"pIntParentStation", GlVars.gIntAssyParentStation, "Data");
			if (lJsnObj != null) {
				JSONArray lJsnAssyLines = (JSONArray) lJsnObj;
				if (lJsnAssyLines != null && lJsnAssyLines.length() > 0) {
					SQLiteDatabase db = this.getWritableDatabase();
					int lIntDeleted = db.delete(TBL_ASSY_LINE, null, null);
					if (lIntDeleted >= 0) {
						for (int i = 0; i < lJsnAssyLines.length(); i++) {
							JSONObject lJsnAssyLine = lJsnAssyLines
									.getJSONObject(i);
							ContentValues lCVRow = new ContentValues();
							lCVRow.put("AssyLine",
									lJsnAssyLine.getString("AssyStation"));
							lLngInserted = db.insert(TBL_ASSY_LINE, null,
									lCVRow);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lLngInserted;
	}

	public long SyncAssyStations() {
		long lLngInserted = 0;
		try {
			Object lJsnObj = common.GetData("GetAllAssySubStations",
					"pIntParentStation", GlVars.gIntAssyParentStation, "Data");
			if (lJsnObj != null) {
				JSONArray lJsnAssyStations = (JSONArray) lJsnObj;
				if (lJsnAssyStations != null && lJsnAssyStations.length() > 0) {
					SQLiteDatabase db = this.getWritableDatabase();
					int lIntDeleted = db.delete(TBL_ASSY_STATION, null, null);
					if (lIntDeleted >= 0) {
						for (int i = 0; i < lJsnAssyStations.length(); i++) {
							JSONObject lJsnAssyLine = lJsnAssyStations
									.getJSONObject(i);
							ContentValues lCVRow = new ContentValues();
							lCVRow.put("AssyStation",
									lJsnAssyLine.getString("AssyStation"));
							lCVRow.put("AssyLine",
									lJsnAssyLine.getString("AssyLine"));
							lLngInserted = db.insert(TBL_ASSY_STATION, null,
									lCVRow);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lLngInserted;
	}

	public long SyncAssyMachines() {
		long lLngInserted = 0;
		try {
			Object lJsnObj = common.GetData("GetAllAssyMachines",
					"pIntParentStation", GlVars.gIntAssyParentStation, "Data");
			if (lJsnObj != null) {
				JSONArray lJsnAssyStations = (JSONArray) lJsnObj;
				if (lJsnAssyStations != null && lJsnAssyStations.length() > 0) {
					SQLiteDatabase db = this.getWritableDatabase();
					int lIntDeleted = db.delete(TBL_ASSY_MACHINES, null, null);
					if (lIntDeleted >= 0) {
						for (int i = 0; i < lJsnAssyStations.length(); i++) {
							JSONObject lJsnAssyLine = lJsnAssyStations
									.getJSONObject(i);
							ContentValues lCVRow = new ContentValues();
							lCVRow.put("AssyMachine",
									lJsnAssyLine.getString("Machine"));
							lCVRow.put("AssyStation",
									lJsnAssyLine.getString("AssyStation"));
							lCVRow.put("MachineFamily",
									lJsnAssyLine.getString("MachineFamily"));
							lLngInserted = db.insert(TBL_ASSY_MACHINES, null,
									lCVRow);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lLngInserted;
	}

	public ArrayList<String> GetAssyLines(String pStrRetCol) {
		ArrayList<String> lArrLst = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String lStrQuery = "SELECT " + pStrRetCol + " from "
					+ TBL_ASSY_LINE;
			Cursor cAssyLines = db.rawQuery(lStrQuery, null);
			if (cAssyLines != null && cAssyLines.moveToFirst()) {
				do {
					lArrLst.add(cAssyLines.getString(cAssyLines
							.getColumnIndex(pStrRetCol)));
				} while (cAssyLines.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lArrLst;
	}

	public ArrayList<String> GetAssyStations(String pStrAssyLine,
			String pStrRetCol) {
		ArrayList<String> lArrLst = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String lStrQuery = "SELECT " + pStrRetCol + " from "
					+ TBL_ASSY_STATION + " WHERE AssyLine = '" + pStrAssyLine
					+ "'";
			Cursor cAssyStations = db.rawQuery(lStrQuery, null);
			if (cAssyStations != null && cAssyStations.moveToFirst()) {
				do {
					lArrLst.add(cAssyStations.getString(cAssyStations
							.getColumnIndex(pStrRetCol)));
				} while (cAssyStations.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lArrLst;
	}

	public HashMap<String, List<String>> GetAssyStationMachines(
			String pStrAssyLine, String pStrMachineFamily) {
		HashMap<String, List<String>> lHMLst = new HashMap<String, List<String>>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String lStrQuery = "SELECT AssyStation from " + TBL_ASSY_STATION
					+ " WHERE AssyLine = '" + pStrAssyLine + "'";
			Cursor cAssyStations = db.rawQuery(lStrQuery, null);
			if (cAssyStations != null && cAssyStations.moveToFirst()) {
				do {
					String lStrAssyStation = cAssyStations
							.getString(cAssyStations
									.getColumnIndex("AssyStation"));
					lStrQuery = "SELECT AssyMachine from " + TBL_ASSY_MACHINES
							+ " WHERE AssyStation = '" + lStrAssyStation
							+ "' AND " + "MachineFamily = '"
							+ pStrMachineFamily + "'";

					Cursor cAssyMachines = db.rawQuery(lStrQuery, null);
					ArrayList<String> lArrLst = new ArrayList<String>();
					if (cAssyMachines != null && cAssyMachines.moveToFirst()) {
						do {
							lArrLst.add(cAssyMachines.getString(cAssyMachines
									.getColumnIndex("AssyMachine")));
						} while (cAssyMachines.moveToNext());
					}
					lHMLst.put(lStrAssyStation, lArrLst);
				} while (cAssyStations.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lHMLst;
	}
}
