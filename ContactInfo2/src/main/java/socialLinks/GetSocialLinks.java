package socialLinks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import bean.SocialLinksBean;
import contactValues.ContactInfo;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;
import emailLinks.GetEmailLinks;
import jsoupCode.JsoupDocument;
import phoneNumber.GetPhoneNumbers;

public class GetSocialLinks implements ContactInfo {

	static Pattern pattern = Pattern.compile(extracttwitterLinkRegex);
	static Matcher matcher;

	SocialLinksBean beanObject;
	HttpURLConnection connection;
	URL url;

	public SocialLinksBean socialLinks(Document htmlDoc, String link) throws IOException {
		beanObject = new SocialLinksBean();

		Elements links;
		// Matches query (Regex)
		links = htmlDoc.select("a[abs:href~=" + fbRegex + "]");
		beanObject.setFacebookLink(getLink(links, fbRegex).trim());

		links = htmlDoc.select("a[abs:href~=" + linkedinRegex + "]");
		beanObject.setLinkedinLink(getLink(links, linkedinRegex).trim());

		links = htmlDoc.select("a[abs:href~=" + twitterRegex + "]");
		beanObject.setTwitterLink(validateTwitterLink(getLink(links, twitterRegex)));

		links = htmlDoc.select("a[abs:href~=" + googleRegex + "]");
		beanObject.setGoogleLink(getLink(links, googleRegex).trim());

		links = htmlDoc.select("a[abs:href~=" + instaRegex + "]");
		beanObject.setInstagramLink(getLink(links, instaRegex).trim());

		links = htmlDoc.select("a[abs:href~=" + pinterestRegex + "]");
		beanObject.setPintrestLink(getLink(links, pinterestRegex).trim());

		return beanObject;
	}

	public String validateTwitterLink(String link) {
		Set<String> twitterLink = new HashSet<String>();
		matcher = pattern.matcher(link);

		while (matcher.find()) {
			twitterLink.add(matcher.group());
		}

		return twitterLink.toString().substring(1, twitterLink.toString().length() - 1);
	}

	// Get Social Links from Homepage according to regex provided
	public String getLink(Elements links, String regex) {
		String socialLink = " ";
		Document doc;
		try {

			for (Element link : links) {
				if (!(link.absUrl("abs:href").toLowerCase().contains("/share")
						|| link.absUrl("abs:href").toLowerCase().contains("twitter.com/twitter")
						|| link.absUrl("abs:href").toLowerCase().contains("twitter.com/intent/tweet")
						|| link.absUrl("abs:href").toLowerCase().contains("facebook.com/facebook")
						|| link.absUrl("abs:href").toLowerCase().contains("linkedin.com/linkedin")
						|| link.absUrl("abs:href").toLowerCase().contains("pinterest.com/pinterest")
						|| link.absUrl("abs:href").toLowerCase().contains("google.com/+google")
						|| link.absUrl("abs:href").toLowerCase().contains("google.com/+gmail"))) {
					String finalLink;
					try {

						finalLink = link.absUrl("abs:href");
						if (finalLink.contains("?")) {
							finalLink = finalLink.substring(0, finalLink.indexOf("?"));
						}
						// doc = new JsoupDocument().jsoupDocument(finalLink);
						url = new URL(link.absUrl("abs:href"));
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.connect();

						int statusCode = connection.getResponseCode();
						if(statusCode == 404){
							continue;
						}
					} /*
						 * catch (HttpStatusException e) { continue; }
						 */
					catch (Exception e) {
						continue;
					}
					if (!socialLink.contains(finalLink))
						socialLink = socialLink + "," + finalLink;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		socialLink = socialLink.trim();
		if (socialLink.startsWith(","))
			socialLink = socialLink.substring(1);
		return socialLink;
	}

	public static void main(String[] args) {
		System.out.println("http://www.twitter.com/jrichlive".matches(twitterRegex));
		Pattern pattern = Pattern.compile("((http|https)://)?(www[.])?twitter.com\\/[\\w-_]+");
		Matcher matcher = pattern.matcher("https://twitter.com/4512");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
}
