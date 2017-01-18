package contactValues;

public interface ContactInfo {
	final static String fbRegex = "((http|https)://)?(www[.])?(in.)?facebook.com/.+";
	final static String linkedinRegex = "((http|https)://)?(www[.])?(in.)?linkedin.com/.+";
	final static String twitterRegex = "((http|https)://)?(www[.])?(in.)?twitter.com/.+";
	final static String extracttwitterLinkRegex = "((http|https)://)?(www[.])?twitter.com\\/[\\w-_]+";
	final static String googleRegex = "((http|https)://)?(www[.])?plus.google.com/.+";
	final static String instaRegex = "((http|https)://)?(www[.])?(in.)?instagram.com/.+";
	final static String pinterestRegex = "((http|https)://)?(www[.])?(in.)?pinterest.com/.+";
	
	final static String openingBracketRegex = "(\\[|\\(|\\{|\\<)";
	final static String closingBracketRegex = "(\\]|\\)|\\}|\\>)";
	final static String domainRegex = "[A-Za-z0-9\\-]";
	//final static String domainRegex = "\\w*\\D{1,}\\w*";
	final static String dotRegex = "(\\.| dot )";
	final static String afterDotRegex = "[a-zA-Z\\-\\.]{2,5} ?";
	final static String nameRegex = "[A-Za-z0-9\\_\\.\\-]+ {0,2}";
}
