/**
 *    Copyright 2023 Sven Loesekann
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package de.xxx.dbtorest.source.debezium;

import de.xxx.dbtorest.model.DbChangeDto;
import de.xxx.dbtorest.source.DbChangeSourceService;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class DebeziumRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebeziumRunner.class);
    @Value("${datasource.host}")
    private String customerDbHost;

    @Value("${datasource.database}")
    private String customerDbName;

    @Value("${datasource.port:5432}")
    private String customerDbPort;

    @Value("${datasource.username}")
    private String customerDbUsername;

    @Value("${datasource.password}")
    private String customerDbPassword;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private DebeziumEngine<ChangeEvent<String, String>> engine;

	private final DbChangeSourceService dbChangeSourceService;

	public DebeziumRunner(DbChangeSourceService dbChangeSourceService) {
		this.dbChangeSourceService = dbChangeSourceService;
	}

    @EventListener
    public void onEvent(ApplicationReadyEvent event) {
    	final Properties props = new Properties();
    	props.setProperty("name", "cdc-orderproduct");
		props.setProperty("plugin.name", "pgoutput");
    	props.setProperty("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
    	props.setProperty("database.hostname", this.customerDbHost);
    	props.setProperty("database.port", this.customerDbPort);
    	props.setProperty("database.user", this.customerDbUsername);
    	props.setProperty("database.password", this.customerDbPassword);
    	props.setProperty("database.dbname", this.customerDbName);
    	props.setProperty("topic.prefix", "stream");
    	props.setProperty("table.include.list", "public.order_product");
    	props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
    	props.setProperty("offset.storage.file.filename", "./offsets.dat");
    	props.setProperty("offset.flush.interval.ms", "60000");


		try{
			this.engine = DebeziumEngine.create(Json.class)
    	        .using(props)
    	        .notifying(myRecord -> {
					this.dbChangeSourceService.sendChange(new DbChangeDto(myRecord.key(), myRecord.value(), myRecord.destination(), myRecord.partition()));
    	            //LOGGER.info(myRecord.toString());
    	        })
    	        .build();
    		this.executor.execute(this.engine);

    	} catch (Exception e) {
    		throw new RuntimeException(e);
		}
    }
    	
    	@PreDestroy
    	public void onShutdown() {
    		try {
				//this.engine.close();
    		    executor.shutdown();
    		    while (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
    		        LOGGER.info("Waiting another 5 seconds for the embedded engine to shut down");
    		    }
    		}
    		catch ( InterruptedException e ) {
    		    Thread.currentThread().interrupt();
    		}
    	}
    
}
