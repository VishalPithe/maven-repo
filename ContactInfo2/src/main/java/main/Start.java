package main;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import bean.SocialLinksBean;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;
import emailLinks.GetEmailLinks;
import jsoupCode.JsoupDocument;
import phoneNumber.GetPhoneNumbers;
import socialLinks.GetSocialLinks;

public class Start {

	public static void main(String[] args) throws IOException {

		MongoClient mongoClient = new MongoClient("192.168.1.7", 27017);

		// Now connect to your databases
		DB db = mongoClient.getDB("crawler");
		DBCollection InvestorsTesting = db.getCollection("InvestorsTesting");
		DBCollection ContactInfo = db.getCollection("ContactInfo");
		
		Document doc;
		GetSocialLinks socialLinks = new GetSocialLinks();
		SocialLinksBean socialLinksBean;
		String link, mainContent;
		GetEmailLinks emailLinks = new GetEmailLinks();
		GetPhoneNumbers phoneNumber = new GetPhoneNumbers();
		DBCursor cur = InvestorsTesting.find();
		cur.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		DBObject object;
		BasicDBObject obj = new BasicDBObject();
		while (cur.hasNext()) {
			try {
				object = cur.next();
				link = (String) object.get("link");
				System.out.println(link);
				doc = new JsoupDocument().jsoupDocument(link);
				mainContent = KeepEverythingExtractor.INSTANCE.getText(doc.toString()).replaceAll("<[^>]*?>", "");
				socialLinksBean = socialLinks.socialLinks(doc, link);
				System.out.println("Facebook Link " + socialLinksBean.getFacebookLink());
				System.out.println("Linkedin Link " + socialLinksBean.getLinkedinLink());
				System.out.println("Twitter Link " + socialLinksBean.getTwitterLink());
				System.out.println("Google Link " + socialLinksBean.getGoogleLink());
				System.out.println("Instagram Link " + socialLinksBean.getInstagramLink());
				System.out.println("Pinterest Link " + socialLinksBean.getPintrestLink());
				System.out.print("Email :- ");
				System.out.println(emailLinks.getEmailId(mainContent));
				System.out.print("Email Mail to :- ");
				System.out.println(emailLinks.getEmailIdMailTo(doc));
				System.out.print("Phone Number :- ");
				System.out.println(phoneNumber.getPhone(mainContent));

				obj.put("link", link);
				obj.put("facebookLink", socialLinksBean.getFacebookLink());
				obj.put("twitterLink", socialLinksBean.getTwitterLink());
				obj.put("LinkedinLink", socialLinksBean.getLinkedinLink());
				obj.put("googleLink", socialLinksBean.getGoogleLink());
				obj.put("instagramLink", socialLinksBean.getInstagramLink());
				obj.put("pinterestLink", socialLinksBean.getPintrestLink());
				obj.put("email", emailLinks.getEmailId(mainContent));
				obj.put("mailTo", emailLinks.getEmailIdMailTo(doc));
				obj.put("phoneNumber", phoneNumber.getPhone(mainContent));
				ContactInfo.insert(obj);
				obj.clear();
				System.out.println("--------------------------------------------");
			} catch (Exception e) {
				continue;
			}
		}
	}
}
