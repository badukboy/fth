package ca.bcit.comp2613.a00192788.fth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class HordeUtil {
	
	// creates 100 Random Horde Characters
	public static ArrayList<Character> create100HordeCharacters() {
		ArrayList<Character> retval = new ArrayList<Character>();
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {

			Character character = new Character(Race.values()[rand.nextInt(Race
					.values().length)], rand.nextInt(85), rand.nextInt(100),
					rand.nextInt(100));
			retval.add(character);
		}
		return retval;
	}
	
	// returns an XML representation of a Characters ArrayList
	public static String getCharacterArrayListAsString (ArrayList<Character> characters) {
		String retval = null;
		ObjectMapper objectMapper = createJacksonXmlMapper();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			objectMapper.writeValue(baos, characters); // we could write this to a file directly with an overloaded writeValue ... but let's do it the hard way
			retval = baos.toString();
		
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return retval;
	}
	
	// save this XML to the same directory where HordeUtilTestDriver is
	public static void saveCharactersToXMLFile(String characterArrayListAsString) throws InvalidDirectoryException, IOException {		
		File file = getFile("characters.xml");
		if (!file.getParentFile().exists()) {			
			throw new InvalidDirectoryException(file.getParent());
		}
		FileUtils.writeStringToFile(file, characterArrayListAsString);
	}
	
	private static File getFile(String name) {
		// Done - fileStr bug fixed
		String fileStr = System.getProperty("user.dir") + File.separator + "src" + File.separator + 
				HordeUtilTestDriver.class.getPackage().getName().replace(".", "\\") + File.separator + name;
		return new File(fileStr);
	}
	
	public static ArrayList<Character> getCharactersArrayListFromXML(String xmlStr) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = createJacksonXmlMapper();
		return objectMapper.readValue(xmlStr, new TypeReference<ArrayList<Character>>(){});
	}

	private static XmlMapper createJacksonXmlMapper() {
		return new XmlMapper();
		// Done - return an XML Mapper
	}

	// Done - write the contents of xmlStr to a file (charactersReport.xml)
	public static void saveXMLToFile(String xmlStr) throws InvalidDirectoryException, IOException {
		File file = getFile("charactersReport.xml");
		if (!file.getParentFile().exists()) {			
			throw new InvalidDirectoryException(file.getParent());
		}
		// Done - FileUtils.write...
		FileUtils.writeStringToFile(file, xmlStr);
	}
}
