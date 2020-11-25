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
		String[] parts = str.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			sb.append(URLEncoder.encode(parts[i], "UTF-8"));
			if (i != parts.length - 1) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}
	
}
