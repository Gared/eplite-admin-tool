package org.etherpad.lite.client.admin.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;
import org.etherpad_lite_client.EPLiteException;

public class EPLite {

	private static EPLite epLite = null;
	private static EPLiteClient api = null;
	private static SettingsModel setModel = null;

	private String url = null;
	private String apiKey = null;

	private EPLite() {
		setModel = new SettingsModel();

		// Load connection settings (host url and api key)
		String[] config = setModel.getServerConfig();
		// Check if config could be loaded
		if (config != null) {
			// Set config
			url = config[0];
			apiKey = config[1];
			api = new EPLiteClient(this.url, this.apiKey);
		}
	}

	public static EPLite getInstance() {
		if (epLite == null) {
			epLite = new EPLite();
		}
		return epLite;
	}

	public String getUrl() {
		if (url == null) {
			return "";
		}
		return url;
	}

	public String getApiKey() {
		if (apiKey == null) {
			return "";
		}
		return apiKey;
	}

	public boolean hasServerConfig() {
		return ((url != null && !url.isEmpty()) && (apiKey != null && !apiKey.isEmpty()));
	}

	public void setServer(String url, String apiKey) {
		this.url = url;
		this.apiKey = apiKey;

		api = new EPLiteClient(this.url, this.apiKey);
		setModel.saveServerConfig(this.url, this.apiKey);
	}

	public String createGroup() {
		HashMap group = api.createGroup();
		String groupID = group.get("groupID").toString();
		return groupID;
	}

	public void deleteGroup(String groupID) {
		api.deleteGroup(groupID);
	}

	public List getAllPads() {
		HashMap padIds = api.listAllPads();
		return (List) padIds.get("padIDs");
	}

	public List getAllGroups() {
		HashMap groupIds = api.listAllGroups();
		return (List) groupIds.get("groupIDs");
	}

	public List getAllPads(String groupID) {
		HashMap padIds = api.listPads(groupID);
		return (List) padIds.get("padIDs");
	}

	public void createPad(String padId) throws EPLiteException {
		api.createPad(padId);
	}

	public void createGroupPad(String groupId, String padId) throws EPLiteException {
		api.createGroupPad(groupId, padId);
	}

	public void deletePad(String padId) {
		api.deletePad(padId);
	}

	public String getReadOnlyPadId(String padId) {
		HashMap roId = api.getReadOnlyID(padId);
		return roId.get("readOnlyID").toString();
	}

	public String getPadText(String padId) throws EPLiteException {
		HashMap pad = api.getText(padId);
		String text = pad.get("text").toString();
		return text;
	}

	public void setPadText(String padId, String padText) {
		api.setText(padId, padText);
	}

	public String getPadRevisionCount(String padId) {
		HashMap rev = api.getRevisionsCount(padId);
		return rev.get("revisions").toString();
	}

	public Boolean getGroupPadPublicStatus(String padId) {
		HashMap pub = api.getPublicStatus(padId);
		return ((Boolean) pub.get("publicStatus"));
	}

	public Date getPadLastEditedTime(String padId) {
		HashMap lastEdited = api.getLastEdited(padId);
		long timestamp = Long.valueOf(lastEdited.get("lastEdited").toString());
		return new Date(timestamp);
	}

	public boolean isGroupPadPasswordProtected(String padId) {
		HashMap passwordProtected = api.isPasswordProtected(padId);
		Boolean isPadProtected = (Boolean) passwordProtected.get("isPasswordProtected");
		return isPadProtected;
	}

	public void getActualPadUsers(String padId) {
		HashMap padUsers = api.padUsers(padId);
		padUsers.get("padUsers");
	}

	public Long getPadUserCount(String padId) {
		return api.padUsersCount(padId);
	}

	public Long[] getPadUserCountOfPads(String[] padNames) {
		Long[] userCount = new Long[padNames.length];
		for (int i = 0; i < padNames.length; i++) {
			userCount[i] = getPadUserCount(padNames[i]);
		}
		return userCount;
	}

	public List listAuthorsOfPad(String padId) {
		HashMap listAuthorsOfPad = api.listAuthorsOfPad(padId);
		return (List) listAuthorsOfPad.get("authorIDs");
	}

	public String getAuthorName(String authorId) {
		return api.getAuthorName(authorId);
	}
}
