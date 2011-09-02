package cn.youmay;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnBackup;
	private Button btnRestore;
	private List<ApplicationInfo> userApps = new ArrayList<ApplicationInfo>();
	private final String backupMainPath = "/sdcard/BackupSimple/";
	private PackageManager manager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = getPackageManager();
		
		btnBackup = (Button) findViewById(R.id.btnBackup);
		btnRestore = (Button) findViewById(R.id.btnRestore);
		
		userApps = getUserApps();

		btnBackup.setOnClickListener(backupListener);
	}
	
	private List<ApplicationInfo> getUserApps() {
		List<ApplicationInfo> result = new ArrayList<ApplicationInfo>();
		final List<ApplicationInfo> apps = manager.getInstalledApplications(0);
		int size = apps.size();

		for (int i = 0; i < size; i++) {
			ApplicationInfo app = apps.get(i);
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0
					&& (app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
				result.add(app);
			}
		}
		
		return result;
	}

	public OnClickListener backupListener = new OnClickListener() {
		public void onClick(View paramView) {
			String parentPath = backupMainPath + new Date().toLocaleString() + "/";
			parentPath = parentPath.replace(":", ".");

			File backupFolder = new File(parentPath);
			if (!backupFolder.exists())
				backupFolder.mkdirs();

			//int i = 0;

			for (ApplicationInfo app : userApps) {
				try {
					String path = parentPath
							+ app.loadLabel(manager).toString() + ".apk";
					copyFile(new File(app.sourceDir), new File(path));
				} catch (IOException e) {
					e.printStackTrace();
				}

				//i++;

				//if (i > 10)
					//break;
				// break;
			}
		}
	};

	private void copyFile(File sourceFile, File targetFile) throws IOException {

		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
}
