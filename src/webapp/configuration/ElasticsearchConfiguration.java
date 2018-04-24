package webapp.configuration;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by hungnguyen on 12/16/14.
 */
@Configuration
//@PropertySource(value = "classpath:elasticsearch.properties")
public class ElasticsearchConfiguration {
    @Resource
    private Environment environment;
    
    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;
    
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNode;
    
    @Value("${spring.data.elasticsearch.userName}")
    private String userName;
    
    @Value("${spring.data.elasticsearch.password}")
    private String password;
    
    /*@Bean
    public Client client() throws NumberFormatException, UnknownHostException {
       
    	Settings settings = Settings.builder()
    	        .put("cluster.name", "adsaleses").build();
    	
    	TransportClient client = new PreBuiltTransportClient(settings)
    	        .addTransportAddress(new TransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host")), Integer.parseInt(environment.getProperty("elasticsearch.port1"))))
    	        .addTransportAddress(new TransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host")), Integer.parseInt(environment.getProperty("elasticsearch.port2"))));

       
        
        Settings settings = Settings.builder()
        		//.put("cluster.name", "elasticsearch311lb")
        		.put("cluster.name", "IRIS5.0")
        		.put("xpack.security.user", "206426113:P@44word")
        		.build();
    	TransportClient client = new PreBuiltXPackTransportClient(settings)
    	.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host")), Integer.parseInt(environment.getProperty("elasticsearch.port"))))
    	//;
    	.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(environment.getProperty("elasticsearch.host2")), Integer.parseInt(environment.getProperty("elasticsearch.port"))));
    	return client;
    }*/
    
   /* @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws NumberFormatException, UnknownHostException {
        return new ElasticsearchTemplate(client());
    }*/
    
    @Bean
    public RestHighLevelClient restClient() throws NumberFormatException, UnknownHostException {
    	
    	final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    	credentialsProvider.setCredentials(AuthScope.ANY,
    	        new UsernamePasswordCredentials(userName, password));

    	RestClientBuilder builder = RestClient.builder(new HttpHost(clusterNode))
    	        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
    	            @Override
    	            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
    	                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    	            }
    	        });

    	RestHighLevelClient client = new RestHighLevelClient(builder);
    	return client;
    }

   

   
}
