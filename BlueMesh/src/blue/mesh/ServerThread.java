package blue.mesh;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;



public class ServerThread {
	
	private Handler handler;
    private BluetoothAdapter adapter;
    private RouterObject routerObject;
    
	ServerThread( Handler mHandler, BluetoothAdapter mAdapter, 
	     	      RouterObject mRouterObject ) {
		handler = mHandler;
		adapter = mAdapter;
		routerObject = mRouterObject;
	}
	
	void run() {
		//Using variables in code to get rid of warnings
		handler.notify();
		adapter.enable();
		routerObject.notify();
		return;
	}
	
}
