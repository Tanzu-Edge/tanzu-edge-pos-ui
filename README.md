# Tanzu Point of Sale UI

![Alt text](edge-pos-app-arch.jpg?raw=true "POS app architecture")

# Using Concouse for CI/CD

The pipeline assumes the following:
1. There are 2 separate clusters doing builds and where the app is deployed
2. Concourse is deployed to the same cluster that Tanzu Build Service is deployed to
3. The user has an account/key on Tanzu Network to pull down the RabbitMQ operator and Tanzu SQL operator
4. On the deploy cluster, 2 namespaces have already been created: edge-store and edge-data-services
5. a file params.yaml is created and it's path is set to where this file is located.  see params-example.yaml for and example with comments
