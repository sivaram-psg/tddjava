package org.mail.template;

import static org.junit.Assert.assertEquals;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TemplateTest {

	Template template;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	MissingValueException missingValueException;
	NullPointerException nullPointerException;

	@Before
	public void setup() {
		template = new Template("${one}, ${two}, ${three}");
		template.set("one", "1");
		template.set("two", "2");
		template.set("three", "3");
	}

	@Test
	public void multipleVariables() {
		assertTemplateEquals();
	}
	
	@Test
	public void variableprocessedonce()
	{
		template.set("one", "${one}");
		template.set("two", "${two}");
		template.set("three", "${one}");
		assertEquals(template.evaluate(),"${one}, ${two}, ${one}");
	}

	public void assertTemplateEquals() {
		assertEquals("1, 2, 3", template.evaluate());
	}

	@Test
	public void unknownVariables() {
		template.set("unknown", "unknown");
		assertTemplateEquals();
	}

	@Test
	public void missingvalRaiseException() {
		expectedException.expect(MissingValueException.class);
		expectedException.expectMessage("no value for ${foo}");
        new Template("${foo}").evaluate();
	}

}
