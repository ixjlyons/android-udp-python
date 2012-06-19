package vorsorken.androidudppython;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private EditText ipInput;
	private EditText portInput;
	private EditText messageInput;
	private Button sendButton;
	private DatagramSocket socket;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
		ipInput = (EditText) findViewById(R.id.address);
		portInput = (EditText) findViewById(R.id.port);
		messageInput = (EditText) findViewById(R.id.message);
        
		sendButton = (Button) findViewById(R.id.send);
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = messageInput.getText().toString();
				sendPacket(message);
			}
		});
	}
    
	private void sendPacket(String message) {
		byte[] messageData = message.getBytes();
 		
		try {
			InetAddress addr = InetAddress.getByName(ipInput.getText().toString());
			int port = Integer.parseInt(portInput.getText().toString());
			DatagramPacket sendPacket = new DatagramPacket(messageData, 0, messageData.length, addr, port);
			if (socket != null) {
				socket.disconnect();
				socket.close();
			}
			socket = new DatagramSocket(port);
			socket.send(sendPacket);
		} catch (UnknownHostException e) {
			Log.e("MainActivity sendPacket", "getByName failed");
		} catch (IOException e) {
			Log.e("MainActivity sendPacket", "send failed");
		}
 	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		socket.disconnect();
		socket.close();
	}
}