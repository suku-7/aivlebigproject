# 

## Model
www.msaez.io/#/189596125/storming/8cd5abadaf3ef9e1441a8704e8b2c6fc

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- funeralcontext
- funeralpython


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- funeralcontext
```
 http :8088/customers customerId="customerId"customerLoginId="customerLoginId"customerLoginPassword="customerLoginPassword"customerRole="customerRole"customerName="customerName"customerRrn="customerRrn"customerAge="customerAge"customerGender="customerGender"customerAddress="customerAddress"customerEmail="customerEmail"customerPhone="customerPhone"customerJob="customerJob"customerHasChildren="customerHasChildren"customerIsMarried="customerIsMarried"customerDiseaseList="customerDiseaseList"
 http :8088/funeralInfos funeralInfoId="funeralInfoId"customerId="customerId"customerName="customerName"customerRrn="customerRrn"customerPhone="customerPhone"deceasedName="deceasedName"deceasedAge="deceasedAge"deceasedDate="deceasedDate"deceasedBirthOfDate="deceasedBirthOfDate"deceasedGender="deceasedGender"deceasedNameHanja="deceasedNameHanja"deceasedRrn="deceasedRrn"deceasedReligion="deceasedReligion"deceasedRelationToHouseholdHead="deceasedRelationToHouseholdHead"reportRegistrationDate="reportRegistrationDate"reporterName="reporterName"reporterRrn="reporterRrn"reporterRelationToDeceased="reporterRelationToDeceased"reporterAddress="reporterAddress"reporterPhone="reporterPhone"reporterEmail="reporterEmail"submitterName="submitterName"submitterRrn="submitterRrn"funeralCompanyName="funeralCompanyName"directorName="directorName"directorPhone="directorPhone"funeralHomeName="funeralHomeName"mortuaryInfo="mortuaryInfo"funeralHomeAddress="funeralHomeAddress"funeralDuration="funeralDuration"processionDateTime="processionDateTime"burialSiteInfo="burialSiteInfo"chiefMourners="chiefMourners"templateKeyword="templateKeyword"
 http :8088/obituaries obituaryId="obituaryId"funeralInfoId="funeralInfoId"obituaryTemplateId="obituaryTemplateId"obituaryFilePath="obituaryFilePath"obituaryFileUrl="obituaryFileUrl"obituaryStatus="obituaryStatus"obituaryCreatedAt="obituaryCreatedAt"customerId="customerId"customerName="customerName"customerRrn="customerRrn"customerPhone="customerPhone"deceasedName="deceasedName"deceasedAge="deceasedAge"deceasedDate="deceasedDate"deceasedBirthOfDate="deceasedBirthOfDate"deceasedGender="deceasedGender"deceasedNameHanja="deceasedNameHanja"deceasedRrn="deceasedRrn"deceasedReligion="deceasedReligion"deceasedRelationToHouseholdHead="deceasedRelationToHouseholdHead"reportRegistrationDate="reportRegistrationDate"reporterName="reporterName"reporterRrn="reporterRrn"reporterRelationToDeceased="reporterRelationToDeceased"reporterAddress="reporterAddress"reporterPhone="reporterPhone"reporterEmail="reporterEmail"submitterName="submitterName"submitterRrn="submitterRrn"funeralCompanyName="funeralCompanyName"directorName="directorName"directorPhone="directorPhone"funeralHomeName="funeralHomeName"mortuaryInfo="mortuaryInfo"funeralHomeAddress="funeralHomeAddress"funeralDuration="funeralDuration"processionDateTime="processionDateTime"burialSiteInfo="burialSiteInfo"chiefMourners="chiefMourners"templateKeyword="templateKeyword"
 http :8088/deathReports deathReportId="deathReportId"funeralInfoId="funeralInfoId"deathReportTemplateId="deathReportTemplateId"deathReportFilePath="deathReportFilePath"deathReportFileUrl="deathReportFileUrl"deathReportStatus="deathReportStatus"deathReportCreatedAt="deathReportCreatedAt"customerId="customerId"customerName="customerName"customerRrn="customerRrn"customerPhone="customerPhone"deceasedName="deceasedName"deceasedAge="deceasedAge"deceasedDate="deceasedDate"deceasedBirthOfDate="deceasedBirthOfDate"deceasedGender="deceasedGender"deceasedNameHanja="deceasedNameHanja"deceasedRrn="deceasedRrn"deceasedReligion="deceasedReligion"deceasedRelationToHouseholdHead="deceasedRelationToHouseholdHead"reportRegistrationDate="reportRegistrationDate"reporterName="reporterName"reporterRrn="reporterRrn"reporterRelationToDeceased="reporterRelationToDeceased"reporterAddress="reporterAddress"reporterPhone="reporterPhone"reporterEmail="reporterEmail"submitterName="submitterName"submitterRrn="submitterRrn"funeralCompanyName="funeralCompanyName"directorName="directorName"directorPhone="directorPhone"funeralHomeName="funeralHomeName"mortuaryInfo="mortuaryInfo"funeralHomeAddress="funeralHomeAddress"funeralDuration="funeralDuration"processionDateTime="processionDateTime"burialSiteInfo="burialSiteInfo"chiefMourners="chiefMourners"templateKeyword="templateKeyword"
 http :8088/schedules scheduleId="scheduleId"funeralInfoId="funeralInfoId"scheduleTemplateId="scheduleTemplateId"scheduleFilePath="scheduleFilePath"scheduleFileUrl="scheduleFileUrl"scheduleStatus="scheduleStatus"scheduleCreatedAt="scheduleCreatedAt"customerId="customerId"customerName="customerName"customerRrn="customerRrn"customerPhone="customerPhone"deceasedName="deceasedName"deceasedAge="deceasedAge"deceasedDate="deceasedDate"deceasedBirthOfDate="deceasedBirthOfDate"deceasedGender="deceasedGender"deceasedNameHanja="deceasedNameHanja"deceasedRrn="deceasedRrn"deceasedReligion="deceasedReligion"deceasedRelationToHouseholdHead="deceasedRelationToHouseholdHead"reportRegistrationDate="reportRegistrationDate"reporterName="reporterName"reporterRrn="reporterRrn"reporterRelationToDeceased="reporterRelationToDeceased"reporterAddress="reporterAddress"reporterPhone="reporterPhone"reporterEmail="reporterEmail"submitterName="submitterName"submitterRrn="submitterRrn"funeralCompanyName="funeralCompanyName"directorName="directorName"directorPhone="directorPhone"funeralHomeName="funeralHomeName"mortuaryInfo="mortuaryInfo"funeralHomeAddress="funeralHomeAddress"funeralDuration="funeralDuration"processionDateTime="processionDateTime"burialSiteInfo="burialSiteInfo"chiefMourners="chiefMourners"templateKeyword="templateKeyword"
```
- funeralpython
```
 http :8088/pythons id="id"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
