/*
 * Copyright 2020, Microsoft Corp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nextflow.cloud.azure.config

import groovy.transform.CompileStatic

/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@CompileStatic
class AzBatchOpts {

    String accountName
    String accountKey
    String endpoint
    Boolean cleanup

    AzBatchOpts(Map config) {
        assert config!=null
        accountName = config.accountName
        accountKey = config.accountKey
        endpoint = config.endpoint
        cleanup = config.cleanup
    }

    AzPoolOpts pool() {
        new AzPoolOpts()
    }

    String toString() {
        "endpoint=$endpoint; account-name=$accountName; account-key=${accountKey?.redact()}"
    }
}
