package ca.bcit.comp2613.a00192788.fth;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class CustomQueryHelper {
	private EntityManagerFactory emf;
	private PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public CustomQueryHelper(EntityManagerFactory emf) {
		this.emf = emf;
		try {
			propertiesConfiguration.load(CustomQueryHelper.class.getResourceAsStream("query.properties"));
		} catch (ConfigurationException e) {
			ForTheHordeSwingApplication.log.error("", e);
		}
		
	}

	// not able to figure out how to set the XML mapper/data binding to set 
	// xml to green automatically - so as a small consolation I have set 
	// the text area of the mysterious query result to green. 
	// (report - I want the color of my query report to be ... green) 
	//
	// OPTIONAL, run the mysteriousQuery via the browser aka (H2 Console).  See comments under the H2Config java file
	@SuppressWarnings("unchecked")
	public List<Character> mysteriousQuery() {
		List<Character> retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			// Done - fixed. mysteriousQuerySQL is pulled from a .properties file
			String mysteriousQuerySQL = propertiesConfiguration.getString("mysteriousQuery");
			ForTheHordeSwingApplication.log.info("mysteriousQuerySQL : " + mysteriousQuerySQL);
			Query query = em.createNativeQuery(
					mysteriousQuerySQL,
					Character.class);
			retval = query.getResultList();
		} catch (Exception e) {

		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		return retval;
	}

}
