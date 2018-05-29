
package common.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtil {

	public static void close(Socket socket) {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void close(ServerSocket ss) {
		if (ss != null && !ss.isClosed()) {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
