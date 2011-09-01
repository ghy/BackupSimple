package cn.youmay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

public class Main extends Activity {
	/** Called when the activity is first created. */
	ListView lv;
	Adapter adapter;
	ArrayList<HashMap<String, Object>> items=new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = (ListView) findViewById(R.id.lv);
		PackageManager  pm= getPackageManager();
		//�õ�PackageManager����
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		//�õ�ϵͳ ��װ�����г�����PackageInfo����
		
		for (PackageInfo pi : packs) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", pi.applicationInfo.loadIcon(pm));
			//ͼ��
			map.put("appName", pi.applicationInfo.loadLabel(pm));
			//Ӧ����
			map.put("packageName", pi.packageName);
			//����
			items.add(map);
			//ѭ����ȡ�浽HashMap,�����ӵ�ArrayList.һ��HashMap����һ��
		}

		adapter = new Adapter(this, items, R.layout.piitem, new String[] {
				"icon", "appName", "packageName" }, new int[] { R.id.icon,
				R.id.appName, R.id.packageName });
		//����:Context,ArrayList(item�ļ���),item��layout,��ArrayList��Hashmap��key������,key���Ӧ��ֵ���Ӧ�Ŀؼ�id
		lv.setAdapter(adapter);

	}
}