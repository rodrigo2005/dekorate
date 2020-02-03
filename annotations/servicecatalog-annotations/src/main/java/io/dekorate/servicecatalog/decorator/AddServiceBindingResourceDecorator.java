/**
 * Copyright 2018 The original authors.
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
 * 
 **/

package io.dekorate.servicecatalog.decorator;

import io.dekorate.deps.kubernetes.api.model.KubernetesListBuilder;
import io.dekorate.deps.kubernetes.api.model.ObjectMeta;
import io.dekorate.deps.servicecatalog.api.model.ServiceBindingBuilder;
import io.dekorate.doc.Description;
import io.dekorate.kubernetes.decorator.ResourceProvidingDecorator;
import io.dekorate.servicecatalog.config.ServiceCatalogInstance;

@Description("Add a ServiceBinding resource(s) to the list of generated resources.")
public class AddServiceBindingResourceDecorator extends ResourceProvidingDecorator<KubernetesListBuilder> {
    
  private ServiceCatalogInstance instance;

  public AddServiceBindingResourceDecorator(ServiceCatalogInstance instance) {
    this.instance = instance;
  }

  public void visit(KubernetesListBuilder list) {
    ObjectMeta meta = getMandatoryDeploymentMetadata(list);
          list.addToItems(new ServiceBindingBuilder()
                          .withNewMetadata()
                          .withName(instance.getName())
                          .endMetadata()
                          .withNewSpec()
                          .withNewInstanceRef(instance.getName())
                          .withSecretName(instance.getBindingSecret())
                          .endSpec());
  }
}
