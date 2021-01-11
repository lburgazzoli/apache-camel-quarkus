/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.component.infinispan;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(InfinispanServerTestResource.class)
public class InfinispanTest {
    @Test
    public void inspect() {
        RestAssured.when()
                .get("/test/inspect")
                .then().body(
                        "hosts", is(notNullValue()),
                        "cache-manager", is("none"));
    }

    @Test
    public void putAndGet() {
        RestAssured.with()
                .body("Hello Infinispan")
                .post("/test/put").then();

        RestAssured.when()
                .get("/test/get")
                .then().body(is("Hello Infinispan"));
    }
}
