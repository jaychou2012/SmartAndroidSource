package com.tandong.sa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.tandong.sa.appInfo.AppInfo;
import com.tandong.sa.netWork.NetWork;
import com.tandong.sa.system.SystemInfo;

public class XMainActivity extends Activity {
	private TextView tv_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_info = (TextView) this.findViewById(R.id.tv_info);
		AppInfo ai = new AppInfo(this);
		NetWork net = new NetWork(this);
		SystemInfo info = new SystemInfo(this);

		tv_info.setText(info.getSensor());
	}
}
