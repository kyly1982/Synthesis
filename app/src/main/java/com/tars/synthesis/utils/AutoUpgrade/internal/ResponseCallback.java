package com.tars.synthesis.utils.AutoUpgrade.internal;


import com.tars.synthesis.utils.AutoUpgrade.Version;

public interface ResponseCallback {
	void onFoundLatestVersion(Version version);
	void onCurrentIsLatest();
}
