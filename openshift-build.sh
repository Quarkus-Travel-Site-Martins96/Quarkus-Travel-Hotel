cd src/main/angular/hotel-ng
npm i
ng build --prod
cd ../../../..
./mvnw clean package -Dquarkus.kubernetes.deploy=true -Dquarkus.kubernetes.deployment-target=openshift -Dquarkus.openshift.expose=true -DskipTests
