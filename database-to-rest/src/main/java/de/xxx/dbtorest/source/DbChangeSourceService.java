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
package de.xxx.dbtorest.source;

import de.xxx.dbtorest.kafka.KafkaProducer;
import de.xxx.dbtorest.model.DbChangeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DbChangeSourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbChangeSourceService.class);
    private final KafkaProducer kafkaProducer;

    public DbChangeSourceService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendChange(DbChangeDto dbChangeDto) {
        //LOGGER.info(dbChangeDto.toString());
        this.kafkaProducer.sendDbChangeMsg(dbChangeDto);
    }
}
