mvn clean verify -X &&
mvn sonar:sonar -Dsonar.host.url=http://localhost:9001 -Dsonar.projectKey=offnance -Dsonar.projectName=offnance -Dsonar.sources=src/main/java -Dsonar.sourceEncoding=UTF-8 -Dsonar.exclusions='target/**' -Dsonar.java.binaries=target
