package com.tars.synthesis.utils.AutoUpgrade.internal;

public interface VersionDialogListener {
	void doUpdate(boolean laterOnWifi);
	void doIgnore();
}
