package jsoupCode;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupDocument {

	public Document jsoupDocument(String link) throws IOException {

		return (Jsoup.connect(link)
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
				.referrer("http://www.google.com").header("Accept-Language", "en").timeout(180000).followRedirects(true)
				.get());
	}
}
