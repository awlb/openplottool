package dev.awlb.opt.utils;

import java.io.File;

public class FileUtils {
	public static String getExtension(File file) {
		// find and return file extension
		String fileName = file.getName();
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			String extension = fileName.substring(lastDot + 1);
			return extension;
		} else {
			return "";
		}
	}
}
