package org.mail.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {

	private Map<String,String> variables;
	private String template;
	
	public Template(String template) {
		this.variables = new HashMap<String,String>();
		this.template=template;
	}

	public void set(String name, String value) {
		this.variables.put(name, value);
		
	}

	public String evaluate() {
		replaceVariables();
		checkMissingValues();
		return template;
	}

	private void replaceVariables() {
		for(Entry<String,String> entries:variables.entrySet())
		{
			String regex = "\\$\\{"+entries.getKey()+"\\}";
			template=template.replaceAll(regex, entries.getValue());
		}
	}

	private void checkMissingValues() {
		Matcher m = Pattern.compile("\\$\\{.+\\}").matcher(template);
		if(m.find())
		{
			throw new MissingValueException("no value for "+m.group());
		}
		
	}

}
