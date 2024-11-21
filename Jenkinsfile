pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/RaphiSmadja/takima-voyage.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_HOST_URL = 'http://your-sonarqube-server:9000'
                SONAR_AUTH_TOKEN = credentials('sonar-token')
            }
            steps {
                sh '''
                mvn sonar:sonar \
                  -Dsonar.projectKey=your-project-key \
                  -Dsonar.host.url=$SONAR_HOST_URL \
                  -Dsonar.login=$SONAR_AUTH_TOKEN
                '''
            }
        }

        stage('Deploy (optional)') {
            steps {
                echo 'Déploiement à configurer DOCKER COMPOSE'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
    }
}
