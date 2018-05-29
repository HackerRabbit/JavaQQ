
package common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class IOUtil {

	public static void close(InputStream is){
		if(null != is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

	public static void close(OutputStream os){
		if(null != os){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	

	public static void close(InputStream is,OutputStream os){
		close(is);
		close(os);
	}
}
