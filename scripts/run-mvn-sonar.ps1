# configuration
# https://www.baeldung.com/sonarqube-jacoco-code-coverage

# não precisa passar parametros para o sonar, pois já se encontram configurados no pom.xml e variável de ambiente com o token SONAR_TOKEN
mvn clean verify sonar:sonar
