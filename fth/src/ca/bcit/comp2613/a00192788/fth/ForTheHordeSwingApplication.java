package ca.bcit.comp2613.a00192788.fth;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.persistence.EntityManagerFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class ForTheHordeSwingApplication {
	public static Logger log = Logger.getLogger(HordeUtilTestDriver.class);
	static {
		PropertyConfigurator.configure(ForTheHordeSwingApplication.class
				.getResourceAsStream("log4j.properties"));
	}
	private JFrame frame;
	private JTextArea textArea;
	private CharacterRepository characterRepository;
	private CustomQueryHelper customQueryHelper;
	private JScrollPane scrollPane;
	private JButton runMysteriousQueryButton;
	private JButton insertXMLButton;
	private JButton saveTextAreaToFile;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForTheHordeSwingApplication window = new ForTheHordeSwingApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ForTheHordeSwingApplication() {

		initialize();
	}

	public void initialize() {
		ConfigurableApplicationContext context = null;
		context = SpringApplication.run(H2Config.class);
		characterRepository = context.getBean(CharacterRepository.class);
		EntityManagerFactory emf = (EntityManagerFactory) context
				.getBean("entityManagerFactory");
		customQueryHelper = new CustomQueryHelper(emf);

		frame = new JFrame();
		frame.setBounds(100, 100, 601, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		insertXMLButton = new JButton("Insert XML");
		insertXMLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					// Done -  convert the contents from the textarea to an
					// ArrayList<Character> persist that arraylist to the in 
					// memory DB (H2)
					ArrayList<Character> characters =  HordeUtil.getCharactersArrayListFromXML(textArea.getText());
					characterRepository.save(characters);
				} catch (Exception e) {
					log.error("", e);
				}
			}
		});
		insertXMLButton.setBounds(10, 336, 270, 23);
		frame.getContentPane().add(insertXMLButton);

		textArea = new JTextArea();
		// textArea.setBounds(0, 0, 585, 325);
		// frame.getContentPane().add(textArea);

		textArea.setLineWrap(true);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		scrollPane.setBounds(0, 0, 585, 325);
		frame.getContentPane().add(scrollPane);

		runMysteriousQueryButton = new JButton("Run Mysterious Query");
		runMysteriousQueryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Character> characters = (ArrayList<Character>) customQueryHelper
						.mysteriousQuery();
				// Done - sort the characters by Level
				// by a Comparator
				
				/* code if using Comparator     */
				//Collections.sort(characters, new LevelComparator());
				
				// Done - used the SQL in mysteriousQuery 
				// with an 'order by xxx' clause
				String xmlStr = HordeUtil
						.getCharacterArrayListAsString(characters);
				textArea.setForeground(Color.GREEN);
				textArea.setText(xmlStr);

			}
		});
		runMysteriousQueryButton.setBounds(10, 370, 270, 23);
		frame.getContentPane().add(runMysteriousQueryButton);

		saveTextAreaToFile = new JButton("Save Text Area To File");
		saveTextAreaToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// Done - after query, generate a report in XML 
				// format and output it to charactersReport.xml
				String xmlStr = textArea.getText();
				try {
					HordeUtil.saveXMLToFile(xmlStr);
				} catch (Exception e) {
					log.error("", e);
				}
			}
		});
		saveTextAreaToFile.setBounds(10, 409, 270, 23);
		frame.getContentPane().add(saveTextAreaToFile);

	}
	
/*	Comparator if not using SQL "order by"	*/
//	class LevelComparator implements Comparator<Character> {
//	    @Override
//	    public int compare(Character c1, Character c2) {
//	        return c1.getLevel() - c2.getLevel();
//	    }
//	}
}
