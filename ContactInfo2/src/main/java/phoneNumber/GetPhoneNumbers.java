package phoneNumber;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;
import jsoupCode.JsoupDocument;

public class GetPhoneNumbers {

	LinkedHashSet<String> phoneNumber;

	public LinkedHashSet<String> getPhone(String textProcess) {
		phoneNumber = new LinkedHashSet<String>();
		// Regex for phone
		Matcher m1 = Pattern.compile("\\D*:?\\+?[ 0-9. ()-]{10,25}").matcher(textProcess);
		while (m1.find()) {

			if (phoneNumber.size() == 10) {
				break;
			}

			// Remove the starting and ending spaces only take phone number
			// others are remove like(,.;:'"?/()*+-_%$#!=))
			String num = m1.group().replaceAll("[^0-9]", "").trim();
			// constraints for phone number
			if (num.length() <= 15 && num.length() >= 10) {
				phoneNumber.add(num);
			}
		}
		return phoneNumber;
	}
	
	
	public static void main(String[] args) throws BoilerpipeProcessingException, IOException {
		Document doc = new JsoupDocument().jsoupDocument("http://dir.texas.gov/View-Search/Contracts-Detail.aspx?contractnumber=DIR-TEX-AN-NG-CTSA-005&keyword=all");
		String mainContent = KeepEverythingExtractor.INSTANCE.getText(doc.toString()).replaceAll("<[^>]*?>", "");
		LinkedHashSet<String> num = new GetPhoneNumbers().getPhone(mainContent);
		System.out.println(num);
	}
}
