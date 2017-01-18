package main;

import java.io.IOException;

import org.jsoup.nodes.Document;

import bean.SocialLinksBean;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;
import emailLinks.GetEmailLinks;
import jsoupCode.JsoupDocument;
import links.FinalLinks;
import phoneNumber.GetPhoneNumbers;
import socialLinks.GetSocialLinks;

public class Test {

	public static void main(String[] args) throws IOException, BoilerpipeProcessingException {
		GetSocialLinks socialLinks = new GetSocialLinks();
		GetEmailLinks emailLinks = new GetEmailLinks();
		GetPhoneNumbers numbers = new GetPhoneNumbers();
		//String link = args[0];
		String link = "http://www.austinventures.com";
		Document doc = new JsoupDocument().jsoupDocument(link);
		String mainContent = KeepEverythingExtractor.INSTANCE.getText(doc.toString()).replaceAll("<[^>]*?>", "");
		SocialLinksBean socialLinksBean = socialLinks.socialLinks(doc, link);
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
		System.out.println(numbers.getPhone(mainContent));
		
		System.out.println("Domain specific links");
		System.out.println(new FinalLinks().ValidLinks(true, "austinventures", socialLinksBean.getFacebookLink()));
		System.out.println(new FinalLinks().ValidLinks(true, "austinventures", socialLinksBean.getLinkedinLink()));
		System.out.println(new FinalLinks().ValidLinks(true, "austinventures", socialLinksBean.getTwitterLink()));
		System.out.println(new FinalLinks().ValidLinks(true, "austinventures", socialLinksBean.getGoogleLink()));
		System.out.println(new FinalLinks().ValidLinks(true, "austinventures", socialLinksBean.getInstagramLink()));
		System.out.println(new FinalLinks().ValidLinks(true, "austinventures", socialLinksBean.getPintrestLink()));
	}
}
