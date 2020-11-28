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
