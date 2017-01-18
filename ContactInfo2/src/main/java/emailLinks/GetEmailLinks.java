package emailLinks;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import contactValues.ContactInfo;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;
import jsoupCode.JsoupDocument;
import phoneNumber.GetPhoneNumbers;

public class GetEmailLinks implements ContactInfo {

	LinkedHashSet<String> emailID;

	public static void main(String[] args) throws IOException, BoilerpipeProcessingException {

		Document doc = new JsoupDocument()
				.jsoupDocument("http://detroit.jobs/monroe-mi/digital-artist/C97CE227A5764EDA93EBBC1AB51E286D/job/");
		String mainContent = KeepEverythingExtractor.INSTANCE.getText(doc.toString()).replaceAll("<[^>]*?>", "");
		System.out.println(new GetEmailLinks().getEmailId(mainContent));
	}

	public LinkedHashSet<String> getEmailId(String textProcess) {
		textProcess = " " + textProcess + " ";
		emailID = new LinkedHashSet<String>();
		Matcher m;
		// Email Id regex.

		// m = Pattern.compile("[\\w_\\.+-]+ *(@|(\\[|\\()at(\\]|\\)))
		// +[\\w-]+\\.[\\w-\\.]{2,5} ").matcher(textProcess);
		m = Pattern.compile(nameRegex + "(@|" + openingBracketRegex + "at" + closingBracketRegex + ") *" + domainRegex
				+ "+" + dotRegex + afterDotRegex).matcher(textProcess);
		String temp1;
		while (m.find()) {
			temp1 = m.group().toLowerCase().trim().replaceAll("\\.+", ".");

			if (temp1.startsWith(".")) {
				temp1 = temp1.substring(1, temp1.length());
			}

			if (temp1.endsWith(".")) {
				temp1 = temp1.substring(0, temp1.length() - 1);
			}

			temp1 = temp1.replaceAll((openingBracketRegex + "at" + closingBracketRegex), "@");
			temp1 = temp1.replaceAll("\\sdot\\s", ".");
			temp1 = temp1.replaceAll(" +", "");
			emailID.add(temp1);
		}

		return emailID;
	}

	// Checks email id on Home page
	public LinkedHashSet<String> getEmailIdMailTo(Document doc) {
		emailID = new LinkedHashSet<String>();
		Matcher m;
		// Email Id regex.

		Elements elements = doc.select("a[href~= *mailto]");
		for (Element element : elements) {
			m = Pattern.compile("[a-zA-Z0-9_.+-]+ *@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(element.attr("href"));
			String temp1;
			while (m.find()) {
				// System.out.println("In Mailto="+element.attr("href")+" =>
				// "+m.group());
				temp1 = m.group().toLowerCase().trim().replaceAll("\\.+", ".");

				if (temp1.startsWith(".")) {
					temp1 = temp1.substring(1, temp1.length());
				}

				if (temp1.endsWith(".")) {
					temp1 = temp1.substring(0, temp1.length() - 1);
				}

				emailID.add(temp1);
			}
		}
		return emailID;
	}
}
