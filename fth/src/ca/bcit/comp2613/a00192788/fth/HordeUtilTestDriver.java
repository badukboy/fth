package ca.bcit.comp2613.a00192788.fth;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class HordeUtilTestDriver {
	public static Logger log = Logger.getLogger(HordeUtilTestDriver.class);
	static {
		PropertyConfigurator.configure(HordeUtilTestDriver.class
				.getResourceAsStream("log4j.properties"));
	}

	public static void main(String[] args) throws Exception {
		ArrayList<Character> characters = HordeUtil.create100HordeCharacters();

		String characterArrayListAsString = HordeUtil
				.getCharacterArrayListAsString(characters);
		log.info("XML: " + characterArrayListAsString);
		HordeUtil.saveCharactersToXMLFile(characterArrayListAsString);
		
		ArrayList<Character> charactersCopy = HordeUtil.getCharactersArrayListFromXML(characterArrayListAsString);
		log.info("charactersCopy size: " + charactersCopy.size());
	}

}
