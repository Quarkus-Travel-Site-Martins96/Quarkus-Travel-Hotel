cd src/main/angular/hotel-ng
npm i
ng build --prod
cd ../../../..
./mvnw clean package -Dquarkus.kubernetes.deploy=true -DskipTests
