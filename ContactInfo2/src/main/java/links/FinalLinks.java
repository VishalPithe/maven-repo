package links;

public class FinalLinks {

	// 1. Code to keep/ eliminate domain specific social links.
	// 2. If flag true then keep social links according to domain specified
	// 3. If flag false then remove social links according to domain specified.
	public String ValidLinks(boolean flag, String domain, String links) {

		String[] allLinks = links.split(",");
		String newSocialLink = " ";
		// flag = true so keep social links according to domain specified
		for (String link : allLinks) {
			if (flag == true) {
				if (link.contains(domain)) {
					newSocialLink = newSocialLink + "," + link;
				}
			} else {
				if (!link.contains(domain)) {
					newSocialLink = newSocialLink + "," + link;
				}
			}
		}
		newSocialLink = newSocialLink.trim();
		if (newSocialLink.startsWith(","))
			newSocialLink = newSocialLink.substring(1);
		return newSocialLink;
	}
}
