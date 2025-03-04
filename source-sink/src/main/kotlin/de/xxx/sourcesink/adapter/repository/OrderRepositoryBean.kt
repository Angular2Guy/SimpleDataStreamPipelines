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
package de.xxx.sourcesink.adapter.repository

import de.xxx.sourcesink.domain.entity.OrderProduct
import de.xxx.sourcesink.domain.entity.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryBean(val jpaOrderRepository: JpaOrderRepository): OrderRepository {
    override fun findAll() : MutableList<OrderProduct> {
        return this.jpaOrderRepository.findAll()
    }

    override fun save(order: OrderProduct): OrderProduct {
        return this.jpaOrderRepository.save(order)
    }

    override fun saveAll(orders: MutableList<OrderProduct>): MutableList<OrderProduct> {
        return this.jpaOrderRepository.saveAll(orders)
    }

    override fun deleteAll() {
        return this.jpaOrderRepository.deleteAll()
    }
}