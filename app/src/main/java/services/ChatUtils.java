package services;

import java.io.File;

import android.os.Environment;

public class ChatUtils {
	
	public static final String BASE_HOST = "http://beta.spacesetter.in/";
	
	public static final String BASE_DIR = "SpaceSetter";
	public static final String IMAGE_DIR = BASE_DIR + "/image";
	public static final String IMAGE_SENT_DIR = BASE_DIR + "/image/sent";
	public static final String VIDEO_DIR = BASE_DIR + "/video";
	public static final String VIDEO_SENT_DIR = BASE_DIR + "/video/sent";
	public static final String VIDEO_THUMB_DIR = BASE_DIR + "/video/thumb";
	
	public static final String IMAGE_UPLOAD_FOLDER = BASE_HOST + "uploads/chatting/";
	
	public static final String IMAGE_EXTENSION = ".jpg";
	
	public static boolean createDirIfNotExists(String path) {
	    boolean ret = true;

	    File file = new File(Environment.getExternalStorageDirectory(), path);
	    if (!file.exists()) {
	        if (!file.mkdirs()) {
	            ret = false;
	        }
	    }
	    return ret;
	}
}
