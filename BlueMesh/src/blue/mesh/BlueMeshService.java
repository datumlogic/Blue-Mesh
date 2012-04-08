package blue.mesh;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

public class BlueMeshService {

    private BluetoothAdapter    adapter;
    private RouterObject        router;
    private ServerThread        serverThread;
    private ClientThread        clientThread;
    private static final String TAG = "BlueMesh Service";

    // BMS constructor
    public BlueMeshService() throws NullPointerException {

        // Gets bluetooth hardware from phone and makes sure that it is
        // non-null;
        adapter = BluetoothAdapter.getDefaultAdapter();

        // If bluetooth hardware does not exist...
        if (adapter == null) {
            if (Constants.DEBUG)
                Log.d(TAG, "BluetoothAdapter is null");
            throw new NullPointerException("BluetoothAdapter is null");
        } else {
            if (Constants.DEBUG)
                Log.d(TAG, "BluetoothAdapter is is non-null");
        }

        // Create a new router object
        router = new RouterObject();
        if (Constants.DEBUG)
            Log.d(TAG, "Router Object Created");
        // Try to create a new ServerThread
        try {
            serverThread = new ServerThread(adapter, router);
        } catch (NullPointerException e) {
            throw e;
        }
        if (Constants.DEBUG)
            Log.d(TAG, "Sever Thread Created");
        // Create a new clientThread
        clientThread = new ClientThread(adapter, router);
        if (Constants.DEBUG)
            Log.d(TAG, "Client Thread Created");
    }

    // TODO: Implement later if needed
    public int config() {
        return Constants.SUCCESS;
    }

    public int launch() {

        // TODO: Conditionals are untested
        if (!serverThread.isAlive()) {
            serverThread.start();
        }
        if (!clientThread.isAlive()) {
            clientThread.start();
        }
        return Constants.SUCCESS;
    }

    // function that writes message to devices
    public int write(byte[] buffer) {
        router.write(buffer, Constants.BYTE_LEVEL_USER);
        return Constants.SUCCESS;
    }

    // function to grab most recent message off of message queue
    // (message stack actually a linked list but is used like a queue)
    public byte[] pull() {

        return router.getNextMessage();
    }

    // TODO: This does not work
    public int getNumberOfDevicesOnNetwork() {
        return router.getNumberOfDevicesOnNetwork();
    }

    // Returns the Bluetooth name of the device
    public String getMyDeviceName() {
        return adapter.getName();
    }

    // Kills threads and stops all communications
    public int disconnect() {
        Log.d(TAG, "kill start");
        this.clientThread.kill();

        this.serverThread.kill();

        this.router.stop();

        Log.d(TAG, "kill success");
        return Constants.SUCCESS;
    }

}
