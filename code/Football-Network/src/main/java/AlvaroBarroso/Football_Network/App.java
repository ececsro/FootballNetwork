package AlvaroBarroso.Football_Network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

@SpringBootApplication
@EnableCaching
@EnableHazelcastHttpSession
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);

    }
    @Bean
    public Config config() {
	    Config config = new Config();
	    JoinConfig joinConfig = config.getNetworkConfig().getJoin();
	    joinConfig.getMulticastConfig().setEnabled(false);
	    joinConfig.getTcpIpConfig().addMember( "localhost" ).setEnabled( true );
	    return config;
    }
    @Bean
    public CacheManager cacheManager() {
    	return new ConcurrentMapCacheManager("player");
    }
}
