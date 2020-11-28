package xyz.bzennn.wavyarch.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.springframework.stereotype.Component;

/**
 * Utility class for strings and urls
 *
 * @author bzennn
 * @version 1.0
 */
@Component
public class StringUtils {

	public boolean isAsciiString(String str) {
		return Charset.forName("US-ASCII").newEncoder().canEncode(str);
	}
	
	public String encodeToUtf8(String str) throws UnsupportedEncodingException {
//		String[] partsSlash = str.split("/");
//		
//		StringBuilder mainBuilder = new StringBuilder();
//		for (int i = 0; i < partsSlash.length; i++) {
//			String[] parts = partsSlash[i].split(" ");
//			StringBuilder sb = new StringBuilder();
//			for (int j = 0; j < parts.length; j++) {
//				sb.append(URLEncoder.encode(parts[j], "UTF-8"));
//				if (j != parts.length - 1) {
//					sb.append(" ");
//				}
//			}
//			mainBuilder.append(sb);
//			if (i != partsSlash.length - 1) {
//				mainBuilder.append("/");
//			}
//		}
		
		StringBuilder mainBuilder = new StringBuilder();
		for (String ch : str.split("")) {
			if (isAsciiString(ch)) {
				mainBuilder.append(ch);
			} else {
				mainBuilder.append(URLEncoder.encode(ch, "UTF-8"));
			}
		}
		
		return mainBuilder.toString();
	}
	
}
