package ca.bcit.comp2613.a00192788.fth;

import java.sql.SQLException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
@EnableAutoConfiguration
// uses Spring boot's default in-memory DB http://www.h2database.com
// to start the embedded server:
// org.h2.tools.Server.createWebServer(null).start();
// then goto your browser:
// http://localhost:8082
// Generic H2 (Embedded)
// org.h2.Driver
// jdbc:h2:mem:testdb
// blank username and password
//
// see also: http://stackoverflow.com/questions/17803718/view-content-of-embedded-h2-database-started-by-spring
/*
 * <bean id="h2Server" class="org.h2.tools.Server" factory-method="createTcpServer" init-method="start" destroy-method="stop" depends-on="h2WebServer">
    <constructor-arg value="-tcp,-tcpAllowOthers,-tcpPort,9092"/>
</bean>
<bean id="h2WebServer" class="org.h2.tools.Server" factory-method="createWebServer" init-method="start" destroy-method="stop">
    <constructor-arg value="-web,-webAllowOthers,-webPort,8082"/>
</bean>
 */
public class H2Config {
	static {
		try {
			org.h2.tools.Server.createWebServer(null).start();
		} catch (SQLException e) {
		}
	}
	
}
