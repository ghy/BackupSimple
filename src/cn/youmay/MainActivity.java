package cn.youmay;



import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity{
	private Button btnBackup;
	private Button btnRestore;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		btnBackup=(Button)findViewById(R.id.btnBackup);
		btnRestore=(Button)findViewById(R.id.btnRestore);
	}
}
