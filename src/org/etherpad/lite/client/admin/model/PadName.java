package org.etherpad.lite.client.admin.model;

public class PadName {
	private String padName;
	private String padNameShort;
	
	public PadName(String padName) {
		setPadName(padName);
	}
	
	public String getPadName() {
		return padName;
	}
	
	public void setPadName(String padName) {
		this.padName = padName;
		if (padName.contains("$")) {
			String[] padNameArr = padName.split("\\$");
			if (padNameArr.length == 2) {
				this.padNameShort = padNameArr[1];
			} else {
				System.out.println("Invalid padName");
			}
		} else {
			this.padNameShort = padName;
		}
	}
	
	public String getPadNameShort() {
		return padNameShort;
	}
	
	public void setPadNameShort(String padNameShort) {
		this.padNameShort = padNameShort;
	}
	
	public String toString() {
		return this.padNameShort;
	}
}
